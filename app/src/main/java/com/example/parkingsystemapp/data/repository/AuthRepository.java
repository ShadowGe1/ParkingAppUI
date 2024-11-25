package com.example.parkingsystemapp.data.repository;

public class AuthRepository {
    private String message;
    private boolean success;

    public AuthRepository() {
        this.message = "";
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
