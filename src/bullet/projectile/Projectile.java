package bullet.projectile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.mob.Mob;
import bullet.mob.effect.StatusEffect;
import bullet.world.Tile;

public class Projectile{

	public final static int DEFAULT_SPEED = 20;

	protected BufferedImage texture;
	protected int width, height, lifetime, speed, r, xComp, xComp2, yComp, widthComp, heightComp, damage;
	protected Mob m;
	protected Point2D.Double origin, pos, des;
	protected double angle, hitboxAngle, angleDrawComp = 45, velocityX, velocityY;
	protected float distanceX, distanceY;
	protected long cLifetime, lastTime;
	public Rectangle bounds;
	public Shape hitbox;

	protected boolean isActive = true;
	protected StatusEffect effect = null;

	protected AffineTransform at, old;

	public Projectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, int damage){
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.lifetime = lifetime;
		this.speed = speed;
		this.m = m;
		this.damage = damage;

		//compensation for hitbox rotation
		xComp = 0;
		yComp = 0;
		widthComp = 0;
		heightComp = 0;
		xComp2 = 0;

		origin = new Point2D.Double(m.getPos().x + m.getWidth()/2 - width/2, m.getPos().y + m.getHeight()/2 - height/2);
		pos = new Point2D.Double(origin.x, origin.y);

		r = 0;		

		cLifetime = 0;
		lastTime = System.currentTimeMillis();

		at = new AffineTransform();
		old = new AffineTransform();

		bounds = new Rectangle();

		hitbox = new Rectangle();
	}

	public Projectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, Point2D.Double des, int damage){
		this(texture, width, height, lifetime, speed, m, damage);
		angle = calculateAngle(origin, des);

		velocityX = Math.cos(angle);
		velocityY = Math.sin(angle);
	}

	public Projectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, double angle, int damage){
		this(texture, width, height, lifetime, speed, m, damage);
		this.angle = angle;

		velocityX = Math.cos(angle);
		velocityY = Math.sin(angle);
	}

	public void tick(double delta){			
		//COLLISION//
		bounds.setBounds((int)(pos.x  - Handler.getGameCamera().getXOffset() + xComp),(int) (pos.y - Handler.getGameCamera().getYOffset() + yComp), width + widthComp, height + heightComp);

		AffineTransform t = new AffineTransform();

		t.rotate(angle, pos.x + width/2 - Handler.getGameCamera().getXOffset(), pos.y + height/2 - Handler.getGameCamera().getYOffset());
		hitbox = t.createTransformedShape(bounds);

		collidingWithMob(m);

		//POSITIONS//
		//r += speed * delta;

		//pos.x = (int) ((r*velocityX) + origin.x);
		//pos.y = (int) ((r*velocityY) + origin.y);	

		//System.out.println(velocityX * speed + " " + velocityY * speed);

		pos.x += velocityX * speed * delta;
		pos.y += velocityY * speed * delta;

		//LIFETIME//
		cLifetime+=System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if(cLifetime >= lifetime)
			setActive(false);	

		if(!(this instanceof BounceProjectile) && collidingWithTile(hitbox))
			setActive(false);
	}

	//rotated sprite
	public void render(Graphics2D g){
		old = g.getTransform();
		at = new AffineTransform();
		at.rotate(angle + Math.toRadians(angleDrawComp), pos.x + width/2 - Handler.getGameCamera().getXOffset(), pos.y + height/2 - Handler.getGameCamera().getYOffset());
		g.setTransform(at);
		
		g.drawImage(texture, (int)(pos.x - Handler.getGameCamera().getXOffset()), (int)(pos.y - Handler.getGameCamera().getYOffset()), width, height, null);	
		g.setTransform(old);

		//HITBOX//
		//g.setColor(Color.magenta);
		//g.draw(hitbox);				
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

	//tile collision detection
	public boolean collidingWithTile(Shape r){

		for(Tile t : Handler.getGame().getGameState().getMap().getTileManager().getTiles()){
			if(t.isSolid()){
				if(r.intersects(t.getOffsetTile())){
					return true;					
				}
			}
		}
		return false;
	}
	
	//hitbox compensation
	public void setComp(int x,int y,int width,int height){
		xComp = x;
		xComp2 = xComp;
		yComp = y;
		widthComp = width;
		heightComp = height;
	}

	//player collisiondetection
	public void collidingWithMob(Mob originMob){

		for(Entity m : Handler.getGame().getGameState().getMap().getEntityManager().entities){
			if(m instanceof Mob){
				
				//same types do not damage each other
				if(originMob.getType() == ((Mob) m).getType())
					continue;

				if(testIntersection(hitbox, ((Mob)m).hitbox)){

					((Mob)m).hurt(damage);

					if(effect != null){
						((Mob)m).applyEffect(effect.clone());
					}

					setActive(false);
					return;						
				}
			}
		}
	}

	public static boolean testIntersection(Shape shapeA, Shape shapeB) {
		Area areaA = new Area(shapeA);
		areaA.intersect(new Area(shapeB));
		return !areaA.isEmpty();
	}

	public boolean isActive(){
		return isActive;
	}

	public double getAngle(){
		return angle;
	}

	public static double calculateAngle(Point2D.Double p1, Point2D.Double p2){
		double distanceX = p2.x - p1.x;
		double distanceY = p2.y - p1.y;

		return Math.atan2(distanceY, distanceX);	
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setOrigin(Point2D.Double origin){		
		this.origin = origin;

		pos.x = origin.getX();
		pos.y = origin.getY();
	}

	public BufferedImage getTexture(){
		return texture;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLifetime() {
		return lifetime;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDamage() {
		return damage;
	}

	public Mob getMob() {
		return m;
	}	

	public double getAngleDrawComp() {
		return angleDrawComp;
	}

	public void setAngleDrawComp(double angleDrawComp) {
		this.angleDrawComp = angleDrawComp;
	}

	public int getxComp() {
		return xComp;
	}

	public int getyComp() {
		return yComp;
	}

	public int getWidthComp() {
		return widthComp;
	}

	public int getHeightComp() {
		return heightComp;
	}	

	public StatusEffect getEffect(){
		return effect;
	}

	public void setEffect(StatusEffect e){
		effect = e;
	}
	
	public Point2D.Double getPos(){
		return pos;
	}
}