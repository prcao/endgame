package bullet.items.ability;

import java.awt.image.BufferedImage;

import bullet.Handler;

public abstract class SummonAbility extends Ability {

	public SummonAbility(BufferedImage itemTexture) {
		super(itemTexture);
	}
	
	public abstract void tick(double delta);
}
