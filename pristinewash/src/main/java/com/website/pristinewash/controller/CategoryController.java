package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.CategoryDTO;
import com.website.pristinewash.DTO.ProductDTO;
import com.website.pristinewash.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ProductService productService;

    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
        return productService.getAllCategories();
    }

    /**
     * Fetch All product by Category
     * used in thymleaf Controller
     */
    @GetMapping("/product_by_category/{categoryId}/categories")
    public List<ProductDTO> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping("/categories")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDTO categoryDTO) {
        productService.saveCategory(categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<Void> addProductToCategory(@PathVariable Integer categoryId, @RequestBody ProductDTO productDTO) {
        productService.addProductToCategory(categoryId, productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
