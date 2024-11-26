package com.example.parkingsystemapp.data.parser;

public class PhoneParser {

    public static boolean isPhoneValid(String phone) {
        String phonePattern = "^(06|07)\\d{7}$";
        return phone.matches(phonePattern);
    }
}
