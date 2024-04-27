package PCD.BACKEND.RECRAFTMARKET.service.chat;


import PCD.BACKEND.RECRAFTMARKET.dto.chat.chatRoomDTO;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class chatRoomServiceImpl implements chatRoomService{
    @Autowired
    private ChatRoomRepository chatRoomRepo;

    @Override
    public chatRoom save(chatRoom chatRoom) {
        return chatRoomRepo.save(chatRoom);
    }
    @Override
    public chatRoom findBySenderAndRecipient(UserEntity user1, UserEntity user2) {
        // Find a chat room where either user1 or user2 is the sender and either user1 or user2 is the recipient
        chatRoom chatRoom1 = chatRoomRepo.findBySenderAndRecipient(user1, user2);
        chatRoom chatRoom2 = chatRoomRepo.findBySenderAndRecipient(user2, user1);

        // Return chatRoom1 if found, otherwise return chatRoom2
        return chatRoom1 != null ? chatRoom1 : chatRoom2;
    }

    @Override
    public List<chatRoom> findChatRoomsByUserId(UUID userId) {
        // Implement the logic to find chat rooms by user ID
        return chatRoomRepo.findChatRoomsByUserId(userId);
    }

    @Override
    public chatRoomDTO getChatRoomById(Long roomId) {
        chatRoom chatRoom = chatRoomRepo.findById(roomId).orElse(null);
        if (chatRoom != null) {
            return new chatRoomDTO(chatRoom.getId(), chatRoom.getSender().getId(), chatRoom.getRecipient().getId());
        }
        return null;
    }
}

