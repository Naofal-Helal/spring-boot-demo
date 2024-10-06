package world.array.springboot.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorldController
 */
@RestController
public class HelloWorldController {

  @Autowired
  MessageSource messageSource;

  @GetMapping("/")
  public String hello() {
    return "Hello World";
  }

  @GetMapping("/hello-world-bean")
  public HelloWorldBean helloWorldBean() {
    return new HelloWorldBean();
  }

  @GetMapping("/hello/{name}")
  public HelloWorldBean helloWorldBeanPath(@PathVariable String name) {
    return new HelloWorldBean(String.format("Hello, %s", name));
  }

  @GetMapping("/hello-i18l")
  public String helloI18lized() {
    return messageSource.getMessage(
        "good.morning.message",
        null,
        "Default Message",
        LocaleContextHolder.getLocale());
  }

}
