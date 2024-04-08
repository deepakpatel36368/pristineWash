package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.Cart;
import com.website.pristinewash.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class CartItemDTO {

    private Long id;

    private ProductDTO productDto;

    private int quantity;

    private double cartItemTotalPrice;

}
