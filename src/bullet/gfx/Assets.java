package bullet.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
private static final int w = 8, h = 8;
	
	public static ArrayList<BufferedImage> player_down, player_up, player_right, player_left, player_atk_down, player_atk_up, player_atk_left, player_atk_right,
	statueAnimation, statueIceAnimation, statueFireAnimation, statueDarkAnimation;
	public static BufferedImage statueMinionRight, statueMinionLeft;
	public static BufferedImage player_up_idle, player_down_idle, hitbox, healthBar;
	public static BufferedImage stoneTile, stoneTileSquare, stoneWall, dirtTile, stonePath, stoneWallEdge, stoneWallMiddle, stairs;
	public static BufferedImage redArrow, fireBolt, largeFireBall, iceStar, icicle, iceBall, sword, darknessBolt, darknessLaser, syringe, shieldProj, whiteGoldBolt, goldShieldProj;
	public static BufferedImage stoneGrave, invSpace, weaponInvSpace, abilityInvSpace, ringInvSpace, armorInvSpace;
	public static BufferedImage candle;
	public static BufferedImage basicBow, blackBow, whiteGoldBow;
	public static BufferedImage leatherArmor, chainArmor, whiteGoldArmor;
	public static BufferedImage healthPotion, strengthPotion, hastePotion;
	public static BufferedImage bag, torch;
	public static BufferedImage poisoned, regeneration, strength, haste;
	public static BufferedImage shield, healer, goldShield;
	public static BufferedImage whiteGoldRing;
	public static BufferedImage readMe;
	
	public static void init(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/character.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));
		SpriteSheet projectiles = new SpriteSheet(ImageLoader.loadImage("/textures/projectiles.png"));
		SpriteSheet enemy = new SpriteSheet(ImageLoader.loadImage("/textures/enemy.png"));
		SpriteSheet misc = new SpriteSheet(ImageLoader.loadImage("/textures/misc.png"));
		SpriteSheet lights = new SpriteSheet(ImageLoader.loadImage("/textures/lights.png"));
		SpriteSheet weapons = new SpriteSheet(ImageLoader.loadImage("/textures/weapons.png"));
		SpriteSheet armor = new SpriteSheet(ImageLoader.loadImage("/textures/armor.png"));
		SpriteSheet consumables = new SpriteSheet(ImageLoader.loadImage("/textures/consumable.png"));
		SpriteSheet statusEffects = new SpriteSheet(ImageLoader.loadImage("/textures/status.png"));
		SpriteSheet abilities = new SpriteSheet(ImageLoader.loadImage("/textures/ability.png"));
		
		readMe = misc.crop(0, 24, w*2, 2*h);
		
		//enemies//;
		statueAnimation = new ArrayList<>();
		statueAnimation.add(enemy.crop(0, 0, 16, 16));
		statueAnimation.add(enemy.crop(16, 0, 16, 16));
		
		statueIceAnimation = new ArrayList<>();
		statueIceAnimation.add(enemy.crop(0, 16, 16, 16));
		statueIceAnimation.add(enemy.crop(16, 16, 16, 16));
		
		statueFireAnimation = new ArrayList<>();
		statueFireAnimation.add(enemy.crop(0, 32, 16, 16));
		statueFireAnimation.add(enemy.crop(16, 32, 16, 16));
		
		statueDarkAnimation = new ArrayList<>();
		statueDarkAnimation.add(enemy.crop(0, 48, 16, 16));
		statueDarkAnimation.add(enemy.crop(16, 48, 16, 16));
		
		statueMinionRight = enemy.crop(4*w, 0, w, h);
		statueMinionLeft = enemy.crop(5*w, 0, w, h);
		
		//misc//
		healthBar = misc.crop(0, 0, 4*w, h);
		stoneGrave = misc.crop(0,h,w,h);
		bag = misc.crop(0, 2*h, w, h);
		invSpace = misc.crop(w, h, w * 2, h * 2);
		weaponInvSpace = misc.crop(3 * w, h, w * 2, h * 2);
		armorInvSpace = misc.crop(5 * w, h, w * 2, h * 2);
		abilityInvSpace = misc.crop(7 * w, h, w * 2, h * 2);
		ringInvSpace = misc.crop(9 * w, h, w * 2, h * 2);

		//PLAYER//
		player_down = new ArrayList<BufferedImage>();
		player_down.add(sheet.crop(w,h,w,h));
		player_down.add(sheet.crop(2*w,h,w,h));
		
		player_up = new ArrayList<BufferedImage>();
		player_up.add(sheet.crop(w,2*h,w,h));
		player_up.add(sheet.crop(2*w,2*h,w,h));
		
		player_right = new ArrayList<BufferedImage>();
		player_right.add(sheet.crop(0, 0, w, h));
		player_right.add(sheet.crop(w, 0, w, h));
		
		player_left = new ArrayList<BufferedImage>();
		player_left.add(sheet.crop(2*w, 0, w, h));
		player_left.add(sheet.crop(3*w, 0, w, h));
		
		player_up_idle = sheet.crop(0, 2*w, w, h);
		player_down_idle = sheet.crop(0, w, w, h);
		
		//PLAYER ATTACKING//
		player_atk_down = new ArrayList<BufferedImage>();
		player_atk_down.add(sheet.crop(4*w, h, w, h));
		player_atk_down.add(sheet.crop(5*w, h, w, h));
		
		player_atk_up = new ArrayList<BufferedImage>();
		player_atk_up.add(sheet.crop(4*w, 2*h, w, h));
		player_atk_up.add(sheet.crop(5*w, 2*h, w, h));
		
		player_atk_left = new ArrayList<BufferedImage>();
		player_atk_left.add(sheet.crop(4*w, 3*h, w, h));
		player_atk_left.add(sheet.crop(5*w, 3*h, w, h));
		
		player_atk_right = new ArrayList<BufferedImage>();
		player_atk_right.add(sheet.crop(4*w, 0, w, h));
		player_atk_right.add(sheet.crop(5*w, 0, w, h));
		
		//TILES//
		stoneTile = tiles.crop(0, 0, w, h);
		stoneTileSquare = tiles.crop(w, 0, w, h);
		stoneWall = tiles.crop(2*w, 0, w, h);
		dirtTile = tiles.crop(0, h, w, h);
		stonePath = tiles.crop(w, h, w, h);
		stoneWallEdge = tiles.crop(0, 2*h, w, h);
		stoneWallMiddle = tiles.crop(w, 2*h, w, h);
		stairs = tiles.crop(0, 3 * h, w, h);
		
		//PROJECTILES
		redArrow = projectiles.crop(0, 0, w, h);
		fireBolt = projectiles.crop(0, w, w, h);
		largeFireBall = projectiles.crop(w, 0, 16, 16);
		iceStar = projectiles.crop(3*w, 0, w, h);
		iceBall = projectiles.crop(3*w, h, w, h);
		sword = projectiles.crop(0, 2*h, w, h);
		darknessBolt = projectiles.crop(w, 2*h, w, h);
		darknessLaser = projectiles.crop(2*w, 2*h, w, h);
		icicle = projectiles.crop(3*w, 2*h, w, h);
		syringe = projectiles.crop(0, 3 * h, w, h);
		shieldProj = projectiles.crop(w, 3 * h, w, h);
		goldShieldProj = projectiles.crop(3 * w, 3 * h, w, h);
		whiteGoldBolt = projectiles.crop(2 * w, 3 * h, w, h);
		
		//HITBOX
		hitbox = sheet.crop(3*w, h, w, h);
		
		//lighting//
		candle = lights.crop(0, 0, w, h);
		
		//weapons//
		basicBow = weapons.crop(0, 0, w, h);
		blackBow = weapons.crop(w, 0, w, h);
		whiteGoldBow = weapons.crop(0, h, w, h);
		
		//armor//
		leatherArmor = armor.crop(0,0,w,h);
		chainArmor = armor.crop(w, 0, w, h);
		whiteGoldArmor = armor.crop(2 * w, 0, w, h);
		
		//consumables//
		healthPotion = consumables.crop(0, 0, w, h);
		torch = consumables.crop(0, h, w, h);
		strengthPotion = consumables.crop(w, 0, w, h);
		hastePotion = consumables.crop(2 * w, 0, w, h);
		
		//effects//
		poisoned = statusEffects.crop(0, 0, w, h);
		regeneration = statusEffects.crop(w, 0, w, h);
		strength = statusEffects.crop(2 * w, 0, w, h);
		haste = statusEffects.crop(3 * w, 0, w, h);
		
		//abilities//
		shield = abilities.crop(0, 0, w, h);
		healer = abilities.crop(0, h, w, h);
		goldShield = abilities.crop(w, h, w, h);
		
		//rings//
		whiteGoldRing = armor.crop(0, h, w, h);
		
	}
}
