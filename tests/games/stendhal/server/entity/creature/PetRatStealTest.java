package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;
import utilities.RPClass.RatTestHelper;

public class PetRatStealTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		MockStendlRPWorld.get();
	}
	
	@Test
	public void testPetRatStealsItemFromCorpse() {
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		RatTestHelper.generateRPClasses();
		
		final StendhalRPWorld somewhere = SingletonRepository.getRPWorld();
		StendhalRPZone somePlace = new StendhalRPZone("testZone");
		somewhere.addRPZone(somePlace);
		
		final Player someGuy = PlayerTestHelper.createPlayer("testPlayer");
		Item something = new Item("something", "", "", null);
		
		Corpse thing = new Corpse("some dead thing", 0, 0);
		thing.getSlot("content").add(something);
		Item i = (Item) thing.getSlot("content").getFirst();
		assertEquals(i.getName(), "something");
		thing.setKiller(someGuy.getName());
		somePlace.add(thing);
		somePlace.add(someGuy);
		someGuy.setPosition(0, 0);
		
		Rat ratticus = new Rat(someGuy); //Rat pet owned by test player
		somePlace.add(ratticus);
		
		assertTrue(ratticus.stealFromCreatureCorpse());
		assertTrue(someGuy.isEquipped(something.getName(), 1));
	}
	
	@Test
	public void testPetRatIgnoresCorpseKilledByAnotherPlayer() {
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		RatTestHelper.generateRPClasses();
		
		final StendhalRPWorld somewhere = SingletonRepository.getRPWorld();
		StendhalRPZone somePlace = new StendhalRPZone("testZone");
		somewhere.addRPZone(somePlace);
		
		final Player someGuy = PlayerTestHelper.createPlayer("testPlayer");
		Item something = new Item("something", "", "", null);
		
		Corpse thing = new Corpse("some dead thing", 0, 0);
		thing.getSlot("content").add(something);
		thing.setKiller(someGuy.getName());
		somePlace.add(thing);
		somePlace.add(someGuy);
		someGuy.setPosition(0, 0);
		
		final Player someOtherGuy = PlayerTestHelper.createPlayer("otherTestPlayer");
		somePlace.add(someOtherGuy);
		someOtherGuy.setPosition(1, 0);
		
		Rat ratticus = new Rat(someOtherGuy);
		somePlace.add(ratticus);
		
		assertFalse(ratticus.stealFromCreatureCorpse());
	}
	
	@Test
	public void testPetRatIgnoresFarAwayCorpse() {
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		RatTestHelper.generateRPClasses();
		
		final StendhalRPWorld somewhere = SingletonRepository.getRPWorld();
		StendhalRPZone somePlace = new StendhalRPZone("testZone");
		somewhere.addRPZone(somePlace);
		
		final Player someGuy = PlayerTestHelper.createPlayer("testPlayer");
		Item something = new Item("something", "", "", null);
		
		Corpse thing = new Corpse("some dead thing", 0, 0);
		thing.getSlot("content").add(something);
		thing.setKiller(someGuy.getName());
		somePlace.add(thing);
		somePlace.add(someGuy);
		someGuy.setPosition(10, 10);
		
		Rat ratticus = new Rat(someGuy);
		somePlace.add(ratticus);
	
		assertFalse(ratticus.stealFromCreatureCorpse());
	}
	
	@Test
	public void testCorpseIsEmpty() {
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		RatTestHelper.generateRPClasses();
		
		final StendhalRPWorld somewhere = SingletonRepository.getRPWorld();
		StendhalRPZone somePlace = new StendhalRPZone("testZone");
		somewhere.addRPZone(somePlace);
		
		final Player someGuy = PlayerTestHelper.createPlayer("testPlayer");
		
		Corpse thing = new Corpse("some dead thing", 0, 0);
		thing.setKiller(someGuy.getName());
		somePlace.add(thing);
		somePlace.add(someGuy);
		someGuy.setPosition(0, 0);
		
		Rat ratticus = new Rat(someGuy); //Rat pet owned by test player
		somePlace.add(ratticus);
		
		assertFalse(ratticus.stealFromCreatureCorpse());
	}

}
