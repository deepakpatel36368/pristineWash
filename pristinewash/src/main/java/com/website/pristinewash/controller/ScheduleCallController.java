package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.service.ScheduleCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleCallController {

    @Autowired
    ScheduleCallService scheduleCallService;

    @Autowired
    OTPController otpController;

    public ResponseEntity<String> ScheduleCallToUser(final ScheduleCallDTO scheduleCallDTO) {
        try {
            scheduleCallService.saveScheduleCall(scheduleCallDTO);
            final String smsMessage = "Hey " + scheduleCallDTO.getName()
                    + " you will get back from Pristine Wash expert on " + scheduleCallDTO.getDate()
                    + " at " + scheduleCallDTO.getTime() +". Kindly be available. \n Thanks Pristine Wash.";
            //Trigger message to the user for call confirmation as a reminder
            ResponseEntity<String> response = otpController.sendWhatsAppWithTwilio(scheduleCallDTO.getUsername(), smsMessage);

            final String adminPhoneNumber = "8130036368"; // this list can be fetched by DB for user role as ADMIN
            final String adminSmsMessage = "Hurry !! , Please call " + scheduleCallDTO.getName() + " : " + scheduleCallDTO.getUsername()
                    + " on " + scheduleCallDTO.getDate() + " at " + scheduleCallDTO.getTime() + "\n Query - " + scheduleCallDTO.getMessage() + ". \n Kindly call the user on time. \n Thanks Pristine Wash.";

            if(response.getStatusCode() == HttpStatus.OK) {
                //send sms to ADMIN to inform that call is schedule
                ResponseEntity<String> adminResponse = otpController.sendWhatsAppWithTwilio(adminPhoneNumber, adminSmsMessage);
                if(adminResponse.getStatusCode() == HttpStatus.OK) {
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
}
