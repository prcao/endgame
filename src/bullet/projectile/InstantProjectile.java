package bullet.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.mob.Mob;
import bullet.mob.enemy.Enemy;
import bullet.world.Tile;

//doesnt work
//isnt used anyways

public class InstantProjectile extends Projectile{

	private double range;
	private Line2D.Double path;

	public InstantProjectile(Mob m, int lifetime, double range, Point2D.Double des, int damage){
		super(null, 0, 0, lifetime, 0, m, des, damage);
		this.range = range;

		path = new Line2D.Double(m.getShiftedMidPos(), des);
	}

	public void tick(double delta){


		cLifetime+=System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if(cLifetime >= lifetime)
			setActive(false);	
	}

	public void render(Graphics2D g){
		g.setColor(Color.yellow);
		g.draw(path);
	}

	public boolean collidingWithTile(Shape r){
		boolean hasIntersected = false;
		double distance = -1;

		for(Tile t : Handler.getGame().getGameState().getMap().getTileManager().getTiles()){
			if(t.isSolid()){
				if(r.intersects(t)){

					hasIntersected = true;	

					if(distance == -1){
						distance = Math.hypot(t.getX() - m.getPos().x, t.getY() - m.getPos().y);
						path.setLine(m.getPos(), new Point2D.Double(t.getCenterX(), t.getCenterY()));
					}
					else if(distance > Math.hypot(t.getX() - m.getPos().x, t.getY() - m.getPos().y)){
						distance = Math.hypot(t.getX() - m.getPos().x, t.getY() - m.getPos().y);
						path.setLine(m.getPos(), new Point2D.Double(t.getCenterX(), t.getCenterY()));
					}
				}
			}
		}
		return hasIntersected;
	}

	public void collidingWithMob(Mob originMob){
		Mob targetMob = null;
		double distance = -1;

		for(Entity m : Handler.getGame().getGameState().getMap().getEntityManager().entities){
			if(m instanceof Mob){
				if((originMob instanceof Enemy) && (m instanceof Enemy))
					continue;
				else if(originMob == m)
					continue;

				if(path.intersects(((Mob)m).hitbox.getBounds())){
					if(distance == -1){
						distance = Math.hypot(originMob.getPos().x - m.getPos().x, originMob.getPos().y - m.getPos().y);
						targetMob = (Mob) m;
					}
					else if(distance > Math.hypot(originMob.getPos().x - m.getPos().x, originMob.getPos().y - m.getPos().y)){
						distance = Math.hypot(originMob.getPos().x - m.getPos().x, originMob.getPos().y - m.getPos().y);
						targetMob = (Mob) m;
					}
				}
			}
		}

		if(targetMob != null)
			targetMob.hurt(damage);
	}

}
