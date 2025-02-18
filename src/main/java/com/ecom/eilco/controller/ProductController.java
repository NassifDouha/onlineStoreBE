package com.ecom.eilco.controller;

import com.ecom.eilco.dto.ProductRequest;
import com.ecom.eilco.dto.ProductResponse;
import com.ecom.eilco.model.Product;
import com.ecom.eilco.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {
        return ProductResponse.builder().product(productService.save(productRequest)).build();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Long id){
        return productService.findById(id);
    }

    @GetMapping
    public List<Product> List() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable("id") Long id){
        return ProductResponse.builder().product(productService.update(productRequest, id)).build();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteById(id);
    }

    @GetMapping("/category/{id}")
    public List<Product> getProductByCategory(@PathVariable("id") Long id){
        return productService.findByCategory(id);
    }

    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("name") String name) {
        return productService.findByName(name);
    }
    
}
