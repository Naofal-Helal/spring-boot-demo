package world.array.springboot.demo.course;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * CourseJpaRepository
 */
@Repository
@Transactional
public class CourseJpaRepository {

  @PersistenceContext
  EntityManager entityManager;

  public void insert(Course course) {
    entityManager.merge(course);
  }

  public Course findById(long id) {
    return entityManager.find(Course.class, id);
  }

}
