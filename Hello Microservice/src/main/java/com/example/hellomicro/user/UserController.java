package com.example.hellomicro.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("users/{id}")
    public User get(@PathVariable int id){
        return userService.get(id);
    }
}