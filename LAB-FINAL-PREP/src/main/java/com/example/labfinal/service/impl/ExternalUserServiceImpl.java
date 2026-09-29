package com.example.labfinal.service.impl;

import com.example.labfinal.dto.ExternalUserDTO;
import com.example.labfinal.service.ExternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalUserServiceImpl implements ExternalUserService {

    private final RestTemplate restTemplate;

    @Override
    @Cacheable(value = "external_users")
    public List<ExternalUserDTO> fetchExternalUsers() {
        String url = "https://jsonplaceholder.typicode.com/users";
        try {
            ExternalUserDTO[] users = restTemplate.getForObject(url, ExternalUserDTO[].class);
            return users != null ? Arrays.asList(users) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    @CacheEvict(value = "external_users", allEntries = true)
    public void evictCache() {
    }
}
