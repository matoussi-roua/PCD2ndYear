package PCD.BACKEND.RECRAFTMARKET.controller.chatMsg;

import PCD.BACKEND.RECRAFTMARKET.dto.chat.ChatMessageDTO;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatMessage;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.service.user.UserEntityService;
import PCD.BACKEND.RECRAFTMARKET.service.chat.chatMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {

    @Autowired
    private chatMessageService chatMessageService;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @MessageMapping("/messages/send")
    @SendTo("/topic/messages")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageDTO request) {
        // Log the received message
        System.out.println("Received message: Sender ID - " + request.getSenderId() + ", Recipient ID - " + request.getRecipientId() + ", Content - " + request.getContent());

        // Retrieve sender and recipient IDs from the request
        UUID senderId = request.getSenderId();
        UUID recipientId = request.getRecipientId();

        // Retrieve sender and recipient users from the database
        Optional<UserEntity> senderOptional = userEntityRepository.findById(senderId);
        Optional<UserEntity> recipientOptional = userEntityRepository.findById(recipientId);

        // Check if sender and recipient exist
        if (senderOptional.isEmpty() || recipientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender or recipient not found");
        }

        // Get sender and recipient from optionals
        UserEntity sender = senderOptional.get();
        UserEntity recipient = recipientOptional.get();

        // Create a new chat room
        chatRoom chatRoom = new chatRoom();
        chatRoom.setSender(sender);
        chatRoom.setRecipient(recipient);

        // Send the message using the chat message service
        chatMessageService.sendMessage(chatRoom, request.getContent(), sender, recipient);

        // Return success response
        return ResponseEntity.ok("Message sent successfully");
    }


    @GetMapping("/chat-room/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesForChatRoom(@PathVariable Long chatRoomId) {
        // Call the service method to fetch messages for the specified chat room
        List<chatMessage> messages = chatMessageService.getMessagesForChatRoom(chatRoomId);

        // Convert ChatMessage objects to ChatMessageDTO objects
        List<ChatMessageDTO> messageDTOs = messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(messageDTOs);
    }

    // Helper method to convert ChatMessage to ChatMessageDTO
    private ChatMessageDTO convertToDTO(chatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setStatus(message.getStatus());
        dto.setSenderId(message.getSender().getId());
        dto.setRecipientId(message.getRecipient().getId());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }


}