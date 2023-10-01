package pl.ptag.ttyd.ttydbe.app.target_query;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
class TargetDbQueryServiceImpl implements TargetDbQueryService {

    private static final int MAX_ROWS_IN_SHORT_RESULT = 5;

    @Override
    public @NonNull ShortResult getShortResult(@NonNull String sqlQuery) {
        val config = new SQLiteConfig();
        config.setReadOnly(true);
        try(
            val conn = DriverManager.getConnection("jdbc:sqlite:/target.db", config.toProperties());
            val statement = conn.prepareStatement(sqlQuery);
            val rs = statement.executeQuery()
        ){
            val metadata = statement.getMetaData();
            val columns = IntStream.range(0, metadata.getColumnCount())
                .mapToObj(i -> excGuard(() -> metadata.getColumnLabel(i+1)))
                .toList();
            val rows = new ArrayList<List<String>>();
            var complete = true;
            for(int rowNo = 0; rowNo <= MAX_ROWS_IN_SHORT_RESULT && rs.next(); rowNo++){
                if (rowNo < MAX_ROWS_IN_SHORT_RESULT) {
                    val row = IntStream.range(0, columns.size())
                        .mapToObj(i -> excGuard(() -> rs.getString(i+1)))
                        .toList();
                    rows.add(row);
                } else {
                    complete = false;
                }
            }
            return aShortResult(columns, Collections.unmodifiableList(rows), complete);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @NonNull File createTmpFile(){
        try {
            return Files.createTempFile(UUID.randomUUID().toString(), "csv", PosixFilePermissions.asFileAttribute(
                EnumSet.of(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)
            )).toFile();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public @NonNull File getCSVResult(@NonNull String sqlQuery) {
        val config = new SQLiteConfig();
        config.setReadOnly(true);
        try(
            val conn = DriverManager.getConnection("jdbc:sqlite:/target.db", config.toProperties());
            val statement = conn.prepareStatement(sqlQuery);
            val rs = statement.executeQuery()
        ){
            val metadata = statement.getMetaData();
            val columns = IntStream.range(0, metadata.getColumnCount())
                .mapToObj(i -> excGuard(() -> metadata.getColumnLabel(i+1)))
                .toList();
            val tmpFile = createTmpFile();
            try (val writer = new FileWriter(tmpFile, StandardCharsets.UTF_8)) {
                writeCsvRow(writer, columns);
                while (rs.next()) {
                    val row = IntStream.range(0, columns.size())
                        .mapToObj(i -> excGuard(() -> rs.getString(i + 1)))
                        .toList();
                    writeCsvRow(writer, row);
                }
            }
            return tmpFile;
        } catch (SQLException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeCsvRow(@NonNull Writer writer, @NonNull List<String> row){
        val rowStr = row.stream()
            .map(s -> String.format("\"%s\"", s.replace("\"", "\"\"")))
            .collect(Collectors.joining(",", "", "\r\n"));
        try {
            writer.write(rowStr);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    private static <T> T excGuard(@NonNull ThrowingSupplier<T> supp){
        try {
            return supp.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    @NonNull
    private String getTargetDbPath(){
        return "/target.db";
    }

    @NonNull
    private ShortResult aShortResult(@NonNull List<String> columns, @NonNull List<List<String>> rows, boolean complete){
        return new ShortResult() {
            @Override
            public @NonNull List<String> getColumns() {
                return columns;
            }

            @Override
            public @NonNull List<List<String>> getRows() {
                return rows;
            }

            @Override
            public boolean isComplete() {
                return complete;
            }
        };
    }

}
