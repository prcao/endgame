package bullet.mob.enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import bullet.Handler;
import bullet.gfx.Animation;
import bullet.mob.Mob;
import bullet.world.Tile;

public abstract class Enemy extends Mob {

	public static Point2D.Double playerPos;
	protected Animation a;
	protected long lastTime, attackCooldown, timer;
	protected int speed;
	protected int angle = 0;
	protected Random r = new Random();
	
	public Enemy(int x, int y, int health, int width, int height, double potency) {
		super(x, y, health, width, height, potency);
		
		type = Type.ENEMY;
	}
	
	public BufferedImage getCurrentFrame(){
		return a.getCurrentAnimationFrame();
	}
	
	@Override
	public void tick(double delta){
		super.tick(delta);
		
		playerPos = Handler.getPlayer().getPos();
		((Rectangle) hitbox).setBounds((int) (pos.x - Handler.getGameCamera().getXOffset()), (int)(pos.y - Handler.getGameCamera().getYOffset()), width, height);
		bounds.setBounds((Rectangle) hitbox);
		
		stats.tick(delta);
		damageTextManager.tick(delta);
	}
	
	@Override
	public void render(Graphics2D g){
	//	stats.render(g);
		super.render(g);
		damageTextManager.render(g);
	}
	
	public void followPlayer(int posX, int posY){
		
		//Point2D.Double target = Handler.getPlayer().getPos();

		//System.out.println(Tile.tileMatrix.size());
		//Tile.tileMatrix.get((int) (pos.x / Tile.TILE_SIZE));
		/*int[] move = AStar.simplePathfind(Tile.tileMatrix[(int) (pos.x / Tile.TILE_SIZE)][(int) (pos.y / Tile.TILE_SIZE)],
										  Tile.tileMatrix[(int) (target.x / Tile.TILE_SIZE)][(int) (target.y / Tile.TILE_SIZE)]);
		
		System.out.println(move[0] + " " + move[1]);
		xMove = 0;
		yMove = 0;
		
		if(move[0] < 0)
			xMove = -speed;
		else if(move[0] > 0)
			xMove = speed;
		
		if(move[1] < 0)
			yMove = -speed;
		else if(move[1] > 0)
			yMove = speed;
		*/
		xMove = 0;
		yMove = 0;
		
		Point2D.Double target = new Point2D.Double(Handler.getPlayer().getX() + posX, Handler.getPlayer().getY() + posY);
		
		if(pos.x < target.x - Tile.TILE_SIZE)
			xMove = speed;
		else if(pos.x > target.x + Tile.TILE_SIZE)
			xMove = -speed;
		else
			xMove = 0;
		
		if(pos.y < target.y - Tile.TILE_SIZE)
			yMove = speed;
		else if(pos.y > target.y + Tile.TILE_SIZE)
			yMove = -speed;
		else if(pos.y == target.y)
			yMove = 0;
			
	}
	
	@Override
	public void hurt(int damage){
		super.hurt(damage);
	}
	
	@Override
	public void die(){
		super.die();
		
	}
	
	public abstract void attack();

}
