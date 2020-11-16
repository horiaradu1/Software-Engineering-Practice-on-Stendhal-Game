package games.stendhal.server.entity.item;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import utilities.ZonePlayerAndNPCTestImpl;

public class RiverFerryTicketTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "testzone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupZone(ZONE_NAME);
		
	}
	
	public RiverFerryTicketTest() {
		setZoneForPlayer(ZONE_NAME);
	}
	

	@Test
	// Check for getting the ticket
	public void testGetTicket() {
		final Item ticket = SingletonRepository.getEntityManager().getItem("ferry ticket");
		
		// Checks to see if ticket exists
		assertNotNull("Ferry ticket returns <null>", ticket);
		// Checks for the correct name of the ticket
		assertEquals("ferry ticket", ticket.getName());
		// Check for the correct description of the ticket
		assertEquals("This is a ticket for the river ferry which is coming out soon.", ticket.getDescription());
		// Check to see if the ticket can be equipped in a bag
		assertTrue(ticket.canBeEquippedIn("bag"));
		// Check to see if the ticket can't be equipped in the hands
		assertFalse(ticket.canBeEquippedIn("lhand"));
		assertFalse(ticket.canBeEquippedIn("rhand"));
		
	}
}
