package world.array.springboot.aop.business;

import org.springframework.stereotype.Service;
import world.array.springboot.aop.annotations.TrackTime;
import world.array.springboot.aop.data.DataService1;

import java.util.Arrays;

@Service
public class BusinessService1 {
    DataService1 dataService1;

    public BusinessService1(DataService1 dataService1) {
        this.dataService1 = dataService1;
    }

    @TrackTime
    public int calculateMax() {
        return Arrays.stream(dataService1.retrieveData()).max().orElse(0);
    }
}
