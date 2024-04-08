package com.website.pristinewash.serviceImp;

import com.website.pristinewash.DTO.AddressDTO;
import com.website.pristinewash.DTO.UserDTO;
import com.website.pristinewash.entity.Address;
import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.AddressRepository;
import com.website.pristinewash.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void saveAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setZipCode(addressDTO.getZipCode());
        // Set other properties of the address if needed
        // Assume you have a way to get the user ID from the session or request context
        address.setUser(addressDTO.getUser());

        addressRepository.save(address);
    }

    @Override
    public List<AddressDTO> getAddressByUserId(Integer userId) {
        List<Address> addresses = addressRepository.findByUserUser_id(userId);
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address :addresses ) {
            addressDTOS.add(mapToAddressDTO(address));
        }
        return addressDTOS;
    }

    private AddressDTO mapToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setZipCode(address.getZipCode());
        User user = new User();
        user.setUser_id(address.getUser().getUser_id());
        user.setUsername(address.getUser().getUsername());
        addressDTO.setUser(user);
        // Set other properties of the address DTO if needed
        return addressDTO;
    }

}
