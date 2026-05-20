package org.example.btth.service;

import org.example.btth.model.entity.Product;
import org.springframework.data.domain.Page;
import java.util.Map;
public interface ProductService {
    Page<Product> getAllProducts(int page, int size, String sort);
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product patchProduct(Long id, Map<String, Object> updates);
    void deleteProduct(Long id);
}