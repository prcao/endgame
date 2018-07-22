package bullet.entity.interactable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.world.Map;
import bullet.world.Tile;

public class LevelStairs extends Entity implements Interactable{

	public static final int SIZE = Tile.TILE_SIZE;
	public BufferedImage texture;
	private boolean showDialogBox = false;
	private static final Font FONT = new Font("DialogInput", Font.BOLD, 17);
	private static String TYPE = "Press E to ascend further";
	
	public LevelStairs(Point2D.Double pos, BufferedImage texture, Map m) {
		super(pos, 1);
		this.texture = texture;
		
		m.getLightSourceManager().addLight(lightSource);
	}
	
	public LevelStairs(Handler handler, Point2D.Double pos, BufferedImage texture) {
		this(pos, texture, Handler.getGame().getGameState().getMap());
	}

	@Override
	public void tick(double delta) {
		showDialogBox = calcDistanceToPlayer() < Tile.TILE_SIZE;
		
	}
	
	public double calcDistanceToPlayer(){
		return Math.hypot(Handler.getGame().getPlayer().getX() - pos.x, Handler.getGame().getPlayer().getY() - pos.y);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(texture, (int)pos.x - Handler.getGameCamera().getXOffset(), (int)pos.y - Handler.getGameCamera().getYOffset(), SIZE, SIZE, null);
		
		if(showDialogBox){
			
			
			g.setFont(FONT);
			FontMetrics fm = g.getFontMetrics(FONT);
			
			int width = fm.stringWidth(TYPE);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect((int)pos.x - Handler.getGameCamera().getXOffset() - 5 - width/3, (int)pos.y - Handler.getGameCamera().getYOffset() - Tile.TILE_SIZE, width + 10 , fm.getHeight() * 2, 20, 20);
			
			g.setColor(Color.CYAN);
			g.drawString(TYPE, (int)pos.x - Handler.getGameCamera().getXOffset() - width/3, (int)pos.y - Handler.getGameCamera().getYOffset() + fm.getHeight() - Tile.TILE_SIZE);
		}
	}

	@Override
	public void interact() {
		System.out.println("interacted from levelstairs");
		Handler.getGame().getGameState().setNewMap();
	}

	@Override
	public boolean isInteractable() {
		return calcDistanceToPlayer() < Tile.TILE_SIZE;
	}

}
