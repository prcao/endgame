package bullet.world.lighting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.world.Tile;

public class Dark extends Rectangle{

	public static final int TILE_RATIO = 1;
	public static final int DARK_SIZE = Tile.TILE_SIZE/TILE_RATIO;
	private int x, y, alpha;
	private BufferedImage texture;
	private boolean isActive = true;
	private Point2D pos;

	public Dark(int x, int y){
		pos = new Point2D.Double(x - Handler.getGameCamera().getXOffset(), y - Handler.getGameCamera().getYOffset());

		alpha = 255;
		
		setBounds((int)pos.getX(), (int)pos.getY(), DARK_SIZE, DARK_SIZE);
	}

	public void tick(){
		
		setBounds((int)pos.getX(), (int)pos.getY(), DARK_SIZE, DARK_SIZE);
		
		if(!isActive)
			return;
		
		int minAlpha = 256;

		alpha = 255;

		for(LightSource l : Handler.getGame().getGameState().getMap().getLightSourceManager().getLights()){
			
			minAlpha = Math.min(((int)(Math.hypot((pos.getX() + DARK_SIZE/2) - l.getPos().x, (pos.getY() + DARK_SIZE/2) - l.getPos().y) * l.getPotency())), minAlpha);
			if(minAlpha > 255)
				minAlpha = 255;
			if(minAlpha < 0)
				minAlpha = 0;
		}

		alpha = minAlpha;
	}

	public void render(Graphics2D g){
		if(!isActive)
			return;
		
		if(alpha > 255)
			alpha = 255;
		else if(alpha < 0)
			alpha = 0;
		
		g.setColor(new Color(0,0,0,alpha));
		g.fillRect((int)pos.getX() - Handler.getGameCamera().getXOffset(), (int) pos.getY() - Handler.getGameCamera().getYOffset(), DARK_SIZE, DARK_SIZE);
		//debugging//
		//g.setColor(Color.WHITE);
		//g.drawString("" + alpha, (int)pos.getX() - Handler.getGameCamera().getXOffset(), (int) pos.getY() - Handler.getGameCamera().getYOffset());
		
		//LightSource l = Handler.getGame().getGameState().getMap().getLightSourceManager().getLights().get(0);
		//g.drawString(""+((int) (Math.hypot(pos.getX() - l.getPos().x, pos.getY() - l.getPos().y) * l.getPotency())), (int)pos.getX() - Handler.getGameCamera().getXOffset(), (int) pos.getY() - Handler.getGameCamera().getYOffset());
	}

	public void setActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isActive(){
		return isActive;
	}
}
