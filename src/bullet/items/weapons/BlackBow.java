package bullet.items.weapons;

import bullet.gfx.Assets;
import bullet.projectile.Projectile;

public class BlackBow extends Weapon{

	public BlackBow() {
		super(Assets.blackBow, Assets.sword);
		
		baseDamage = 100000;
		damageRange = 1;
		projSpeed = Projectile.DEFAULT_SPEED/2;
		projLifetime = 105;
		defense = -10;
		dex = -40;
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		return "neato bow\nrip\n" + descriptionStats;
	}
}
