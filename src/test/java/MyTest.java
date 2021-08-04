import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	// this function tests the check move method
	// if the x coordinate  is 5 then return true
	@Test
	void chekMoveTest1() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(true, obj.checkMove(5,1), "expected value not equals (!=) actual value");
	}
	
	// this function tests the check move method
		// if the x coordinate  is 5 then return true
	// it works in all cases, irreespective of y value
	@Test
	void chekMoveTest2() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(true, obj.checkMove(5, 0), "expected value not equals (!=) actual value");
	}
	
	// this function tests the check move method
			// if the x coordinate  is 5 then return true
		// it works in all cases, irreespective of y value
	@Test
	void chekMoveTest3() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(true, obj.checkMove(5, 5), "expected value not equals (!=) actual value");
	}
	
	// this function tests
	// player before starting the game
	@Test
	void getPlayer() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(0, obj.getPlayer(), "expected value not equals (!=) actual value");
	}
	
	// this function tests
	// initially the game shouldn't be tie.
	@Test
	void getTie() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(false, obj.getTie(), "expected value not equals (!=) actual value");
	}

	// this function tests winner pattern
	// initially it need to be zero
	@Test
	void getWinPattern() {
		JavaFXTemplate obj = new JavaFXTemplate();
		assertEquals(0, obj.getWinPattern(), "expected value not equals (!=) actual value");
	}
}
