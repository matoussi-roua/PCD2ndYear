package PCD.BACKEND.RECRAFTMARKET.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor

public class chatRoomDTO {
    private Long id;
    private UUID senderId;
    private UUID recipientId;

}
