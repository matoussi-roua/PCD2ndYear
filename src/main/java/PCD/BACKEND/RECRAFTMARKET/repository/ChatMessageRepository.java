gipackage PCD.BACKEND.RECRAFTMARKET.repository;

import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.MessageStatus;
import PCD.BACKEND.RECRAFTMARKET.model.chatRoom.chatMessage;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ChatMessageRepository extends JpaRepository<chatMessage, Long> {
    List<chatMessage> findByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);
}
