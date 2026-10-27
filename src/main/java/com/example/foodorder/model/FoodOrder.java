
package com.example.foodorder.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class FoodOrder {

    private Long orderId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Food item cannot be empty")
    private String foodItem;

    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Enter a valid email address")
    private String customerEmail;

    @NotBlank(message = "Customer mobile number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Enter a valid 10-digit Indian mobile number"
    )
    private String customerMobile;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private boolean available = true;
}
