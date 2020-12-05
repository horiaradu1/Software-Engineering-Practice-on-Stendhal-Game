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

public class BanActionTest {
	
	private static SlashAction action;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
		action = SlashActionRepository.get("ban");
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

		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ban", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schneck", action.get("hours"));
				assertEquals("schnack", action.get("reason"));
			}
		};
		assertTrue(action.execute(new String[] {"schnick", "schneck"}, "schnack"));
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
		assertThat(action.getMinimumParameters(), is(2));
	}

}
