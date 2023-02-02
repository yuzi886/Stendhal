package utilities.RPClass;

import static org.junit.Assert.*;

import org.junit.Test;

import marauroa.common.game.RPClass;

public class RatTestHelperTest {

	@Test
	public void testGenerateRPClasses() {
		RatTestHelper.generateRPClasses();
		assertTrue(RPClass.hasRPClass("pet_rat"));
	}

}
