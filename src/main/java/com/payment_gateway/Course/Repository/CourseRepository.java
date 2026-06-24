package com.payment_gateway.Course.Repository;

import com.payment_gateway.Course.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository
        extends JpaRepository<Course, Long> {

    List<Course> findByActiveTrueAndDeletedFalse();

    List<Course> findByActiveFalseAndDeletedFalse();

    List<Course> findTop5ByDeletedFalseOrderByCreatedAtDesc();
    boolean existsByTitle(String title);

    Optional<Course> findByIdAndDeletedFalse(Long id);

    List<Course> findByDeletedFalse();

    List<Course> findByDeletedTrue();
}