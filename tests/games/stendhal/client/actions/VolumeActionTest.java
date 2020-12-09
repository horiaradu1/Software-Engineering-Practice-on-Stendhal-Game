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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.MockClientUI;
import games.stendhal.client.StendhalClient;

public class VolumeActionTest {

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
	public void testExecuteAllParamsNotNull() {
		
		final MockClientUI clientUI = new MockClientUI();
		ClientSingletonRepository.setUserInterface(clientUI);
		
		final SlashAction action = SlashActionRepository.get("volume");
		assertTrue(action.execute(new String[] {"param1", "param2"}, "random"));
		assertTrue(action.execute(new String[] {"param1", "3"}, "random"));
		assertTrue(action.execute(new String[] {"1", "3"}, "random"));
		assertTrue(action.execute(new String[] {"master", "param2"}, "random"));
		assertTrue(action.execute(new String[] {"master", "5"}, "random"));
		assertTrue(action.execute(new String[] {"10", "5"}, "random"));
	}
	
	
	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecuteFirstParamNull() {
		
		final MockClientUI clientUI = new MockClientUI();
		ClientSingletonRepository.setUserInterface(clientUI);
		
		final SlashAction action = SlashActionRepository.get("volume");
		assertTrue(action.execute(new String[] {null, "param2"}, "random"));
		assertTrue(action.execute(new String[] {null, null}, "random"));
		assertTrue(action.execute(new String[] {null}, "random"));
	}
	
	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecuteFirstParamNotNullButSecondNull() {
		
		final MockClientUI clientUI = new MockClientUI();
		ClientSingletonRepository.setUserInterface(clientUI);
		
		final SlashAction action = SlashActionRepository.get("volume");
		assertTrue(action.execute(new String[] {"param1", null}, "random"));
	}
	

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("volume");
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("volume");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
