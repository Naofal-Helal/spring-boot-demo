package world.array.springboot.demo.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import world.array.springboot.demo.course.Course;
import world.array.springboot.demo.course.springdatajpa.CourseSpringDataRepository;

/**
 * CourseJdbcCommandLineRunner
 */
@Component
public class CourseCommandLineRunner implements CommandLineRunner {

  // @Autowired
  // CourseJdbcRepository repository;

  // @Autowired
  // CourseJpaRepository repository;

  @Autowired
  CourseSpringDataRepository repository;

  // @Override
  // public void run(String... args) throws Exception {
  // repository.insert(new Course(1, "Learn AWS Now!", "Nofal"));
  // repository.insert(new Course(2, "Learn Azure Now!", "Nofal"));
  // repository.insert(new Course(3, "Learn Spring Now!", "Nofal"));
  //
  // System.out.println(repository.findById(2));
  // }

  @Override
  public void run(String... args) throws Exception {
    repository.save(new Course(1, "Learn AWS Right Now!", "Nofal"));
    repository.save(new Course(2, "Learn Azure Right Now!", "Nofal"));
    repository.save(new Course(3, "Learn Spring Right Now!", "Nofal"));

    System.out.println(repository.findById(2l));

    System.out.println(repository.findByAuthor("Nofal"));
  }

}
