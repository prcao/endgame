package bullet.entity.interactable;

import java.util.ArrayList;

import bullet.Handler;

public class InteractableManager {
	
	public ArrayList<Interactable> interactables = new ArrayList<>();
	
	public void interact(double delta){
		for(Interactable i : interactables){
			if(i.isInteractable())
				i.interact();
		}
	}
	
	public void purgeInteractables(){
		interactables.clear();
	}
}
