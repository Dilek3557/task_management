package com.dilekkaraca.taskmanagament.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/tasks/test")
    public String test() {
        return "JWT OK - authenticated request";
    }
}
