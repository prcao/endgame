package bullet.gfx;

import java.awt.Graphics2D;
import java.util.ArrayList;

//ticks and renders all of the damage texts of a mob

public class DamageTextManager {
	
	private ArrayList<DamageText> damages = new ArrayList<>();
		
	public void tick(double delta){
		for(int i = 0; i < damages.size(); i++){
			damages.get(i).tick(delta);
			
			if(!damages.get(i).isActive()){
				damages.remove(damages.get(i));
			}
		}
	}
	
	public void render(Graphics2D g){
		for(DamageText d : damages){
			d.render(g);
		}
	}
	
	public void addDamage(DamageText d){
		damages.add(d);
	}
}
