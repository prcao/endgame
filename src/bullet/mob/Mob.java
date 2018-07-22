package bullet.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.gfx.DamageText;
import bullet.gfx.DamageTextManager;
import bullet.mob.effect.StatusEffect;
import bullet.world.Tile;

public abstract class Mob extends Entity{

	public static final int DEFAULT_MOB_SIZE = 48;

	private ArrayList<StatusEffect> statEffects;
	
	public double damageMultiplier = 1, dexMultiplier = 1;
	protected int health, def = 0, xMove, yMove, boundsx, boundsy, hitboxx, hitboxy, maxHealth, potency = 1;
	public Rectangle bounds;
	public Shape hitbox;
	protected Stats stats;
	protected DamageTextManager damageTextManager;
	
	protected boolean hasDied = false, isActive;
	protected Type type;
	
	public Mob(int x, int y, int health, int width, int height, double potency){
		super(new Point2D.Double(x, y), potency);

		statEffects = new ArrayList<>();
		
		this.health = health;
		maxHealth = health;
		this.width = width;
		this.height = height;

		bounds = new Rectangle(x,y,width,height);
		hitbox = new Rectangle(x,y,width,height);
		
		stats = new Stats(this, 100);
		damageTextManager = new DamageTextManager();
		
		
		
		if(health <= 0)
			isAlive = false;
	}
	
	public abstract BufferedImage getCurrentFrame();

	
	////////////////////////////////MOVEMENT///////////////////////////////////////////////
	public void move(double delta){		
		moveX(delta);
		moveY(delta);
	}

	//collision detection
	//checks the two of the four corners and tests if they will intersect with a solid object
	//for example, if xMove > 0, the mob will attempt to move right
	//so it will check the top right and the bottom right corners and test if they intersect
	//if they do intersect, a collision has occurred
	
	public void moveX(double delta){
		
		if(xMove == 0) return;
		
		Point2D.Double p1 = new Point2D.Double(bounds.x + xMove, bounds.y);
		Point2D.Double p2 = new Point2D.Double(bounds.x + xMove, bounds.y + bounds.height);

		if(xMove > 0){
			p1.x += bounds.width;
			p2.x += bounds.width;
		}

		if(!collidingWithTile(p1,p2))
			pos.x += xMove * delta;
	}

	public void moveY(double delta){
		if(yMove == 0) return;
		
		Point2D.Double p1 = new Point2D.Double(bounds.x, bounds.y + yMove);
		Point2D.Double p2 = new Point2D.Double(bounds.x + bounds.width, bounds.y + yMove);

		if(yMove > 0){			
			p1.y += bounds.height;
			p2.y += bounds.height;
		}
		
		if(!collidingWithTile(p1,p2))
			pos.y += yMove * delta;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void tick(double delta){		
		damageTextManager.tick(delta);
		for(int i = 0; i < statEffects.size(); i++){
			statEffects.get(i).effect(this);
			
			if(!statEffects.get(i).isActive())
				removeEffect(statEffects.get(i));
		}
		
		Handler.getGame().getGameState().getMap().getLightSourceManager().removeLight(lightSource);
		
		lightSource.setPos(new Point2D.Double(pos.x + width/2, pos.y + height/2));
		
		if(isAlive)
			Handler.getGame().getGameState().getMap().getLightSourceManager().addLight(lightSource);
	}
	public void render(Graphics2D g){
		
		damageTextManager.render(g);
		int length = StatusEffect.DRAWSIZE * statEffects.size();
		
		int i = 0;
		
		for(StatusEffect se : statEffects){
			g.drawImage(se.getIcon(),(int)(pos.x + width/2 - length/2 - Handler.getGameCamera().getXOffset()) + (StatusEffect.DRAWSIZE * i),
					(int) (pos.y - (StatusEffect.DRAWSIZE * 1.5) - Handler.getGameCamera().getYOffset()),
					StatusEffect.DRAWSIZE, StatusEffect.DRAWSIZE, null);
			
			i++;
		}
		
	//	g.setColor(Color.CYAN);
	//	g.draw(hitbox);
	}

	public boolean collidingWithTile(Point2D.Double p1, Point2D.Double p2){
		for(Tile t : Handler.getGame().getGameState().getMap().getTileManager().getTiles()){
			if(t.isSolid()){
				if(t.getOffsetTile().contains(p1) || t.getOffsetTile().contains(p2)){
					return true;
				}
			}
		}
		return false;
	}

	public void hurt(int damage){			
			health -= Math.max(1,Math.max(.15 * damage, damage - def));
			
			damageTextManager.addDamage(new DamageText(this, (int)Math.max(1,Math.max(.15 * damage, damage - def)), Color.RED));
			
			if(health <= 0 && !hasDied){
				die();
				hasDied = true;
			}
	}
	
	public void armorPierceHurt(int damage){
		health -= damage;
		
		damageTextManager.addDamage(new DamageText(this, damage, Color.RED));
		
		if(health <= 0)
			die();
	}
	
	public void heal(int amount){
		damageTextManager.addDamage(new DamageText(this, -amount, Color.GREEN));
		if(health < maxHealth){
			health += amount;
		}
		if (health > maxHealth){
			health = maxHealth;
		}
	}
	
	protected void die() {
		isAlive = false;
	//	Handler.getGame().getGameState().getMap().getLightSourceManager().purgeLights();
		Handler.getGame().getGameState().getMap().getLightSourceManager().removeLight(lightSource);
	}
	
	public int maxHealth() {
		return maxHealth;
	}

	public double getX() {
		return pos.x;
	}
	
	public double getY(){
		return pos.y;
	}
	
	public Point2D.Double getShiftedMidPos(){
		return new Point2D.Double(pos.x + width/2, pos.y + width/2);
	}
	
	public Point2D.Double getShiftedAimPos(){
		return new Point2D.Double(pos.x - width/2, pos.y - width/2);
	}
	
	
	public Point2D.Double getPos(){
		return new Point2D.Double(pos.x, pos.y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void applyEffect(StatusEffect e){
		boolean isDifferent = true;
		int i = 0;
		
		for(StatusEffect stat : statEffects){
			
			
			if(stat.getClass().equals(e.getClass())){
				isDifferent = false;
				statEffects.get(i).resetTimer();
			}
			
			i++;
		}
		if(isDifferent){
			statEffects.add(e);
			
		}
			
	}
	
	public void removeEffect(StatusEffect e){
		statEffects.remove(e);
	}
	
	public Type getType(){
		return type;
	}
	
	public enum Type{
		ALLY, ENEMY
	}
}
