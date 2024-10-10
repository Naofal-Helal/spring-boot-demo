package world.array.springboot.mockitodemo.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * SomeBusinessImplTest
 */
@ExtendWith(MockitoExtension.class)
public class SomeBusinessImplMockTest {

  @Mock
  DataService dataServiceMock;

  @InjectMocks
  SomeBusinessImpl businessImpl;

  @Test
  void findTheGreatestOfThemAll_basicScenario() {
    when(dataServiceMock.retrieveData())
        .thenReturn(new int[] { 25, 15, 5 });
    int result = businessImpl.findTheGreatestOfThemAll();
    assertEquals(25, result);
  }

  @Test
  void findTheGreatestOfThemAll_withOneValue() {
    when(dataServiceMock.retrieveData())
        .thenReturn(new int[] { 35 });
    int result = businessImpl.findTheGreatestOfThemAll();
    assertEquals(35, result);
  }

  @Test
  void findTheGreatestOfThemAll_withEmptyArray() {
    when(dataServiceMock.retrieveData())
        .thenReturn(new int[] {});
    int result = businessImpl.findTheGreatestOfThemAll();
    assertEquals(0, result);
  }
}
