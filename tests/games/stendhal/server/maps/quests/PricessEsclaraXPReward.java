package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.amazon.hut.PrincessNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class PricessEsclaraXPReward {
	
	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		final ZoneConfigurator zoneConf = new PrincessNPC();
		zoneConf.configureZone(new StendhalRPZone("admin_test"), null);
		npc = SingletonRepository.getNPCList().get("Princess Esclara");
		en = npc.getEngine();

		final AbstractQuest quest = new AmazonPrincess();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("player");
		

	}
	@After
	public void tearDown() {
		SingletonRepository.getNPCList().clear();
	}
	
	
	@Test
	public void XPAddedTest() {
		
		// Introduce the quest and get 3 karma
		en.step(player, "hi");
		en.step(player, "task");
		en.step(player, "yes");
		en.step(player, "bye");
		
		// Initial values of Karma and XP
		final double karmaBefore = player.getKarma();
		final int XPBefore = player.getXP();
		
		
		// Equip the cocktail that needs to be delivered
		final Item item = ItemTestHelper.createItem("pina colada");
		player.getSlot("bag").add(item);
		
		// Step through the quest to complete it
		en.step(player, "hi");
		en.step(player, "pina colada");
		en.step(player, "bye");

		
		// Get new values
		final double karmaAfter = player.getKarma();
		final int XPAfter = player.getXP();
		
		
		// Compare them
		assertTrue(karmaBefore == karmaAfter);
		assertTrue(XPBefore + 500 == XPAfter);
		
		
	}

}
