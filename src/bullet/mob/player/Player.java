package bullet.mob.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.gfx.Assets;
import bullet.gfx.TextToScreen;
import bullet.items.ability.Ability;
import bullet.items.armor.Armor;
import bullet.items.armor.LeatherArmor;
import bullet.items.weapons.BasicBow;
import bullet.items.weapons.Weapon;
import bullet.misc.Grave;
import bullet.mob.Mob;
import bullet.projectile.Projectile;
import bullet.world.Tile;

public class Player extends Mob{

	public static final long DEFAULT_SHOOTSPEED = 200;
	public static int baseSpeed = 5, baseAtt = 50, baseDex = 50, baseHP = 150, baseDef = 0;

	private int speed, attack, dex, def, mana, maxMana;
	private Animation aUp, aDown, aLeft, aRight, lastA, atkUp, atkDown, atkLeft, atkRight;
	private long lastTime, timer, shootSpeed = DEFAULT_SHOOTSPEED, lastManaHealTime, healManaTimer, healManaCooldown = 100;
	private double angle;
	private boolean showHitbox;
	private Grave grave;

	private Weapon equippedWeapon;
	private Armor equippedArmor;
	private Ability equippedAbility;
	private Inventory inv;
	
	//	private Poisoned poison = new Poisoned(this, 10000, 2);
	//	private Regeneration heal = new Regeneration(this, 100000, 2);

	//render distance
	public int renderW = 5 * Tile.TILE_SIZE, renderH = 5 * Tile.TILE_SIZE;
	public static Rectangle render; 

	public Player(int x, int y) {
		super(x, y, baseHP, DEFAULT_MOB_SIZE, DEFAULT_MOB_SIZE, .7);
		maxHealth = baseHP;

		type = Type.ALLY;
		mana = maxMana = 100;
		speed = baseSpeed;

		//render
		render = new Rectangle();

		//a coach, a managers//
		//	projectileManager = new ProjectileManager();

		//animations//
		aUp = new Animation(Assets.player_up, Animation.DEFAULT_ASPD);
		aDown = new Animation(Assets.player_down, Animation.DEFAULT_ASPD);
		aLeft = new Animation(Assets.player_left, Animation.DEFAULT_ASPD);
		aRight = new Animation(Assets.player_right, Animation.DEFAULT_ASPD);
		lastA = aDown;
		//atk animations//
		atkUp = new Animation(Assets.player_atk_up, shootSpeed/2);
		atkDown = new Animation(Assets.player_atk_down, shootSpeed/2);
		atkLeft = new Animation(Assets.player_atk_left, shootSpeed/2);
		atkRight = new Animation(Assets.player_atk_right, shootSpeed/2);

		//stat bars//

		//hitboxes//
		bounds.x = width/8;
		bounds.y = height/8 * 4;
		bounds.width = width/8 * 5;
		bounds.height = width/8* 4;

		boundsx = bounds.x;
		boundsy = bounds.y;

		hitbox = new Rectangle(bounds.x + bounds.width/2 - 4, bounds.y, 8, 8);

		hitboxx = hitbox.getBounds().getBounds().x;
		hitboxy = hitbox.getBounds().getBounds().y;

		//timer//
		lastTime = System.currentTimeMillis();
		lastManaHealTime = System.currentTimeMillis();

		healManaTimer = 0;

		grave = new Grave(this);

		equippedWeapon = new BasicBow();
		equippedArmor = new LeatherArmor();

		inv = new Inventory(equippedWeapon, equippedArmor, this);

		stats = new PlayerStats(this, 32);
	}

	public void tick(double delta){	
		
		
		speed = baseSpeed;
		attack = baseAtt;
		dex = baseDex;
		maxHealth = baseHP;
		setDef(baseDef);

		equippedWeapon = (Weapon) Inventory.items[0][0];
		equippedArmor = (Armor) Inventory.items[1][0];
		equippedAbility = (Ability) Inventory.items[2][0];

		for(int i = 0; i < Inventory.items.length; i++){
			if(Inventory.items[i][0] != null){
				speed += Inventory.items[i][0].getSpeed();
				attack += Inventory.items[i][0].getAttack();
				dex += Inventory.items[i][0].getDex();
				maxHealth += Inventory.items[i][0].getHP();
				setDef(getDef() + Inventory.items[i][0].getDefense());
			}
		}
		
		if(health > maxHealth)
			health = maxHealth;
		if(speed < 1)
			speed = 1;
		if(attack < 0)
			attack = 0;
		if(dex < 0)
			dex = 0;
		stats.tick(delta);
		if(isAlive){
			super.tick(delta);

			//lighting//
			if(lightSource != null){
				lightSource.setPotency(.7);
			}

			//MOVE PLAYER//
			getInput();
			move(delta);


			//UPDATE HITBOXES//

			//render is a rectangle that surrounds the edges of the screen
			//tiles and darkness will only be rendered if they intersect the render rectangle

			render.setBounds(
					-Tile.TILE_SIZE + Handler.getGameCamera().getXOffset(), -Tile.TILE_SIZE + Handler.getGameCamera().getYOffset(),
					Handler.getWidth() + 2*Tile.TILE_SIZE,
					Handler.getHeight() + 2*Tile.TILE_SIZE
					);


			//bounds is for tile collision detection
			bounds.setBounds((int)(pos.x + boundsx - Handler.getGameCamera().getXOffset()), (int) (pos.y + boundsy - Handler.getGameCamera().getYOffset()), bounds.width, bounds.height);

			//hitbox is for projectile collision detection
			hitbox = new Rectangle((int)(pos.x + hitboxx - Handler.getGameCamera().getXOffset()),
					(int) (pos.y + hitboxy - Handler.getGameCamera().getYOffset()),
					hitbox.getBounds().width, hitbox.getBounds().height);

			
			damageTextManager.tick(delta);

			//CENTER GAME CAMERA//
			Handler.getGameCamera().centerWithMouse(this);

			//handles shooting
			checkShoot();

			//mana recovery
			healManaTimer += System.currentTimeMillis() - lastManaHealTime;
			lastManaHealTime = System.currentTimeMillis();

			if(healManaTimer > healManaCooldown && mana < maxMana && !Handler.getInput().space){
				healManaTimer = 0;

				mana++;

			}
			
			if(Handler.getInput().e){
				System.out.println("interacated from player");
				Handler.getGame().getGameState().getMap().getInteractableManager().interact(delta);
			}
		}
		else
			grave.tick(delta);
	}

	public void checkShoot(){
		//cooldown between each shot
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();		

		if(inv.getMouseItem() == null){
			
			//dex
			shootSpeed = (long) (1000/((6.5 * dex/50) + 1.5));

			//calculates angle to switch animation direction
			angle = Math.toDegrees(Projectile.calculateAngle(
					pos, new Point2D.Double(Handler.getInput().getMouseWorldPos().x - 48/2,	//width/2
							Handler.getInput().getMouseWorldPos().y - 48/2)));	//height/2);

			//ability
			if(equippedAbility != null){
				if(Handler.getInput().space)
					equippedAbility.use(damageMultiplier, angle);
				else
					equippedAbility.cleanup();
			}

			if(timer < shootSpeed / dexMultiplier) return;

			timer = 0;

			if(Handler.getInput().leftPressed && equippedWeapon != null){

				//old code
				/*			Projectile p =	new Projectile(
						equippedWeapon.getProjTexture(),
						48,	//width
						48,	//height
						equippedWeapon.getProjLifetime(),	//lifetime ms
						equippedWeapon.getProjSpeed(),	//speed (pixels/tick)
						this,	//entity shoots from
						//desitnation to
						Math.toRadians(angle),
						(int)((r.nextInt(damageRange) + baseDamage) * damageMultiplier)	//damage
						);									

				p.setComp(0, 16, 16, -32);
				 */
				ArrayList<Projectile> playerShot = equippedWeapon.getProjectile(Math.toRadians(angle), damageMultiplier);
				
				//sets hitboxes
				for(Projectile p : playerShot) p.setComp(0, 16, 16, -32);
				
				Handler.getGame().getGameState().getMap().getProjectileManager().addProjectile(playerShot);
			}
		}
	}

	public void render(Graphics2D g){
		
		if(isAlive){
			g.drawImage(getCurrentFrame(), (int)(pos.x - Handler.getGameCamera().getXOffset()), (int) (pos.y - Handler.getGameCamera().getYOffset()), width, height, null);	
			if(showHitbox)
				g.drawImage(Assets.hitbox, hitbox.getBounds().x, hitbox.getBounds().y, hitbox.getBounds().width, hitbox.getBounds().height, null);

			//`	g.setColor(Color.RED);

			//renders hp and mp
			
			super.render(g);
			damageTextManager.render(g);

			//debug stuff///////////////////////////////////////////////

			//render distance//
			//g.drawRect(render.x, render.y, renderW, renderH);

			//hitboxes//

			//g.drawRect(bounds.x , bounds.y , bounds.width, bounds.height);
			//g.drawRect(hitbox.getBounds().x , hitbox.getBounds().y , hitbox.getBounds().width, hitbox.getBounds().height);


		}else{
			grave.render(g);
		}
		stats.render(g);
	}

	public BufferedImage getCurrentFrame(){

		//ATTACK ANIMATIONS//
		if(Handler.getInput().leftPressed && inv.getMouseItem() == null){			
			if(angle <= -45 && angle > -135){
				atkUp.speed = shootSpeed/2;
				atkUp.tick();
				lastA = aUp;
				return atkUp.getCurrentAnimationFrame();
			}
			if(angle <= 135 && angle > 45){
				atkDown.speed = shootSpeed/2;
				atkDown.tick();
				lastA = aDown;
				return atkDown.getCurrentAnimationFrame();
			}
			if(angle <= 45 && angle > -45){
				atkRight.speed = shootSpeed/2;
				atkRight.tick();
				lastA = aRight;
				return atkRight.getCurrentAnimationFrame();
			}else{
				atkLeft.speed = shootSpeed/2;
				atkLeft.tick();
				lastA = aLeft;
				return atkLeft.getCurrentAnimationFrame();
			}
		}

		//move animation
		if(xMove != 0 || yMove != 0)
			return lastA.getCurrentAnimationFrame();

		//idle frame
		if(lastA == aUp)
			return Assets.player_up_idle;
		if(lastA == aDown)
			return Assets.player_down_idle;
		if(lastA == aLeft)
			return Assets.player_left.get(1);
		if(lastA == aRight)
			return Assets.player_right.get(0);

		return Assets.player_down_idle;
	}

	public void getInput(){
		xMove = 0;
		yMove = 0;

		int tempSpd = speed;

		if(Handler.getInput().shift){
			tempSpd = speed/2;

			showHitbox = true;
		}else{
			showHitbox = false;
		}

		if(Handler.getInput().w){
			yMove = -tempSpd;

			aUp.tick();
			lastA = aUp;
		}
		if(Handler.getInput().a){
			xMove = -tempSpd;

			aLeft.tick();
			lastA = aLeft;
		}
		if(Handler.getInput().s){
			yMove = tempSpd;

			aDown.tick();
			lastA = aDown;
		}
		if(Handler.getInput().d){
			xMove = tempSpd;

			aRight.tick();
			lastA = aRight;
		}
	}

	@Override
	public void hurt(int damage){

		if(equippedArmor != null){
			damage = (int) Math.max(damage - getDef(), .1 * damage);
		}

		super.hurt(damage);

		//	stats.damage(damage);
	}

	public void die(){
		if(isAlive){
			isAlive = false;

			grave = new Grave(this);
			hitbox.getBounds().setBounds(0, 0, 0, 0);
			
		}
	}

	public Weapon getEquippedWeapon(){
		return equippedWeapon;
	}

	public void setEquippedWeapon(Weapon w){
		equippedWeapon = w;
	}

	public Inventory getInventory(){
		return inv;
	}

	public double getAngle(){
		return angle;
	}

	public int getMana(){
		return mana;
	}

	public void setMana(int mana){
		this.mana = mana;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}
	
	//honestly i should rename this to isActive
	public boolean isAlive() {
		return true;
	}
}
