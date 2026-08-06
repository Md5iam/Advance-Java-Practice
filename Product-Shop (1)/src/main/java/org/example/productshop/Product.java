package org.example.productshop;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NotNull(message = "ID is required")
    @Min(value = 1, message = "Id must be more then 1")
    @Max(value = 99, message = "Id must be less then 100")
    private int id;
    @NotBlank(message = "Name cant be blank")
    private String name;
    @NotEmpty
    private String category;
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cant be negative")
    private int stock;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1" , message = "Price must be more than 0")
    @DecimalMax(value = "999.99" , message = "Price must be less than 1000")
    private double price;
}
