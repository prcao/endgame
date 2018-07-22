package bullet.mob.enemy.boss;

import java.awt.Graphics2D;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.gfx.Assets;
import bullet.projectile.Projectile;

public class FireStatue extends Statue{

	public FireStatue(int x, int y, int health, int width, int height) {
		super(x, y, health, width, height);
		
		a = new Animation(Assets.statueFireAnimation, Animation.DEFAULT_ASPD*4);
	}
	
	@Override
	public void attack(){
		if(health >= maxHealth * .9){
			fireball();
		}
		if(health < maxHealth * .9 && health >= maxHealth * .7){
			fireballBounce();
		}
		if(health < maxHealth * .7 && health >= maxHealth * .5){
			flower();
		}
			
		if(health < maxHealth * .5 && health >= maxHealth * .4){
			spray360();
		}
			
		if(health < maxHealth * .4 && health >= maxHealth * .25){
			flower();
			spray360();
			
		}
		if(health < maxHealth * .25){
			fireball();
			flower();
			
		}
	}
	
	@Override
	public void die(){
		isAlive = false;		
		
		Statue s = new IceStatue((int)pos.x, (int)pos.y, 13000, width, height);
		
		Handler.getGame().getGameState().getMap().getEntityManager().addEntity(s);
		
		for(int i = 0; i < 360; i += 9){
			Projectile p = new Projectile(Assets.largeFireBall, 64, 64, 13000, 5, s, Math.toRadians(i), 10);
			p.setComp(0, 14, 0, -28);
			
			Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);
		}
	}
}
