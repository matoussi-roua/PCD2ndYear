package PCD.BACKEND.RECRAFTMARKET.model.chatRoom;

import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="chatMessage")
public class chatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonBackReference
    private UserEntity recipient;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private chatRoom chatRoom;

}
