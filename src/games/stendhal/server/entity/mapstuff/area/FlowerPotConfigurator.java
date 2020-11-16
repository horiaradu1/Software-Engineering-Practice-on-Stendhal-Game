package games.stendhal.server.entity.mapstuff.area;

import java.util.List;
import java.util.Map;

import games.stendhal.common.MathHelper;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;

public class FlowerPotConfigurator implements ZoneConfigurator{

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		if (isValid(attributes)) {
			final int x = MathHelper.parseInt(attributes.get("x"));
			final int y = MathHelper.parseInt(attributes.get("y"));
			buildFlowerPot(zone, x, y);
		}
	}

	private void buildFlowerPot(final StendhalRPZone zone, final int x, final int y) {
		if (!flowerPotAt(zone, x, y)) {
			final FlowerPot pot = new FlowerPot();
			pot.setPosition(x, y);
			pot.hide();
			zone.add(pot);
		}
	}
	
	private boolean flowerPotAt(final StendhalRPZone zone, final int x, final int y) {
		final List<Entity> list = zone.getEntitiesAt(x, y);
		for (Entity entity : list) {
			if (entity instanceof FlowerPot) {
				// Don't put a flower pot over an existing flower pot.
				return true;
			}
		}
		return false;
	}
	
	private boolean isValid(final Map<String, String> attributes) {
		return attributes.containsKey("x") && attributes.containsKey("y");
	}

}
