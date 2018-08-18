package bullet.items.consumable;

import bullet.gfx.Assets;
import bullet.mob.Mob;
import bullet.mob.effect.Strength;

public class StrengthPotion extends EffectPotion {

	public StrengthPotion(int duration, Mob m, double potency) {
		super(Assets.strengthPotion, new Strength(duration, potency), duration, potency);
	}

	@Override
	public String description() {
		return "Potion of Strength\n"
				+ "A potion that applies strength\n"
				+ descriptionStats;
	}

}
