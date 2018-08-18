package bullet.world.lighting;

import java.util.ArrayList;

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
