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
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.token.Token;
import games.stendhal.server.entity.mapstuff.portal.Door;
import games.stendhal.server.entity.mapstuff.sign.Sign;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import games.stendhal.server.maps.quests.ReverseArrow.ReverseArrowCheck;


public class ReverseArrowTest {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		StendhalRPZone zone = new StendhalRPZone("int_ados_reverse_arrow");
		MockStendlRPWorld.get().addRPZone(zone);
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("0_semos_mountain_n2"));

		if (!RPClass.hasRPClass("door")) {
			Door.generateRPClass();
		}
		if (!RPClass.hasRPClass("sign")) {
			Sign.generateRPClass();
		}

	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
		NPCList.get().clear();
	}


	/**
	 * Tests for getSlotName.
	 */
	@Test
	public void testGetSlotName() {
		assertEquals("reverse_arrow", new ReverseArrow().getSlotName());
	}

	/**
	 * Tests for addToWorld.
	 */
	@Test
	public void testAddToWorld() {

		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
	}
	/**
	 * Tests for checkBoard. (test used for Issue 2)
	 */
	@Test
	public void testcheckBoard() {
		
		ReverseArrow arrowquest = new ReverseArrow();
		ReverseArrowCheck arrowcheck = arrowquest.new ReverseArrowCheck();
		assertFalse(arrowcheck.checkBoard());
		
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		arrowquest.removeAllTokens();
		arrowquest.addAllTokens();
		assertFalse(arrowcheck.checkBoard());
		
		arrowquest.removeAllTokens();
		arrowquest.tokens = new LinkedList<Token>();
		arrowquest.addTokenToWorld(10, 9);
		arrowquest.addTokenToWorld(9, 10);
		arrowquest.addTokenToWorld(10, 10);
		arrowquest.addTokenToWorld(11, 10);
		for (int i = 0; i < 5; i++) {
			arrowquest.addTokenToWorld(8 + i, 11);
		}
		assertTrue(arrowcheck.checkBoard());
		
		arrowquest.removeAllTokens();
		arrowquest.tokens = new LinkedList<Token>();
		arrowquest.addTokenToWorld(10, 9);
		arrowquest.addTokenToWorld(9, 10);
		arrowquest.addTokenToWorld(11, 10);
		for (int i = 0; i < 5; i++) {
			arrowquest.addTokenToWorld(8 + i, 11);
		}
		arrowquest.addTokenToWorld(10, 12);
		assertTrue(arrowcheck.checkBoard());
		

		
		
	}

	/**
	 * Tests for finish.
	 */
	@Test
	public void testFinish() {
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(arrowquest.player);
		arrowquest.finish(false, null);
		assertNotNull(arrowquest.player);

		arrowquest.finish(true, null);
		assertNull(arrowquest.player);
	}

}
