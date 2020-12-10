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
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class IgnoreActionTest {
	
	// Multiple test cases use SlashAction Ignore.
	private final SlashAction action = SlashActionRepository.get("ignore");

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
	 * Test 1: when the user typed only /ignore
	 * If the first parameter is null, the rest should be ignored.
	 */
	@Test
	public void testExecute1() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ignore", action.get("type"));
				assertEquals("1", action.get("list"));
			}
		};
		assertTrue(action.execute(new String[] {null, null}, null));
		assertTrue(action.execute(new String[] {null, "randomString"}, null));
	}
	
	
	/**
	 * Tests for execute.
	 * Test2: when argument for duration is provided
	 */
	@Test
	public void testExecute2() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ignore", action.get("type"));
				
				assertEquals("myTarget", action.get("target"));
				assertEquals("15", action.get("duration"));
			}
		};
		assertTrue(action.execute(new String[] {"myTarget", "15"}, ""));
	}
	
	
	/**
	 * Tests for execute.
	 * Test 3: when argument for duration is either "*", "-", an invalid string and an integer
	 */
	@Test
	public void testExecute3() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ignore", action.get("type"));
				assertEquals("myTarget", action.get("target"));				
			}
		};
		assertFalse(action.execute(new String[] {"myTarget", "*"}, ""));
		assertFalse(action.execute(new String[] {"myTarget", "-"}, ""));
		
		// Test various inputs for duration
		assertFalse(action.execute(new String[] {"myTarget", "hello3"}, ""));
		assertFalse(action.execute(new String[] {"myTarget", "3Hello"}, ""));
		assertFalse(action.execute(new String[] {"myTarget", " 34 2"}, ""));
	}
	
	
	/**
	 * Test for execute
	 * Test 4: when remainder is not an empty string
	 */
	@Test
	public void testExecute4() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ignore", action.get("type"));
				
				assertEquals("myTarget", action.get("target"));
				assertEquals("150", action.get("duration"));
				assertEquals("myReason", action.get("reason"));
			}
		};
		assertTrue(action.execute(new String[] {"myTarget", "150"}, "myReason"));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		assertThat(action.getMinimumParameters(), is(0));
	}

}
