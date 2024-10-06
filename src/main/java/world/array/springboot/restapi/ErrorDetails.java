package world.array.springboot.restapi;

import java.time.LocalDateTime;

/**
 * ErrorDetails
 */
public class ErrorDetails {
  String title;
  int status;
  String detail;
  LocalDateTime timestamp;

  public ErrorDetails() {
  }

  public ErrorDetails(String title, int status, String detail, LocalDateTime timestamp) {
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.timestamp = timestamp;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
