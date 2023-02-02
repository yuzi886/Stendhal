/***************************************************************************
 *                (C) Copyright 2005-2013 - Faiumoni e. V.                 *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.status;

import java.util.Collections;
import java.util.List;

import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

/**
 * eating turn listener
 */
public class SleepStatusTurnListener implements TurnListener {
	private StatusList statusList;
	private static final String ATTRIBUTE_NAME = "sleeping";

	/**
	 * EatStatusTurnListener
	 *
	 * @param statusList StatusList
	 */
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}

	@Override
	public void onTurnReached(int turn) {
		RPEntity entity = statusList.getEntity();
		List<SleepStatus> toConsume = statusList.getAllStatusByClass(SleepStatus.class);

		// check that the entity exists
		if (entity == null) {
			return;
		}

		// cleanup status
		if (toConsume.isEmpty()) {
			if (entity.has(ATTRIBUTE_NAME)) {
				entity.remove(ATTRIBUTE_NAME);
			}
			entity.notifyWorldAboutChanges();
			return;
		}

		Collections.sort(toConsume);
		final ConsumableStatus food = toConsume.get(0);

		if (turn % food.getFrecuency() == 0) {
			final int amount = food.consume();
				entity.put(ATTRIBUTE_NAME, amount);
				entity.notifyWorldAboutChanges();

			// is full hp?
			if (entity.heal(amount, true) == 0) {
				if (entity instanceof Player) {
					statusList.removeAll(SleepStatus.class);
				}
			}

			// is item used up?
			
		}

		TurnNotifier.get().notifyInTurns(0, this);
	}


	@Override
	public int hashCode() {
		return statusList.hashCode() * 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		SleepStatusTurnListener other = (SleepStatusTurnListener) obj;
		return statusList.equals(other.statusList);
	}

}