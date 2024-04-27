package PCD.BACKEND.RECRAFTMARKET.service.chat;


import PCD.BACKEND.RECRAFTMARKET.dto.chat.chatRoomDTO;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface chatRoomService {
    chatRoom save(chatRoom chatRoom);
    chatRoom findBySenderAndRecipient(UserEntity sender, UserEntity recipient);
    List<chatRoom> findChatRoomsByUserId(UUID userId);
    chatRoomDTO getChatRoomById(Long roomId);

}
