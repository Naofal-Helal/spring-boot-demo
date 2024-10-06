package world.array.springboot.restapi.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VersioningPersonController
 */
@RestController
public class VersioningPersonController {

  // url based versioning
  @GetMapping("/v1/person")
  public PersonV1 getFirstVersionOfPerson() {
    return new PersonV1(new PersonV1.Name("Bob", "Charlie"));
  }

  @GetMapping("/v2/person")
  public PersonV2 getSecondVersionOfPerson() {
    return new PersonV2("Bob Charlie");
  }

  // query parameter based versioning (I don't prefer this)
  @GetMapping(path = "/person", params = "version=1")
  public PersonV1 getFirstVersionOfPersonByRequestParameter() {
    return getFirstVersionOfPerson();
  }

  @GetMapping(path = "/person", params = "version=2")
  public PersonV2 getSecondVersionOfPersonByRequestParameter() {
    return getSecondVersionOfPerson();
  }

  // request header based versioning
  @GetMapping(path = "/api/person", headers = "X-API-VERSION=1")
  public PersonV1 getFirstVersionOfPersonByRequestHeader() {
    return getFirstVersionOfPerson();
  }

  @GetMapping(path = "/api/person", headers = "X-API-VERSION=2")
  public PersonV2 getSecondVersionOfPersonByRequestHeader() {
    return getSecondVersionOfPerson();
  }

  // content negotiation based versioning (I don't prefer this)
  @GetMapping(path = "/api/person", produces = "application/vnd.world.array-v1+json")
  public PersonV1 getFirstVersionOfPersonByContentNegotiation() {
    return getFirstVersionOfPerson();
  }

  @GetMapping(path = "/api/person", produces = "application/vnd.world.array-v2+json")
  public PersonV2 getSecondVersionOfPersonByContentNegotiation() {
    return getSecondVersionOfPerson();
  }

}
