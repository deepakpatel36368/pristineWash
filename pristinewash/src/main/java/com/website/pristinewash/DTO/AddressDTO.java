package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Integer id;

    private String street;

    private String city;

    private String zipCode;

    private User user;
}
