package bullet.mob.effect;

import bullet.gfx.Assets;
import bullet.mob.Mob;

public class Regeneration extends StatusEffect {

	public Regeneration(int duration, double potency) {
		super(Assets.regeneration, duration, potency);
	}

	@Override
	public void effect(Mob m) {
		super.effect(m);

		if(isActive){
			if(timer < 300)
				return;

			timer = 0;


			m.heal((int) potency);
		}
	}
	
	@Override
	public StatusEffect clone() {
		return new Regeneration(duration, potency);
	}
}


