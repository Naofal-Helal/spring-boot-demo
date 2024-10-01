package world.array.springboot.webapp.todo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * WelcomeController
 */
@Controller
@SessionAttributes("name")
public class WelcomeController {

  @GetMapping("/")
  public String index(ModelMap model) {
    model.put("name", getLoggedInUsername());
    return "todo/welcome";
  }

  private Object getLoggedInUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

}
