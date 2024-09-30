package world.array.springboot.demo.course;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {
  @Id
  public long id;
  public String name;
  public String author;

  @SuppressWarnings("unused")
  private Course() {
  }

  public Course(long id, String name, String author) {
    this.id = id;
    this.name = name;
    this.author = author;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", name=" + name + ", author=" + author + "]";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

}
