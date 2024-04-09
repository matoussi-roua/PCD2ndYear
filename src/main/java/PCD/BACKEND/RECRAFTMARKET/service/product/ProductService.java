package PCD.BACKEND.RECRAFTMARKET.service.product;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    // Method to add a new product
    Product addProduct(Product product);

    // Method to retrieve a product by its ID

    Product getProductById(long productId);

    // Method to retrieve all products
    List<Product> getAllProducts();

    // Method to update an existing product
    Product updateProduct(long productId, Product updatedProduct);

    // Method to delete a product by its ID
    void deleteProduct(long productId)throws IOException;
    public ResponseEntity<Object> addImageToProduct(long productId, @NotNull MultipartFile image) throws IOException;

    void removeAllImagesFromProduct(long productId) throws IOException;

    public ResponseEntity<Object> removeImageFromProduct(long productId, long imageId) throws IOException;
    public  ResponseEntity<byte[]> fetchImageFromProduct(final long productId,final int fileIndex) throws IOException;

}
