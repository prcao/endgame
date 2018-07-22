package bullet.mob.enemy.boss;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.enemy.Enemy;
import bullet.projectile.Projectile;

public class StatueMinion extends Enemy{

	private int baseSpeed, posXcomp, posYcomp;
	private Random r = new Random();
	
	public StatueMinion(int x, int y, int health, int width, int height) {
		super(x, y, health, width, height, 1);
		
		timer = 0;
		lastTime = System.currentTimeMillis();
		attackCooldown = 100;
		speed = 3;
		
		stats.setComp(width/3);
		
		posXcomp =  r.nextInt(100) - 50;
		posYcomp =  r.nextInt(100) - 50;
	}

	@Override
	public void attack() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer < attackCooldown / dexMultiplier)
			return;
		
		timer = 0;
		
		double a = Projectile.calculateAngle(pos, Handler.getPlayer().getPos());
		angle += 1;
		
		Projectile p = new Projectile(Assets.sword, 32, 32, 5000, 3, this, Math.sin(angle) + a, (int)(5 * damageMultiplier));
		p.setComp(8, 12, 0, -24);
		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);
	}
	
	@Override
	public void tick(double delta){		
		
		
		attack();
		//MOVEMENT//
		
		followPlayer(posXcomp, posYcomp);
		move(delta);
		
		super.tick(delta);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		g.drawImage(getCurrentFrame(),
				(int)(pos.x - Handler.getGameCamera().getXOffset()),
				(int)(pos.y - Handler.getGameCamera().getYOffset()),
				width,
				height,
				null);
		
		super.render(g);
	}
	
	@Override
	public BufferedImage getCurrentFrame(){
		if(xMove > 0)
			return Assets.statueMinionRight;
		else
			return Assets.statueMinionLeft;
	}
	
	

}
