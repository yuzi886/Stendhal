package games.stendhal.server.entity.item.consumption;

import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.SleepStatus;

public class Sleeper implements Feeder{
	@Override
	public boolean feed(final ConsumableItem item, final Player player) {
	ConsumableItem splitOff = (ConsumableItem) item.splitOff(1);
	SleepStatus status = new SleepStatus(splitOff.getAmount(), splitOff.getFrecuency(), splitOff.getRegen());
	player.getStatusList().inflictStatus(status, splitOff);
	return true;
}

}
