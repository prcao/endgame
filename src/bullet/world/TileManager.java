package bullet.world;

import java.awt.Graphics2D;
import java.util.ArrayList;

import bullet.mob.player.Player;

public class TileManager {

	private ArrayList<Tile> tiles;
	
	
	public TileManager(){
		tiles = new ArrayList<>();
		
	}

	public void tick(double delta){		
		for(Tile t : tiles){			
			t.setActive(t.intersects(Player.render));
			
			//t.tick(delta);
		}
	}

	public void render(Graphics2D g){
		for(Tile t : tiles){			
			if(t.isActive())
				t.render(g);				
		}
	}

	public void addTile(Tile t){
		tiles.add(t);
	}
	
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
}
