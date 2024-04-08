package com.website.pristinewash.serviceImp;

import com.website.pristinewash.DTO.CartDTO;
import com.website.pristinewash.DTO.CartItemDTO;
import com.website.pristinewash.DTO.ProductDTO;
import com.website.pristinewash.entity.Cart;
import com.website.pristinewash.entity.CartItem;
import com.website.pristinewash.entity.Product;
import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.CartRepository;
import com.website.pristinewash.service.CartService;
import com.website.pristinewash.service.ProductService;
import com.website.pristinewash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Override
    public String addProductInCart(Integer userId, Integer productId) {
        // Retrieve the user's cart from the database, or create a new one if it doesn't exist
        Cart cart = cartRepository.findByUserUserId(userId).orElse(new Cart());

        if(cart.getId() == null) {
            User user = userService.getUserById(userId);
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
        }

        Product product = productService.getProductById(productId);
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item->item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if(existingCartItem != null ) {
            existingCartItem.setQuantity(existingCartItem.getQuantity()+1);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setCart(cart);

            cart.getCartItems().add(newCartItem);
        }
//        List<Product> productSet ;
//        if(cart.getProducts() !=null) {
//            productSet = cart.getProducts();
//        } else {
//            productSet = new ArrayList<>();
//        }
//        productSet.add(product);
//        cart.setProducts(productSet);
        cartRepository.save(cart);
        return  cart.toString() + "Product Added " + product.getName();
    }

    @Override
    public CartDTO findCartByUserId(Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserUserId(userId);

        if(optionalCart.isPresent()) {
            return convertCartToCartDTO(optionalCart.get());
        } else {
            throw new UsernameNotFoundException("Not able to find cart with user id " + userId);
        }
    }

    CartDTO convertCartToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        //Set cart Id
        cartDTO.setId(cart.getId());


        List<CartItem> cartItemList = cart.getCartItems();
        if(!cartItemList.isEmpty()) {
            List<CartItemDTO> cartItemDtoList = new ArrayList<>();
            CartItemDTO cartItemDTO ;
            double totalPrice =0;
            for(CartItem cartItem : cartItemList) {
                cartItemDTO = new CartItemDTO();
                cartItemDTO.setId(cartItem.getId());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(cartItem.getProduct().getId());
                productDTO.setName(cartItem.getProduct().getName());
                productDTO.setPrice(cartItem.getProduct().getPrice());

                cartItemDTO.setProductDto(productDTO);
                cartItemDTO.setQuantity(cartItem.getQuantity());
                //calculate the total price per product according to quantity.
                cartItemDTO.setCartItemTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
                //calculate the total cart price.
                totalPrice = totalPrice + (cartItem.getQuantity() * cartItem.getProduct().getPrice());

                cartItemDtoList.add(cartItemDTO);
            }

            cartDTO.setCartItemsDto(cartItemDtoList);
            cartDTO.setTotalPrice(totalPrice);
            return cartDTO;
        } else {
            cartDTO.setCartItemsDto(new ArrayList<>());
            return cartDTO;
        }

    }
}
