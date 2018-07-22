package bullet.items.armor;

import bullet.Handler;
import bullet.gfx.Assets;

public class ChainArmor extends Armor{

	public ChainArmor() {
		super(Assets.chainArmor);
		
		defense = 10;
		speed = -1;
	}

	@Override
	public String description() {
		return "Chain Armor\n"
				+ "Armor made from chain\n"
				+ descriptionStats;
	}

}
