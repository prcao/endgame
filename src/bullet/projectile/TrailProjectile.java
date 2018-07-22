package bullet.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.mob.Mob;

public class TrailProjectile extends Projectile{
	
	private double size;
	private Ellipse2D.Double[] particles = new Ellipse2D.Double[256];
	private long timer = 0, lastTime, cooldown = 50;

	public TrailProjectile(BufferedImage texture, int width, int height, int lifetime, int speed,
			Mob m, int damage, double size) {
		super(texture, width, height, lifetime, speed, m, damage);
		
		for(int i = 0; i < particles.length; i++){
			particles[i] = new Ellipse2D.Double(pos.x - Handler.getGameCamera().getXOffset(), pos.y - Handler.getGameCamera().getYOffset(), size, size);
		}
		
		lastTime = System.currentTimeMillis();
	}
	
	public TrailProjectile(BufferedImage texture, int width, int height, int lifetime, int speed,
			Mob m, Double des, int damage, double size) {
		super(texture, width, height, lifetime, speed, m, des, damage);
		this.size = size;
		
		for(int i = 0; i < particles.length; i++){
			particles[i] = new Ellipse2D.Double(pos.x - Handler.getGameCamera().getXOffset(), pos.y - Handler.getGameCamera().getYOffset(), size, size);
		}
	}
	
	public TrailProjectile(BufferedImage texture, int width, int height, int lifetime, int speed,
			Mob m, double angle, int damage, double size) {
		super(texture, width, height, lifetime, speed, m, angle, damage);
		this.size = size;	
		
		for(int i = 0; i < particles.length; i++){
			particles[i] = new Ellipse2D.Double(pos.x - Handler.getGameCamera().getXOffset(), pos.y - Handler.getGameCamera().getYOffset(), size, size);
		}
	}
	
	public void tick(double delta){
		super.tick(delta);
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer < cooldown)
			return;
		
		timer = 0;
		
		for(int i = 0; i < particles.length - 1; i++){
			particles[i+1] = particles[i];
		}
		
		particles[0].x = pos.x - Handler.getGameCamera().getXOffset();
		particles[1].y = pos.y - Handler.getGameCamera().getYOffset();	
	}
	
	public void render(Graphics2D g){		
		super.render(g);
		g.setColor(Color.WHITE);
		
		for(Ellipse2D.Double e : particles){
			g.draw(e);
		}	
	}

}
