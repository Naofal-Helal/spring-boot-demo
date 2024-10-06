package world.array.springboot.restapi.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/**
 * User
 */
public class User {
  Integer id;
  @Size(min = 2, message = "Name shold have at least 2 characters")
  String name;
  @Past(message = "Birth date should be in the past")
  LocalDate birthDate;

  public User() {
  }

  public User(Integer id, String name, LocalDate birthDate) {
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
  }

}
