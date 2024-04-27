package PCD.BACKEND.RECRAFTMARKET.dto.chat;

import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.MessageStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageDTO {
    private Long id;
    private String content;
    private MessageStatus status;
    private UUID senderId;
    private UUID recipientId;
    private LocalDateTime createdAt;

}