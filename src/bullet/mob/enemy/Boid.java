package bullet.mob.enemy;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import bullet.Handler;
import bullet.mob.Mob;

public abstract class Boid{

	public static ArrayList<Boid> boids = new ArrayList<>();
	protected static double range = 150;
	protected ArrayList<Boid> flock = new ArrayList<>();

	public Point2D.Double pos, movement;
	protected int speed = 5;

	public Boid(Point2D.Double pos){
		this.pos = pos;
		movement = new Point2D.Double(0, 0);

		boids.add(this);
	}

	public void tick(double delta){
		updateFlock();
		cohesion();
		seperation();
		alignment();
	}

	private void updateFlock() {
		for(Boid b : boids){
			if(this != b){
				if(calcDistance(this, b) < range)
					flock.add(b);
				else
					flock.remove(b);
			}
		}

	}

	public void cohesion(){
		Point2D.Double average = calcFlockPos(flock);
		
		if(average.x > pos.x){
			movement.x += speed;
			
			movement.x/=2;

		}else{
			movement.x += -speed;
			
			movement.x/=2;
		}
		if(average.y > pos.y){
			movement.y += speed;
			
			movement.y/=2;
		}else{
			movement.y += -speed;
			
			movement.y/=2;
		}
	}

	public void seperation(){
		for(Boid b : flock){
			if(this != b){
				if(calcDistance(this, b) < range/2){
					if(Math.abs(pos.x - b.getPos().x) < Math.abs(pos.y - b.getPos().y)){
						movement.x += -movement.x;
						movement.x/=2;
					}else{
						movement.y += -movement.y;
						movement.y/=2;
					}
				}
			}
		}
	}

	public void alignment(){
		movement.x = calcFlockMovement(flock).x;
		movement.y = calcFlockMovement(flock).y;
		
		movement.x/=2;
		movement.y/=2;
	}

	public static double calcDistance(Boid b1, Boid b2){
		return Math.abs(Math.hypot(b1.pos.x - b2.pos.x, b1.pos.y - b2.pos.y));
	}

	public static Point2D.Double calcFlockMovement(ArrayList<Boid> b){
		double x = 0;
		double y = 0;

		for(Boid boid : b){
			x += boid.getMovement().x;
			y += boid.getMovement().y;
		}

		return new Point2D.Double(x/b.size(), y/b.size());

	}
	
	public static Point2D.Double calcFlockPos(ArrayList<Boid> b){
		double x = 0;
		double y = 0;

		for(Boid boid : b){
			x += boid.getPos().x;
			y += boid.getPos().y;
		}

		return new Point2D.Double(x/b.size(), y/b.size());

	}

	public Point2D.Double getPos(){
		return pos;
	}
	
	public Point2D.Double getMovement(){
		return movement;
	}

}
