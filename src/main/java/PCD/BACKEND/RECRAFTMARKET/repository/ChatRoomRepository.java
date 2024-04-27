package PCD.BACKEND.RECRAFTMARKET.repository;

import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatRoom;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ChatRoomRepository extends JpaRepository<chatRoom,Long> {
    chatRoom findBySenderAndRecipient(UserEntity sender, UserEntity recipient);
    @Query("SELECT cr FROM chatRoom cr WHERE cr.sender.id = :userId OR cr.recipient.id = :userId")
    List<chatRoom> findChatRoomsByUserId(UUID userId);
}
