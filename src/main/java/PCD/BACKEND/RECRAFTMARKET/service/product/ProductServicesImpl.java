package PCD.BACKEND.RECRAFTMARKET.service.product;

import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
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
       ProductToUpdate.setStatus(updatedProduct.getStatus());
       return productRepository.save(ProductToUpdate);
    }

  @Override
  public void deleteProduct(long productId) throws IOException {
      // Check if the product exists before attempting to delete it
      if (productRepository.existsById(productId)) {
          // Remove all images associated with the product
         removeAllImagesFromProduct(productId);

          // Delete the product from the database
          //productRepository.deleteById(productId);
          productRepository.delete(getProductById(productId));
      } else {
          // Handle the case where the product does not exist
          throw new ResourceNotFoundException("Product with ID " + productId + " not found");
      }
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
    public void removeAllImagesFromProduct(long productId) throws IOException {
        // Step 1: Get the existing product by its ID
        final Product existingProduct = getProductById(productId);

        if (existingProduct == null) {
            // Handle the case where the product with the given ID does not exist
            throw new ResourceNotFoundException("Product with ID " + productId + " not found");
        }

        // Step 2: Retrieve the list of files associated with the product
        List<FileData> filesProduct = existingProduct.getFilesProduct();

        // Step 3: Check if the list of files is not empty before proceeding
        if (!filesProduct.isEmpty()) {
            // Step 4: Iterate through the list of files and delete each file
            for (FileData fileData : filesProduct) {
                fileService.deleteFileFromFileSystem(fileData);
            }

            // Step 5: Clear the list of files associated with the product
            filesProduct.clear();
        } else {
            // Handle the case where there are no files associated with the product
            //throw new IllegalStateException("No files associated with product ID " + productId);
        }

        // Step 6: Save the updated product to the database
        productRepository.save(existingProduct);
    }


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
///////////////////////// calculating the score ////////////////////////////////////////
    @Override
    public void increaseShopPointsProduct(Long productId, long l) {
        final Product product=getProductById(productId);
        product.setShopPoints(product.getShopPoints()+l);
        productRepository.save(product);
    }
// updating  points of product
    @Override
    public void updatePointsProduct(Long productId) {
        Product product = getProductById(productId);

        // Calculate the points based on likes, wish lists, shop points, etc.
        long totalPoints = calculateTotalPoints(product);

        // Update the points attribute of the product
        product.setPoints(totalPoints);

        // Save the updated product to the database
        productRepository.save(product);
    }
//calculate the point of a product
    private long calculateTotalPoints(Product product) {
        // Calculate total points here based on your business logic
        long likesPoints = product.getLoversList().size(); // Example: Number of likes
        long wishListPoints = product.getWantersList().size(); // Example: Number of wish lists
        long shopPoints = product.getShopPoints(); // Example: Shop points
        long commentsPoints=product.getComments().size();


        // Calculate total points
        long totalPoints = likesPoints + wishListPoints + shopPoints + commentsPoints;

        return totalPoints;
    }

    @Override
    public void updatePointsAllProduct() {
        // Retrieve all products from the database
        List<Product> allProducts = productRepository.findAll();

        // Iterate through each product and update its points
        for (Product product : allProducts) {
            updatePointsProduct(product.getIdProduct());
        }
    }



}



