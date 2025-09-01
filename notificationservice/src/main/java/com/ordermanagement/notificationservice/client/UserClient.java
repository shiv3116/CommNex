package com.ordermanagement.notificationservice.client;

import com.ordermanagement.notificationservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice", url="http://localhost:8081/ordermanagement")
public interface UserClient {

    @GetMapping("/user/{id}")
    UserResponse getUserById(@PathVariable long id);
}
