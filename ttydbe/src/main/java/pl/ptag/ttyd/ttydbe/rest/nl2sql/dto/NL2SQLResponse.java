package pl.ptag.ttyd.ttydbe.rest.nl2sql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Value
@Jacksonized
@AllArgsConstructor(staticName = "of")

public class NL2SQLResponse {
    @NonNull String sqlQueryText;
}
