package bullet.projectile;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.mob.Mob;
import bullet.world.Tile;

public class BounceProjectile extends Projectile{

	private Side side;

	public BounceProjectile(BufferedImage texture, int width, int height, int lifetime, int speed,
			Mob m, int damage) {
		super(texture, width, height, lifetime, speed, m, damage);
	}

	public BounceProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, Point2D.Double des, int damage){
		super(texture, width, height, lifetime, speed, m, des, damage);
	}

	public BounceProjectile(BufferedImage texture, int width, int height, int lifetime, int speed, Mob m, double angle, int damage){
		super(texture, width, height, lifetime, speed, m, angle, damage);
	}

	public void tick(double delta){
		super.tick(delta);

		if(collidingWithTile(hitbox)){
			
			if(side == Side.RIGHT || side == Side.LEFT){//left or right

				velocityX = -velocityX;
				angle = -angle - Math.toRadians(180);
			}
			else{	//up and down
				velocityY = -velocityY;
				angle = -angle;
			}
		}
	}

	@Override
	public boolean collidingWithTile(Shape r){

		for(Tile t : Handler.getGame().getGameState().getMap().getTileManager().getTiles()){
			if(t.isSolid()){
				
				Rectangle to = t.getOffsetTile();
				
				if(testIntersection(r, to)){

					Rectangle2D shapeRect = r.getBounds2D();
					
					//something something advanced math minkowski sum to detect which side the collision is on
					
					float w = (float) (0.5 * (shapeRect.getWidth() + to.getWidth()));
					float h = (float) (0.5 * (shapeRect.getHeight() + to.getHeight()));
					float dx = (float) (shapeRect.getCenterX() - to.getCenterX());
					float dy = (float) (shapeRect.getCenterY() - to.getCenterY());

					/* collision! */
					float wy = w * dy;
					float hx = h * dx;

					if (wy > hx){
						if (wy > -hx){
							side = Side.TOP;
						}else
							side = Side.LEFT;
					} else{
						if (wy > -hx)
							side = Side.RIGHT;
						else
							side = Side.BOTTOM;
					}	
					
					return true;
				}
			}
		}
		return false;
	}

	public enum Side{
		TOP, BOTTOM, LEFT, RIGHT
	}
}
