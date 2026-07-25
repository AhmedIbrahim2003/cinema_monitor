package com.ahmed.monitor.models;

public class CheckResult {

    private boolean available;

    private String message;

    public CheckResult(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getMessage() {
        return message;
    }

}
