package world.array.springboot.webapp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 * BindingResultExtension
 */
public class BindingResultExtension {
  public static Map<String, String> allErrorMessages(BindingResult bindingResult) {
    return new HashMap<>() {
      {
        for (var error : bindingResult.getAllErrors()) {
          put(error.getObjectName(), error.getDefaultMessage());
        }
      }
    };
  }

  public static Map<String, String> fieldErrorMessages(BindingResult bindingResult) {
    return new HashMap<>() {
      {
        for (var error : bindingResult.getFieldErrors()) {
          put(error.getField(), error.getDefaultMessage());
        }
      }
    };
  }
}
