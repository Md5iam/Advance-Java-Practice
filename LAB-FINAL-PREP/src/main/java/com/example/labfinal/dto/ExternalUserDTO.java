package com.example.labfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
}
