package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.CartItem;
import com.website.pristinewash.entity.Product;
import com.website.pristinewash.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CartDTO {
    private Integer id;

    private List<CartItemDTO> cartItemsDto;

    private double totalPrice;
}
