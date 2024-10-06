package world.array.springboot.restapi.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * UserResource
 */
@RestController
public class UserJpaResource {

    UserRepository userRepository;
    PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
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
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        var entityModel = EntityModel.of(user);
        var linkBuilder = linkTo(methodOn(this.getClass()).getUsers());
        entityModel.add(linkBuilder.withRel("all-users"));
        linkBuilder = linkTo(methodOn(this.getClass()).getUserPosts(id));
        entityModel.add(linkBuilder.withRel("posts"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user = userRepository.save(user);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable Integer id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable Integer id,
                                     @Valid @RequestBody Post post
    ) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }


        post.user = user;
        postRepository.save(post);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/post/{id}")
                .buildAndExpand(post.id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
