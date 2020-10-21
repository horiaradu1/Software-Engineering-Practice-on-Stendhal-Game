package games.stendhal.server.maps.deniran.cityinterior.bakery;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
//import games.stendhal.server.maps.deniran.cityinterior.bakery.*;

import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChefNPCTypoTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "Deniran";
	private static final String speakerName = "Patrick";
	
	private static SpeakerNPC speaker;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public ChefNPCTypoTest() {
		setNpcNames("Patrick");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new ChefNPC(), ZONE_NAME);
	}
	
	@Test
	public void testJobString() {
		final SpeakerNPC npc = getNPC("Patrick");
		assertNotNull(npc);
		final Engine en = npc.getEngine();
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		en.step(player, "job");
		String string = getReply(npc);
		assertNotNull(string);
		assertEquals("I run Deniran Bakery. ", string);
	}
}
