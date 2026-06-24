package com.payment_gateway.Course.Service.Impl;

import com.payment_gateway.Course.exception.BadRequestException;
import com.payment_gateway.Course.DTO.CourseRequest;
import com.payment_gateway.Course.DTO.Response.CourseResponse;
import com.payment_gateway.Course.DTO.Response.DataResponse;
import com.payment_gateway.Course.DTO.Response.MessageResponse;
import com.payment_gateway.Course.Entity.Course;
import com.payment_gateway.Course.Repository.CourseRepository;
import com.payment_gateway.Course.Service.CourseService;
import com.payment_gateway.Course.Service.Utill.CourseMapper;
import com.payment_gateway.Course.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl
        implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    @Override
    public ResponseEntity<DataResponse<CourseResponse>>
    createCourse(CourseRequest request) {

        if(courseRepository.existsByTitle(request.getTitle())) {
            throw new BadRequestException("Course title already exists");
        }


        System.out.println("TITLE = " + request.getTitle().length());
        System.out.println("CATEGORY = " + request.getCategory().length());
        System.out.println("DESCRIPTION = " + request.getDescription().length());
        System.out.println("IMAGE = " + request.getImage().length());

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .instructor(request.getInstructor())
                .price(request.getPrice())
                .duration(request.getDuration())
                .level(request.getLevel())
                .image(request.getImage())
                .active(true)
                .deleted(false)
                .build();

        Course saved = courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new DataResponse<>(
                                true,
                                HttpStatus.CREATED,
                                "Course created successfully",
                                courseMapper.toResponse(saved)
                        )
                );
    }

    @Override
    public ResponseEntity<?> getAllCourses() {

        return ResponseEntity.ok(
                new DataResponse<>(
                        true,
                        HttpStatus.OK,
                        "Courses Found",
                        courseRepository.findByDeletedFalse()
                )
        );
    }

    @Override
    public ResponseEntity<DataResponse<CourseResponse>>
    getCourse(Long id) {

        Course course = courseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Course not found"
                        )
                );

        return ResponseEntity.ok(
                new DataResponse<>(
                        true,
                        HttpStatus.OK,
                        "Course found",
                        courseMapper.toResponse(course)
                )
        );
    }
    @Override
    public ResponseEntity<?> updateCourse(
            Long id,
            CourseRequest request) {

        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setInstructor(request.getInstructor());
        course.setPrice(request.getPrice());
        course.setDuration(request.getDuration());
        course.setLevel(request.getLevel());
        course.setImage(request.getImage());

        courseRepository.save(course);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "Course Updated Successfully"
                )
        );
    }

    @Override
    public ResponseEntity<?> activateCourse(Long id) {

        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );

        course.setActive(true);

        courseRepository.save(course);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "Course Activated"
                )
        );
    }

    @Override
    public ResponseEntity<?> deactivateCourse(Long id) {

        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );

        course.setActive(false);

        courseRepository.save(course);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "Course Deactivated"
                )
        );
    }

    @Transactional
    @Override
    public ResponseEntity<MessageResponse>
    softDelete(Long id) {

        Course course = courseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Course not found"
                        )
                );

        if (course.getDeleted()) {

            throw new BadRequestException(
                    "Course already deleted"
            );
        }

        course.setDeleted(true);

        courseRepository.save(course);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "Course deleted successfully"
                )
        );
    }
    @Override
    public ResponseEntity<DataResponse<List<CourseResponse>>> getLatestCourses() {

        List<Course> courses =
                courseRepository.findTop5ByDeletedFalseOrderByCreatedAtDesc();

        List<CourseResponse> response =
                courses.stream()
                        .map(courseMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                new DataResponse<>(
                        true,
                        HttpStatus.OK,
                        "Latest courses fetched",
                        response
                )
        );
    }
    @Override
    public ResponseEntity<?> restoreCourse(Long id) {

        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );

        course.setDeleted(false);

        courseRepository.save(course);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "Course Restored Successfully"
                )
        );
    }

    @Override
    public ResponseEntity<?> deletedCourses() {

        return ResponseEntity.ok(
                new DataResponse<>(
                        true,
                        HttpStatus.OK,
                        "Deleted Courses",
                        courseRepository.findByDeletedTrue()
                )
        );
    }
}