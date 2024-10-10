package world.array.springboot.restapi.todo;

import java.time.Duration;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

/**
 * TodoResource
 */
@RestController
public class TodoResource {
  TodoRepository repository;

  public TodoResource(TodoRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/users/{username}/todos-slow")
  public ResponseEntity<List<Todo>> retrieveTodosSlow(@PathVariable String username) {
    try {
      // artificial delay to test async loading in the client
      Thread.sleep(Duration.ofMillis(500));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    var todos = repository.findByUsername(username);
    if (todos.size() == 0)
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(todos);
  }

  @GetMapping("/users/{username}/todos")
  @PreAuthorize("#username == authentication.name")
  @RolesAllowed({ "ADMIN", "USER" })
  public ResponseEntity<List<Todo>> retrieveTodos(@PathVariable String username) {
    var todos = repository.findByUsername(username);
    if (todos.size() == 0)
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(todos);
  }

  @GetMapping("/users/{username}/todos/{id}")
  @PreAuthorize("#username == authentication.name")
  @RolesAllowed({ "ADMIN", "USER" })
  public ResponseEntity<Todo> retrieveTodo(@PathVariable String username, @PathVariable Long id) {
    var todo = repository.findById(id).orElse(null);
    if (todo == null) {
      return ResponseEntity.notFound().build();
    } else if (!todo.username.equals(username)) {
      // unauthorized
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(todo);
  }

  @DeleteMapping("/users/{username}/todos/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable Long id) {
    var todo = repository.findById(id).orElse(null);
    if (todo == null) {
      return ResponseEntity.notFound().build();
    } else if (!todo.username.equals(username)) {
      // unauthorized
      return ResponseEntity.notFound().build();
    }

    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/users/{username}/todos/{id}")
  public ResponseEntity<Todo> putTodo(
      @PathVariable String username,
      @PathVariable Long id,
      @RequestBody Todo todo) {
    if (todo == null) {
      return ResponseEntity.notFound().build();
    } else if (!todo.username.equals(username)) {
      // unauthorized
      return ResponseEntity.notFound().build();
    }
    var savedTodo = repository.save(todo);
    return ResponseEntity.ok(savedTodo);
  }

  @PostMapping("/users/{username}/todos")
  public ResponseEntity<Todo> createTodo(
      @PathVariable String username,
      @RequestBody Todo todo) {
    var savedTodo = repository.save(todo);
    return ResponseEntity.ok(savedTodo);
  }
}
