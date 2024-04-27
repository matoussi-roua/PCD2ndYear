package PCD.BACKEND.RECRAFTMARKET.controller.chatMsg;

import PCD.BACKEND.RECRAFTMARKET.dto.chat.chatRoomDTO;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.service.chat.chatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chatrooms")
public class chatRoomController {
    @Autowired
    private chatRoomService chatRoomService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<chatRoom>> getChatRoomsByUserId(@PathVariable UUID userId) {
        List<chatRoom> chatRooms = chatRoomService.findChatRoomsByUserId(userId);
        return ResponseEntity.ok(chatRooms);
        //return chatRoomService.fct(prm);
        //service findchat : return responseEntity
    }
    //chatRoomMapper dto/chat../
    @GetMapping("/{roomId}")
    public ResponseEntity<chatRoomDTO> getChatRoomById(@PathVariable Long roomId) {
        chatRoomDTO chatRoom = chatRoomService.getChatRoomById(roomId);
        if (chatRoom != null) {
            return ResponseEntity.ok(chatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
