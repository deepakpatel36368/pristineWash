package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.Category;
import com.website.pristinewash.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Integer id;

    private String name;

    private double price;

    private String url;

    private CategoryDTO categoryDto;
}
