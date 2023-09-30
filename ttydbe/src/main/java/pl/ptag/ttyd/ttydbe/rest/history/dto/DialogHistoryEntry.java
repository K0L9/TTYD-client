package pl.ptag.ttyd.ttydbe.rest.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Jacksonized
public class DialogHistoryEntry {


}
