/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.area;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.filter.FilterCriteria;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.maps.MockStendlRPWorld;

public class FlowerPotConfiguratorTest {
	static StendhalRPZone zone;
	static FlowerPotConfigurator configurator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		zone = new StendhalRPZone("0_ados_city_n");
		MockStendlRPWorld.get().addRPZone(zone);
		configurator = new FlowerPotConfigurator();
	}

	/**
	 * Remove the added flower pot(s).
	 */
	@After
	public void purgeFlowerPots() {
		if (zone != null) {
			for (Entity entity : getFlowerPots(zone)) {
				zone.remove(entity);
			}
		}
	}
	
	/**
	 * Gets all the FlowerPot entities in a zone
	 */
	public List<Entity> getFlowerPots(StendhalRPZone zone) {
		return zone.getFilteredEntities(new FilterCriteria<Entity>() {
		    @Override
		    public boolean passes(final Entity object) {
		        return (object instanceof FlowerPot);
		    }
		});
	}
	
	/**
	 * Creates the attributes required for the FlowerPot configurator to create a FlowerPot entity
	 */
	public Map<String, String> createAttributes(int x, int y) {
		return new HashMap<String, String>() {{
	        put("x", String.valueOf(x));
	        put("y", String.valueOf(y));
	    }};
	}

	/**
	 * Tests for placing a single flower pot
	 */
	@Test
	public void testSingleFlowerPot() {
		assertEquals(0, getFlowerPots(zone).size());
		
		configurator.configureZone(zone, createAttributes(45, 52));

		List<Entity> flowerPots = getFlowerPots(zone);
		assertEquals(1, flowerPots.size());
		assertTrue(flowerPots.get(0) instanceof FlowerPot);
		assertEquals(45, flowerPots.get(0).getX());
		assertEquals(52, flowerPots.get(0).getY());
	}
}
