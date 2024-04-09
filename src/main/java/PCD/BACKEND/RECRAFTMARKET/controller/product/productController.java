package PCD.BACKEND.RECRAFTMARKET.controller.product;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v3/products")
public class productController {
    private ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }
//product controller contains what the admin and the visitor need it doesn't depend on the user id
  @GetMapping(value = "/permit/allproducts")
  public List<Product> getAllProducts(){
        return productService.getAllProducts();
  }
  @DeleteMapping(value = "/admin/deleteproduct/{id}")
  public void deleteProduct(@PathVariable long id)throws IOException{
        productService.deleteProduct(id);
  }
  //get product by id
  //worked
    //AMA maandi manaamel biha manajemch nfassa5 imaget lproduit akahaw l mafroudh nesthaqha kn ki nfassa5 lproduit
  /*@DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> removeAllImagesFromProduct(@PathVariable long id) throws IOException {
        return productService.removeAllImagesFromProduct(id);
    }*/

    /*    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long productId) {
        Product product = productRepository.findById(productId).get();
        return product;
    }
    @Override
    public Product updateProduct(long productId, Product updatedProduct) {
       Product ProductToUpdate= productRepository.findById(productId).get();
       ProductToUpdate.setTitle(updatedProduct.getTitle());
       ProductToUpdate.setDescription(updatedProduct.getDescription());
       ProductToUpdate.setPrice(updatedProduct.getPrice());
       ProductToUpdate.setCategory(updatedProduct.getCategory());
       ProductToUpdate.setMaterials(updatedProduct.getMaterials());
       return productRepository.save(ProductToUpdate);
    }

    @Override
    public void deleteProduct(long productId) {
    productRepository.deleteById(productId);
    }
    @Override
    public ResponseEntity<Object> addImageToProduct(long productId, @NotNull MultipartFile image) throws IOException {
        final Product existingProduct =  getProductById(productId);
        final FileData newImage = fileService.processUploadedFile(image);
        newImage.setProduct(existingProduct);
        productRepository.save(existingProduct);

        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromProduct(long productId, long imageId) throws IOException {
        final Product exisitingProduct = getProductById(productId);
        final FileData existingImage = fileService.getFileDataById(imageId);

        if(!exisitingProduct.getFilesProduct().contains(existingImage))
        {
            throw new IllegalStateException(String.format("The Image with ID : %d  does not belong to this product", imageId));
        }

        exisitingProduct.getFilesProduct().remove(existingImage);
        existingImage.setProduct(null);
        fileService.deleteFileFromFileSystem(existingImage);
        productRepository.save(exisitingProduct);
        final String successResponse = String.format("The image with ID : %d deleted successfully",imageId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }
    @Override
    public  ResponseEntity<byte[]> fetchImageFromProduct(final long productId,final int fileIndex) throws IOException {

        final Product product = getProductById(productId);
        if(fileIndex >= product.getFilesProduct().size())
        {
            throw new IllegalStateException("The file index is out of range.");
        }
        final FileData fileData = product.getFilesProduct().get(fileIndex);
        return fileService.downloadFile(fileData);
    }*/
}
