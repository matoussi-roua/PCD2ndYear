package PCD.BACKEND.RECRAFTMARKET.controller.user;

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
    //worked
    @GetMapping(value="/user/{username}")  //si je suis certain que la methode est get
    public ResponseEntity<Object> fetchUserByUsername(@PathVariable String username) {
    return userService.fetchUserByUsername(username);
    }
    //it doesn't work
    //confidential
    @PutMapping(value="/updateuser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserEntity userUpdated) {
        return userService.updateUserEntity(id,userUpdated);
    }

///////////////////////////////////////////image of user/////////////////////////////////////////////////
    ///this part about the relation between user and his image
    //fetchimage from user doesn't work
    //worked
    //confidential
    @PostMapping(value="/addimagetouser/{id}")
    public ResponseEntity<Object> addImageToUser(@PathVariable UUID id, @RequestParam("file") MultipartFile file)throws IOException{
        return userService.addImageToUser(id,file);
    }
    //worked
    //confidential
    @DeleteMapping(value = "/removeimage/{id}")
    public ResponseEntity<Object> removeImageFromUser(@PathVariable UUID id ) throws IOException{
        return userService.removeImageFromUser(id);
    }
//doesn't work
    @GetMapping(value = "/imageuser/{id}")
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID id) throws IOException{
        return userService.fetchImageFromUser(id);
    }
/////////////////////////////products of user////////////////////////////////
    ///this part about the relation between the user and his products
    //neqess bech nzid tsawer ll kol product



    //worked
    //confidential OK
    @PutMapping(value = "/addproductuser/{id}")
    public ResponseEntity<Object> addProductToUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id, @RequestBody Product product) {
        return userService.addProductToUser(userDetails,id,product);
    }

    //worked
    //confidential OK
    @PutMapping(value = "/addimageproduct/{iduser}/{idproduct}")
    public ResponseEntity<Object> addImageToProductUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct,@RequestParam("file") MultipartFile image) throws IOException {
        return userService.addImageToProductUser(userDetails,iduser,idproduct,image);
    }
    //worked
    //confidential OK
    @PutMapping(value = "/updateproductuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> addProductToUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct,@RequestBody Product productupdated){
        return userService.updateProductUser(userDetails,iduser,idproduct,productupdated);
    }
    //worked
    //confidential OK
    @DeleteMapping(value = "/deleteproductuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> deleteProductFromUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
return userService.deleteProductFromUser(userDetails,iduser,idproduct);
    }

    //worked
    @GetMapping(value = "/allproductsuser/{id}")
    public ResponseEntity<Object> getAllProductsOfUser(@PathVariable UUID id){
        return userService.getAllProductsOfUser(id);
    }

///////////////////////////////////this part is for the Likes list/////////////////
    @GetMapping(value = "/alllikeslistuser/{id}")
    public ResponseEntity<Object> getAllLikesListOfUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id) throws IOException {
    return userService.getAllLikesListOfUser(userDetails,id);
}
    @PutMapping(value = "/addtolikeslistuser/{id}")
    public ResponseEntity<Object> addProductToLikesListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
        return userService.addProductToLikesListUser(userDetails,iduser,idproduct);
    }
    @DeleteMapping(value = "/deletefromlikeslistuser/{id}")
    public ResponseEntity<Object> deleteFromLikesListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
        return userService.deleteFromLikesListUser(userDetails,iduser,idproduct);
    }
    ///////////////////////////////////this part is for the Likes list/////////////////
    @GetMapping(value = "/allfavouritelistuser/{id}")
    public ResponseEntity<Object> getAllFavouriteListOfUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID id)throws IOException{
        return userService.getAllFavouriteListOfUser(userDetails,id);
    }
    @PutMapping(value = "/addtofavouritelistuser/{id}")
    public ResponseEntity<Object> addProductToFavouriteListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
        return userService.addProductToFavouriteListUser(userDetails,iduser,idproduct);
    }
    @DeleteMapping(value = "/deletefromfavouritelistuser/{id}")
    public ResponseEntity<Object> deleteFromFavouriteListUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID iduser,@PathVariable Long idproduct)throws IOException{
        return userService.deleteFromFavouriteListUser(userDetails,iduser,idproduct);
    }

}
