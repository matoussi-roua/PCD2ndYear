package PCD.BACKEND.RECRAFTMARKET.repository;

import PCD.BACKEND.RECRAFTMARKET.model.product.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
