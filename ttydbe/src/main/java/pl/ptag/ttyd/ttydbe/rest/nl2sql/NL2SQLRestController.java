package pl.ptag.ttyd.ttydbe.rest.nl2sql;

import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ptag.ttyd.ttydbe.rest.nl2sql.dto.NL2SQLRequest;
import pl.ptag.ttyd.ttydbe.rest.nl2sql.dto.NL2SQLResponse;

@RestController
@RequestMapping("/nl-2-sql")
public class NL2SQLRestController {

    @PostMapping
    @NonNull
    public NL2SQLResponse ml2sql(@NonNull @RequestBody NL2SQLRequest nl2SQLRequest){
        return NL2SQLResponse.builder()
            .sqlQueryText("select * from qqrq")
            .build();
    }
}
