package bullet.world.lighting;

import java.awt.Graphics2D;
import java.util.ArrayList;

import bullet.Handler;

public class LightSourceManager {

	private ArrayList<LightSource> lights;
		
	public LightSourceManager(){
		lights = new ArrayList<>();
	}
	
	public void addLight(LightSource... l){
		for(LightSource light : l)
			lights.add(light);
	}
	
	public void removeLight(LightSource l){
		lights.remove(l);
	}
	
	public void purgeLights(){
		lights.clear();
	}
	
	public ArrayList<LightSource> getLights(){
		return lights;
	}
}
