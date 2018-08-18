package bullet.world;

import java.awt.Point;

import bullet.mob.enemy.boss.Statue;
import bullet.mob.player.Player;

public class BossRoomMap extends Map{

	public BossRoomMap(String path, Point playerSpawn) {
		super(path);
		
		player = new Player(playerSpawn.x, playerSpawn.y);
		entityManager.addEntity(player);
		Statue statue = new Statue(mapX/2 * Tile.TILE_SIZE - (Tile.TILE_SIZE/2), mapY/2 * Tile.TILE_SIZE - (Tile.TILE_SIZE), 200, 2*Tile.TILE_SIZE, 2*Tile.TILE_SIZE);
		entityManager.addEntity(statue);
	}

}
