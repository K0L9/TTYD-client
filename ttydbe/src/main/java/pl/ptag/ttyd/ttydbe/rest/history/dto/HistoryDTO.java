package pl.ptag.ttyd.ttydbe.rest.history.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Jacksonized
public class HistoryDTO {
    @NonNull @Singular
    List<ConversationDTO> conversations;
}
