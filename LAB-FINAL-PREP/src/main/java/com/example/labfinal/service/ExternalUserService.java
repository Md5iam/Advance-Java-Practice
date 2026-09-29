package com.example.labfinal.service;

import com.example.labfinal.dto.ExternalUserDTO;

import java.util.List;

public interface ExternalUserService {
    List<ExternalUserDTO> fetchExternalUsers();
    void evictCache();
}
