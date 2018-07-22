package bullet.world;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import bullet.Handler;
import bullet.mob.enemy.boss.StatueMinion;
import bullet.mob.player.Player;

public class Level1 extends Level{

	public Level1(String path, Point playerSpawn) {
		super(path);

		player = new Player(playerSpawn.x, playerSpawn.y);
		entityManager.addEntity(player);
	}
	
	public Level1(BufferedImage map, Point playerSpawn, Point stairsPoint) {
		super(map, stairsPoint);

		player = new Player(playerSpawn.x, playerSpawn.y);
		entityManager.addEntity(player);
		
		Random rng = new Random();
		
		for(int i = 0; i < 1; i++){
			System.out.println("ye");
			int x, y;
			do{
				x = rng.nextInt(map.getWidth());
				y = rng.nextInt(map.getHeight());
			}while(map.getRGB(x, y) != Color.decode("#000000").getRGB());
				
			StatueMinion sm = new StatueMinion(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, 100, 48, 48);
			entityManager.addEntity(sm);
		}
	}

}
