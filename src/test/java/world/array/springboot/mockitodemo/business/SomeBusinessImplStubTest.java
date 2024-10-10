package world.array.springboot.mockitodemo.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * SomeBusinessImplTest
 */
public class SomeBusinessImplStubTest {

  @Test
  void findTheGreatestOfThemAll_basicScenario() {
    DataService dataService = new DataServiceStub1();
    SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataService);
    int result = businessImpl.findTheGreatestOfThemAll();
    assertEquals(25, result);
  }

  @Test
  void findTheGreatestOfThemAll_withOneValue() {
    DataService dataService = new DataServiceStub2();
    SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataService);
    int result = businessImpl.findTheGreatestOfThemAll();
    assertEquals(25, result);
  }
}

class DataServiceStub1 implements DataService {

  @Override
  public int[] retrieveData() {
    return new int[] { 25, 15, 5, 9 };
  }

}

class DataServiceStub2 implements DataService {

  @Override
  public int[] retrieveData() {
    return new int[] { 25 };
  }

}
