package world.array.springboot.webapp.todo;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContext;

import jakarta.validation.Valid;
import world.array.springboot.webapp.BindingResultExtension;

/**
 * TodoController
 */
@Controller
@SessionAttributes("name")
public class TodoController {
  @Autowired
  TodoRepository todoRepository;

  @GetMapping("/list-todos")
  public String listTodos(ModelMap model) {
    var username = getLoggedInUsername();
    model.put("todos", todoRepository.findByUsername(username));
    return "todo/all-todos";
  }

  private String getLoggedInUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  // @Autowired
  RequestContext requestContext;

  @GetMapping("/add-todo")
  public String addTodo(ModelMap model) {
    model.put("todo", new Todo(
        0,
        "",
        "",
        LocalDate.now().plusDays(1),
        false));
    // requestContext.getWebApplicationContext().getBean("_csrf")
    return "todo/todo";
  }

  @PostMapping("/add-todo")
  public String addTodoPost(
      ModelMap model,
      @Valid @ModelAttribute Todo todo,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      var errors = new HashMap<String, Object>() {
        {
          for (var fielderror : bindingResult.getFieldErrors()) {
            put(fielderror.getField(), fielderror.getDefaultMessage());
          }
        }
      };
      model.put("errors", errors);
      return "todo/todo";
    } else {
      todo.setUsername(getLoggedInUsername());
      todoRepository.save(todo);
      return "redirect:/list-todos";
    }
  }

  @GetMapping("/delete-todo/{id}")
  public String deleteTodo(@PathVariable Long id) {
    todoRepository.deleteById(id);
    return "redirect:/list-todos";
  }

  @GetMapping("/update-todo/{id}")
  public String updateTodo(ModelMap model, @PathVariable Long id) {
    var todo = todoRepository.findById(id).get();
    model.put("todo", todo);
    return "todo/todo";
  }

  @PostMapping("/update-todo/{id}")
  public String updateTodoPost(
      ModelMap model,
      @PathVariable Long id,
      @Valid @ModelAttribute Todo todo,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      var errors = BindingResultExtension.fieldErrorMessages(bindingResult);
      model.put("errors", errors);
      return "todo/update-todo";
    } else {
      // var oldTodo = todoRepository.findById(id).get();
      // oldTodo.description = todo.description;
      // oldTodo.targetDate = todo.targetDate;
      // oldTodo.done = todo.done;
      todoRepository.save(todo);
      return "redirect:/list-todos";
    }
  }

}
