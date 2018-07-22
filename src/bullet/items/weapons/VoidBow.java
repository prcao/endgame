package bullet.items.weapons;

import java.util.ArrayList;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.projectile.Projectile;

public class VoidBow extends Weapon {

	public VoidBow() {
		super(Assets.whiteGoldBow, Assets.whiteGoldBolt);
		
		projSpeed = 5;
		dex = -500;
		baseDamage = 50;
		damageRange = 50;
		
	}

	@Override
	public String description() {
		return "Neat Bow\nShoots multiple projectiles with different ranges\n" + descriptionStats;
	}
	
	@Override
	public ArrayList<Projectile> getProjectile(double angle, double damageMult){
		ArrayList<Projectile> list = new ArrayList<Projectile>();
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed, Handler.getPlayer(), angle, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed + 5, Handler.getPlayer(), angle, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed + 10, Handler.getPlayer(), angle, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed + 15, Handler.getPlayer(), angle, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		
		return list;
	}

}
