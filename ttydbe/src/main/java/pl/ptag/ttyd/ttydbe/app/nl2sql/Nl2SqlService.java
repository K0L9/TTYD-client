package pl.ptag.ttyd.ttydbe.app.nl2sql;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public interface Nl2SqlService {

    @NonNull Response nl2Sql(@Nullable String conversationId, @NonNull String sql);


    @Value
    @Builder(toBuilder = true)
    @RequiredArgsConstructor(staticName = "of")
    class Response {
        String conversationId;
        String sqlQuery;
    }

}
