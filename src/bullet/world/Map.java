package bullet.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bullet.Handler;
import bullet.entity.EntityManager;
import bullet.entity.interactable.InteractableManager;
import bullet.gfx.Assets;
import bullet.gfx.ImageLoader;
import bullet.items.ToolTip;
import bullet.mob.player.Player;
import bullet.projectile.ProjectileManager;
import bullet.world.lighting.Dark;
import bullet.world.lighting.Light;
import bullet.world.lighting.LightMap;
import bullet.world.lighting.LightSourceManager;

public class Map {

	protected TileManager tileManager;
	protected LightSourceManager lightManager;
	protected LightMap lightMap;
	protected EntityManager entityManager;
	protected ProjectileManager projectileManager;
	protected InteractableManager interactableManager = new InteractableManager();
	public Player player;

	public ArrayList<Color> topLayer = new ArrayList<>();

	public ToolTip tt;

	protected int mapX;
	protected int mapY;

	protected Light l;

	public Map(BufferedImage map){
		mapX = map.getWidth();
		mapY = map.getHeight();

		tileManager = new TileManager();
		lightManager = new LightSourceManager();
		lightMap = new LightMap();
		entityManager = new EntityManager();
		projectileManager = new ProjectileManager();

		l = new Light(new Point2D.Double(100, 100), .7, Assets.candle);
		//lightManager.addLight(l);

		for(int x = 0; x<mapX; x++){
			for(int y = 0; y < mapY; y++){

				int color = map.getRGB(x, y);

				switch(color & 0xFFFFFF){
				case 0x000000:
					tileManager.addTile(new Tile(x, y, Assets.stoneTile));
					break;
				case 0x404040:
					tileManager.addTile(new Tile(x, y, Assets.stoneWall, true));
					break;
				case 0x808080:
					tileManager.addTile(new Tile(x, y, Assets.stoneTileSquare));
					break;
				case 0x6F4317:
					tileManager.addTile(new Tile(x, y, Assets.dirtTile));
					break;
				case 0x606060:
					tileManager.addTile(new Tile(x, y, Assets.stonePath));
					break;
				case 0xADADAD:
					tileManager.addTile(new Tile(x,y,Assets.stoneWallEdge, true));
					break;
				case 0xF2F2F2:
					tileManager.addTile(new Tile(x, y, Assets.stoneWallMiddle, true));
					break;
				case 0x000001:
					tileManager.addTile(new Tile(x, y, Assets.stairs));
					break;
				default:
					tileManager.addTile(new Tile(x, y, Assets.stoneTile));
				}

				for(int i = 0; i < Dark.TILE_RATIO; i++){
					for(int j = 0; j < Dark.TILE_RATIO; j++){
						lightMap.addDark(new Dark(x * Tile.TILE_SIZE + (Dark.DARK_SIZE * i), y * Tile.TILE_SIZE + (Dark.DARK_SIZE * j)));
					}
				}
			}
		}
	}

	public Map(String path){
		this(ImageLoader.loadImage(path));
	}

	public void tick(double delta) {
		tileManager.tick(delta);
		lightMap.tick(delta);
		entityManager.tick(delta);
		player.getInventory().tick();
		projectileManager.tick(delta);
	}

	public void render(Graphics2D g) {
		tileManager.render(g);
		
		lightMap.render(g);
		projectileManager.render(g);

		entityManager.render(g);
		
		if(tt != null){
			tt.render(g);
			tt = null;
		}

		player.getInventory().render(g);

		for(int i = 0; i < topLayer.size(); i++){
			g.setColor(topLayer.get(i));
			g.fillRect(0, 0, Handler.getWidth(), Handler.getHeight());
			topLayer.set(i, new Color(topLayer.get(i).getRed(), topLayer.get(i).getGreen(), topLayer.get(i).getBlue(), topLayer.get(i).getAlpha() - 1));
			if(topLayer.get(i).getAlpha() <= 0)
				topLayer.remove(i);
		}
	}

	public TileManager getTileManager(){
		return tileManager;
	}

	//	public MobManager getMobManager(){
	//		return mobManager;
	//	}

	public int getWidth(){
		return mapX * Tile.TILE_SIZE;
	}

	public int getHeight(){
		return mapY * Tile.TILE_SIZE;
	}

	public Player getPlayer(){
		return player;
	}

	public LightSourceManager getLightSourceManager(){
		return lightManager;
	}

	public LightMap getLightMap(){
		return lightMap;
	}

	public EntityManager getEntityManager(){
		return entityManager;
	}

	public ProjectileManager getProjectileManager(){
		return projectileManager;
	}
	
	public InteractableManager getInteractableManager(){
		return interactableManager;
	}
}
