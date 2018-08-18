package bullet.world.lighting;

import java.awt.Graphics2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.world.Tile;

//a light is an entity that contains a lightsource, like a placed torch

public class Light extends Entity{

	private BufferedImage texture;
	private double potency;
	
	private final static int SIZE =(int) (Tile.TILE_SIZE/1.5);
	
	public Light(Double pos, double potency, BufferedImage texture) {
		super(pos, potency);
		
		this.potency = potency;
		this.texture = texture;
		width = SIZE;
		height = SIZE;
	}
	
	public Light(Double pos, double potency, BufferedImage texture, int size) {
		this(pos, potency, texture);
		width = size;
		height = size;
	}
	
	public void tick(double delta){
		lightSource.setPotency(potency);
		
		Handler.getGame().getGameState().getMap().getLightSourceManager().removeLight(lightSource);
		Handler.getGame().getGameState().getMap().getLightSourceManager().addLight(lightSource);
		
	}
	
	public void render(Graphics2D g){
		g.drawImage(texture, (int)pos.x - Handler.getGameCamera().getXOffset(), (int)pos.y - Handler.getGameCamera().getYOffset(), width, height, null);
	}

}
