package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
//import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.creature.Rat;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.slot.PlayerSlot;

public class PetOwnerAction implements ChatAction{
	@Override
	public void fire(Player player, Sentence sentence, EventRaiser npc) {
		Rat rat = new Rat(player);
		rat.setPosition(player.getX(), player.getY() + 2);
		player.setPet(rat);
		player.notifyWorldAboutChanges();
	}
}
