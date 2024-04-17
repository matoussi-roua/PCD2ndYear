package PCD.BACKEND.RECRAFTMARKET.model.product;

import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
//you're telling the ORM framework that the Comment field should be mapped as if Post's attribute
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;

    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date targetDate;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idProduct")
    private Product commentProduct;

    @ManyToOne  //(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity commenter;
    @PrePersist
    protected void onCreate() {
        this.targetDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.targetDate = new Date();
    }


}
