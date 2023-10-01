package pl.ptag.ttyd.ttydbe.rest.target_query.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class QueryRequestDTO {
    @NonNull
    String sqlQuery;
}
