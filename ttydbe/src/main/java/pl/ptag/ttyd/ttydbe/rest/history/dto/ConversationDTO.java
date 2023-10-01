package pl.ptag.ttyd.ttydbe.rest.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Jacksonized
public class ConversationDTO {

    @NonNull String conversationId;
    @NonNull Instant timestamp;

    @NonNull @Singular
    List<ExchangeDTO> exchanges;

    @Value
    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Jacksonized
    public static class ExchangeDTO {
        @NonNull String nlQuery;
        @NonNull String sqlQuery;

    }


}
