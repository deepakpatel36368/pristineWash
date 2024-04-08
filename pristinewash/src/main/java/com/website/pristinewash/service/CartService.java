package com.website.pristinewash.service;

import com.website.pristinewash.DTO.CartDTO;

public interface CartService {

    String addProductInCart(Integer userId, Integer productId);

    CartDTO findCartByUserId(Integer userId);
}
