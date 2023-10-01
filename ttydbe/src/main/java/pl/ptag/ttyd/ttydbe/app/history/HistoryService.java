package pl.ptag.ttyd.ttydbe.app.history;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface HistoryService {


    @NonNull List<Conversation> getConversations();

    @NonNull Optional<Conversation> getConversationById(@NonNull String convId);

    @NonNull String createConversation(@NonNull String nlQuery, @NonNull String sqlQuery);

    void removeConversation(@NonNull String conversationId);

    void removeAllConversations();

    void addExchange(@NonNull String conversationId, @NonNull String nlQuery, @NonNull String sqlQuery);



    @Value
    @RequiredArgsConstructor(staticName = "of")
    @Builder(toBuilder = true)
    class Conversation {
        String conversationId;
        Instant timestamp;
        List<Exchange> exchanges;

        @Value
        @RequiredArgsConstructor(staticName = "of")
        @Builder(toBuilder = true)
        public static class Exchange {
            String nlQuery;
            String sqlQuery;
        }
    }

}
