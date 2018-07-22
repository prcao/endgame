package bullet.projectile;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ProjectileManager {
	
	private LinkedList<Projectile> projectiles;
	
	public ProjectileManager(){		
		projectiles = new LinkedList<>();
	}
	
	public void tick(double delta){
		
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile current = projectiles.get(i);
			
			current.tick(delta);
		
			if(!current.isActive()) {
				projectiles.remove(current);
			}
		}

		//Unsure why this throws a concurrentModificationException
		//TODO: fix
//		ListIterator<Projectile> iter = projectiles.listIterator();
//		
//		while(iter.hasNext()) {
//			
//			Projectile current = iter.next();
//			
//			current.tick(delta);
//			
//			if(!current.isActive()) {
//				//iter.remove();
//			}
//			
//		}
	}
	
	public void render(Graphics2D g){		
		for(Projectile p : projectiles){
			p.render(g);
		}
	}
	
	public void addProjectile(Projectile... p){
		for(Projectile proj : p)
			projectiles.add(proj);
	}
	
	public void addProjectile(List<Projectile> p){
		for(Projectile proj : p)
			projectiles.add(proj);
	}
	
	public void removeProjectile(Projectile p){
		projectiles.remove(p);
	}
	
	public LinkedList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public void clear(){
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.remove(i);
		}
	}
	
}
