package pl.ptag.ttyd.ttydbe.jpa.history;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Exchange {

    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private Instant timestamp = Instant.now();

    @Column
    private String nlQuery;

    @Column
    private String sqlQuery;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

}
