package pl.ptag.ttyd.ttydbe.jpa.history;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Conversation {

    @Id
    private UUID id = UUID.randomUUID();


    @Column
    private Instant timestamp = Instant.now();

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Exchange> exchanges = new ArrayList<>();
}
