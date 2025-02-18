package com.ecom.eilco.controller;

import com.ecom.eilco.dto.CategoryRequest;
import com.ecom.eilco.dto.CategoryResponse;
import com.ecom.eilco.model.Category;
import com.ecom.eilco.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
        return CategoryResponse.builder().category(categoryService.save(categoryRequest)).build();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable("id") Long id){
        return categoryService.findById(id);
    }

    @GetMapping
    public List<Category> List() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable("id") Long id){
        return CategoryResponse.builder().category(categoryService.update(categoryRequest, id)).build();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
    }
}
