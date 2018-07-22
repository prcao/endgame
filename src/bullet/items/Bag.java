package bullet.items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.gfx.Assets;
import bullet.mob.player.Inventory;
import bullet.world.Tile;

//hella inefficient

public class Bag extends Entity {

	public Item[] items = new Item[4];

	public static final int SIZE = 35, RANGE = Tile.TILE_SIZE * 2;
	private BufferedImage texture;
	public int lastEquipI;
	private boolean showItems = false, mousePressed = false;

	public Bag(Point2D.Double pos){
		super(pos, 2.5);

		this.texture = Assets.bag;

		width = SIZE;
		height = SIZE;

		Handler.getGame().getGameState().getMap().getLightSourceManager().addLight(lightSource);
	}

	public void tick(double delta){
		if(showItems){
			int numItems = 0;

			for(int i = 0; i < items.length; i++){
				//WHAT is going on

				//moves item in bag to mouse item??
				if(items[i] != null && Handler.getPlayer().getInventory().getMouseItem() == null && showItems){
					if(!mousePressed
							&& new Rectangle((int) pos.x - Handler.getGameCamera().getXOffset() - (2 * Inventory.size) + SIZE/2 + (Inventory.size * i),
									(int) (pos.y - Inventory.size * 1.3 - Handler.getGameCamera().getYOffset()), Inventory.size,
									Inventory.size).contains(Handler.getInput().getMousePos())
							&& Handler.getInput().leftPressed){	

						Handler.getPlayer().getInventory().setMouseItem(items[i]);
						items[i] = null;

						lastEquipI = i;
						Handler.getPlayer().getInventory().lastBag = this;
						Handler.getPlayer().getInventory().fromBag = true;
					}
				}


				if(items[i] != null)
					numItems++;
			}

			mousePressed = Handler.getInput().leftPressed;

			isAlive = numItems > 0;

		}
		showItems = (RANGE > Math.hypot(pos.x - Handler.getPlayer().getPos().x, pos.y - Handler.getPlayer().getPos().y)) && isAlive;

		if(showItems)
			Handler.getPlayer().getInventory().setClosestBag(this);
		else
			Handler.getPlayer().getInventory().setClosestBag(null);

		if(!isAlive)
			Handler.getGame().getGameState().getMap().getLightSourceManager().removeLight(lightSource);


	}

	public void render(Graphics2D g){
		g.setColor(Color.WHITE);
		g.drawImage(texture, (int)pos.x - Handler.getGameCamera().getXOffset(), (int)pos.y - Handler.getGameCamera().getYOffset(), width, height, null);

		if(showItems){
		ToolTip t = null;

		
			for(int i = 0; i < items.length; i++){
				if(items[i] != null){
					if(new Rectangle((int) pos.x - Handler.getGameCamera().getXOffset() - (2 * Inventory.size) + SIZE/2 + (Inventory.size * i),
							(int) (pos.y - Inventory.size * 1.3 - Handler.getGameCamera().getYOffset())
							, Inventory.size, Inventory.size).contains(Handler.getInput().getMousePos())){
						if(items[i] != null)
							t = items[i].getToolTip();
					}
				}

				g.drawImage(Assets.invSpace, (int) pos.x - Handler.getGameCamera().getXOffset() - (2 * Inventory.size) + SIZE/2 + (Inventory.size * i),
						(int) (pos.y - Inventory.size * 1.3 - Handler.getGameCamera().getYOffset()),
						Inventory.size, Inventory.size, null);

				if(items[i] != null && items[i] != Handler.getPlayer().getInventory().getMouseItem()){
					g.drawImage(items[i].getItemTexture(),
							(int) ((i * Inventory.size) + pos.x + 1 - Handler.getGameCamera().getXOffset() - (2 * Inventory.size) + SIZE/2  + Inventory.pixel),
							(int) (pos.y + 1 - Inventory.size * 1.3 - Handler.getGameCamera().getYOffset())  + Inventory.pixel,
							Inventory.size - 2 - 2*Inventory.pixel, Inventory.size - 2 - 2*Inventory.pixel, null);
				}
			}

			if(t != null){
				Handler.getGame().getGameState().getMap().tt = t;
			}
		}
	}	
}
