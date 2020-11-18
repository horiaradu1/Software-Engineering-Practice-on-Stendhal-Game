package games.stendhal.server.maps.ados.city;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

public class RiverFerryAdosNPC implements ZoneConfigurator {
	
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
	    final SpeakerNPC npc = new SpeakerNPC("Ferry Ticket Seller") {
	    	@Override
	    	protected void createPath() {
	    		List<Node> nodes=new LinkedList<Node>();
	    		nodes.add(new Node(70,115));
	    		nodes.add(new Node(70,120));
	    		nodes.add(new Node(74,120));
	    		nodes.add(new Node(74,115));
	    		setPath(new FixedPath(nodes,true));
	    	}
	    	
	    	@Override
	    	protected void createDialog() {
					
	    		addGreeting("Hi, I am selling the tickets for the river ferry.");
	    		final Map<String, Integer> offerings = new HashMap<String, Integer>();
                offerings.put("ferry ticket", 100);
                new SellerAdder().addSeller(this, new SellerBehaviour(offerings));
	    		addReply("wooden  spear","Please ask for my #offer.");
	    		addGoodbye();
	    	}
	    };

	    npc.setEntityClass("ferryTicketSeller");
	    npc.setDescription("You see Ferry Ticket Seller, he looks a bit busy at the moment but perhaps he can help you anyway.");
	    npc.setPosition(70, 115);
	    npc.initHP(100);

	    zone.add(npc);   
	}
}
