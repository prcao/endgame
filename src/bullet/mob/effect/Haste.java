package bullet.mob.effect;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.Mob;

public class Haste extends StatusEffect {

	public Haste(int duration, double potency) {
		super(Assets.haste, duration, potency);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Mob m) {
		super.effect(m);

		if(isActive)
			m.dexMultiplier = 1 + potency;
		else
			m.dexMultiplier = 1;
	}

	@Override
	public StatusEffect clone() {
		return new Haste(duration, potency);
	}
	
	

}
