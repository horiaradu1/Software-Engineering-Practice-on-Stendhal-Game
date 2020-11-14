package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import utilities.PlayerTestHelper;

public class WandOfSluggishnessTest {
	
	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	/*
	 * Tests whether the wand (wandOfSluggishness) item exists, and whether it can be equipped
	 */
	@Test
	public void testEquip() {
		
		final Player player = PlayerTestHelper.createPlayer("testPlayer");
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");

		player.equip("bag", item);
		assertTrue(player.isEquipped("wandOfSluggishness"));
	}

}
