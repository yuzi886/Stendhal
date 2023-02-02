package games.stendhal.server.maps.deniran.cityinterior.furnitureshop;

import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.core.pathfinder.FixedPath;
//import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.BuyerAdder;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.BuyerBehaviour;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.player.Player;

public class FurnitureSellerNPC implements ZoneConfigurator  {


	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
		buildSigns(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Johnson") {
			@Override
			public void createDialog() {
				addGreeting("Hello, and welcome to the deniran furniture shop.");
				addJob("I am the local furniture seller.");
				addOffer("Check out the blackboards for my prices.");
				addGoodbye();
				
				add(ConversationStates.ATTENDING,
						ConversationPhrases.YES_MESSAGES,
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
							@Override
							public void fire(final Player player,final Sentence sentence, final EventRaiser npc) {
								((SpeakerNPC) npc.getEntity()).getEngine().step(player, "buy furniture");
								}
							} );
				
				final Map<String, Integer> offers = new HashMap<String, Integer>();
				offers.put("furniture", 1000);
				new SellerAdder().addSeller(this, new SellerBehaviour(offers));
				addGoodbye("Bye, enjoy your day!");
				
			}
			
			@Override
			protected void onGoodbye(RPEntity player) {
				setDirection(Direction.DOWN);
			}

			@Override
			protected void createPath() {
//				final List<Node> nodes = new LinkedList<Node>();
//				nodes.add(new Node(29, 5));
//				nodes.add(new Node(11, 5));
				setPath(null);
			}
		};

		// buys
		final Map<String, Integer> pricesBuy = new LinkedHashMap<String, Integer>() {{
			put("chair", 1000);
			put("wardrobe", 1000);
			put("furniture" , 1000);
		}};
		new BuyerAdder().addBuyer(npc, new BuyerBehaviour(pricesBuy), false);

		// sells
		final Map<String, Integer> pricesSell = new LinkedHashMap<String, Integer>() {{
			put("table", 1000);
			put("cabinet", 1000);
			put("chest of draws", 1000);
			put("furniture" , 1000);
		}};
		new SellerAdder().addSeller(npc, new SellerBehaviour(pricesSell), false);
		
		//buying furniture
		

		// add shops to the general shop list
		final ShopList shops = ShopList.get();
		for (final String key: pricesBuy.keySet()) {
			shops.add("deniranfurniturebuy", key, pricesBuy.get(key));
		}
		for (final String key: pricesSell.keySet()) {
			shops.add("deniranfurnituresell", key, pricesSell.get(key));
		}

		npc.setPosition(11, 5);
		npc.setEntityClass("wellroundedguynpc");
		npc.setDescription("You see Johnson, the furniture seller.");
		zone.add(npc);
	}

	private void buildSigns(final StendhalRPZone zone) {
		final ShopSign buys = new ShopSign("deniranfurniturebuy", "Johnson's Shop (buying)", "You can sell these things to John.", false);
		buys.setEntityClass("blackboard");
		buys.setPosition(5, 6);

		final ShopSign sells = new ShopSign("deniranfurnituresell", "Johnson's Shop (selling)", "You can buy these things from John.", false);
		sells.setEntityClass("blackboard");
		sells.setPosition(10, 6);

		zone.add(buys);
		zone.add(sells);
	}
}
