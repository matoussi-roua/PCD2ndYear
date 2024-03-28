package PCD.BACKEND.RECRAFTMARKET.service.product;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import PCD.BACKEND.RECRAFTMARKET.repository.ProductRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.ResponseHandler;
import PCD.BACKEND.RECRAFTMARKET.service.file.FileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServicesImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileService fileService;


    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long productId) {
        Product product = productRepository.findById(productId).get();
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
    }

}
