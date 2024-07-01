package com.website.pristinewash.serviceImp;

import com.website.pristinewash.DTO.AddressDTO;
import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.DTO.UserDTO;
import com.website.pristinewash.entity.Address;
import com.website.pristinewash.entity.ScheduleCall;
import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.AddressRepository;
import com.website.pristinewash.repository.ScheduleCallRepository;
import com.website.pristinewash.repository.UserRepository;
import com.website.pristinewash.service.ScheduleCallService;
import com.website.pristinewash.utility.ScheduleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleCallServiceImpl implements ScheduleCallService {

    @Autowired
    ScheduleCallRepository scheduleCallRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void saveScheduleCall(ScheduleCallDTO scheduleCallDTO, String userName) {
        ScheduleCall scheduleCall = mapScheduleCallDTOTOScheduleCall(scheduleCallDTO);
        saveAddressOfUser(scheduleCallDTO.getAddressDTO(), userName);

        scheduleCallRepository.save(scheduleCall);
    }

    @Override
    public List<ScheduleCallDTO> findAllScheduleCall() throws Exception {
        List<ScheduleCall> scheduleCallList = scheduleCallRepository.findAll();
        if(scheduleCallList.isEmpty()) {
            throw new Exception("Schedule call list is empty ");
        }
        List<ScheduleCallDTO> scheduleCallDTOList = new ArrayList<>();
        for(ScheduleCall scheduleCall : scheduleCallList) {
            scheduleCallDTOList.add(mapScheduleCallToScheduleCallDTO(scheduleCall));
        }
        return scheduleCallDTOList;
    }

    @Override
    public void updateStateById(Long id, ScheduleState scheduleState) {
        scheduleCallRepository.updateStateById(id, scheduleState);
    }

    public void saveAddressOfUser(AddressDTO addressDTO, String userName) {
        Address address;
        User user = userRepository.findByUsername(userName);
        if(user != null) {
            List<Address> addressListTmp = addressRepository.findByUserUsername(userName);
            address = new Address();
            if(addressListTmp.isEmpty()) {
                //Address is still not added by user
                address = mapAddressDTOToAddress(addressDTO, user, true, null);
            } else {
                //Address already exist by User, updated the user's address
                address = mapAddressDTOToAddress(addressDTO, user, false, addressListTmp.get(0).getId());
            }
            //address add or updated for existing user
            addressRepository.save(address);
        } else {
            throw new UsernameNotFoundException("user not found with username " + userName);
        }
    }

    public Address mapAddressDTOToAddress(AddressDTO addressDTO, User user, Boolean newAddress, Integer addressId) {
        Address address = new Address();
        if(!newAddress) {
            address.setId(addressId);
        }
        address.setName(addressDTO.getName());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setZipCode(addressDTO.getZipCode());
        address.setUser(user);
        return address;
    }

    public ScheduleCall mapScheduleCallDTOTOScheduleCall(ScheduleCallDTO scheduleCallDTO) {
        ScheduleCall scheduleCall ;
        User user = userRepository.findByUsername(scheduleCallDTO.getUsername());
        if(user != null) {
            scheduleCall = new ScheduleCall();
            scheduleCall.setUser(user);
            scheduleCall.setDate(scheduleCallDTO.getDate());
            scheduleCall.setTime(scheduleCallDTO.getTime());
            scheduleCall.setMessage(scheduleCallDTO.getMessage());

            return  scheduleCall;
        } else {
            throw new UsernameNotFoundException("User is trying to schedule call to different number " + scheduleCallDTO.getUsername());
        }

    }

    public ScheduleCallDTO mapScheduleCallToScheduleCallDTO(ScheduleCall scheduleCall) {
        ScheduleCallDTO scheduleCallDTO ;
        AddressDTO addressDTO;
        UserDTO userDTO;
        if(scheduleCall != null) {
            scheduleCallDTO = new ScheduleCallDTO();
            addressDTO = new AddressDTO();
            userDTO = new UserDTO();

            //Set user DTO details
            //Set Phone Number
            userDTO.setUsername(scheduleCall.getUser().getUsername());
            scheduleCallDTO.setUserDTO(userDTO);

            //Set User Address
            addressDTO.setName(scheduleCall.getUser().getAddress().getName());
            addressDTO.setStreet(scheduleCall.getUser().getAddress().getStreet());
            addressDTO.setCity(scheduleCall.getUser().getAddress().getCity());
            addressDTO.setZipCode(scheduleCall.getUser().getAddress().getZipCode());
            scheduleCallDTO.setAddressDTO(addressDTO);

            //Set Schedule call data
            scheduleCallDTO.setDate(scheduleCall.getDate());
            scheduleCallDTO.setTime(scheduleCall.getTime());
            scheduleCallDTO.setMessage(scheduleCall.getMessage());
            scheduleCallDTO.setId(scheduleCall.getId());
            scheduleCallDTO.setScheduleState(scheduleCall.getState());
            return scheduleCallDTO;
        } else {
            throw new UsernameNotFoundException("No Schedule Entry for user");
        }

    }

}
