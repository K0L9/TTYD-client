package pl.ptag.ttyd.ttydbe.rest.target_query;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ptag.ttyd.ttydbe.app.target_query.TargetDbQueryService;
import pl.ptag.ttyd.ttydbe.rest.target_query.dto.QueryRequestDTO;
import pl.ptag.ttyd.ttydbe.rest.target_query.dto.QueryResultDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("target-query")
@RequiredArgsConstructor
public class TargetQueryRestController {


    @NonNull
    private final TargetDbQueryService targetDbQueryService;

    @PostMapping("/as-json")
    public @NonNull QueryResultDTO asJson(@NonNull @RequestBody QueryRequestDTO queryRequest){
        val result = targetDbQueryService.getShortResult(queryRequest.getSqlQuery());
        return QueryResultDTO.builder()
            .query(queryRequest.getSqlQuery())
            .columns(result.getColumns())
            .complete(result.isComplete())
            .result(result.getRows())
            .build();
    }

    @PostMapping(value = "/as-csv", produces = "text/csv; charset=UTF-8")
    public @NonNull ResponseEntity<Resource> asCsv(@NonNull @RequestBody QueryRequestDTO queryRequest){
        val tmpFile = targetDbQueryService.getCSVResult(queryRequest.getSqlQuery());
        val fileSize = tmpFile.length();
        try {
            val autoRemovingIs = new InputStream() {

                final InputStream tmpIs = new FileInputStream(tmpFile);

                @Override
                public int read() throws IOException {
                    return tmpIs.read();
                }

                @Override
                public void close() throws IOException {
                    tmpIs.close();
                    tmpFile.delete();
                }
            };
            val resource = new InputStreamResource(autoRemovingIs);
            return ResponseEntity.ok()
                .contentLength(fileSize)
                .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
