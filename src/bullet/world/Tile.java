package bullet.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bullet.Handler;

public class Tile extends Rectangle{

	public static final int TILE_SIZE = 64;
	public static Tile[][] tileMatrix = new Tile[256][256];
	
	private int tileX, tileY;
	private BufferedImage texture;
	private boolean isSolid, isActive = true;
	private Point2D pos;
	
	public Tile(int tileX, int tileY, BufferedImage texture){
		this.tileX = tileX;
		this.tileY = tileY;
		this.texture = texture;
		pos = new Point2D.Double(tileX * TILE_SIZE, tileY * TILE_SIZE);
		
		setBounds((int)pos.getX(), (int)pos.getY(), TILE_SIZE, TILE_SIZE);
		tileMatrix[tileX][tileY] = this;
	}
	
	public Tile(int tileX, int tileY, BufferedImage texture, boolean isSolid){
		this(tileX, tileY, texture);
		this.isSolid = isSolid;
	}
	
	public void tick(double delta){ 		
	//	setBounds((int)pos.getX() - Handler.getGameCamera().getXOffset(), (int)pos.getY() - Handler.getGameCamera().getYOffset(), TILE_SIZE, TILE_SIZE);
	}
	
	public void render(Graphics2D g){
		
		g.drawImage(texture,(int)pos.getX() - Handler.getGameCamera().getXOffset(), (int)pos.getY() - Handler.getGameCamera().getYOffset(), TILE_SIZE, TILE_SIZE, null);
	}
	
	public void drawOutline(Graphics2D g){
		g.setColor(Color.cyan);
		
		g.draw(this);
	}
	
	public void setSolid(boolean isSolid){
		this.isSolid = isSolid;
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public void setActive(boolean isActive){
		this.isActive = isActive;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	
	public Rectangle getOffsetTile(){
		return new Rectangle((int)(pos.getX() - Handler.getGameCamera().getXOffset()), (int) (pos.getY() - Handler.getGameCamera().getYOffset()), width, height);
	}
	
	public boolean equals(Tile t){
		return tileX == t.tileX && tileY == t.tileY;
	}
}
