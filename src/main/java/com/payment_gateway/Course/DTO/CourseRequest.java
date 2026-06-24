package com.payment_gateway.Course.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000)
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Instructor is required")
    private String instructor;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotBlank(message = "Level is required")
    private String level;

    @Size(max = 1000000)
    private String image;
}