package world.array.springboot.webapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * TodoService
 */
@Service
public class TodoService {
  static List<Todo> todos = new ArrayList<>();

  static {
    todos.add(new Todo(1, "admin", "Learn AWS", LocalDate.now().plusYears(1),
        false));
    todos.add(new Todo(2, "admin", "Learn Azure", LocalDate.now().plusYears(2),
        false));
    todos.add(new Todo(3, "admin", "Learn Full Stack Development",
        LocalDate.now().plusYears(3), false));
  }

  public Optional<Todo> get(long id) {
    return todos.stream().filter(todo -> todo.id == id).findAny();
  }

  public List<Todo> findByUsername(String username) {
    return todos.stream().filter(todo -> todo.username == username).toList();
  }

  public void addTodo(Todo todo) {
    todo.setId(todos.size() + 1);
    todos.add(todo);
  }

  public void deleteTodo(long id) {
    todos.removeIf(todo -> todo.id == id);
  }

  public void updateTodo(Long id, Todo todo) {
    var oldTodo = get(id).get();
    oldTodo.description = todo.description;
    oldTodo.targetDate = todo.targetDate;
  }

}
