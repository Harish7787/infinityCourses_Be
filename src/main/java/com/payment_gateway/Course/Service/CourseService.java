package com.payment_gateway.Course.Service;


import com.payment_gateway.Course.DTO.CourseRequest;
import com.payment_gateway.Course.DTO.Response.CourseResponse;
import com.payment_gateway.Course.DTO.Response.DataResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    ResponseEntity<DataResponse<CourseResponse>>
    createCourse(CourseRequest request);

    ResponseEntity<?> getAllCourses();

    ResponseEntity<?> getCourse(Long id);

    ResponseEntity<?> updateCourse(
            Long id,
            CourseRequest request);

    ResponseEntity<?> activateCourse(Long id);

    ResponseEntity<?> deactivateCourse(Long id);

    ResponseEntity<?> softDelete(Long id);

    ResponseEntity<?> restoreCourse(Long id);

    ResponseEntity<?> deletedCourses();
    ResponseEntity<DataResponse<List<CourseResponse>>> getLatestCourses();
}
