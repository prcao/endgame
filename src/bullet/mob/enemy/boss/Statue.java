package bullet.mob.enemy.boss;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.gfx.Assets;
import bullet.gfx.ShakeFadeManager;
import bullet.items.Bag;
import bullet.items.consumable.HealthPotion;
import bullet.items.weapons.BlackBow;
import bullet.mob.Stats;
import bullet.mob.enemy.Enemy;
import bullet.projectile.BounceProjectile;
import bullet.projectile.Projectile;
import bullet.projectile.SpawnerProjectile;

public class Statue extends Enemy{

	private double[] angle, angleInc;
	private long[] attackTimer, lastTime;
	private Random r = new Random();
	private static boolean hasSpawned = false;
	private int laserTime = 0;

	protected ShakeFadeManager sfm;

	public Statue(int x, int y, int health, int width, int height) {
		super(x, y, health, width, height, 4);

		stats = new Stats(this, 100);

		sfm = new ShakeFadeManager(this);

		a = new Animation(Assets.statueAnimation, Animation.DEFAULT_ASPD * 8);

		int numAttacks = 10;

		angle = new double[numAttacks];
		angleInc = new double[numAttacks];

		attackTimer = new long[numAttacks];
		lastTime = new long[numAttacks];

		for(int i = 0; i < numAttacks; i++){
			angle[i] = 1;
			angleInc[i] = 5;
			attackTimer[i] = 100000000;
			lastTime[i] = System.currentTimeMillis();
		}
		//attack 0


		angleInc[1] = 7;
		angleInc[0] = 5;
		angleInc[8] = Math.toRadians(.25);
		//	tempAngleInc = angle[8];

		stats.setSize(width * 2);		
		
		damageMultiplier = 3;
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);

		if(hasSpawned){
			sfm.render(g);
			g.drawImage(getCurrentFrame(), (int)pos.x - Handler.getGameCamera().getXOffset() + r.nextInt(4) - 2,
				(int) (pos.y - Handler.getGameCamera().getYOffset() + r.nextInt(4) - 2), width, height, null);
		}else
			g.drawImage(getCurrentFrame(), (int)pos.x - Handler.getGameCamera().getXOffset(),
					(int) (pos.y - Handler.getGameCamera().getYOffset()), width, height, null);

		//g.setColor(Color.CYAN);
		//g.draw(hitbox);

		damageTextManager.render(g);
		stats.renderAsHUD(g);
		
	}

	public BufferedImage getCurrentFrame(){
		return a.getFrames().get(0);//a.getCurrentAnimationFrame();
	}

	@Override
	public void tick(double delta) {
		
		super.tick(delta);

		//lighting//
		if(lightSource != null)
			lightSource.setPotency(1);

		if(hasSpawned){
			attack();
			sfm.tick(delta);
		}

		if(a != null)
			a.tick();
	}

	@Override
	public void attack(){			
		if(health > maxHealth - (maxHealth * .25)){
			darknessBolt(400, 5);
			spray360();
		}
		else if(health > maxHealth - (maxHealth * .5)){
			fireballBounce();
			flower();
			//iceSpawner();
			//spawn();
		}
		else if(health > maxHealth - (maxHealth * .75)){
			icicle();
			iceSpawner();
			//spawn();
			//fireball();
			//laser();
		}
		else if(health > 0){			
			//flower();
			//icicle();
			darknessBolt(4000, 6);
			laser();
			swords();
		}
	}

	public void icicle(){
		attackTimer[0] += System.currentTimeMillis() - lastTime[0];
		lastTime[0] = System.currentTimeMillis();

		if(attackTimer[0] < 10 / dexMultiplier)
			return;

		attackTimer[0] = 0;
		//////////////////////////////////////////////////


		angle[0] += angleInc[4];

		double a = Projectile.calculateAngle(pos, Handler.getPlayer().getShiftedAimPos());

		if(angle[0] > 180)
			angle[0] -= 360;
		
		Projectile p = new Projectile(Assets.icicle, 32, 32, 20000, 3, this,
				angle[0]
						, (int)(10 * damageMultiplier));

		Projectile p1 = new Projectile(Assets.icicle, 32, 32, 20000, 3, this,
				angle[0] - Math.toRadians(180)
				, (int)(10 * damageMultiplier));

		p.setComp(8, 14, 0, -28);
		p1.setComp(8, 14, 0, -28);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1);
	}

	public void swords(){
		attackTimer[9] += System.currentTimeMillis() - lastTime[9];
		lastTime[9] = System.currentTimeMillis();

		if(attackTimer[9] < 20 / dexMultiplier)
			return;

		attackTimer[9] = 0;
		//////////////////////////////////////////		
		angle[9] += angleInc[9];

		double a = Projectile.calculateAngle(pos, Handler.getPlayer().getShiftedAimPos());

		Projectile p = new Projectile(Assets.sword, 32, 32, 20000, r.nextInt(2) + 2, this,
				Math.sin(angle[9]) + a, (int)(10 * damageMultiplier));

		p.setComp(8, 12, 0, -24);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);

	}

	public void laser(){
		attackTimer[8] += System.currentTimeMillis() - lastTime[8];
		lastTime[8] = System.currentTimeMillis();

		if(attackTimer[8] < 25 / dexMultiplier){
			return;

		}

		laserTime++;
		if(laserTime >= 400){
			laserTime = 0;
			angleInc[8] *= -1;
		}

		attackTimer[8] = 0;
		//////////////////////////////////////////////////
		angle[8] += angleInc[8];

		Projectile p = new Projectile(Assets.darknessLaser, 48, 48, 20000, 5, this,
				angle[8]
						, (int)(15 * damageMultiplier));
		Projectile p1 = new Projectile(Assets.darknessLaser, 48, 48, 20000, 5, this,
				angle[8] + Math.toRadians(90)
				, (int)(15 * damageMultiplier));
		Projectile p2 = new Projectile(Assets.darknessLaser, 48, 48, 20000, 5, this,
				angle[8] + Math.toRadians(180)
				, (int)(15 * damageMultiplier));
		Projectile p3 = new Projectile(Assets.darknessLaser, 48, 48, 20000, 5, this,
				angle[8] - Math.toRadians(90)
				, (int)(15 * damageMultiplier));

		p.setComp(0, 10, 0, -20);
		p1.setComp(0, 10, 0, -20);
		p2.setComp(0, 10, 0, -20);
		p3.setComp(0, 10, 0, -20);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1, p2, p3);
	}


	public void darknessBolt(int timer, int speed){
		attackTimer[7] += System.currentTimeMillis() - lastTime[7];
		lastTime[7] = System.currentTimeMillis();

		if(attackTimer[7] < timer / dexMultiplier){
			return;

		}

		attackTimer[7] = 0;
		//////////////////////////////////////////////////



		for(int i = 1; i < speed; i++){
			Projectile p = new Projectile(Assets.darknessBolt, 48, 48, 20000, i , this,
					Handler.getPlayer().getPos()
					, (int)(10 * damageMultiplier));

			p.setComp(10, 20, 0, -20);

			Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);
		}
	}

	public void spawn(){
		attackTimer[6] += System.currentTimeMillis() - lastTime[6];
		lastTime[6] = System.currentTimeMillis();

		if(attackTimer[6] < 3000)
			return;

		attackTimer[6] = 0;
		////////////////////////////////////////////////////

		for(int i = 0; i < 2; i++){
			Handler.getGame().getGameState().getMap().getEntityManager().addEntity(
					new StatueMinion((int)pos.y, (int)pos.x, 120, 64, 64));
		}

	}

	public void flower(){
		attackTimer[4] += System.currentTimeMillis() - lastTime[4];
		lastTime[4] = System.currentTimeMillis();

		if(attackTimer[4] < 1 / dexMultiplier)
			return;

		attackTimer[4] = 0;
		////////////////////////////////////////////////////
		angle[4] += angleInc[4];

		Projectile p = new Projectile(Assets.fireBolt, 32, 32, 6000, 3, this, angle[4], (int)(5 * damageMultiplier));
		Projectile p1 = new Projectile(Assets.fireBolt, 32, 32, 6000, 3, this, -angle[4],(int) (5 * damageMultiplier));

		p.setComp(8, 11, 0, -22);
		p1.setComp(8, 11, 0, -22);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1);
	}

	public void iceSpawner(){
		attackTimer[3] += System.currentTimeMillis() - lastTime[3];
		lastTime[3] = System.currentTimeMillis();

		if(attackTimer[3] < 5000 / dexMultiplier)
			return;

		attackTimer[3] = 0;

		double angle = Projectile.calculateAngle(pos, Handler.getPlayer().getShiftedAimPos());

		Projectile sp = new Projectile(Assets.iceStar, 32, 32, 5000, 2, this, angle,(int)((r.nextInt(10) + 5) * damageMultiplier));
		sp.setAngleDrawComp(90);
		sp.setComp(6, 6, -16, -16);

		SpawnerProjectile p = new SpawnerProjectile(Assets.iceBall, 128, 128, 10000, 2, this, angle + Math.toRadians(30), (int)((r.nextInt(5)+60)  * damageMultiplier),
				sp);		

		p.setAngleDrawComp(90);
		p.setComp(16, 16, -32, -32);

		SpawnerProjectile p1 = new SpawnerProjectile(Assets.iceBall, 128, 128, 10000, 2, this, angle  - Math.toRadians(30), (int)((r.nextInt(5)+60) * damageMultiplier),
				sp);		
		p1.setRotation(-1);

		p1.setAngleDrawComp(90);
		p1.setComp(16, 16, -32, -32);
		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p, p1);
	}

	//fireball
	public void fireball(){		
		attackTimer[2] += System.currentTimeMillis() - lastTime[2];
		lastTime[2] = System.currentTimeMillis();

		if(attackTimer[2] < 600 / dexMultiplier)
			return;
		/////////////////////////////////////////////////////////////

		attackTimer[2] = 0;

		Projectile p = new Projectile(Assets.largeFireBall, 128, 128, 20000, 6, this, 
				Handler.getPlayer().getShiftedAimPos(), (int)((r.nextInt(15)+10) * damageMultiplier));
		p.setComp(30, 40, -30, -75);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);

	}

	//fireball bounce
	public void fireballBounce(){		
		attackTimer[5] += System.currentTimeMillis() - lastTime[5];
		lastTime[5] = System.currentTimeMillis();

		if(attackTimer[5] < 3000 / dexMultiplier)
			return;
		/////////////////////////////////////////////////////////////

		attackTimer[5] = 0;

		double angle = Projectile.calculateAngle(pos, Handler.getPlayer().getShiftedAimPos());

		Projectile p = new BounceProjectile(Assets.largeFireBall, 128, 128, 20000, 7, this, 
				angle, (int)((r.nextInt(10)+10) * damageMultiplier));
		p.setComp(30, 40, -30, -75);

		Projectile p1 = new BounceProjectile(Assets.largeFireBall, 128, 128, 20000, 7, this, 
				angle + Math.toRadians(20), (int)((r.nextInt(10)+10) * damageMultiplier));
		p1.setComp(30, 40, -30, -75);

		Projectile p2 = new BounceProjectile(Assets.largeFireBall, 128, 128, 20000, 7, this, 
				angle - Math.toRadians(20), (int)((r.nextInt(10)+10) * damageMultiplier));
		p2.setComp(30, 40, -30, -75);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p1, p2, p);
	}

	public void spray360() {
		attackTimer[1] += System.currentTimeMillis() - lastTime[1];
		lastTime[1] = System.currentTimeMillis();

		if(attackTimer[1] < 0 / dexMultiplier)
			return;
		//////////////////////////////////////////////////////////////		
		angle[1] += angleInc[1];
		if(angle[1] > 180)
			angle[1] -= 360;

		attackTimer[1] = 0;
		//attack1
		Projectile p = new Projectile(Assets.fireBolt, 32, 32, 10000, r.nextInt(4)+3, this, angle[1], (int)((r.nextInt(5) + 5) * damageMultiplier));
		p.setComp(8, 11, 0, -22);

		Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(p);


		//DEBUGGING
		//System.out.println(projectileManager.getProjectiles().size());
	}

	public void hurt(int damage){
		super.hurt(damage);
	}

	public Stats getStats(){
		return stats;
	}

	@Override
	public void die(){
		isAlive = false;

		if(!hasSpawned)
			Handler.getGame().getGameState().getMap().getEntityManager().addEntity(new FireStatue((int)pos.x, (int)pos.y, 10000, width, height));
		else{			
			Bag bag = new Bag(new Point2D.Double(pos.x + width/2, pos.y + height/2));
			bag.items[0] = new BlackBow();
			bag.items[1] = new HealthPotion();
			Handler.getGame().getGameState().getMap().getEntityManager().addEntity(bag);

			Handler.getGame().getGameState().getMap().topLayer.add(new Color(255, 255, 255, 255));
			
			//Handler.getGame().getGameState().getMap().getEntityManager().addEntity(new LevelStairs(this.pos, Assets.stairs));
		}

		hasSpawned = true;
	}
}
