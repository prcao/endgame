package bullet.projectile;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.mob.Mob;

public class SpinProjectile extends Projectile{

	protected long lastSpinTime = System.currentTimeMillis();
	protected long spinTimer = 0;
	protected long spinCooldown = 10;
	protected int rotation = 1;
	
	public SpinProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, int damage) {
		super(texture, width, height, lifetime, speed, m, damage);
	}
	
	public SpinProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, Point2D.Double des, int damage){
		super(texture, width, height, lifetime, speed, m, des, damage);
	}
	
	public SpinProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, double angle, int damage){
		super(texture, width, height, lifetime, speed, m, angle, damage);
	}

	@Override
	public void tick(double delta){
		super.tick(delta);
		
		spinTimer += System.currentTimeMillis() - lastSpinTime;
		lastSpinTime = System.currentTimeMillis();
		
		if(spinTimer > spinCooldown){					
			angle += Math.toRadians(rotation);
			spinTimer = 0;
		}
	}

	public void setRotation(int rotation){
		this.rotation = rotation;
	}
}
