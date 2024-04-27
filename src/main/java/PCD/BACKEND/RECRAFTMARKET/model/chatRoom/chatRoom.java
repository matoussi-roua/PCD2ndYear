package PCD.BACKEND.RECRAFTMARKET.model.chatRoom;

import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
@Builder
@Table(name="chatRoom")
public class chatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;
}
