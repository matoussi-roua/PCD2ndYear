package PCD.BACKEND.RECRAFTMARKET.model.product;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idProduct;
    private String title;
    private String description;
    private String price;
    private String category;
    private Date targetDate;
    private Long shopPoints;
    private Long points; //number of likes number of comments number of wish list number + shopPoints
    private boolean isDone; // the product is sold or not
    private String materials;

   // @ElementCollection(fetch = FetchType.LAZY)

    @OneToMany(mappedBy = "commentProduct")
    @JsonIgnore
    private List<Comment> comments ;


    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<FileData> filesProduct;

    @ManyToOne
    @JoinColumn(name="idUser")
    @JsonIgnore
    private UserEntity publisher;


    @ManyToMany(mappedBy = "LikedProducts")
    @JsonIgnore
    private List<UserEntity> loversList;

    @ManyToMany(mappedBy = "favouriteProducts")
    @JsonIgnore
    private List<UserEntity> wantersList ;
/*
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idPost ^ (idPost >>> 32));
        return result;
        }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
        if (idPost != other.idPost)
            return false;
        return true;
    }*/
    /*@ManyToMany(mappedBy = "listfavourite")
    Set<Users> listusers = new HashSet<>();

    @ManyToMany(mappedBy = "shoppinglist")
    Set<Users> listusersforshopping = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idImage", referencedColumnName = "idImage")
    private ImageProduct imageproduct;*/
}
