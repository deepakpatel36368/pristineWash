package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.service.ScheduleCallService;
import com.website.pristinewash.serviceImp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScheduleCallController {

    @Autowired
    ScheduleCallService scheduleCallService;

    @Autowired
    OTPController otpController;

    public ResponseEntity<String> ScheduleCallToUser(final ScheduleCallDTO scheduleCallDTO) {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final String username ;
            if (authentication != null && authentication.isAuthenticated()){
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                username = customUserDetails.getUser().getUsername();
            } else {
                throw new UsernameNotFoundException("Authentication failed, Not able to extract username");
            }
            scheduleCallService.saveScheduleCall(scheduleCallDTO, username);
            final String smsMessage = "Hey " + scheduleCallDTO.getAddressDTO().getName()
                    + " you will get back from Pristine Wash expert on " + scheduleCallDTO.getDate()
                    + " at " + scheduleCallDTO.getTime() +". Kindly be available. \n Thanks Pristine Wash.";
            //Trigger message to the user for call confirmation as a reminder
            ResponseEntity<String> response = otpController.sendWhatsAppWithTwilio(scheduleCallDTO.getUsername(), smsMessage);

            //final String adminPhoneNumber = "8130036368";
            // this list can be fetched by DB for user role as ADMIN
            List<String> adminPhoneNumberList = List.of("8130036368","9999824685");
            final String adminSmsMessage = "Hurry !! , Please call " + scheduleCallDTO.getAddressDTO().getName() + " : " + scheduleCallDTO.getUsername()
                    + " on " + scheduleCallDTO.getDate() + " at " + scheduleCallDTO.getTime() + "\n Address - " + scheduleCallDTO.getAddressDTO().getStreet() + ". \n Kindly call the user on time. \n Thanks Pristine Wash.";

            if(response.getStatusCode() == HttpStatus.OK) {
                ResponseEntity<String> adminResponse = null;
                for(String adminPhoneNumber : adminPhoneNumberList) {
                    //send sms to ADMIN to inform that call is schedule
                    adminResponse = otpController.sendWhatsAppWithTwilio(adminPhoneNumber, adminSmsMessage);
                }
                if(adminResponse!= null && adminResponse.getStatusCode() == HttpStatus.OK) {
                    return new ResponseEntity<>(smsMessage, HttpStatus.OK);
                } else {
                    //sms not sent to ADMIN but send to user
                    return new ResponseEntity<>(smsMessage, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Message not sent. Something went wrong. Please retry Thanks" , HttpStatus.BAD_REQUEST);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<List<ScheduleCallDTO>> findAllScheduleCall() {
        try {
           return new ResponseEntity<>(scheduleCallService.findAllScheduleCall(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

}
