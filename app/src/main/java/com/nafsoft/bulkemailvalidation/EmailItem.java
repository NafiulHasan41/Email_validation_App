package com.nafsoft.bulkemailvalidation;

public class EmailItem {

    private String email;
    private String status;

    public EmailItem(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
