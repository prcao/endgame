package bullet.items.weapons;

import java.util.ArrayList;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.projectile.Projectile;

public class DoubleBow extends Weapon{

	private int a = 0;
	
	public DoubleBow() {
		super(Assets.whiteGoldBow, Assets.whiteGoldBolt);
		
		baseDamage = 5;
		damageRange = 15;
		
		projSpeed = 18;
		dex = 40;
		descriptionStats = descriptionStats();
	}

	@Override
	public String description() {
		return "Bow of Trigonometry\n" +
				"Trigonometry breh\n\n"
				+ descriptionStats;
	}
	
	@Override
	public ArrayList<Projectile> getProjectile(double angle, double damageMult){
		
		a+=10;
		ArrayList<Projectile> list = new ArrayList<Projectile>();
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed, Handler.getPlayer(), angle + (Math.cos(Math.toRadians(a + 180)))/2, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed, Handler.getPlayer(), angle + (Math.cos(Math.toRadians(a)))/2, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed, Handler.getPlayer(), angle + (Math.sin(Math.toRadians(a)))/2, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		list.add(new Projectile(projTexture, 35, 35, projLifetime, projSpeed, Handler.getPlayer(), angle + (Math.sin(Math.toRadians(a + 180)))/2, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage))));
		
		return list;
	}

}
