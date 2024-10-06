package world.array.springboot.restapi.versioning;

public class PersonV1 {
  public record Name(String first, String last) {
  }

  public Name name;

  public PersonV1(Name name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "PersonV1 [name=" + name + "]";
  }

}
