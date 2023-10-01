package pl.ptag.ttyd.ttydbe.rest.history;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ptag.ttyd.ttydbe.app.history.HistoryService;
import pl.ptag.ttyd.ttydbe.rest.history.dto.ConversationDTO;
import pl.ptag.ttyd.ttydbe.rest.history.dto.HistoryDTO;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryRestController {


    @NonNull
    private final HistoryService historyService;

    @NonNull
    @GetMapping
    public HistoryDTO getHistory(){
        val conversations = historyService.getConversations().stream()
            .map(conversation -> ConversationDTO.builder()
                .conversationId(conversation.getConversationId())
                .timestamp(conversation.getTimestamp())
                .exchanges(conversation.getExchanges().stream()
                    .map(exchange -> ConversationDTO.ExchangeDTO.builder()
                        .nlQuery(exchange.getNlQuery())
                        .sqlQuery(exchange.getSqlQuery())
                        .build()
                    ).toList()
                )
                .build()
            ).toList();
        return HistoryDTO.of(conversations);
    }

    @DeleteMapping
    public void clearHistory(){
        historyService.removeAllConversations();
    }

    @DeleteMapping("/{conversationId}")
    public void deleteConversation(@NonNull @PathVariable("conversationId") String conversationId){
        historyService.removeConversation(conversationId);
    }

}
