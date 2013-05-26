package blastergame;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprites {
	private Image sprites;
	private Image explosionspritesheet;
	static Image playerSprites;
	static ArrayList<Image> bSprites;
	static ArrayList<Image> eSprites;
	static ArrayList<Image> powerupSprites;
	static Image[] explosions;
	static Image[] coin1Sprites;
	static Animation explode, coin1;
	private int explodeDelay;
	
	public Sprites() throws SlickException{
		bSprites = new ArrayList<Image>();
		eSprites = new ArrayList<Image>();
		powerupSprites = new ArrayList<Image>();
		explosions = new Image[10];
		coin1Sprites = new Image[4];
		
		explodeDelay = 40;
		
		// spritesheets
		sprites = new Image("res/spritesheet.png");
		explosionspritesheet = new Image("res/explosion_sprite.png");
		
		// player
		playerSprites = new Image("res/playerSprite.png");
		
		// bullets
		bSprites.add(sprites.getSubImage(0, 0, 8, 18)); // 0 blue
		bSprites.add(sprites.getSubImage(9, 0, 10, 10)); // 1 pink circle
		bSprites.add(sprites.getSubImage(19, 0, 23, 78)); // missile
		
		// power up
		powerupSprites.add(sprites.getSubImage(51, 0, 22, 22));
		
		// enemy ships
		eSprites.add(new Image("res/enemy1.png")); // 0 small enemy
		eSprites.add(new Image("res/enemy2.png").getSubImage(0, 0, 74, 58)); // 1 big enemy
		eSprites.add(new Image("res/enemy2.png").getSubImage(74, 0, 74, 58)); // big enemy spark
		
		// explosions
		for(int i = 0; i < explosions.length; i++){
			explosions[i] = explosionspritesheet.getSubImage(i*84, 0, 84, 64);
		}
		
		explode = new Animation(explosions, explodeDelay, true);
		
		// coins
		Image img = new Image("res/coins.png");
		for(int i = 0, coin1size = 16; i < 4; i++){
			coin1Sprites[i] = img.getSubImage(i * coin1size, 0, coin1size, coin1size);
		}
		
		coin1 = new Animation(coin1Sprites, 100, true); 
	}
}