package world.array.springboot.restapi;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * SpringSecurityPlayResource
 */
@RestController
public class SpringSecurityPlayResource {
  @GetMapping("/csrf-token")
  public CsrfToken retrieveCsrfToken(HttpServletRequest request) {
    return (CsrfToken) request.getAttribute("_csrf");
  }

}
