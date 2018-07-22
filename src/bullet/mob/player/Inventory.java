package bullet.mob.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bullet.Handler;
import bullet.entity.Entity;
import bullet.gfx.Assets;
import bullet.items.Bag;
import bullet.items.Item;
import bullet.items.Ring;
import bullet.items.ability.Ability;
import bullet.items.ability.Healer;
import bullet.items.ability.Shield;
import bullet.items.armor.Armor;
import bullet.items.armor.ChainArmor;
import bullet.items.armor.WhiteGoldArmor;
import bullet.items.consumable.Consumable;
import bullet.items.consumable.HastePotion;
import bullet.items.consumable.HealthPotion;
import bullet.items.consumable.Instructions;
import bullet.items.consumable.StrengthPotion;
import bullet.items.rings.WhiteGoldRing;
import bullet.items.weapons.VoidBow;
import bullet.items.weapons.Weapon;

public class Inventory {
	public static Item[][] items;
	public Item[] equipped;
	private Item mouseItem;
	private int lastEquipI, lastEquipJ;
	private Bag closestBag;
	public Bag lastBag;
	public boolean fromBag = false, mousePressed = false;

	public static int size = 48;
	public static int pixel = size / 16;

	public Inventory(Weapon w, Armor a, Player p){

		items = new Item[4][4];
		equipped = new Item[2];

		items[0][0] = w;
		items[1][0] = a;
		items[2][0] = new Shield();
		items[3][0] = new WhiteGoldRing();
		
		items[0][2] = new ChainArmor();
		items[0][3] = new HealthPotion();
		items[1][2] = new HastePotion(500000, p, .3);
		items[1][3] = new StrengthPotion(300000, p, .3);
		items[0][1] = new VoidBow();
		items[1][1] = new WhiteGoldArmor();
		items[2][2] = new Healer();
		items[3][3] = new Instructions();
	}
	
	//please dont roast me i wrote this 2 years ago
	public void tick(){
		boolean bagged = false;

		//starting square
		for(int i = 0; i < items.length; i++){
			for(int j = 0; j < items[i].length; j++){
				if(Handler.getInput().doubleLeftClick){
					if(new Rectangle(i * size, j * size, size, size).contains(Handler.getInput().getMousePos())
							&& mouseItem instanceof Consumable){//items[i][j] instanceof Consumable){
						((Consumable) mouseItem).use();
						mouseItem = null;
					}
				}

				if(mouseItem != null && !Handler.getInput().leftPressed){

					if(new Rectangle(i * size, j * size, size, size).contains(Handler.getInput().getMousePos())){
						bagged = true;
						//target square		

						//item from bag to full space
						if(items[i][j] != null && fromBag){
							lastBag.items[lastBag.lastEquipI] = mouseItem;

							if(!lastBag.isAlive()){
								Bag b = new Bag(lastBag.pos);
								b.items[0] = mouseItem;

								Handler.getGame().getGameState().getMap().getEntityManager().addEntity(b);
							}

							mouseItem = null;

							break;
						}
						else if(items[i][j] != null){
							items[lastEquipI][lastEquipJ] = items[i][j];
						}

						items[i][j] = mouseItem;
						mouseItem = null;

						//malicious input
						//weapon slot
						if(!(items[0][0] instanceof Weapon) && items[0][0] != null){
							if(lastEquipI == 0 && lastEquipJ == 0){
								Item temp = items[i][j];
								items[i][j] = items[0][0];
								items[0][0] = temp;
							}else{
								Item temp = items[0][0];
								items[0][0] = items[lastEquipI][lastEquipJ];
								items[lastEquipI][lastEquipJ] = temp;
							}
						}
						//armor slot
						else if(!(items[1][0] instanceof Armor) && items[1][0] != null){	
							if(lastEquipI == 1 && lastEquipJ == 0){
								Item temp = items[i][j];
								items[i][j] = items[1][0];
								items[1][0] = temp;

							}else{
								Item temp = items[1][0];
								items[1][0] = items[lastEquipI][lastEquipJ];
								items[lastEquipI][lastEquipJ] = temp;
							}
						}	
						
						//ability slot
						else if(!(items[2][0] instanceof Ability) && items[2][0] != null){	
							if(lastEquipI == 2 && lastEquipJ == 0){
								Item temp = items[i][j];
								items[i][j] = items[2][0];
								items[2][0] = temp;

							}else{
								Item temp = items[2][0];
								items[2][0] = items[lastEquipI][lastEquipJ];
								items[lastEquipI][lastEquipJ] = temp;
							}
						}
						
						//ring slot
						else if(!(items[3][0] instanceof Ring) && items[3][0] != null){	
							if(lastEquipI == 3 && lastEquipJ == 0){
								Item temp = items[i][j];
								items[i][j] = items[3][0];
								items[3][0] = temp;

							}else{
								Item temp = items[3][0];
								items[3][0] = items[lastEquipI][lastEquipJ];
								items[lastEquipI][lastEquipJ] = temp;
							}
						}
					}else if(!bagged){
						for(Entity e : Handler.getGame().getGameState().getMap().getEntityManager().entities){
							if(e instanceof Bag){
								for(int k = 0; k < ((Bag) e).items.length; k++){
									if(new Rectangle((int)(e.pos.x - Handler.getGameCamera().getXOffset() + (k * size)),(int)(e.pos.y - size - Handler.getGameCamera().getYOffset()),size, size).contains(Handler.getInput().getMousePos()) && Handler.getInput().leftPressed){

										mouseItem = ((Bag)e).items[k];
										items[lastEquipI][lastEquipJ] = null;

										bagged = true;
									}
								}
							}
						}
					}
				}

				else if(new Rectangle(i * size,j * size,size, size).contains(Handler.getInput().getMousePos()) && Handler.getInput().leftPressed && !mousePressed){	
					if(items[i][j] != null && mouseItem == null){
						mouseItem = items[i][j];
						items[i][j] = null;

						lastEquipI = i;
						lastEquipJ = j;

						fromBag = false;
					}
				}

				if(Handler.getInput().leftPressed)
					bagged = true;
			}		

			if(mouseItem == null)
				bagged = true;
		}

		Handler.getInput().doubleLeftClick = false;
		if(!bagged)
			dropItem();
		
		mousePressed = Handler.getInput().leftPressed;
	}

	public void dropItem(){		
		if(closestBag != null){
			for(int i = 0; i < closestBag.items.length; i++){
				if(closestBag.items[i] == null){
					closestBag.items[i] = mouseItem;
					mouseItem = null;
				}
			}
		}
		else if(mouseItem != null){
			Bag bag = new Bag(Handler.getPlayer().getPos());
			bag.items[0] = mouseItem;
			mouseItem = null;
			Handler.getGame().getGameState().getMap().getEntityManager().addEntity(bag);
		}
	}

	public void render(Graphics2D g){
		int hoverI = -1, hoverJ = -1;

		for(int i = 0; i < items.length; i++){
			for(int j = 0; j < items[i].length; j++){
				if(j > 0)
					g.setColor(Color.white);
				else
					g.setColor(Color.blue);

				if(j == 0 && items[i][j] == null){
					if(i == 0){
						g.drawImage(Assets.weaponInvSpace, i * size, j * size, size, size, null);
					}
					else if(i == 1){
						g.drawImage(Assets.armorInvSpace, i * size, j * size, size, size, null);
					}
					else if(i == 2){
						g.drawImage(Assets.abilityInvSpace, i * size, j * size, size, size, null);
					}
					else if(i == 3){
						g.drawImage(Assets.ringInvSpace, i * size, j * size, size, size, null);
					}
					
					continue;
				}
				else
					g.drawImage(Assets.invSpace, i * size, j * size, size, size, null);
				
				//g.drawRect(i * size, j * size, size, size);

				if(items[i][j] != null && items[i][j] != mouseItem){
					g.drawImage(items[i][j].getItemTexture(), i * size + pixel, j * size + pixel, size - pixel * 2, size - pixel * 2, null);
				}

				if(new Rectangle(i * size, j * size, size, size).contains(Handler.getInput().getMousePos())){
					hoverI = i;
					hoverJ = j;
				}
			}

			if(mouseItem != null)
				g.drawImage(mouseItem.getItemTexture(), (int)Handler.getInput().getMousePos().x - size/2, (int)Handler.getInput().getMousePos().y - size/2, size, size, null);
		}

		if(hoverI != -1 && hoverJ != -1){
			if(items[hoverI][hoverJ] != null){
				items[hoverI][hoverJ].getToolTip().render(g);
			}
		}
	}

	public Item getMouseItem(){
		return mouseItem;
	}

	public void setMouseItem(Item i){
		mouseItem = i;
	}

	public Bag getClosestBag(){
		return closestBag;
	}

	public void setClosestBag(Bag bag){
		closestBag = bag;
	}
}
