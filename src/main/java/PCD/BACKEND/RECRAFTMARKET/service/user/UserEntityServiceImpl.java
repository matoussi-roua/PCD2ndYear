package PCD.BACKEND.RECRAFTMARKET.service.user;


import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTOMapper;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.ResponseHandler;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service
public class UserEntityServiceImpl implements UserEntityService{


    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;




     public ResponseEntity<Object> getUserEntityByUsername(final String username)
     {
         final UserEntity user= userEntityRepository.fetchUserWithUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("The user with username : %s could not be found.", username)));
         final UserEntityDTO userdto=userEntityDTOMapper.apply(user);
         return ResponseHandler.generateResponse(userdto , HttpStatus.OK) ;
     }
/* */
    @Override
    public ResponseEntity<Object> getUserEntityById(UUID idUserEntity) {
        final UserEntity currentUser = getUserEntityById(idUserEntity);
        final UserEntityDTO user = userEntityDTOMapper.apply(currentUser);
        return ResponseHandler.generateResponse(user , HttpStatus.OK);

    }

    @Override
    public void deleteUserEntityById(UUID idUserEntity) {
        userEntityRepository.deleteById(idUserEntity);

    }

    @Override
    public UserEntityDTO updateUserEntity(UUID idUserEntity, UserEntity userEntity) {
        return null;
    }

    @Override
    public List<UserEntityDTO> getAllUserEntity() {
        return null;
    }


}
