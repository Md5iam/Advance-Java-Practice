package org.example.productshop;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @NotNull(message = "ID is required")
    @Size(min = 1, max = 100)
    private int id;
    @NotBlank(message = "name cant be blank")
    private String name;
    @NotEmpty
    private String category;
    @PositiveOrZero(message = "Stock cant be negative")
    private int stock;
    private double price;
}
