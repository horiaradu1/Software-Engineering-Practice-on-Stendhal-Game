package games.stendhal.server.entity.item.scroll;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;
import games.stendhal.server.core.engine.SingletonRepository;
import static org.hamcrest.CoreMatchers.is;

public class BlankPetScrollTest {

	private Player player = null;
	private BlankPetScroll blankPetScroll = null;
	private SummonPetScroll summonPetScroll = null;

	@Before
	public void setUp() throws Exception {
		player = PlayerTestHelper.createPlayer("xx");
		blankPetScroll = (BlankPetScroll) SingletonRepository.getEntityManager().getItem("blank pet scroll");
		summonPetScroll = (SummonPetScroll) SingletonRepository.getEntityManager().getItem("summon pet scroll");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldNotUseScrollAsPetIsInOtherScroll() {
		player.equip("bag", blankPetScroll);
		player.equip("bag", summonPetScroll);
		assertThat(blankPetScroll.onUsed(player), is(false));
	}

}