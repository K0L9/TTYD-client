package pl.ptag.ttyd.ttydbe.rest.target_query;

import lombok.NonNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ptag.ttyd.ttydbe.rest.target_query.dto.QueryRequestDTO;
import pl.ptag.ttyd.ttydbe.rest.target_query.dto.QueryResultDTO;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("target-query")
public class TargetQueryRestController {

    @PostMapping("/as-json")
    public @NonNull QueryResultDTO asJson(@NonNull @RequestBody QueryRequestDTO queryRequest){
        return QueryResultDTO.builder()
            .query(queryRequest.getSqlQuery())
            .column("P_96")
            .column("P_97")
            .result(List.of(
                List.of("132.00", "133.00"),
                List.of("132.50", "145.66")
            ))
            .complete(true)
            .build();
    }

    @PostMapping(value = "/as-csv", produces = "text/csv; charset=UTF-8")
    public @NonNull Resource asCsv(@NonNull @RequestBody QueryRequestDTO queryRequest){
        return new ByteArrayResource(("\"P_96\",\"P_97\"\r\n\"132.00\",\"133.00\"\r\n\"132.50\",\"145.66\"\n").getBytes(StandardCharsets.UTF_8));
    }


}
