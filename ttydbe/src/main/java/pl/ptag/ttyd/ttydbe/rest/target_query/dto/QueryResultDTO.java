package pl.ptag.ttyd.ttydbe.rest.target_query.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class QueryResultDTO {

    @NonNull
    String query;

    @NonNull @Singular
    List<String> columns;

    @NonNull
    List<List<String>> result;

    boolean complete;
}
