package PCD.BACKEND.RECRAFTMARKET.repository;

import PCD.BACKEND.RECRAFTMARKET.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
