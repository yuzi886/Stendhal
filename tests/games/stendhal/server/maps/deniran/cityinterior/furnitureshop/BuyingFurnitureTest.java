package games.stendhal.server.maps.deniran.cityinterior.furnitureshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class BuyingFurnitureTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "deniran_furniture_shop";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}
	
	public BuyingFurnitureTest() {
		setNpcNames("Johnson");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FurnitureSellerNPC(), ZONE_NAME);
	}

	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Johnson");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("Hello, and welcome to the deniran furniture shop.", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Bye, enjoy your day!", getReply(npc));
	}
	
	@Test
	public void testBuyFurniture() {
		final SpeakerNPC npc = getNPC("Johnson");
		final Engine en = npc.getEngine();
		
		
		assertTrue(en.step(player, "hi"));
		assertEquals("Hello, and welcome to the deniran furniture shop.", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("I am the local furniture seller.", getReply(npc));

		assertTrue(en.step(player, "offer"));
		assertEquals("Check out the blackboards for my prices. I sell furniture.", getReply(npc));

		assertTrue(en.step(player, "buy dog"));
		assertEquals("Sorry, I don't sell dogs.", getReply(npc));

		assertTrue(en.step(player, "buy furniture"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));
		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));

		assertTrue(en.step(player, "buy 0 furniture"));
		assertEquals("Sorry, how many furnitures do you want to buy?!", getReply(npc));

		assertTrue(en.step(player, "buy furniture"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));

		assertTrue(en.step(player, "buy furniture"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry, you don't have enough money!", getReply(npc));

		// equip with enough money to buy two furniture
		assertTrue(equipWithMoney(player, 2000));
//
		assertTrue(en.step(player, "buy three piece of furniture"));
		assertEquals("3 furnitures will cost 3000. Do you want to buy them?", getReply(npc));
//
		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry, you don't have enough money!", getReply(npc));
//
		assertTrue(en.step(player, "buy furniture"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));
//
		assertFalse(player.isEquipped("furniture"));
//
		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your furniture!", getReply(npc));
		assertTrue(player.isEquipped("furniture", 1));
//
		assertTrue(en.step(player, "buy furniture"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));
//
		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your furniture!", getReply(npc));
		assertTrue(player.isEquipped("furniture", 2));
//
//		assertTrue(en.step(player, "buy 0 furniture"));
//		assertEquals("Sorry, how many pieces of furniture do you want to buy?!", getReply(npc));

		// buying one furniture by answering "yes" to npc's greeting
		assertTrue(equipWithMoney(player, 1000));
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye, enjoy your day!", getReply(npc));
		assertTrue(en.step(player, "hi"));
		assertEquals("Hello, and welcome to the deniran furniture shop.", getReply(npc));
		assertTrue(en.step(player, "yes"));
		assertEquals("A furniture will cost 1000. Do you want to buy it?", getReply(npc));
		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your furniture!", getReply(npc));
		assertTrue(player.isEquipped("furniture", 3));
	}
}
