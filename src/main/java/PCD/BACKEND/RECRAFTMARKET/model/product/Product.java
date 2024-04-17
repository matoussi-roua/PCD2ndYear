package PCD.BACKEND.RECRAFTMARKET.model.product;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idProduct;
    private String title;
    private String description;
    private String price;
    private String category;
    private Date targetDate;
    // once you click on shop now button
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long shopPoints = 0L;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //totalPoints = likesPoints + wishListPoints + shopPoints + commentsPoints
    private Long points = 0L;
    private boolean isDone; // the product is sold or not
    private boolean sold;
    private String materials;
    private String Status;
    // @ElementCollection(fetch = FetchType.LAZY)

    @OneToMany(mappedBy = "commentProduct")
    @JsonIgnore
    private List<Comment> comments;


    @OneToMany(mappedBy = "product")
   // @JsonIgnore
    private List<FileData> filesProduct;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private UserEntity publisher;


    @ManyToMany(mappedBy = "LikedProducts")
    @JsonIgnore
    private List<UserEntity> loversList;

    @ManyToMany(mappedBy = "favouriteProducts")
    @JsonIgnore
    private List<UserEntity> wantersList;
}