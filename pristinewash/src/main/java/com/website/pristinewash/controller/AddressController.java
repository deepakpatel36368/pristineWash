package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.AddressDTO;
import com.website.pristinewash.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<Void> saveAddress(@RequestBody AddressDTO addressDTO) {
        addressService.saveAddress(addressDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressByUserId(@PathVariable Integer userId) {
        List<AddressDTO> addresses = addressService.getAddressByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

}
