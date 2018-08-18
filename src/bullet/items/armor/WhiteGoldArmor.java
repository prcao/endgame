package bullet.items.armor;

import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.gfx.Assets;

public class WhiteGoldArmor extends Armor{

	public WhiteGoldArmor() {
		super(Assets.whiteGoldArmor);
		defense = 9999;
		hp = 9999;
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Cheater Robe\n"
				+ "Use this if you want to cheat you cheater\n"
				+ descriptionStats;
	}

}
