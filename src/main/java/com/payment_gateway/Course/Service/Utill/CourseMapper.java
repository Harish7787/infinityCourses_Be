package com.payment_gateway.Course.Service.Utill;

import com.payment_gateway.Course.DTO.Response.CourseResponse;
import com.payment_gateway.Course.Entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseResponse toResponse(Course course) {

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .category(course.getCategory())
                .instructor(course.getInstructor())
                .price(course.getPrice())
                .duration(course.getDuration())
                .level(course.getLevel())
                .image(course.getImage())
                .active(course.getActive())
                .build();
    }
}