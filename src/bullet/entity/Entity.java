package bullet.entity;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Comparator;

import bullet.Handler;
import bullet.world.lighting.LightSource;

public abstract class Entity {
	
	public Point2D.Double pos;
	protected int width, height;
	protected boolean isAlive = true;
	protected LightSource lightSource;
	
	public Entity(Point2D.Double pos, double potency){
		this.pos = pos;
		lightSource = new LightSource(new Point2D.Double(pos.x + width/2, pos.y + height/2), potency);
	}
	
	public abstract void tick(double delta);
	public abstract void render(Graphics2D g);

	public Point2D.Double getPos(){
		return pos;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
}
