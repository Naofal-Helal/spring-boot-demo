package world.array.springboot.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import world.array.springboot.demo.course.Course;

/**
 * CourseController
 */
@RestController
public class CourseController {

  @GetMapping("/courses")
  public List<Course> retrieveAllCourses() {
    return Arrays.asList(
        new Course(1, "Learn AWS", "in28minutes"),
        new Course(1, "Learn Azure", "in28minutes"),
        new Course(2, "Learn DevOps", "in28minutes")

    );
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello world";
  }

}
