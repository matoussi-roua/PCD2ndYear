package PCD.BACKEND.RECRAFTMARKET.controller.product;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v3/products")
public class productController {
    private ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping(value = "/addproduct")
    public void addproduct(){

    }
  @GetMapping(value = "/allposts")
  public List<Product> getAllProducts(){
        return productService.getAllProducts();
  }
  @PutMapping(value = "/updateproduct/{id}")
  public Product updateProduct(@PathVariable Long id, @RequestParam Product updatedProduct){
        return productService.updateProduct(id,updatedProduct);
  }


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
