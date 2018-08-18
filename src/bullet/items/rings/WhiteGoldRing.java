package bullet.items.rings;

import bullet.gfx.Assets;
import bullet.items.Ring;

public class WhiteGoldRing extends Ring {

	public WhiteGoldRing() {
		super(Assets.whiteGoldRing);
		
		attack = 10;
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		return "Ring\n"
				+ "cool ring\n"
				+ descriptionStats;
	}

}
