package pl.ptag.ttyd.ttydbe.app.nl2sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import pl.ptag.ttyd.ttydbe.app.history.HistoryService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Nl2SqlServiceImpl implements Nl2SqlService {

    @NonNull
    private final ObjectMapper objectMapper;

    @NonNull
    private final HistoryService historyService;


    @NonNull
    private List<String> getConversationAsList(@Nullable String conversationId, @NonNull String nlQuery){
        val prevExchanges = Optional.ofNullable(conversationId).stream()
            .map(historyService::getConversationById)
            .flatMap(Optional::stream)
            .map(HistoryService.Conversation::getExchanges)
            .flatMap(Collection::stream)
            .flatMap(exchange -> Stream.of(exchange.getNlQuery(), exchange.getSqlQuery()));
        return Stream.concat(prevExchanges, Stream.of(nlQuery)).toList();
    }

    private @NonNull String nl2Sql(@NonNull List<String> convHistory){
        File inputFile = null;
        File outputFile = null;
        try {
            inputFile = Files.createTempFile(UUID.randomUUID().toString(), "txt", PosixFilePermissions.asFileAttribute(
                EnumSet.of(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)
            )).toFile();
            outputFile = Files.createTempFile(UUID.randomUUID().toString(), "sql", PosixFilePermissions.asFileAttribute(
                EnumSet.of(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)
            )).toFile();
            FileUtils.writeStringToFile(inputFile, objectMapper.writeValueAsString(convHistory), StandardCharsets.UTF_8);
            val cmd = String.format("%1$s -c 'import %2$s; import sys; import json; print(%2$s.%3$s(json.loads(sys.stdin.read()),\"%6$s\"))' <%4$s >%5$s",
                getPythonCmd(),
                getModelPyFile(),
                getNl2SqlPyFuncName(),
                inputFile,
                outputFile,
                getTargetDbFilePath()
            );
            val process = Runtime.getRuntime().exec(
                List.of("/bin/sh", "-c", cmd).toArray(new String[0]),
                null,
                new File(getModelWorkDir())
            );
            val exitVal = process.waitFor();
            if (exitVal != 0) {
                throw new IllegalStateException("Model failed.");
            }
            return FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
        } catch (IOException x) {
            throw new IllegalStateException(x);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        } finally {
            Optional.ofNullable(inputFile).ifPresent(File::delete);
            Optional.ofNullable(outputFile).ifPresent(File::delete);
        }

    }

    @Override
    public @NonNull Response nl2Sql(@Nullable String conversationId, @NonNull String nlQuery) {
        val sqlQuery = nl2Sql(getConversationAsList(conversationId, nlQuery));
        if (conversationId == null) {
            conversationId = historyService.createConversation(nlQuery, sqlQuery);
        } else {
            historyService.addExchange(conversationId, nlQuery, sqlQuery);
        }
        return Response.of(conversationId, sqlQuery);
    }


    @NonNull
    private String getModelWorkDir() {
        return "model";
    }

    @NonNull
    private String getModelPyFile() {
        return "test_model";
    }

    @NonNull
    private String getNl2SqlPyFuncName() {
        return "main";
    }

    @NonNull
    private String getPythonCmd() {
        return "python3";
    }

    @NonNull
    private String getTargetDbFilePath(){
        return "/target.db";
    }

}
