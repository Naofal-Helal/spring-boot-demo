package world.array.springboot.webapp.login;

import org.springframework.stereotype.Service;

/**
 * AuthenticationService
 */
@Service
public class AuthenticationService {

  public boolean authenticate(String username, String password) {
    return username.equals("admin") && password.equals("123");
  }
}
