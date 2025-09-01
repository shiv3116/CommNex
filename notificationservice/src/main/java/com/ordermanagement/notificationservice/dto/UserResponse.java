package com.ordermanagement.notificationservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String password;
}
