package com.example.parkingsystemapp.data.repository;

public class RegisterRepository {

    private String message;
    private boolean success;
    private String errorCode;

    public RegisterRepository() {
        this.errorCode = "";
        this.message = "";
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
