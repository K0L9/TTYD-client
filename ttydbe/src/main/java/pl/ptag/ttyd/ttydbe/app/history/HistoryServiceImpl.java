package pl.ptag.ttyd.ttydbe.app.history;


import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.ptag.ttyd.ttydbe.jpa.history.Conversation;
import pl.ptag.ttyd.ttydbe.jpa.history.ConversationJpaRepo;
import pl.ptag.ttyd.ttydbe.jpa.history.Exchange;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {


    @NonNull
    private ConversationJpaRepo conversationJpaRepo;

    @Override
    @Transactional
    public @NonNull List<Conversation> getConversations() {
        return conversationJpaRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
            .stream()
            .map(HistoryServiceImpl::convertToConversation)
            .toList();
    }

    private static @NonNull Conversation convertToConversation(@NonNull pl.ptag.ttyd.ttydbe.jpa.history.Conversation convEntity) {
        return Conversation.builder()
            .conversationId(convEntity.getId().toString())
            .timestamp(convEntity.getTimestamp())
            .exchanges(convEntity.getExchanges().stream()
                .sorted(Comparator.comparing(Exchange::getTimestamp))
                .map(exchangeEntity -> Conversation.Exchange.of(exchangeEntity.getNlQuery(), exchangeEntity.getSqlQuery()))
                .toList()
            )
            .build();
    }

    @Override
    public @NonNull Optional<Conversation> getConversationById(@NonNull String conversationId) {
        final UUID id;
        try {
            id = UUID.fromString(conversationId);
        } catch (IllegalArgumentException ign) {
            return Optional.empty();
        }
        return conversationJpaRepo.findById(id).map(HistoryServiceImpl::convertToConversation);
    }

    @Override
    public @NonNull String createConversation(@NonNull String nlQuery, @NonNull String sqlQuery) {
        val now = Instant.now();
        val conversation = new pl.ptag.ttyd.ttydbe.jpa.history.Conversation().setTimestamp(now);
        val exchange = new Exchange().setConversation(conversation)
            .setNlQuery(nlQuery)
            .setSqlQuery(sqlQuery)
            .setTimestamp(now);
        conversation.getExchanges().add(exchange);
        return conversationJpaRepo.save(conversation).getId().toString();
    }

    @Override
    @Transactional
    public void removeConversation(@NonNull String conversationId) {
        final UUID id;
        try {
            id = UUID.fromString(conversationId);
        } catch (IllegalArgumentException ign) {
            return;
        }
        conversationJpaRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void removeAllConversations() {
        conversationJpaRepo.deleteAll();
    }

    @Override
    @Transactional
    public void addExchange(@NonNull String conversationId, @NonNull String nlQuery, @NonNull String sqlQuery) {
        val now = Instant.now();
        val conversation = conversationJpaRepo.findById(UUID.fromString(conversationId)).orElseThrow();
        val exchange = new Exchange()
            .setTimestamp(now)
            .setNlQuery(nlQuery)
            .setSqlQuery(sqlQuery)
            .setConversation(conversation);
        conversation.getExchanges().add(exchange);
        conversation.setTimestamp(now);
        conversationJpaRepo.save(conversation);
    }
}
