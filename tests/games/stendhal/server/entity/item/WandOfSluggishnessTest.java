package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;

public class WandOfSluggishnessTest {
	private StendhalRPZone zone;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MockStendlRPWorld.get();
        new DatabaseFactory().initializeDatabase();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        PlayerTestHelper.removeAllPlayers();
    }

    @Before
    public void setUp() throws Exception {
        zone = new StendhalRPZone("zone", 20, 20);
        zone.protectionMap.init(1, 1);
        MockStendlRPWorld.get().addRPZone(zone);
    }

    @After
    public void tearDown() throws Exception {
        MockStendlRPWorld.get().removeZone(zone);
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
	public void testDefenderSlowedDownAfterAttack() {
		final Player player = PlayerTestHelper.createPlayer("testPlayer");
		final Player victim = PlayerTestHelper.createPlayer("testVictim");
		zone.add(player);
		zone.add(victim);
		
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");
		
		player.equip("lhand", item);
		StendhalRPAction.startAttack(player, victim);
		StendhalRPAction.playerAttack(player, victim);
		StendhalRPAction.playerAttack(player, victim);
		player.stopAttack();
		
		StatusType statusType = StatusType.HEAVY;
		
		assertTrue("", victim.getStatusList().hasStatus(statusType));
		
		zone.remove(player);
		zone.remove(victim);
	}
	
	@Test
	public void testWandIsAnItem() {
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");
		assertNotNull(item);
	}

	/*
	 * 
	 */
	@Test
	public void testDefenderCreatureSlowedDownAfterAttack() {
		final Player player = PlayerTestHelper.createPlayer("testPlayer");
		final Creature cvictim = SingletonRepository.getEntityManager().getCreature("rat");
		
		zone.add(player);
		zone.add(cvictim);
		
		Item item = SingletonRepository.getEntityManager().getItem("wandOfSluggishness");
		
		player.equip("lhand", item);
		
		StendhalRPAction.startAttack(player, cvictim);
		StendhalRPAction.playerAttack(player, cvictim);
		StendhalRPAction.playerAttack(player, cvictim);
		
		StatusType statusType = StatusType.HEAVY;
		
		assertTrue("", cvictim.getStatusList().hasStatus(statusType));

		player.stopAttack();
		
		zone.remove(player);
		zone.remove(cvictim);
	}
	
}