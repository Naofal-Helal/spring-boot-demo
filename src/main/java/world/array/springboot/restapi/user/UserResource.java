package world.array.springboot.restapi.user;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

/**
 * UserResource
 */
//@RestController
public class UserResource {

  UserDaoService userDaoService;

  public UserResource(UserDaoService userDaoService) {
    this.userDaoService = userDaoService;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return userDaoService.findUsers();
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  class UserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
      return "User could not be found";
    }

  }

  @GetMapping("/users/{id}")
  public EntityModel<User> getUser(@PathVariable Integer id) {
    var user = userDaoService.findUser(id).orElse(null);
    if (user == null) {
      throw new UserNotFoundException();
    }
    var entityModel = EntityModel.of(user);
    var linkBuilder = linkTo(methodOn(this.getClass()).getUsers());
    entityModel.add(linkBuilder.withRel("all-users"));
    return entityModel;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    int id = userDaoService.createUser(user);
    var location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
          .buildAndExpand(id)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Integer id) {
    userDaoService.deleteUser(id);
  }

}
