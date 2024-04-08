package com.website.pristinewash.serviceImp;

import com.website.pristinewash.DTO.CategoryDTO;
import com.website.pristinewash.DTO.ProductDTO;
import com.website.pristinewash.entity.Product;
import com.website.pristinewash.entity.Category;
import com.website.pristinewash.repository.CategoryRepository;
import com.website.pristinewash.repository.ProductRepository;
import com.website.pristinewash.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product getProductById(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new UsernameNotFoundException("Product not found with productId" + productId);
        }
    }

    /**
     * Get the list of all categories
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories) {
            categoryDTOS.add(mapToProductCategoryDTO(category));
        }
        return categoryDTOS;
    }

    /**
     * Get the list of all product in a particular category
     */
    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        List<Product> products = productRepository.findByCategory(category);
        List<ProductDTO> productDTOs = new ArrayList<>();

        for(Product product : products) {
            productDTOs.add(mapToProductDTO(product));
        }

        return productDTOs;
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    @Override
    public void addProductToCategory(Integer categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        // Set other properties of the product if needed

        // Set the category for the product
        product.setCategory(category);

        // Save the product entity using the repository
        productRepository.save(product);
    }

    /**
     * map = category -> categoryDTO
     */
    private CategoryDTO mapToProductCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        // Other mappings if needed
        return categoryDTO;
    }

    /**
     * map = product -> productDTO
     */
    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        // Other mappings if needed
        return productDTO;
    }

}
