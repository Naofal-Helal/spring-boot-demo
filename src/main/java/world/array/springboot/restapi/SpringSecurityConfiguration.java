package world.array.springboot.restapi;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurityConfiguration
 */
@Configuration
public class SpringSecurityConfiguration {

  @Bean
  public InMemoryUserDetailsManager createUserDetailsManager() {

    UserDetails user1 = createNewUser("user", "123");
    UserDetails user2 = createNewUser("bob", "123");

    return new InMemoryUserDetailsManager(user1, user2);
  }

  private UserDetails createNewUser(String username, String password) {
    UserDetails userDetails = User.builder()
        .username(username)
        .passwordEncoder(passwordEncoder()::encode)
        .password(password)
        .roles("USER", "ADMIN")
        .build();
    return userDetails;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.disable());
    http.csrf(csrf -> csrf.disable());
    http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
    http.formLogin(withDefaults());
    return http.build();
  }

}
