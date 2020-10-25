package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import games.stendhal.server.core.engine.SingletonRepository;

@RunWith(Parameterized.class)
public class CreatureDwarfSpeedTest {
	private Creature c;
	
	@Parameters
	public static Collection<Creature[]> crts() {;
		Collection<Creature> creatures = SingletonRepository.getEntityManager().getCreatures();
		Collection<Creature[]> toReturn = new ArrayList<Creature[]>();
		for (Creature c : creatures) {
			if(c.get("class").equals("dwarf")) {
				if(c.getLevel() <= 30)
					toReturn.add(new Creature[] {c});
			}
		}
		return toReturn;
	}
	
	public CreatureDwarfSpeedTest(Creature c) {
		this.c = c;
	}

	@Test
	public void test() {
		double speed = c.getBaseSpeed();
		String name = c.getName();
		assertEquals(name + " speed ", 0.8, speed, 0.00001);
	}

}