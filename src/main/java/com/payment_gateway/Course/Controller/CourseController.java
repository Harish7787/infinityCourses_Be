package com.payment_gateway.Course.Controller;

import com.payment_gateway.Course.DTO.CourseRequest;
import com.payment_gateway.Course.DTO.Response.CourseResponse;
import com.payment_gateway.Course.DTO.Response.DataResponse;
import com.payment_gateway.Course.Entity.Course;
import com.payment_gateway.Course.Service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addCourse(
            @Valid @RequestBody CourseRequest request) {

        return courseService.createCourse(request);
    }

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAllCourses() {

        return courseService.getAllCourses();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getCourse(
            @PathVariable Long id) {

        return courseService.getCourse(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequest request) {

        return courseService.updateCourse(id, request);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateCourse(
            @PathVariable Long id) {

        return courseService.activateCourse(id);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateCourse(
            @PathVariable Long id) {

        return courseService.deactivateCourse(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(
            @PathVariable Long id) {

        return courseService.softDelete(id);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<?> restoreCourse(
            @PathVariable Long id) {

        return courseService.restoreCourse(id);
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> deletedCourses() {

        return courseService.deletedCourses();
    }
    @GetMapping("/latest")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<DataResponse<List<CourseResponse>>>
    getLatestCourses() {

        return courseService.getLatestCourses();
    }
}
