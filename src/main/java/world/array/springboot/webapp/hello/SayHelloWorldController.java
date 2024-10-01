package world.array.springboot.webapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SayHelloWorldController
 */
@Controller
public class SayHelloWorldController {

  @GetMapping("/say-hello")
  @ResponseBody
  public String sayHello() {
    return "Hello! 👋 What are you doing today?";
  }

  @GetMapping("/say-hello-jsp")
  public String sayHelloJsp() {
    return "sayHello";
  }

}
