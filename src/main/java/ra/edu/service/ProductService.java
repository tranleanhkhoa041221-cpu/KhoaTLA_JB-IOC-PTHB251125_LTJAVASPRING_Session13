package ra.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.edu.entity.Product;
import ra.edu.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product create(Product p) {
        return productRepository.save(p);
    }

    public Product update(Long id, Product newData) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        p.setName(newData.getName());
        p.setDescription(newData.getDescription());
        p.setPrice(newData.getPrice());
        p.setSize(newData.getSize());
        p.setToppings(newData.getToppings());

        return productRepository.save(p);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
