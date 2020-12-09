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
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.client.ClientSingletonRepository;
//import games.stendhal.client.MockClientUI;
//import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
//import marauroa.common.game.RPAction;

public class RemessageActionTest {

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
	public void testExecute() {
		
//		final MockClientUI clientUI = new MockClientUI();
//		ClientSingletonRepository.setUserInterface(clientUI);
//		final MessageAction messageCommand = (MessageAction) SlashActionRepository.get("msg");
		
//		final SlashAction messageAction = SlashActionRepository.get("msg");
//		messageAction.execute(new String[] {"hello"}, "cool and fun times");
		
		final SlashAction action = SlashActionRepository.get("/");
		assertFalse(action.execute(new String[] {}, "random"));
		assertFalse(action.execute(null, null));
	}
	
	
	/**
	 * Tests for execute.
	 */
//	@Test
//	public void testExecute2() {
//		
//		final MockClientUI clientUI = new MockClientUI();
//		ClientSingletonRepository.setUserInterface(clientUI);
////		final MessageAction messageCommand = (MessageAction) SlashActionRepository.get("msg");
//		
//		final SlashAction messageAction = SlashActionRepository.get("msg");
//		messageAction.execute(new String[] {"hello"}, "cool and fun times");
//		
//		new MockStendhalClient() {
//			@Override
//			public void send(final RPAction action) {
//				assertEquals("tell", action.get("type"));
//				assertEquals("hello", action.get("text"));
//			}
//		};
//		
//		final SlashAction action = SlashActionRepository.get("/");
//		assertTrue(action.execute(new String[] {}, "hello"));
//	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("/");
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("/");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
