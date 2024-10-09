package world.array.springboot.restapi.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TodoRepository
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findByUsername(String username);

}
