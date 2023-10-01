package pl.ptag.ttyd.ttydbe.rest.nl2sql;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ptag.ttyd.ttydbe.app.nl2sql.Nl2SqlService;
import pl.ptag.ttyd.ttydbe.rest.nl2sql.dto.NL2SQLRequest;
import pl.ptag.ttyd.ttydbe.rest.nl2sql.dto.NL2SQLResponse;

@RestController
@RequestMapping("/nl-2-sql")
@RequiredArgsConstructor
public class NL2SQLRestController {

    @NonNull
    private final Nl2SqlService nl2SqlService;

    @PostMapping
    @NonNull
    public NL2SQLResponse ml2sql(
        @NonNull @RequestBody NL2SQLRequest nl2SQLRequest,
        @Nullable @RequestParam(value = "conversationId", required = false) String conversationId
    ){
        val response = nl2SqlService.nl2Sql(conversationId, nl2SQLRequest.getNlQueryText());
        return NL2SQLResponse.builder()
            .sqlQueryText(response.getSqlQuery())
            .conversationId(response.getConversationId())
            .build();
    }
}
