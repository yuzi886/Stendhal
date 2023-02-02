package games.stendhal.server.events;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.*;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import utilities.PlayerTestHelper;


public class BestiaryEventTest {
	private String playername = "player";
	private Player player;
	private StendhalRPZone zone;
	private List<String> enemy;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() throws Exception {
		MockStendlRPWorld.reset();
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	@Before
	public void setUp() throws Exception {
		zone = new StendhalRPZone("zone");
		player = PlayerTestHelper.createPlayer(playername);
		zone.add(player);
	}
	
	/**
	 * Tests for BestiaryEvent to make sure whether the bestiary only contain the killed creature
	 */
	@Test
	public void bestiaryEventTest() {
		player.setSharedKill("rat");
		player.setSoloKill("bat");
		SingletonRepository.getEntityManager().getCreature("rat");
		SingletonRepository.getEntityManager().getCreature("bat");
		SingletonRepository.getEntityManager().getCreature("spider");
		SingletonRepository.getEntityManager().getCreature("mouse");
		BestiaryEvent event = new BestiaryEvent(player);
		enemy = Arrays.asList(event.get("enemies").split(";"));
		assertEquals(enemy.size(),2);
		
	}

	
	
}