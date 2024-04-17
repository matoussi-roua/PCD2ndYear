package PCD.BACKEND.RECRAFTMARKET.controller.user;

import PCD.BACKEND.RECRAFTMARKET.model.product.Comment;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.service.user.UserEntityService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
// to understand more : to update your profile or delete your post y should use the userdetails as a test
// to verify that the same user is going to update his information the key word is : confidential
@RestController
@RequestMapping("api/v2/users")
public class UserEntityController {
    private final UserEntityService userService;
    public UserEntityController(UserEntityService userService) {
        this.userService = userService;
    }
    //////////////////user details ////////////////////////////////////////////////////////
    //DTO
    @GetMapping(value="/permit/user/{username}")
    public ResponseEntity<Object> fetchUserByUsername(@PathVariable String username) {
    return userService.fetchUserByUsername(username);
    }
    //confidential OK
    @PutMapping(value="/client/updateuser/{id}")
    public ResponseEntity<Object> updateUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id, @RequestBody UserEntity userUpdated) {
        return userService.updateUserEntity(userDetails,id,userUpdated);
    }
    // DTO
    @GetMapping(value = "/permit/allusers")
    public ResponseEntity<Object> allUsers(){
        return userService.fetchAllUserEntity();
    }
//a tester DTO
    @GetMapping(value = "/permit/userbyid/{iduser}")
    public ResponseEntity<Object> usersByid(@PathVariable("iduser") UUID iduser){
        return userService.fetchUserEntityById(iduser);
    }

///////////////////////////////////////////image of user/////////////////////////////////////////////////
    ///this part about the relation between user and his image
    //fetchimage from user doesn't work
    //worked
    //confidential OK
    @PostMapping(value="/client/addimagetouser/{id}")
    public ResponseEntity<Object> addImageToUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id, @RequestParam("file") MultipartFile file)throws IOException{
        return userService.addImageToUser(userDetails,id,file);
    }
    //confidential
    @DeleteMapping(value = "/client/removeimage/{id}")
    public ResponseEntity<Object> removeImageFromUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id ) throws IOException{
        return userService.removeImageFromUser(userDetails,id);
    }
//worked
    @GetMapping(value = "/permit/imageuser/{id}")
    public  ResponseEntity<byte[]> fetchImageFromUser(@PathVariable("id")  UUID id) throws IOException{
        return userService.fetchImageFromUser(id);
    }
/////////////////////////////products of user////////////////////////////////
    ///this part about the relation between the user and his products


    //confidential OK
    @PutMapping(value = "/client/addproductuser/{id}")
    public ResponseEntity<Object> addProductToUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id, @RequestBody Product product) {
        return userService.addProductToUser(userDetails,id,product);
    }

    //worked
    //confidential OK
    @PutMapping(value = "/client/addimageproduct/{iduser}/{idproduct}")
    public ResponseEntity<Object> addImageToProductUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct,@RequestParam("file") MultipartFile image) throws IOException {
        return userService.addImageToProductUser(userDetails,iduser,idproduct,image);
    }
    //confidential OK
    @PutMapping(value = "/client/updateproductuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> addProductToUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct,@RequestBody Product productupdated){
        return userService.updateProductUser(userDetails,iduser,idproduct,productupdated);
    }
    //confidential OK
    @DeleteMapping(value = "/client/deleteproductuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> deleteProductFromUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
return userService.deleteProductFromUser(userDetails,iduser,idproduct);
    }

    @GetMapping(value = "/permit/allproductsuser/{id}")
    public ResponseEntity<Object> getAllProductsOfUser(@PathVariable UUID id){
        return userService.getAllProductsOfUser(id);
    }

///////////////////////////////////this part is for the Likes list/////////////////
    @GetMapping(value = "/client/alllikeslistuser/{id}")
    public ResponseEntity<Object> getAllLikesListOfUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") UUID id) throws IOException {
    return userService.getAllLikesListOfUser(userDetails,id);
}
    @PutMapping(value = "/client/addtolikeslistuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> addProductToLikesListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser,@PathVariable("idproduct") Long idproduct)throws IOException{
        return userService.addProductToLikesListUser(userDetails,iduser,idproduct);
    }
    @DeleteMapping(value = "/client/deletefromlikeslistuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> deleteFromLikesListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser,@PathVariable("idproduct") Long idproduct)throws IOException{
        return userService.deleteFromLikesListUser(userDetails,iduser,idproduct);
    }
    ///////////////////////////////////this part is for the Likes list/////////////////
    @GetMapping(value = "/client/allfavouritelistuser/{id}")
    public ResponseEntity<Object> getAllFavouriteListOfUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") UUID id)throws IOException{
        return userService.getAllFavouriteListOfUser(userDetails,id);
    }
    @PutMapping(value = "/client/addtofavouritelistuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> addProductToFavouriteListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser,@PathVariable("idproduct") Long idproduct)throws IOException{
        return userService.addProductToFavouriteListUser(userDetails,iduser,idproduct);
    }
    @DeleteMapping(value = "/client/deletefromfavouritelistuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> deleteFromFavouriteListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser,@PathVariable("idproduct") Long idproduct)throws IOException{
        return userService.deleteFromFavouriteListUser(userDetails,iduser,idproduct);
    }
    ///////////////////////////////// Comments //////////////////////////////////
    @GetMapping(value = "/permit/getallcommentsproduct/{idproduct}")
    public ResponseEntity<Object> getAllCommentsProduct(@PathVariable("idproduct") Long idproduct)throws IOException{
    return userService.getAllCommentsProduct(idproduct);
    }
    @PutMapping(value = "/client/addcommenttoproduct/{iduser}/{idproduct}")
    public ResponseEntity<Object> addCommentToProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("iduser") UUID iduser, @PathVariable("idproduct") Long idproduct, @RequestBody Comment comment)throws IOException{
    return userService.addCommentToProduct(userDetails,iduser,idproduct,comment);
    }
    //i have to add that the owner can remove the comments also
    @DeleteMapping(value = "/client/deletecommentfromproduct/{iduser}/{idproduct}/{idcomment}")
    public ResponseEntity<Object> deleteCommentFromProduct(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct,@PathVariable Long idcomment) throws IOException{
    return userService.deleteCommentFromProduct(userDetails,iduser,idproduct,idcomment);
    }
    //////////////////change status/////////////////////////////
@PutMapping(value = "/client/changestatus/{iduser}/{idproduct}")
public ResponseEntity<Object> changeStatus(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser ,@PathVariable("idproduct" ) Long idproduct)throws IOException{
        return userService.changeStatus(userDetails,iduser,idproduct);
}

    /////////////////// LeaderBoard /////////////////////////////////
    @GetMapping(value = "/permit/leaderboard/{iduser}")
    public ResponseEntity<Object> getLeaderBoard(@PathVariable("iduser") UUID iduser) throws IOException{
        return userService.getLeaderBoard(iduser);
    }
    ////////////////////////ShopPoints///////////////////////////
    @PutMapping(value = "/client/shopnow/{iduser}/{idproduct}")
    public ResponseEntity<Object> shopnow(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("iduser") UUID iduser ,@PathVariable("idproduct")Long idproduct)throws IOException{
        return userService.shopnow(userDetails,iduser,idproduct);
    }
    //a tester
@GetMapping(value = "/client/userauthorized/{iduser}")
public ResponseEntity<Object> getUserById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("iduser") UUID iduser){
       return userService.getUserByIdAuthorized(userDetails,iduser);
}
}
