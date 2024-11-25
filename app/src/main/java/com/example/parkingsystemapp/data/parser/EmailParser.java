package com.example.parkingsystemapp.data.parser;

public class EmailParser {

    public static boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
