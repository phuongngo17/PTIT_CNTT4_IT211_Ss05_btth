package org.example.btth.service.impl;

import lombok.RequiredArgsConstructor;

import org.example.btth.exception.ResourceNotFoundException;
import org.example.btth.model.entity.Product;
import org.example.btth.repository.ProductRepository;
import org.example.btth.service.ProductService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getAllProducts(int page, int size, String sort) {

        String[] sortParams = sort.split(",");

        Sort sorting = Sort.by(
                Sort.Direction.fromString(sortParams[1]),
                sortParams[0]
        );

        Pageable pageable =
                PageRequest.of(page, size, sorting);

        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + id));
    }

    @Override
    public Product createProduct(Product product) {

        product.setId(null);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id,
                                 Product product) {

        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    @Override
    public Product patchProduct(Long id,
                                Map<String, Object> updates) {

        Product existingProduct = getProductById(id);

        if (updates.containsKey("name")) {
            existingProduct.setName(
                    updates.get("name").toString());
        }

        if (updates.containsKey("price")) {
            existingProduct.setPrice(
                    Double.parseDouble(
                            updates.get("price").toString()));
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }
}