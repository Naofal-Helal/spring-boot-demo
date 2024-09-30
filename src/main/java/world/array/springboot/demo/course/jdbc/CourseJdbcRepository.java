package world.array.springboot.demo.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import world.array.springboot.demo.course.Course;

/**
 * CourseJdbcRepository
 */
@Repository
public class CourseJdbcRepository {
  @Autowired
  JdbcTemplate springJdbcTemplate;

  static final String INSERT_QUERY = """
      insert into course values (?, ?, ?);
      """;

  static final String SELECT_QUERY = "select * from course where id = ?";

  public void insert(Course course) {
    springJdbcTemplate.update(INSERT_QUERY, course.id, course.name, course.author);
  }

  public Course findById(long id) {
    return springJdbcTemplate.queryForObject(
        SELECT_QUERY,
        new BeanPropertyRowMapper<>(Course.class),
        id);
  }
}
