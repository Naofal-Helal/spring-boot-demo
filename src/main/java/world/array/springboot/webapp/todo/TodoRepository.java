package world.array.springboot.webapp.todo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TodoRepository
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
