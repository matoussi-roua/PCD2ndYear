package PCD.BACKEND.RECRAFTMARKET.service.user;

import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserEntityService {
    public ResponseEntity<Object> fetchUserByUsername(final String username);
    public ResponseEntity<Object> fetchUserEntityById(UUID idUserEntity);

    ResponseEntity<Object> fetchAllUserEntity();

    UserEntityDTO mapToDTOItem(UserEntity user);

    List<UserEntityDTO> mapToDTOList(List<UserEntity> users);

    public ResponseEntity<Object> updateUserEntity(UUID  idUserEntity,UserEntity userUpdated);
    public List<UserEntityDTO> getAllUserEntity();


    public ResponseEntity<Object> addImageToUser(UUID UserId, @NotNull MultipartFile image) throws IOException;
    public ResponseEntity<Object> removeImageFromUser(UUID UserId ) throws IOException;
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID UserId) throws IOException;
    public ResponseEntity<Object> addProductToUser(UUID userId, Product product);

    public ResponseEntity<Object> deleteProductFromUser(UUID userId, Long productId);

    public ResponseEntity<Object> getAllProductsOfUser(UUID userId);


    UserEntity getUserByUsername(String username);
}
