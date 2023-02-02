/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.status;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class SleepStatusTest {

	/**
	 * initialisation
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
	}

	/**
	 * Tests for checking whether there is a Sleep State.(need to set player(victim) to sleep, but not possible until code is more complete)
	 */
	@Test
	public void testSleepStatusExists() {
		final Player victim = PlayerTestHelper.createPlayer("bob");
		
		assertTrue(victim.hasStatus(StatusType.SLEEPING));
	}
	
	/**
	 * Tests for healing while sleeping,
	 */
	@Test
	public void testHealWhileSLeeping() {
		final Player sleeper = PlayerTestHelper.createPlayer("bob");
		sleeper.setHP(20);
		int hp_before_sleep = sleeper.getHP();
		// add some code to let player go to the sleep status
		
		
		
		
		int hp_after_sleep = sleeper.getHP();
		assertNotEquals((hp_after_sleep - hp_before_sleep), 0);
		
		
	}


	
}
