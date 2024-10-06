package world.array.springboot.restapi.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// @JsonIgnoreProperties({"field1", "field2"})
@JsonFilter("SomeBeanFilter")
public class SomeBean {
    public String field1;
    // @JsonIgnore
    public String field2;
    public String field3;

    public SomeBean(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }


}
