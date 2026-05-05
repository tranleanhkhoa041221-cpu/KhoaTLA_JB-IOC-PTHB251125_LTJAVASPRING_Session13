package ra.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
