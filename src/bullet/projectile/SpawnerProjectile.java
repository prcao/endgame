package bullet.projectile;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.mob.Mob;

public class SpawnerProjectile extends SpinProjectile{


	private Projectile subProj;

	private long lastSpawnTime = System.currentTimeMillis(), spawnTimer = 0, spawnCooldown = 100;

	public SpawnerProjectile(BufferedImage texture, int width, int height, int lifetime, int speed,
			Mob m, int damage, Projectile subProj) {
		super(texture, width, height, lifetime, speed, m, damage);
		this.subProj = subProj;
	}

	public SpawnerProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, Point2D.Double des, int damage, Projectile subProj){
		super(texture, width, height, lifetime, speed, m, des, damage);
		this.subProj = subProj;
	}

	public SpawnerProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, double angle, int damage, Projectile subProj){
		super(texture, width, height, lifetime, speed, m, angle, damage);
		this.subProj = subProj;
	}

	public void tick(double delta){
		super.tick(delta);
		
		spawnTimer += System.currentTimeMillis() - lastSpawnTime;
		lastSpawnTime = System.currentTimeMillis();
		
		if(spawnTimer > spawnCooldown){
			
			Projectile p = new SpinProjectile(subProj.getTexture(), subProj.getWidth(), subProj.getHeight(),
					subProj.getLifetime(), subProj.getSpeed(), m, angle, subProj.getDamage());
			Projectile p1 = new SpinProjectile(subProj.getTexture(), subProj.getWidth(), subProj.getHeight(),
					subProj.getLifetime(), subProj.getSpeed(), m, angle - Math.toRadians(180), subProj.getDamage());
			
			p.setComp(subProj.getxComp(), subProj.getyComp(), subProj.getWidthComp(), subProj.getHeightComp());
			p1.setComp(subProj.getxComp(), subProj.getyComp(), subProj.getWidthComp(), subProj.getHeightComp());
			
			p.setAngleDrawComp(subProj.getAngleDrawComp());
			p1.setAngleDrawComp(subProj.getAngleDrawComp());
			
			p.setOrigin(new Point2D.Double(pos.x + width/2, pos.y + height/2));
			p1.setOrigin(new Point2D.Double(pos.x + width/2, pos.y + height/2));
			
			Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1);
		
			spawnTimer = 0;
		}
	}
	
	public void setCooldown(long cooldown){
		this.spawnCooldown = cooldown;
	}
}
