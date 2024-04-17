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
    /////////////////////////////////For admin refreshing the points //////////////////////////

    @PutMapping(value = "/admin/refrechpointsproduct")
    public void refreshPointsProduct(){
        productService.updatePointsAllProduct();
    }

}
