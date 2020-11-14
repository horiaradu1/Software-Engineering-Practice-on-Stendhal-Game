package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class WandOfSluggishnessTest {
	private StendhalRPZone zone;
	
	
	@Before
	public void setUp() throws Exception {
		zone = new StendhalRPZone("zone", 20, 20);
		MockStendlRPWorld.get().addRPZone(zone);
	}
	
	
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
	
	
	/*
	 * 
	 */
	@Test
	public void testDefenderSlowedDownAFterAttack() {
		final Player player = PlayerTestHelper.createPlayer("testPlayer");
		final Player victim = PlayerTestHelper.createPlayer("testVictim");
		zone.add(player);
		zone.add(victim);
		
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");
		
		player.equip("lhand", item);
		StendhalRPAction.startAttack(player, victim);
		StendhalRPAction.playerAttack(player, victim);
		
		StatusType statusType = StatusType.HEAVY;
		
		assertTrue(victim.hasStatus(statusType));
		player.stopAttack();
		
	}
	
	@Test
	public void testWandIsAnItem() {
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");
		assertNotNull(item);
	}

}
