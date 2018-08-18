package bullet.items.armor;

import bullet.gfx.Assets;

public class LeatherArmor extends Armor{

	public LeatherArmor() {
		super(Assets.leatherArmor);
		
		defense = 6;
		speed = 1;
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		return "Leather Armor\n"
				+ "Armor made from leather\n"
				+ descriptionStats;
	}
}
