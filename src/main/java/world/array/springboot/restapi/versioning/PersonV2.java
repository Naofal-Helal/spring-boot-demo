package world.array.springboot.restapi.versioning;

public class PersonV2 {
  public String name;

  public PersonV2(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Person [name=" + name + "]";
  }

}
