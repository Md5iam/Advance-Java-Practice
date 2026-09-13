package com.example.siam.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Case {

    @Id
    private String id;

    @NotNull(message = "Title cant be null")
    private String title;
    private String etectiveName;
    private Priority priority;
    private Status status;


}
