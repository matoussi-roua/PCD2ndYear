package PCD.BACKEND.RECRAFTMARKET.service.user;

import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.model.product.Comment;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserEntityService {
    //////////////////////all about user /////////////////////////////////////////////////////////////
    public ResponseEntity<Object> fetchUserByUsername(final String username);
    public ResponseEntity<Object> fetchUserEntityById(UUID idUserEntity);

    ResponseEntity<Object> fetchAllUserEntity();

    UserEntityDTO mapToDTOItem(UserEntity user);

    List<UserEntityDTO> mapToDTOList(List<UserEntity> users);

    public ResponseEntity<Object> updateUserEntity(UserDetails userDetails,UUID  idUserEntity,UserEntity userUpdated);
    public List<UserEntityDTO> getAllUserEntity();


    public ResponseEntity<Object> addImageToUser(UserDetails userDetails,UUID UserId, @NotNull MultipartFile image) throws IOException;
    public ResponseEntity<Object> removeImageFromUser(UserDetails userDetails,UUID UserId ) throws IOException;
    public  ResponseEntity<byte[]> fetchImageFromUser(UUID UserId) throws IOException;

////////////////////// all about products ///////////////////////////////////////////////////
    public ResponseEntity<Object> addProductToUser(UserDetails userDetails, UUID userId, Product product);

    ResponseEntity<Object> addImageToProductUser(UserDetails userDetails,UUID userId, Long productId, @NotNull MultipartFile image) throws IOException;

    ResponseEntity<Object> updateProductUser(UserDetails userDetails,UUID userId, Long productId, Product productupdated);

    public ResponseEntity<Object> deleteProductFromUser(UserDetails userDetails,UUID userId, Long productId)throws IOException;

    public ResponseEntity<Object> getAllProductsOfUser(UUID userId);


    UserEntity getUserByUsername(String username);
//////////////////////////////Likes List//////////////////////////////////////////////////
    ResponseEntity<Object> getAllLikesListOfUser(UserDetails userDetails, UUID userId)throws IOException;

    ResponseEntity<Object> addProductToLikesListUser(UserDetails userDetails, UUID userId, Long productId)throws IOException;

    ResponseEntity<Object> deleteFromLikesListUser(UserDetails userDetails, UUID userId, Long productId)throws IOException;


    /////////////////////////////favourite List //////////////////////////////////////////

    ResponseEntity<Object> getAllFavouriteListOfUser(UserDetails userDetails, UUID userId)throws IOException;

    ResponseEntity<Object> addProductToFavouriteListUser(UserDetails userDetails, UUID userId, Long productId)throws IOException;

    ResponseEntity<Object> deleteFromFavouriteListUser(UserDetails userDetails, UUID userId, Long productId)throws IOException;
//////////////////////////////comments ////////////////////////////////////////////////
    ResponseEntity<Object> getAllCommentsProduct(Long idproduct) throws IOException;

    ResponseEntity<Object> addCommentToProduct(UserDetails userDetails, UUID userId, Long productId, Comment comment) throws IOException;

    ResponseEntity<Object> deleteCommentFromProduct(UserDetails userDetails, UUID iduser, Long idproduct,Long comment) throws  IOException;
    /////////////////////////////////////////////////////////////////////////////////////////
}
