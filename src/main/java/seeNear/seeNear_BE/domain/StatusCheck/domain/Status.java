package seeNear.seeNear_BE.domain.StatusCheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import javax.persistence.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@JsonIgnoreProperties({"chat"})
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private boolean done;

    @Column(name = "chat_id")
    private int chatId;
    @CreatedDate
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Chat chat;

    public Status(String type, boolean done, int chatId) {
        this.type = type;
        this.done = done;
        this.chatId = chatId;
    }

    public String getCreatedAt() {
        return this.createdAt.toString();
    }
}
