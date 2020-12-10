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
package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

import static games.stendhal.common.constants.Actions.CASTSPELL;

public class CastSpellActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 * Test 1: if target is given by name
	 */
	@Test
	public void testTargetName() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals(CASTSPELL, action.get("type"));
				assertEquals("test base item", action.get("baseitem"));
				assertEquals("spells", action.get("baseslot"));
				assertEquals("test name", action.get("target"));
			}
		};
		final SlashAction action = SlashActionRepository.get("cast");
		assertTrue(action.execute(new String[] {"test base item", "test name"}, null));
		
	}
	
	
	/**
	 * Test 2: if the target is given by ID
	 */
	@Test
	public void testTargetID() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals(CASTSPELL, action.get("type"));
				assertEquals("test base item", action.get("baseitem"));
				assertEquals("spells", action.get("baseslot"));
				assertEquals("#123", action.get("target"));
			}
		};
		final SlashAction action = SlashActionRepository.get("cast");
		assertTrue(action.execute(new String[] {"test base item", "123"}, null));
	}
	

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("cast");
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("cast");
		assertThat(action.getMinimumParameters(), is(2));
	}

}
