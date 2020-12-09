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

public class GroupManagementActionTest {

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
	 */
	@Test
	public void testExecuteWithGivenParamMessage() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
			}
		};
		final SlashAction action = SlashActionRepository.get("group");
		assertTrue(action.execute(new String[] {"message"}, "random"));
	}
	
	
	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecuteWithoutGivenParamMessage() {
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("group_management", action.get("type"));
				assertEquals("hello", action.get("action"));
				assertEquals("random", action.get("params"));
			}
		};
		final SlashAction action = SlashActionRepository.get("group");
		assertTrue(action.execute(new String[] {"hello"}, "random"));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("group");
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("group");
		assertThat(action.getMinimumParameters(), is(1));
	}

}
