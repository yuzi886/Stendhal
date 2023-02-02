/**
 * 
 */
package utilities.RPClass;

import games.stendhal.server.entity.creature.Rat;
import marauroa.common.game.RPClass;

/**
 * @author dougl
 *
 */
public class RatTestHelper {
	public static void generateRPClasses() {
		PetTestHelper.generateRPClasses();
		
		if (!RPClass.hasRPClass("pet_rat")) {
			Rat.generateRPClass();
		}
	}
}
