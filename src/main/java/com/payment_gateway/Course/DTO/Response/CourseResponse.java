package com.payment_gateway.Course.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponse {

    private Long id;

    private String title;

    private String category;

    private String instructor;

    private Double price;

    private String duration;

    private String level;

    private String image;

    private Boolean active;
}
