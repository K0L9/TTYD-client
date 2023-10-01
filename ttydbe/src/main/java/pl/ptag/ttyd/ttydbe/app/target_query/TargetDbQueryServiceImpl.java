package pl.ptag.ttyd.ttydbe.app.target_query;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteConfig;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
                .mapToObj(i -> excGuard(() -> metadata.getColumnLabel(i)))
                .toList();
            val complete = true;
            for(int rowNo = 0; rowNo < MAX_ROWS_IN_SHORT_RESULT && rs.next(); rowNo++){

            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return null;
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
