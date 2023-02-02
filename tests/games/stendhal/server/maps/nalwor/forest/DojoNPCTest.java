package games.stendhal.server.maps.nalwor.forest;
//added by apoorv 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class DojoNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "0_nalwor_forest";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}

	public DojoNPCTest() {
		setNpcNames("Omura Sumitada");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Dojo(), ZONE_NAME);
	}

	/**
	 * tests for checking the cost returns the same dialogue as "train" if level too high
	 */
	@Test
	public void testForUnsatisfiedAttackValueForCostDialogue() {
		
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		assertNotNull(npc);
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "hello"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		
		setLevel_dojo(player,40,60);//(player,level,attack) lvl<atk, means player doesn't need to train, this was inferred from logic given. 
		assertTrue(en.step(player, "cost"));
		String reply = getReply(npc);
		assertEquals("At your level of experience, your attack strength is too high to train here at this time.", reply);				
	
	}
	@Test
	public void testForSatisfiedAttackValueForCostDialogue() {
		setLevel_dojo(player,60,40);//  lvl>atk, means player should train
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		assertNotNull(npc);
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "hello"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		
		assertTrue(en.step(player, "cost"));
		String reply = getReply(npc);
		assertEquals("The fee to #train for your skill level is 2500 money.", reply);				
	
	}
	
	
		
	
	

}


