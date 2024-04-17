package PCD.BACKEND.RECRAFTMARKET.model.file;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User_files")
public class FileDataUser {

        @SequenceGenerator(
                name = "file_user_sequence",
                sequenceName = "file_user_sequence",
                allocationSize = 1
        )

        @Id
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "file_user_sequence"
        )
        @Column(name = "id")
        private long id;
        @Column(name ="name")
        private String name;
        @Column(name ="type")
        private String type;
        @Column(name ="file_path")
        private String filePath;


        @OneToOne
        @JsonIgnore
        @JoinColumn(name = "idUser", referencedColumnName = "id")
        private UserEntity userFile;

}
