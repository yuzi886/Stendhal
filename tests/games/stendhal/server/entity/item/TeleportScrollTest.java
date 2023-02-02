package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.client.entity.RPEntity;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.item.consumption.;
import games.stendhal.server.entity.item.consumption.Poisoner;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
//import games.stendhal.server.entity.status.StatusType;
import utilities.PlayerTestHelper;
///import games.stendhal.server.entity.item.scroll.Scroll;
//import games.stendhal.server.entity.item.scroll.TeleportScroll;
import utilities.RPClass.ItemTestHelper;

public class TeleportScrollTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		Log4J.init();
	}

	@Test
	public final void testFeed() {
		final StackableItem s = (StackableItem) SingletonRepository.getEntityManager().getItem("marked scroll");
		int a = s.getQuantity();
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		final Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("amount", "1000");
		attributes.put("regen", "200");
		attributes.put("frequency", "1");
		attributes.put("id", "1");
		final StendhalRPWorld world = SingletonRepository.getRPWorld();
		final StendhalRPZone zone = new StendhalRPZone("test");
		world.addRPZone(zone);
		final ConsumableItem c200_1 = new ConsumableItem("cheese", "", "", attributes);
		zone.add(c200_1);
		final Poisoner poisoner = new Poisoner();
		final Player bob = PlayerTestHelper.createPlayer("player");
		poisoner.feed(c200_1, bob);
		//final StackableItem teleportscroll = new StackableItem("item", "clazz", "TeleportScroll", attributes);
		//final StackableItem s = (StackableItem) SingletonRepository.getEntityManager().getItem("marked scroll");
		//assertFalse(s.useTeleportScroll(bob));
		//assertFalse(s.onUsed(bob));
		assertTrue(s.getQuantity() == a);
	}
}
