package world.array.springboot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CourseController
 */
@RestController
public class CurrencyConfigurationController {

  @Autowired
  private CurrencyServiceConfiguration configuration;

  @GetMapping("/currency-configuration")
  public CurrencyServiceConfiguration retrieveAllCourses() {
    return configuration;
  }

}
