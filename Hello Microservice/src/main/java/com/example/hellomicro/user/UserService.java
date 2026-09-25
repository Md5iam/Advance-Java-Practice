package com.example.hellomicro.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Cacheable(value = "users")
    public List<User> getAll(){
        String url = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }

    @Cacheable(value = "users" , key = "#id")
    public User get(int id){
        IO.println("I am inside get user method");
        String url = "https://jsonplaceholder.typicode.com/users/" + id;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, User.class);
    }
}
