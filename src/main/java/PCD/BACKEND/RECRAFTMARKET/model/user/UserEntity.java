package PCD.BACKEND.RECRAFTMARKET.model.user;




import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id",unique = true,nullable = false)
    private UUID id;

    @Column(name ="username" , unique = true , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "phone")
    private Number phonenumber;

    @Column(name = "address")
    private String address;
    @Column(name = "points")
    private Number points; // number of products with their likes and comments and shop button

    @OneToOne
    private Role role ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFileUser", referencedColumnName = "id")
    private FileDataUser fileUser;






    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<Product> productsList ;


    @ManyToMany
    @JoinTable(name = "likesList", joinColumns = @JoinColumn(name = "idUserEntity"), inverseJoinColumns = @JoinColumn(name = "idProduct"))
    @JsonIgnore
    private List<Product> LikedProducts ;

    @ManyToMany
    @JoinTable(name = "favouriteList", joinColumns = @JoinColumn(name = "idUserEntity"), inverseJoinColumns = @JoinColumn(name = "idProduct"))
    @JsonIgnore
    private List<Product> favouriteProducts ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
