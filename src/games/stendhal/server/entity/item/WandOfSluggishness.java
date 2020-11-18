package games.stendhal.server.entity.item;

import java.util.Map;

public class WandOfSluggishness extends StackableItem {
	
	/**
	 * Default Constructor
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public WandOfSluggishness(String name, String clazz, String subclass,
			Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor
	 *
	 * @param item
	 * 		Item to be copied
	 */
	public WandOfSluggishness(final WandOfSluggishness item) {
		super(item);
	}
}
