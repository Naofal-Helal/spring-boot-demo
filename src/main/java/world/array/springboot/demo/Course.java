package world.array.springboot.demo;

public class Course {
  public long id;
  public String name;
  public String author;

  public Course(long id, String name, String author) {
    this.id = id;
    this.name = name;
    this.author = author;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", name=" + name + ", author=" + author + "]";
  }

}
