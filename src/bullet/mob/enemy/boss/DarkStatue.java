package bullet.mob.enemy.boss;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.gfx.Assets;
import bullet.mob.effect.Haste;
import bullet.projectile.Projectile;

public class DarkStatue extends Statue{

	public DarkStatue(int x, int y, int health, int width, int height) {
		super(x, y, health, width, height);
		
		a = new Animation(Assets.statueDarkAnimation, Animation.DEFAULT_ASPD * 4);
		
		applyEffect(new Haste(10000000, .5));
	}
	
	@Override
	public void attack(){
		if(health >= maxHealth - (maxHealth * .25)){
			darknessBolt(900, 10);
			
			
		}
		if(health < maxHealth - (maxHealth * .25) && health >= maxHealth - (maxHealth * .5)){
			spawn();
			laser();
			
			
		}
		if(health < maxHealth - (maxHealth * .5) && health >= maxHealth - (maxHealth * .75)){
			spawn();
			swords();
			
						
		}
		if(health < maxHealth - (maxHealth * .75)){
			darknessBolt(1000, 8);
			laser();
			
			
		}
	}
	
	@Override
	public void die(){
		isAlive = false;
		
		Statue s = new Statue((int)pos.x, (int)pos.y, 10000, width, height);
		
		Handler.getGame().getGameState().getMap().getEntityManager().addEntity(s);
		
		for(int i = 0; i < 360; i += 10){
			Projectile p = new Projectile(Assets.darknessBolt, 64, 64, 20000, 5, s, Math.toRadians(i), 10);
			p.setComp(0, 14, 0, -28);
			Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);
		}
	}
	

}
