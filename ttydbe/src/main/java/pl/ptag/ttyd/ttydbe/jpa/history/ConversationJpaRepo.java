package pl.ptag.ttyd.ttydbe.jpa.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationJpaRepo extends JpaRepository<Conversation, UUID> {
}
