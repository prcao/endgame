package bullet.misc;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.gfx.TextToScreen;
import bullet.mob.Mob;
import bullet.world.Tile;

public class Grave {

	private Mob m;
	private Point2D.Double pos;
	private TextToScreen rip;

	public Grave(Mob m){
		this.m = m;
		
		pos = new Point2D.Double();
		
		pos.x = m.getPos().x;
		pos.y = m.getPos().y - Handler.getHeight();		
		rip = new TextToScreen("Theres no way to restart the game so you'll have to close/reopen rip sry");
	}
	
	public void tick(double delta){		
		if(pos.y < m.getPos().y)
			pos.y += 10 * delta;
		
		rip.tick(delta);
	}
	
	public void render(Graphics2D g){
		rip.render(g);
		g.drawImage(Assets.stoneGrave,(int) (pos.x - Handler.getGameCamera().getXOffset()), (int) (pos.y - Handler.getGameCamera().getYOffset()), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	}
}
