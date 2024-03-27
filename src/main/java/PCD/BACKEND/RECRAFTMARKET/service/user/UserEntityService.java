package PCD.BACKEND.RECRAFTMARKET.service.user;

import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserEntityService {
    public ResponseEntity<Object> getUserEntityByUsername(final String username);
    public ResponseEntity<Object> getUserEntityById(UUID idUserEntity);
    public void deleteUserEntityById(UUID  idUserEntity ) ;
    UserEntityDTO updateUserEntity(UUID  idUserEntity,UserEntity userEntity);
    public List<UserEntityDTO> getAllUserEntity();
    
}
