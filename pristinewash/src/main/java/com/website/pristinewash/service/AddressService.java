package com.website.pristinewash.service;

import com.website.pristinewash.DTO.AddressDTO;

import java.util.List;

public interface AddressService {
    void saveAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddressByUserId(Integer userId);
}
