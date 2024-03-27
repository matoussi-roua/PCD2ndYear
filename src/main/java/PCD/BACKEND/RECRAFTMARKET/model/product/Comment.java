package PCD.BACKEND.RECRAFTMARKET.model.product;

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
    private String userName;
    private String content;
    private Date targetDate;
    @ManyToOne
    @JoinColumn(name="idProduct")
    private Product commentProduct;

}
