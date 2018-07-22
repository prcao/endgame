package bullet.mob.effect;

import bullet.gfx.Assets;
import bullet.mob.Mob;

public class Strength extends StatusEffect {

	public Strength(int duration, double potency) {
		super(Assets.strength, duration, potency);
	}

	@Override
	public void effect(Mob m) {
		super.effect(m);
		
		if(isActive)
			m.damageMultiplier = 1 + potency;
		else
			m.damageMultiplier = 1;
	}
	
	@Override
	public StatusEffect clone() {
		return new Strength(duration, potency);
	}

}
