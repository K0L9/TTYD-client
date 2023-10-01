package pl.ptag.ttyd.ttydbe.app.target_query;

import lombok.NonNull;

import java.util.List;

public interface TargetDbQueryService {

    @NonNull ShortResult getShortResult(@NonNull String sqlQuery);

    interface ShortResult {
        @NonNull List<String> getColumns();
        @NonNull List<List<String>> getRows();
        boolean isComplete();
    }

}
