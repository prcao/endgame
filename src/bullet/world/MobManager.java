package bullet.world;
//package com.cao.bullet.world;
//
//import java.awt.Graphics2D;
//import java.util.ArrayList;
//
//import com.cao.bullet.Handler;
//import com.cao.bullet.mob.Mob;
//import com.cao.bullet.mob.player.Player;
//
//public class MobManager {
//
//	private ArrayList<Mob> mobs;
//	
//	public MobManager(Handler handler){
//		mobs = new ArrayList<Mob>();
//	}
//	
//	public void tick(double delta){
//		for(int i = 0; i < mobs.size(); i++){
//			mobs.get(i).tick(delta);
//			
//			if(!mobs.get(i).isAlive() && !(mobs.get(i) instanceof Player))
//				removeMob(mobs.get(i));
//		}
//	}
//	
//	public void render(Graphics2D g){
//		for(Mob m : mobs){
//			m.render(g);
//		}
//	}
//	
//	public void addMob(Mob m){
//		mobs.add(m);
//	}
//	
//	public void removeMob(Mob m){
//		mobs.remove(m);
//	}
//	
//	public ArrayList<Mob> getMobs(){
//		return mobs;
//	}
//}
