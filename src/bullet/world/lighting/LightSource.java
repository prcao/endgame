package bullet.world.lighting;

import java.awt.geom.Point2D;

import bullet.Handler;

public class LightSource {

	protected Point2D.Double pos;
	protected int alpha;
	protected double potency;	//potency is stronger when number is less lel
	
	public LightSource(Point2D.Double pos, double potency){
		this.pos = pos;
		this.potency = potency;
		
		alpha = 0;
	}
	
	public void setAlpha(int alpha){
		this.alpha = alpha;
	}
	
	public void setPos(Point2D.Double pos){
		this.pos = pos;
	}
	
	public Point2D.Double getPos(){
		return pos;
	}
	
	public double getPotency(){
		return potency;
	}
	
	public void setPotency(double d){
		this.potency = d;
	}
}
