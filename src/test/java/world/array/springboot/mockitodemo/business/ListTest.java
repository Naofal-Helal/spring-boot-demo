package world.array.springboot.mockitodemo.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * ListTest
 */
@SuppressWarnings("rawtypes")
public class ListTest {

  @Test
  void simpleTest() {
    List list = mock(List.class);
    when(list.size()).thenReturn(3);
    assertEquals(3, list.size());
  }

  @Test
  void multipleReturns() {
    List list = mock(List.class);
    when(list.size()).thenReturn(1).thenReturn(2);
    assertEquals(1, list.size());
    assertEquals(2, list.size());
    assertEquals(2, list.size());
    assertEquals(2, list.size());
  }

  @Test
  void parameters() {
    List list = mock(List.class);
    when(list.get(0)).thenReturn("SomeString");
    assertEquals("SomeString", list.get(0));
    assertEquals(null, list.get(1));
  }

  @Test
  void genericParameters() {
    List list = mock(List.class);
    when(list.get(anyInt())).thenReturn("SomeOtherString");
    assertEquals("SomeOtherString", list.get(0));
    assertEquals("SomeOtherString", list.get(1));
    assertEquals("SomeOtherString", list.get(2));
  }

}
