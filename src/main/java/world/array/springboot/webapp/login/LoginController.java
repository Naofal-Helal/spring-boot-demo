package world.array.springboot.webapp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.annotation.Nullable;

/**
 * LoginController
 */
// @Controller
@SessionAttributes("name")
public class LoginController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  AuthenticationService authenticationService;

  @GetMapping("login")
  public String login(ModelMap model) {
    return "login";
  }

  @PostMapping("login")
  public String loginPost(
      ModelMap model,
      @Nullable @RequestParam String username,
      @Nullable @RequestParam String password) {

    model.put("name", username);
    model.put("password", password);

    logger.debug("Login user: {}, password: {}", username, password);
    if (authenticationService.authenticate(username, password)) {
      return "redirect:list-todos";
    } else {
      model.put("errors", new String[] { "invalid credentials" });
      return "login";
    }

  }

}
