package bullet.items.weapons;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.effect.Regeneration;
import bullet.projectile.Projectile;

public class BasicBow extends Weapon{

	public BasicBow() {
		super(Assets.basicBow, Assets.redArrow);
	
		baseDamage = 20;
		damageRange = 10;
		projSpeed = Projectile.DEFAULT_SPEED;
		projLifetime = 600;
		
	}

	@Override
	public String description() {
		return "Basic Bow\nA basic bow\n" + descriptionStats;
	}
}
