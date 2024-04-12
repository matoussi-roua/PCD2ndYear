package PCD.BACKEND.RECRAFTMARKET.service.user;


import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTOMapper;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import PCD.BACKEND.RECRAFTMARKET.model.product.Comment;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.CommentRepository;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.ResponseHandler;
import PCD.BACKEND.RECRAFTMARKET.service.file.FileServiceUser;
import PCD.BACKEND.RECRAFTMARKET.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserEntityServiceImpl implements UserEntityService{


    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;
    private final CommentRepository commentRepository;
    private final FileServiceUser fileService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;


    ///////////////////////////To DTO ////////////////////////
    @Override
    public UserEntityDTO mapToDTOItem(UserEntity user) {
        return userEntityDTOMapper.apply(user);
    }

    @Override
    public List<UserEntityDTO> mapToDTOList(List<UserEntity> users)
    {
        return users.stream().map(userEntityDTOMapper).toList();
    }


    ///////////////////////////ALL about User //////////////////////////////////////////////////////////
    @Override
     public ResponseEntity<Object> fetchUserByUsername(final String username)
     {
         final UserEntity savedUser = getUserByUsername(username);
         final UserEntityDTO user = mapToDTOItem(savedUser);
         return ResponseHandler.generateResponse(user , HttpStatus.OK) ;
     }

    @Override
    public ResponseEntity<Object> fetchUserEntityById(UUID UserId) {
        final UserEntity savedUser = getUserById(UserId);
        final UserEntityDTO user  = mapToDTOItem(savedUser);
        return ResponseHandler.generateResponse(user,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateUserEntity(UserDetails userDetails,UUID userId, UserEntity userUpdated) {
        final UserEntity userToUpdate=getUserByUsername(userDetails.getUsername());
        if (!userToUpdate.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        userToUpdate.setFirstname(userUpdated.getFirstname());
        userToUpdate.setLastname(userUpdated.getLastname());
       // userToUpdate.setPoints(userUpdated.getPoints());
       userToUpdate.setUsername(userUpdated.getUsername());
        userToUpdate.setPhonenumber(userUpdated.getPhonenumber());
        userToUpdate.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
        userToUpdate.setAddress(userUpdated.getAddress());
        userEntityRepository.save(userToUpdate);
        final String successResponse = String.format("User with ID %s updated successfully", userId);

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
    public ResponseEntity<Object> addImageToUser(UserDetails userDetails,UUID userId, @NotNull MultipartFile image) throws IOException {
        final UserEntity existingUser=getUserByUsername(userDetails.getUsername());
        if (!existingUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        final FileDataUser newImage = fileService.processUploadedFile(image);
        newImage.setUserFile(existingUser);
        existingUser.setFileUser(newImage);
        userEntityRepository.save(existingUser);
        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromUser(UserDetails userDetails,UUID userId) throws IOException {
        final UserEntity existingUser=getUserByUsername(userDetails.getUsername());
        if (!existingUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
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
    public  ResponseEntity<byte[]> fetchImageFromUser(UUID UserId) throws IOException {
        final UserEntity user = getUserById(UserId);
        if(user.getFileUser() == null){
            throw new IllegalStateException("there is no image to show");
        }
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

        Product productadded=productService.addProduct(product);
        increasePointsUser(userId,50L);
        productadded.setPublisher(currentUser);
        // Add product to user's list of products
        currentUser.getProductsList().add(product);
        userEntityRepository.save(currentUser);

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
            productService.deleteProduct(productId);
            decreasePointsUser(userId,50L);
            //the problem is here
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
        return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.OK);
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
        increasePointsUser(userId,1L);
        productService.updatePointsProduct(productId);
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
        decreasePointsUser(userId,1L);
        productService.updatePointsProduct(productId);

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
        increasePointsUser(userId,1L);
        productService.updatePointsProduct(productId);
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
        decreasePointsUser(userId,1L);
        productService.updatePointsProduct(productId);

        // Save changes to the database
        try {
            userEntityRepository.save(currentUser);
            return ResponseHandler.generateResponse("Product removed from favourite successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to remove product from favourite list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity<Object> getAllCommentsProduct(Long idproduct) throws IOException {
        final Product product=productService.getProductById(idproduct);
        if (product.getComments()==null){
            return ResponseHandler.generateResponse("No comments for this product", HttpStatus.OK);
        }
        final List<Comment> commentsOfProduct = product.getComments();
        return  ResponseHandler.generateResponse(commentsOfProduct,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCommentToProduct(UserDetails userDetails, UUID userId, Long productId, Comment comment) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        final Product product=productService.getProductById(productId);
        try{
            product.getComments().add(comment);
            currentUser.getCommentsUser().add(comment);
            comment.setCommenter(currentUser);
            comment.setCommentProduct(product);
            increasePointsUser(userId,1L);
            productService.updatePointsProduct(productId);
            commentRepository.save(comment);
            return  ResponseHandler.generateResponse("comment added successfully ",HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseHandler.generateResponse("error in adding the comment",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> deleteCommentFromProduct(UserDetails userDetails, UUID userId, Long productId,Long commentId) throws IOException {
        final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
        if (!currentUser.getId().equals(userId)) {
            return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
        }
        boolean existingComment=currentUser.getCommentsUser().stream()
                .anyMatch(comment -> comment.getIdComment() == commentId);
        if (!existingComment) {
            ResponseHandler.generateResponse("NOT AUTHORIZED",HttpStatus.UNAUTHORIZED);
        }
        final Comment commentToDelete=commentRepository.getById(commentId);
        final Product product = productService.getProductById(productId);

        product.getComments().remove(commentToDelete);
        currentUser.getCommentsUser().remove(existingComment);
        commentRepository.deleteById(commentId);
        decreasePointsUser(userId,1L);
        productService.updatePointsProduct(productId);
        return ResponseHandler.generateResponse("Comment deleted successfully",HttpStatus.OK);

    }
/////////////////////////////leaderboard///////////////////////////////////////////////
    @Override
    public ResponseEntity<Object> getLeaderBoard(UUID iduser) {
        List<UserEntityDTO> users =mapToDTOList(userEntityRepository.findAllByOrderByPointsDesc());
       // List<UserEntityDTO> userDTOs = users.stream()
               // .map(userEntityDTOMapper)
                //.collect(Collectors.toList());
        return ResponseHandler.generateResponse(users, HttpStatus.OK);
    }


    ////////////////////////////////////fix points User////////////////////////////////////////////////////////////////
    @Override
    public void increasePointsUser(UUID userId, Long points) {
        final UserEntity user=getUserById(userId);
        user.setPoints(user.getPoints()+points);
        userEntityRepository.save(user);
    }
    @Override
    public void decreasePointsUser(UUID userId, Long points) {
        final UserEntity user=getUserById(userId);
        user.setPoints(user.getPoints()-points);
        userEntityRepository.save(user);


    }
    //////////////////fix Points Product ///////////////////////
    /*
    *After a user likes a product.
    * After a user comment a product
    *After a user adds a product to their favorites.
    *After the shop points of a product are updated.
    * call productService.updatePointsProduct(productId);
    * */
/////////////////////////////////////////////Shopping part///////////////////////////

    /* this part will increase the "shop points" of the product for now and
    * later (OUR PERSPECTIVE)this function should transfer a message containing
    * the product you choose to the owner and start a conversation with him
    * each product(frontend) has a button "shop now" once you click on it this function
    * should be called and you will automatically redirect to the chat box with the owner
    */
@Override
public ResponseEntity<Object> shopnow(UserDetails userDetails, UUID userId, Long productId) throws IOException {
    final UserEntity currentUser=getUserByUsername(userDetails.getUsername());
    if (!currentUser.getId().equals(userId)) {
        return ResponseHandler.generateResponse("You are unauthorized", HttpStatus.UNAUTHORIZED);
    }
    //the user should not be the publisher of the product but it is ok we can hide the button in the frontend
    increasePointsUser(userId,5L);
    productService.increaseShopPointsProduct(productId,5L);
    productService.updatePointsProduct(productId);
    return ResponseHandler.generateResponse("great! your points are increasing",HttpStatus.OK);
}


    ///////////////////////////////////////////////important/////////////////////////////////

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
///////////////////////////////////////// END //////////////////////////////////////////////////////////////////




}
