package seeNear.seeNear_BE.domain.Chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import seeNear.seeNear_BE.domain.NamedEntity.domain.NamedEntity;
import seeNear.seeNear_BE.domain.StatusCheck.domain.Status;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int elderlyId;

    private String content;

    private Timestamp createdAt;
    private boolean userSend;
    private String type;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Status> statuses = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NamedEntity> namedEntities = new ArrayList<>();

    public String getCreatedAt() {
        return createdAt.toString();
    }
}
