package PCD.BACKEND.RECRAFTMARKET.service.chat;

import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatMessage;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import java.util.List;

public interface chatMessageService {
    void sendMessage(chatRoom chatRoom, String content, UserEntity sender, UserEntity recipient);
    List<chatMessage> getMessagesForChatRoom(Long chatRoomId);
}
