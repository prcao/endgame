package bullet.world.lighting;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import bullet.Handler;
import bullet.mob.player.Player;

public class LightMap {

	private LinkedList<Dark> darks;

	public LightMap(){
		darks = new LinkedList<>();
	}

	public void tick(double delta){
		for(Dark d : darks){
			d.setActive(d.intersects(Player.render));
			d.tick();
		}
	}

	public void render(Graphics2D g){
		for(Dark d : darks){
			if(d.isActive())
				d.render(g);
		}
	}

	public void addDark(Dark...d){
		for(Dark dark : d){
			darks.add(dark);
		}
	}
}
