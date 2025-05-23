package com.ecom.eilco.repository;

import com.ecom.eilco.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);           
    Optional<Product> findByName(String name);                 
    List<Product> findByCategory_Id(Long id);
    List<Product> findByNameContainingIgnoreCase(String name);
}