package world.array.springboot.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMathTest {

    MyMath math = new MyMath();

    @Test
    void calculateSum_ThreeMemberArray() {
        int result = math.calculateSum(new int[]{1, 2, 3});
        assertEquals(6, result, "Wrong sum");
    }

    @Test
    void calculateSum_ZeroMemberArray() {
        int result = math.calculateSum(new int[]{});
        assertEquals(0, result, "Empty array should sum to zero");
    }


}