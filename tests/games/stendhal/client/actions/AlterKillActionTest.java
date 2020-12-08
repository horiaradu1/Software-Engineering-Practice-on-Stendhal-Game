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

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

import static games.stendhal.common.constants.Actions.ALTERKILL;

public class AlterKillActionTest {

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
	 * Test 1: if remainder is null or empty string
	 */
	@Test
	public void testRemainderNull() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals(ALTERKILL, action.get("type"));
				assertEquals("myTarget", action.get("target"));
				assertEquals("random kill type", action.get("killtype"));
				assertEquals("25", action.get("count"));
			}
		};
		final SlashAction action = SlashActionRepository.get(ALTERKILL);
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, null));
		
		// Same result for empty string
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, ""));
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, "    "));
	}
	
	
	/**
	 * Test 2: if remainder is a non-empty string.
	 * Only 1 whitespace between words should be left
	 */
	@Test
	public void testRemainderNotNull() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals(ALTERKILL, action.get("type"));
				assertEquals("myTarget", action.get("target"));
				
				assertEquals("random kill type", action.get("killtype"));
				assertEquals("25", action.get("count"));
				assertEquals("test for creature 1.1", action.get("creature"));
			}
		};
		final SlashAction action = SlashActionRepository.get(ALTERKILL);
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, "test for creature 1.1"));
		
		// Check for different inputs. Only 1 whitespace should be left after calling execute function
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, "test    for    creature 1.1"));
		assertTrue(action.execute(new String[] {"myTarget", "random kill type", "25"}, "test    for    creature    1.1"));
	}
	

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get(ALTERKILL);
		assertThat(action.getMaximumParameters(), is(3));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get(ALTERKILL);
		assertThat(action.getMinimumParameters(), is(3));
	}

}
