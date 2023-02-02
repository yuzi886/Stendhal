package games.stendhal.server.maps.quests;
import static org.junit.Assert.*;
//import static org.junit.Assert.assertNotNull;
import static utilities.SpeakerNPCTestHelper.getReply;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationStates;
//import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZoneAndPlayerTestImpl;

public class PetRatQueryTest extends ZoneAndPlayerTestImpl {
	private SpeakerNPC npc;
	private static StendhalRPWorld world;
	private Player player = null;
	private Engine en = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		
	}
	
	@Before
	public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone("admin_test");
		MockStendlRPWorld.get().addRPZone(zone);
		player = PlayerTestHelper.createPlayer("bob");
		SingletonRepository.getNPCList().add(new SpeakerNPC("Byron Mcgalister"));
		npc = SingletonRepository.getNPCList().get("Byron Mcgalister");
		zone.add(player);
	}
	
	@Test
	public void testQuest() throws NullPointerException{
		final PetRatQuery quest = new PetRatQuery(); 
		quest.addToWorld();
		npc = quest.npcs.get("Byron Mcgalister");
		en = npc.getEngine();
		en.setCurrentState(ConversationStates.ATTENDING);
		en.step(player, "stealing pet");
		assertEquals("oh, now l catch some useless animals in my house. If you want it, l can give it to you", getReply(npc));
		assertTrue(player.hasPet());
		
		
		
	}
}