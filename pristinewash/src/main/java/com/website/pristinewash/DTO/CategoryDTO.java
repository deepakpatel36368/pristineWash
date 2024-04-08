package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;

    private String name;

    private Set<ProductDTO> productsDto = new HashSet<>();
}
