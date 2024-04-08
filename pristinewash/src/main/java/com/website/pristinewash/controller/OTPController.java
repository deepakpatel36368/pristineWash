package com.website.pristinewash.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.website.pristinewash.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OTPController {

    @Autowired
    OTPService otpService;

    @PostMapping("/{phonenumber}")
    public ResponseEntity<String> generateOTP(@PathVariable String phonenumber) {
        //Generate the random 6 digit OTP
        String otp = generateRandomOTP();
        final String smsMessage = "Your Pristine Wash verification code is : " + otp;
        ResponseEntity<String> response = sendWhatsAppWithTwilio(phonenumber, smsMessage);

        try {
            if(response.getStatusCode() == HttpStatus.OK) {
                otpService.saveOTP(phonenumber, otp);
            } else {
                return new ResponseEntity<>("Message not sent. Something went wrong. Please retry Thanks" , HttpStatus.BAD_REQUEST);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.OK);
        }
        //store the otp in the DB
        return new ResponseEntity<>("OTP ###" + otp +"### has been generated for phone number : " + phonenumber , HttpStatus.OK);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public ResponseEntity<String> sendWhatsAppWithTwilio(final String phoneNumber, final String smsMessage) {
        try {
            //Move below properties to the application properties file

            final String TWILIO_ACCOUNT_SID = "";
            final String TWILIO_AUTH_TOKEN = "";
            final String senderPhoneNumber = "+91" + phoneNumber;
            final String twilioPhoneNumber = "+12569739896";
            Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber(senderPhoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            smsMessage)
                    .create();
            if (message.getErrorMessage() == null) {
                return new ResponseEntity<>(message.getStatus().toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(message.getStatus().toString(), HttpStatus.BAD_REQUEST);
            }
            //Remove below catch block after twilio upgrade
        } catch (Exception e) {
            log.info(phoneNumber + " will get registered in to the database with otp. Otp not send due to twilio not upgrade.");
            return new ResponseEntity<>("new number need to upgrade twilio account", HttpStatus.OK);
        }
    }

}
