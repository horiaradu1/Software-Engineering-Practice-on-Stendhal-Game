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

public class GrumpyActionTest {
	
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
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("grumpy", action.get("type"));
				assertEquals("he ate my lunch", action.get("reason"));
			}
		};
		final SlashAction action = SlashActionRepository.get("grumpy");
		assertTrue(action.execute(null, "he ate my lunch"));
	}

	
	/**
	 * Ensure the action fires with no remainder
	 */
	@Test
	public void testExecuteNoRemainder() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("grumpy", action.get("type"));
				assertEquals(null, action.get("reason"));
			}
		};
		final SlashAction action = SlashActionRepository.get("grumpy");
		assertTrue(action.execute(null, ""));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("grumpy");
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("grumpy");
		assertThat(action.getMinimumParameters(), is(0));
	}


}
