package bullet.items.weapons;

import java.util.ArrayList;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.effect.Poisoned;
import bullet.mob.effect.Regeneration;
import bullet.projectile.Projectile;

public class SprayBow extends Weapon {

	public SprayBow() {
		super(Assets.basicBow, Assets.syringe);
		
		baseDamage = 1;
		damageRange = 1;
		dex = 2;
		projSpeed = 15;
		projLifetime = 700;
		effect = new Poisoned(5000, 100);
		instability = 20;
	}

	@Override
	public String description() {
		return "Spray Bow\n"
				+ "Poison Bois\n"
				+ descriptionStats;
	}
	
	@Override
	public ArrayList<Projectile> getProjectile(double angle, double damageMult){
		Projectile p = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle + Math.toRadians(new Random().nextInt(instability) - instability/2), (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
		Projectile p1 = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle + Math.toRadians(new Random().nextInt(10) - 5), (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
		Projectile p2 = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle + Math.toRadians(new Random().nextInt(10) - 5), (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
		Projectile p3 = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle + Math.toRadians(new Random().nextInt(10) - 5), (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
		
		ArrayList<Projectile> list = new ArrayList<Projectile>();
		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
	
		for(Projectile pr : list){
			pr.setEffect(effect);
		}
		
		return list;
	}

}
