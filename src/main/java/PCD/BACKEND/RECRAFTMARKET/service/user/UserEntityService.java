package PCD.BACKEND.RECRAFTMARKET.service.user;

import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserEntityService {
    public ResponseEntity<Object> getUserEntityByUsername(final String username);
    public ResponseEntity<Object> getUserEntityById(UUID idUserEntity);

    UserEntityDTO mapToDTOItem(UserEntity user);

    List<UserEntityDTO> mapToDTOList(List<UserEntity> users);

    public void deleteUserEntityById(UUID  idUserEntity ) ;
    public ResponseEntity<Object> updateUserEntity(UUID  idUserEntity,UserEntity userUpdated);
    public ResponseEntity<Object> getAllUserEntity();

    void deleteAllUserEntity(List<UserEntity> users);
    public ResponseEntity<Object> addImageToUser(UUID UserId, @NotNull MultipartFile image) throws IOException;
    public ResponseEntity<Object> removeImageFromUser(UUID UserId, long imageId) throws IOException;
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID UserId) throws IOException;

}
