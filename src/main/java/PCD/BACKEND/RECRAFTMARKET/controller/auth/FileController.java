package PCD.BACKEND.RECRAFTMARKET.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/v1/user")
@Controller
public class FileController {
   /* @PutMapping("/{productId}/images")
    public ResponseEntity<Object> addImageToProduct(@PathVariable("productId") final long productId , @RequestParam(value = "image" , required = true) final MultipartFile multipartFile) throws IOException {
        return FileService.addImageToProduct(productId, multipartFile);
    }
    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<Object> removeImageFromProduct(@PathVariable("productId") final long productId , @PathVariable("imageId") final long imageId) throws IOException {
        return fileService.removeImageFromProduct(productId, imageId);
    }

    @GetMapping("/{productId}/images/{fileIndex}")
    public ResponseEntity<byte[]>  fetchImageFromProduct(@PathVariable("productId") final long productId ,@PathVariable("fileIndex") final int fileIndex) throws IOException {
        return fileService.fetchImageFromProduct(productId , fileIndex);
    }*/


}
