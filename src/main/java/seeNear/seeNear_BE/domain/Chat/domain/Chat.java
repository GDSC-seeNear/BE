package seeNear.seeNear_BE.domain.Chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import seeNear.seeNear_BE.domain.NamedEntity.domain.NamedEntity;
import seeNear.seeNear_BE.domain.StatusCheck.domain.Status;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@JsonIgnoreProperties({"statuses", "namedEntities"})
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int elderlyId;

    private String content;
    @CreatedDate
    private Timestamp createdAt;
    private boolean userSend;
    private String type;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Status> statuses = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NamedEntity> namedEntities = new ArrayList<>();

    public Chat(int elderlyId, String content, boolean userSend, String type) {
        this.elderlyId = elderlyId;
        this.content = content;
        this.userSend = userSend;
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }
}
