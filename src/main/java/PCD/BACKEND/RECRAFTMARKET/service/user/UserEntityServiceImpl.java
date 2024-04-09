package PCD.BACKEND.RECRAFTMARKET.service.user;


import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTOMapper;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.ResponseHandler;
import PCD.BACKEND.RECRAFTMARKET.service.file.FileServiceUser;
import PCD.BACKEND.RECRAFTMARKET.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final FileServiceUser fileService;
    private final ProductService productService;
///////////////////////////ALL about User //////////////////////////////////////////////////////////
    @Override
     public ResponseEntity<Object> fetchUserByUsername(final String username)
     {
         final UserEntity savedUser = getUserByUsername(username);
         final UserEntityDTO user = userEntityDTOMapper.apply(savedUser);
         return ResponseHandler.generateResponse(user , HttpStatus.OK) ;
     }

    @Override
    public ResponseEntity<Object> fetchUserEntityById(UUID UserId) {
        final UserEntity savedUser = getUserById(UserId);
        final UserEntityDTO user  = userEntityDTOMapper.apply(savedUser);
        return ResponseHandler.generateResponse(user,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateUserEntity(UUID UserId, UserEntity userUpdated) {
        UserEntity userToUpdate = getUserById(UserId);
       // userToUpdate.setPassword(userUpdated.getPassword());
        userToUpdate.setPoints(userUpdated.getPoints());
       // userToUpdate.setUsername(userUpdated.getUsername());
        userToUpdate.setPhonenumber(userUpdated.getPhonenumber());
        userToUpdate.setAddress(userUpdated.getAddress());
        userEntityRepository.save(userToUpdate);
        final String successResponse = String.format("User with ID %d updated successfully", UserId);

        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);

    }
    @Override
    public List<UserEntityDTO> getAllUserEntity() {
    final List<UserEntityDTO> users =mapToDTOList(userEntityRepository.findAll());
    return users;
    }
    @Override
    public ResponseEntity<Object> fetchAllUserEntity() {
        final List<UserEntityDTO> users = getAllUserEntity();
        return ResponseHandler.generateResponse(users,HttpStatus.OK);
    }

    @Override
    public UserEntityDTO mapToDTOItem(UserEntity user) {
        return userEntityDTOMapper.apply(user);
    }

    @Override
    public List<UserEntityDTO> mapToDTOList(List<UserEntity> users)
    {
        return users.stream().map(userEntityDTOMapper).toList();
    }


    @Override
    public ResponseEntity<Object> addImageToUser(UUID UserId, @NotNull MultipartFile image) throws IOException {
        final UserEntity existingUser =  getUserById(UserId);
        final FileDataUser newImage = fileService.processUploadedFile(image);
        newImage.setUserFile(existingUser);
        existingUser.setFileUser(newImage);
        userEntityRepository.save(existingUser);
        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromUser(UUID UserId) throws IOException {
        final UserEntity existingUser = getUserById(UserId);
        if(existingUser.getFileUser() == null){
            throw new IllegalStateException("there is no image to delete");
        }
        
        final FileDataUser existingImage = fileService.getFileDataUserById(existingUser.getFileUser().getId());
    
        existingUser.setFileUser(null);
        existingImage.setUserFile(null);
        fileService.deleteFileFromFileSystemUser(existingImage);
        userEntityRepository.save(existingUser);
        final String successResponse = String.format("The image with ID : %d deleted successfully",existingImage.getId());
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID UserId) throws IOException {
        final UserEntity user = getUserById(UserId);
        final FileDataUser fileDataUser = user.getFileUser();
        return fileService.downloadFile(fileDataUser);
    }


    ////////////////////////////////////ALL about the product ////////////////////////////////////////////
    @Override
    public ResponseEntity<Object> addProductToUser(UserDetails userDetails, UUID userId, Product product) {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = getUserById(userId);
        Product productadded=productService.addProduct(product);
        productadded.setPublisher(user);
        // Add product to user's list of products
        user.getProductsList().add(product);
        userEntityRepository.save(user);

        return ResponseHandler.generateResponse("Product added to user successfully.", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> addImageToProductUser(UserDetails userDetails,UUID userId, Long productId, @NotNull MultipartFile image) throws IOException {

        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        boolean productFound = currentUser.getProductsList().stream()
                .anyMatch(product -> product.getIdProduct()==productId);
        // If productFound is true, add image to product and save the user entity
        if (productFound) {
            productService.addImageToProduct(productId,image);
            //productNoImage.getFilesProduct().add(newImage);
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("image Product added successfully.", HttpStatus.OK);
        } else {
            // Product not found, return an error response
            return ResponseHandler.generateResponse("Product not found for the user.", HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<Object> updateProductUser(UserDetails userDetails,UUID userId, Long productId, Product productupdated) {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }

        // Check if any product in the user's list has the provided productId
        boolean productFound = currentUser.getProductsList().stream()
                .anyMatch(product -> product.getIdProduct()==productId);

        // If productFound is true, update the product and save the user entity
        if (productFound) {
            productService.updateProduct(productId, productupdated);
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("Product updated successfully.", HttpStatus.OK);
        } else {
            // Product not found, return an error response
            return ResponseHandler.generateResponse("Product not found for the user.", HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<Object> deleteProductFromUser(UserDetails userDetails,UUID userId, Long productId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        // Check if any product in the user's list has the provided productId
        boolean productFound = currentUser.getProductsList().stream()
                .anyMatch(product -> product.getIdProduct() == productId);
        // If productFound is true, update the product and save the user entity
        if (productFound) {
            productService.deleteProduct(productId);//the problem is here
            //userEntityRepository.save(currentUser); here is the problem :{
            //    "timestamp": "2024-04-08T17:03:49.1732377",
            //    "status": "BAD_REQUEST",
            //    "message": "Other exception occurs",
            //    "errors": [
            //        "Unable to find PCD.BACKEND.RECRAFTMARKET.model.product.Product with id 252"
            //    ]
            //}
            return ResponseHandler.generateResponse("Product deleted successfully.", HttpStatus.OK);
        }
        else {
            // Product not found, return an error response
            return ResponseHandler.generateResponse("Product not found for the user.", HttpStatus.NOT_FOUND);
        }

    }
    @Override
    public ResponseEntity<Object> getAllProductsOfUser(UUID userId) {
        UserEntity user = getUserById(userId);
        List<Product> products = user.getProductsList();
        return ResponseHandler.generateResponse(products, HttpStatus.OK);
    }


//////////////////////////////// Likes List ///////////////////////////////////////////
@Override
public ResponseEntity<Object> getAllLikesListOfUser(UserDetails userDetails, UUID userId) throws IOException {
        //make sure the id of user is really his id
    final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
    if (!currentUser.getId().equals(userId)) {
        return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
    }
    List<Product> likesList=currentUser.getLikedProducts();
    return ResponseHandler.generateResponse(likesList, HttpStatus.OK);
}

    @Override
    public ResponseEntity<Object> addProductToLikesListUser(UserDetails userDetails, UUID userId, Long productId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        final Product likedProduct =productService.getProductById(productId);
        currentUser.getLikedProducts().add(likedProduct);
        likedProduct.getLoversList().add(currentUser);
        // Add product to user's list of products
        userEntityRepository.save(currentUser);
        return ResponseHandler.generateResponse("Product added to likes list successfully.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteFromLikesListUser(UserDetails userDetails, UUID userId, Long productId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        // Get the liked product
        final Product likedProduct = productService.getProductById(productId);

        // Remove product from user's likes list
        boolean removedFromLikes = currentUser.getLikedProducts().remove(likedProduct);

        if (!removedFromLikes) {
            return ResponseHandler.generateResponse("Product is not in user's likes list.", HttpStatus.BAD_REQUEST);
        }

        // Remove user from product's lovers list
        likedProduct.getLoversList().remove(currentUser);

        // Save changes to the database
        try {
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("Product removed from likes list successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to remove product from likes list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//////////////////////////// Favourite List//////////////////////////////////////////////////
@Override
public ResponseEntity<Object> getAllFavouriteListOfUser(UserDetails userDetails, UUID userId) throws IOException {
    final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
    if (!currentUser.getId().equals(userId)) {
        return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
    }
    List<Product> favouriteListList=currentUser.getFavouriteProducts();
    return ResponseHandler.generateResponse(favouriteListList, HttpStatus.OK);
}

    @Override
    public ResponseEntity<Object> addProductToFavouriteListUser(UserDetails userDetails, UUID userId, Long productId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        final Product favouriteProduct =productService.getProductById(productId);
        currentUser.getFavouriteProducts().add(favouriteProduct);
        favouriteProduct.getWantersList().add(currentUser);
        // Add product to user's list of products
        try {
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("Product removed from likes successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to remove product from likes list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }   }

    @Override
    public ResponseEntity<Object> deleteFromFavouriteListUser(UserDetails userDetails, UUID userId, Long productId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        // Get the liked product
        final Product favouriteProduct = productService.getProductById(productId);

        // Remove product from user's likes list
        boolean removedFromFavourite = currentUser.getFavouriteProducts().remove(favouriteProduct);

        if (!removedFromFavourite) {
            return ResponseHandler.generateResponse("Product is not in user's favourite list.", HttpStatus.BAD_REQUEST);
        }

        // Remove user from product's lovers list
        favouriteProduct.getWantersList().remove(currentUser);

        // Save changes to the database
        try {
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("Product removed from favourite successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to remove product from favourite list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////

    public UserEntity getUserById(final UUID userId) {
        return userEntityRepository.fetchUserById(userId).orElseThrow(
                () -> new ResourceNotFoundException("The User with ID %s could not be found in our system.".formatted(userId))
        );
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userEntityRepository.fetchUserWithUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("The User with USERNAME '%s' could not be found in our system.".formatted(username))
        );
    }





}
