package com.website.pristinewash.service;

public interface OTPService {

    public boolean verifyOTP(String phoneNumber, String otp);

    public String saveOTP(String phoneNumber, String opt);
}
