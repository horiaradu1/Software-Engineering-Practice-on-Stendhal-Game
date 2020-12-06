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
		final GrumpyAction action = new GrumpyAction();
		assertTrue(action.execute(null, "he ate my lunch"));
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GrumpyAction action = new GrumpyAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GrumpyAction action = new GrumpyAction();
		assertThat(action.getMinimumParameters(), is(0));
	}


}
