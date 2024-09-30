package world.array.springboot.demo.course.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import world.array.springboot.demo.course.Course;

/**
 * CourseSpringDataRepository
 */
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
  List<Course> findByAuthor(String author);
}
