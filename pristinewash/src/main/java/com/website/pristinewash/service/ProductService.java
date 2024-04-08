package com.website.pristinewash.service;

import com.website.pristinewash.DTO.CategoryDTO;
import com.website.pristinewash.DTO.ProductDTO;
import com.website.pristinewash.entity.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);
    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getProductsByCategory(Integer categoryId);

    void saveCategory(CategoryDTO categoryDTO);

    void addProductToCategory(Integer categoryId, ProductDTO productDTO);
}
