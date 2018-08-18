package bullet.items.consumable;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.Mob;
import bullet.mob.effect.Haste;

public class HastePotion extends EffectPotion {

	public HastePotion(int duration, Mob m, double potency) {
		super(Assets.hastePotion, new Haste(duration, potency), duration, potency);
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		return "Potion of Haste\n"+ 
				"A potion that applies haste\n" +
				descriptionStats;
	}

}
