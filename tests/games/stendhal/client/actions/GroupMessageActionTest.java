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

public class GroupMessageActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
		SlashActionRepository.register();
	}
	
	/**
	 * Tests that the aliases for the groupmessage command point to the same handler
	 */
	@Test
	public void testAliases() {
		assertEquals(SlashActionRepository.get("groupmessage"), SlashActionRepository.get("p"));
	}
	
	/**
	 * Tests for execute.
	 */
	
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("group_message", action.get("type"));
				assertEquals("he ate my lunch", action.get("text"));
			}
		};
		final SlashAction action = SlashActionRepository.get("groupmessage");
		assertTrue(action.execute(null, "he ate my lunch"));
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("groupmessage");
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("groupmessage");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
