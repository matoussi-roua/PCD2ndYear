package PCD.BACKEND.RECRAFTMARKET.service.chat;

import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.MessageStatus;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatMessage;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class chatMessageServiceImpl implements chatMessageService{
    @Autowired
    private ChatMessageRepository chatMessageRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private chatRoomService chatRoomService;

    @Override
    @Transactional
    public void sendMessage(chatRoom chatRoom, String content, UserEntity sender, UserEntity recipient) {
        // Check if a chat room already exists between sender and recipient
        chatRoom existingChatRoom = chatRoomService.findBySenderAndRecipient(sender, recipient);

        if (existingChatRoom == null) {
            // If no existing chat room, create a new one
            chatRoom = chatRoomService.save(chatRoom);
        } else {
            // Use the existing chat room
            chatRoom = existingChatRoom;
        }

        chatMessage chatMessage = new chatMessage();
        chatMessage.setContent(content);
        chatMessage.setSender(sender);
        chatMessage.setRecipient(recipient);
        chatMessage.setStatus(MessageStatus.DELIVERED); // delivered = sent?
        chatMessage.setCreatedAt(LocalDateTime.now());
        chatMessage.setChatRoom(chatRoom);

        chatMessageRepo.save(chatMessage);

        // Notify recipient about the new message
        messagingTemplate.convertAndSendToUser(recipient.getUsername(), "/topic/messages", chatMessage);

        // Notify sender (optional)
        messagingTemplate.convertAndSendToUser(sender.getUsername(), "/topic/messages", chatMessage);
    }

    @Override
    public List<chatMessage> getMessagesForChatRoom(Long chatRoomId) {
        return chatMessageRepo.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId);
    }
}
