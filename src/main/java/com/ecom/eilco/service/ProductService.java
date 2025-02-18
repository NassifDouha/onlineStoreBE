package com.ecom.eilco.service;

import lombok.AllArgsConstructor;
import com.ecom.eilco.dto.ProductRequest;
import com.ecom.eilco.model.Product;
import com.ecom.eilco.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;

    public Product save(ProductRequest productRequest) {
        return repository.save(convertProductRequestToProduct(productRequest, null));
    }

    public Product update(ProductRequest productRequest, Long id) {
        return repository.save(convertProductRequestToProduct(productRequest, id));
    }

    public void deleteById(Long id) { repository.deleteById(id);}

    public Optional<Product> findById(Long id) { return repository.findById(id);}

    public List<Product> findAll() {return (List<Product>) repository.findAll();}

    public List<Product> findByCategory(Long id) {return repository.findByCategory_Id(id);}

    public List<Product> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    private Product convertProductRequestToProduct(ProductRequest productRequest, Long id) {
        return Product.builder()
            .id(id)
            .name(productRequest.getName())
            .price(productRequest.getPrice())
            .quantity(productRequest.getQuantity())
            .description(productRequest.getDescription())
            .active(productRequest.isActive())
            .imageUrl(productRequest.getImageUrl())
            .build();
    }
}
