package bullet.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import bullet.Handler;
import bullet.mob.player.Player;
import bullet.world.BossRoomMap;
import bullet.world.Level1;
import bullet.world.Map;
import bullet.world.MapGenerator;
import bullet.world.MapGenerator.MapAndStairPoint;
import bullet.world.Tile;

public class GameState extends State{

	private Map map;
	private Map level1, bossRoom, test;
	
	public GameState(){
		
		bossRoom = new BossRoomMap("/world/map.png", new Point(400, 600));
		level1 = new Level1("/world/level1.png", new Point(2* Tile.TILE_SIZE, 2*Tile.TILE_SIZE));
		
		setMap(bossRoom);
		
		//setNewMap();
	}
	
	@Override
	public void tick(double delta) {
		map.tick(delta);
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
		g.setColor(Color.RED);
		g.fillRect((int)Handler.getInput().getMousePos().getX(), (int)Handler.getInput().getMousePos().getY(), 5, 5);
	}
	
	public Map getMap(){
		return map;
	}
	
	public Player getPlayer(){
		return map.getPlayer();
	}
	
	public void setMap(Map m){
		map = m;
	}
	
	public void setMapToBoss(){
		setMap(bossRoom);
	}
	
	public void setNewMap(){
		
		int numTiles = 200;
		MapAndStairPoint testMap = MapGenerator.generateDungeon(100, numTiles, 10, new Point((numTiles/2) * Tile.TILE_SIZE, (numTiles/2) * Tile.TILE_SIZE));
		setMap(new Level1(testMap.map, new Point((numTiles/2) * Tile.TILE_SIZE, (numTiles/2) * Tile.TILE_SIZE), testMap.point));
	}
}
