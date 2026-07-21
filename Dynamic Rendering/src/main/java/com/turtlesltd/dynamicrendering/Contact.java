package com.turtlesltd.dynamicrendering;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String email;
    private String pass;
    private String message;
    private Integer id;
    private Double grade;
}
