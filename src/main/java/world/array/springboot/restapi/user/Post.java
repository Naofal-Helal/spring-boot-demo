package world.array.springboot.restapi.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Post {
    @Id
    @GeneratedValue
    int id;
    @Size(min = 10)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    User user;

    public Post() {
    }

    public Post(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", description='" + description + '\'' + '}';
    }
}
