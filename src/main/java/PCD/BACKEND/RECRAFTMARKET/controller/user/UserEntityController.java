package PCD.BACKEND.RECRAFTMARKET.controller.user;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.service.user.UserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/users")
public class UserEntityController {
    private final UserEntityService userService;
    public UserEntityController(UserEntityService userService) {
        this.userService = userService;
    }
    @GetMapping(value="/user/{username}")  //si je suis certain que l methode est get
    public ResponseEntity<Object> fetchUserByUsername(@PathVariable String username) {
    return userService.fetchUserByUsername(username);
    }
    @PutMapping(value="/updateuser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserEntity userUpdated) {
        return userService.updateUserEntity(id,userUpdated);
    }


    ///this part about the relation between user and his image
    //fetchimage from user doesn't work
    @PostMapping(value="/addimagetouser/{id}")
    public ResponseEntity<Object> addImageToUser(@PathVariable UUID id, @RequestParam("file") MultipartFile file)throws IOException{
        return userService.addImageToUser(id,file);
    }
    @DeleteMapping(value = "/removeimage/{id}")
    public ResponseEntity<Object> removeImageFromUser(@PathVariable UUID id ) throws IOException{
        return userService.removeImageFromUser(id);
    }

    @GetMapping(value = "/imageuser/{id}")
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID id) throws IOException{
        return userService.fetchImageFromUser(id);
    }

    ///this part about the relation between the user and his products
    @PutMapping(value = "/addproductuser/{id}")
    public ResponseEntity<Object> addProductToUser(@PathVariable UUID id,@RequestParam Product product){
        return userService.addProductToUser(id,product);
    }
    @DeleteMapping(value = "/deleteproductuser/{iduser}/{idproduct}")
    public ResponseEntity<Object> deleteProductFromUser(@PathVariable UUID iduser,@PathVariable Long idproduct){
return userService.deleteProductFromUser(iduser,idproduct);
    }
    @GetMapping(value = "/allproductsuser/{id}")
    public ResponseEntity<Object> getAllProductsOfUser(@PathVariable UUID id){
        return userService.getAllProductsOfUser(id);
    }

    /* public ResponseEntity<Object> addProductToUser(UUID userId, Product product);

    public ResponseEntity<Object> deleteProductFromUser(UUID userId, Product product);

    public ResponseEntity<Object> getAllProductsOfUser(UUID userId);*/

/*
    @PostMapping(value="/adduser")
    public Users addUser(@RequestBody Users user) {
        return userservice.addUser(user);
    }
    @DeleteMapping(value="/deleteuser/{Id}")
    public void deleteUser(@PathVariable Long Id) {
        userservice.deleteUser(Id);
    }

    @GetMapping(value="/getallusers")
    public List<Users> getAllUsers() {
        return userservice.getAllUsers();
    }
    @GetMapping(value="/getuserbyid/{id}")
    public Users getUserById(@PathVariable Long id) {
        return userservice.getUserById(id);
    }
    @PostMapping(value="/existedemail")
    public boolean existedEmail(@RequestBody String email) {
        return userservice.existedEmail(email);
    }
    @PostMapping(value = "/addimagetouser/{iduser}")
    public Users addUserToImage(@PathVariable Long iduser,@RequestParam("file") MultipartFile file) throws IOException
    {
        return userservice.addImageToUser(iduser,file);
    }
    @GetMapping(value = "/getimageuser/{iduser}")
    public ImageUser getImageUser(@PathVariable Long iduser)
    {
        return userservice.getImageById(iduser);
    }*/
}
