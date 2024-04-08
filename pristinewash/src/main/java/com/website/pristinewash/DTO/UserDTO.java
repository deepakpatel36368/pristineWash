package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.Address;
import com.website.pristinewash.entity.Cart;
import com.website.pristinewash.entity.Product;
import com.website.pristinewash.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Integer user_id;

    private String username;

    private String password;

    private Cart cart;

    private Address address;

    private Set<Product> products;

    private Set<Role> roles;
}
