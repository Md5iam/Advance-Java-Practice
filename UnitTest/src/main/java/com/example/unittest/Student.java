package com.example.unittest;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Student {
    private int id;
    private String name;
    private String email;

    @Builder.Default
    private boolean active = false ;

}
