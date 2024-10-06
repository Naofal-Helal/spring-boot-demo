package world.array.springboot.restapi;

public class HelloWorldBean {
  String message = "Hello World";

  public HelloWorldBean() {
  }

  public HelloWorldBean(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "HelloWorldBean [message=" + message + "]";
  }

}
