package bullet.items.ability;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.effect.Poisoned;
import bullet.projectile.Projectile;

public class Spray extends AttackAbility {

	private int instability = 16, size = 40;

	public Spray() {
		super(Assets.fireBolt, Assets.fireBolt, Projectile.DEFAULT_SPEED, 500);
		
		cooldown = 100;
		descriptionStats = descriptionStats();
	}

	@Override
	public void use(double damageMult, double angle) {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer < cooldown || Handler.getPlayer().getMana() < 0)
			return;
		
		timer = 0;
		
		angle = Math.toRadians(angle);

		Projectile p = new Projectile(projTexture, size, size, projLifetime, projSpeed, Handler.getPlayer(),
				angle + Math.toRadians(r.nextInt(instability) - instability/2),
				(int)(damageMult * (r.nextInt(3))));
		Projectile p1 = new Projectile(projTexture, size, size, projLifetime, projSpeed, Handler.getPlayer(),
				angle + Math.toRadians(r.nextInt(instability) - instability/2),
				(int)(damageMult * (r.nextInt(3))));	
		Projectile p2 = new Projectile(projTexture, size, size, projLifetime, projSpeed, Handler.getPlayer(),
				angle + Math.toRadians(r.nextInt(instability) - instability/2),
				(int)(damageMult * (r.nextInt(3))));	
		Projectile p3 = new Projectile(projTexture, size, size, projLifetime, projSpeed, Handler.getPlayer(),
				angle + Math.toRadians(r.nextInt(instability) - instability/2),
				(int)(damageMult * (r.nextInt(3))));
		Projectile p4 = new Projectile(projTexture, size, size, projLifetime, projSpeed, Handler.getPlayer(),
				angle + Math.toRadians(r.nextInt(instability) - instability/2),
				(int)(damageMult * (r.nextInt(3))));	

		p.setEffect(new Poisoned(9000, 25));
		p1.setEffect(new Poisoned(9000, 25));
		p2.setEffect(new Poisoned(9000, 25));
		p3.setEffect(new Poisoned(9000, 25));
		p4.setEffect(new Poisoned(9000, 25));

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1, p2, p3, p4);
		
		Handler.getPlayer().setMana(Handler.getPlayer().getMana() - 2);
	}


	@Override
	public String description() {
		return "Shotgun\n"
				+ "bam bam\n"
				+ "Poisons enemy\n"
				+ descriptionStats;
	}

}
