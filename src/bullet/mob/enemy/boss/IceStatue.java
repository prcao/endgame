package bullet.mob.enemy.boss;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.gfx.Assets;
import bullet.projectile.Projectile;

public class IceStatue extends Statue{

	public IceStatue(int x, int y, int health, int width, int height) {
		super(x, y, health, width, height);

		a = new Animation(Assets.statueIceAnimation, Animation.DEFAULT_ASPD*4);

		def = 8;
	}

	@Override
	public void attack(){
		if(health >= maxHealth * .4)
		
		iceSpawner();
		if (health < maxHealth * .4){
			icicle();
		}
	}

	@Override
	public void die(){
		isAlive = false;

		Statue s = new DarkStatue((int)pos.x, (int)pos.y, 10000, width, height);

		Handler.getGame().getGameState().getMap().getEntityManager().addEntity(s);

		for(int i = 0; i < 360; i += 18){
			Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(new Projectile(Assets.iceBall, 64, 64, 20000, 5, s, Math.toRadians(i), 10));
		}
	}
}
