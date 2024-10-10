package world.array.springboot.mockitodemo.business;

import java.util.Arrays;

/**
 * SomeBusinessImpl
 */
public class SomeBusinessImpl {

  DataService dataService;

  public SomeBusinessImpl(DataService dataService) {
    this.dataService = dataService;
  }

  public int findTheGreatestOfThemAll() {
    int[] data = dataService.retrieveData();
    return Arrays.stream(data).max().orElse(0);
  }
}
