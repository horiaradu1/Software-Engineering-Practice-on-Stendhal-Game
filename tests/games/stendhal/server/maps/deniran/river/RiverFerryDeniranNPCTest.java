package games.stendhal.server.maps.deniran.river;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class RiverFerryDeniranNPCTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		new DatabaseFactory().initializeDatabase();
		MockStendlRPWorld.get();
		PlayerTestHelper.generatePlayerRPClasses();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generateNPCRPClasses();

		MockStendhalRPRuleProcessor.get();
		// load item configurations to handle money and other items
		SingletonRepository.getEntityManager();

		SingletonRepository.getNPCList().clear();
	}
	
	
	@Test
	public void testThatPlayerCanBuyTicketIfHasEnoughMoney() {
		RiverFerryDeniranNPC zoneconfig = new RiverFerryDeniranNPC();
		StendhalRPZone testzone = new StendhalRPZone("sellerTestZone");
		zoneconfig.configureZone(testzone , null);
		SpeakerNPC ferryTicketSeller = SingletonRepository.getNPCList().get("Ferry Ticket Seller Deniran");
		assertNotNull("NPC is <null>", ferryTicketSeller);
		Engine engine = ferryTicketSeller.getEngine();
		Player player = PlayerTestHelper.createPlayer("testPlayer");

		engine.step(player,"hi");
		engine.step(player,"buy");
		assertThat(getReply(ferryTicketSeller),is("A ferry ticket will cost 100. Do you want to buy it?"));
		final Item item = ItemTestHelper.createItem("money", 50000);
		
		player.getSlot("bag").add(item);

		engine.step(player,"yes");
		assertThat(getReply(ferryTicketSeller),is("Congratulations! Here is your ferry ticket!"));
	}
	
	@Test
	public void testThatPlayerCannotBuyTicketIfDoesNotHaveEnoughMoney() {
		RiverFerryDeniranNPC zoneconfig = new RiverFerryDeniranNPC();
		StendhalRPZone testzone = new StendhalRPZone("sellerTestZone");
		zoneconfig.configureZone(testzone , null);
		SpeakerNPC ferryTicketSeller = SingletonRepository.getNPCList().get("Ferry Ticket Seller Deniran");
		assertNotNull("NPC is <null>", ferryTicketSeller);
		Engine engine = ferryTicketSeller.getEngine();
		Player player = PlayerTestHelper.createPlayer("testPlayer");
		
		engine.step(player,"hi");
		assertThat(getReply(ferryTicketSeller),is("Hi, I am selling the tickets for the river ferry."));

		engine.step(player,"buy");
		assertThat(getReply(ferryTicketSeller),is("A ferry ticket will cost 100. Do you want to buy it?"));
		final Item item = ItemTestHelper.createItem("money", 50);
		
		player.getSlot("bag").add(item);

		engine.step(player,"yes");
		assertThat(getReply(ferryTicketSeller),is("Sorry, you don't have enough money!"));
	}
	
	@Test
	public void testThatPlayerHasTicketInInventoryAfterBuying() {
		RiverFerryDeniranNPC zoneconfig = new RiverFerryDeniranNPC();
		StendhalRPZone testzone = new StendhalRPZone("sellerTestZone");
		zoneconfig.configureZone(testzone , null);
		SpeakerNPC ferryTicketSeller = SingletonRepository.getNPCList().get("Ferry Ticket Seller Deniran");
		assertNotNull("NPC is <null>", ferryTicketSeller);
		Engine engine = ferryTicketSeller.getEngine();
		Player player = PlayerTestHelper.createPlayer("testPlayer");

		engine.step(player,"hi");
		engine.step(player,"buy");
		final Item item = ItemTestHelper.createItem("money", 500);
		
		player.getSlot("bag").add(item);

		engine.step(player,"yes");
		assertTrue(player.isEquipped("ferry ticket"));
	}

}
