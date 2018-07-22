package bullet.mob.effect;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.Mob;

public class Poisoned extends StatusEffect {

	public Poisoned(int duration, double potency) {
		super(Assets.poisoned, duration, potency);
	}

	@Override
	public void effect(Mob m) {
		super.effect(m);

		if(isActive){
			if(timer < 300)
				return;

			timer = 0;

		//	if(m.getHealth() > potency)
				m.armorPierceHurt((int) potency);
		}
	}
	
	@Override
	public StatusEffect clone() {
		return new Poisoned(duration, potency);
	}

}
