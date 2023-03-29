package seeNear.seeNear_BE.domain.StatusCheck.domain;

import lombok.ToString;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private boolean done;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Chat chat;

}
