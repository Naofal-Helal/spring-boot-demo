package world.array.springboot.restapi.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * UserDaoService
 */
@Service
public class UserDaoService {

  static List<User> users = new ArrayList<>() {
    {
      add(new User(1, "Alice", LocalDate.now().minusYears(18)));
      add(new User(2, "Bob", LocalDate.now().minusYears(17)));
      add(new User(3, "Charlie", LocalDate.now().minusYears(22)));
    }
  };

  public List<User> findUsers() {
    return users;
  }

  public Optional<User> findUser(Integer id) {
    return users.stream().filter(user -> user.id == id).findFirst();
  }

  public int createUser(User user) {
    int id = users.size() + 1;
    user.setId(id);
    users.add(user);
    return id;
  }

  public void deleteUser(Integer id) {
    users.removeIf(user -> user.id == id);
  }

}
