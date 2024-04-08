package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.CartDTO;
import com.website.pristinewash.entity.Cart;
import com.website.pristinewash.repository.CartRepository;
import com.website.pristinewash.service.CartService;
import com.website.pristinewash.service.UserService;
import com.website.pristinewash.serviceImp.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * Add Product in the cart.
     * Used in thymleaf controller
     */
    @PostMapping("/carts/{product_id}")
    public String addProductToCart(@PathVariable String product_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
           return cartService.addProductInCart(customUserDetails.getUser().getUser_id(), Integer.parseInt(product_id));
        } else {
            throw new UsernameNotFoundException("authentication failed and cannot add product to cart");
        }
    }

    /**
     * Get all cart dto result for a user.
     */
    @GetMapping("/user_cart")
    public ResponseEntity<CartDTO> getCartDetailsByUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (authentication != null && authentication.isAuthenticated()){
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                return new ResponseEntity<>(cartService.findCartByUserId(customUserDetails.getUser().getUser_id()), HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException("Authentication failed and cannot show user's cart");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CartDTO(), HttpStatus.NOT_FOUND);
        }
    }
}
