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
	static Animation explode, coin1, redScraps, yellowScraps;
	private int explodeDelay;
	
	public Sprites() throws SlickException{
		bSprites = new ArrayList<Image>();
		eSprites = new ArrayList<Image>();
		powerupSprites = new ArrayList<Image>();
		
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
		Image[] explosions = new Image[10];
		for(int i = 0; i < explosions.length; i++){
			explosions[i] = explosionspritesheet.getSubImage(i*84, 0, 84, 64);
		}
		
		explode = new Animation(explosions, explodeDelay, true);
		
		// coins and scraps
		Image[] coin1Sprites = new Image[4];
		Image[] redSprites = new Image[4];
		Image[] yellowSprites = new Image[4];
		Image img = new Image("res/coins.png");
		Image img2 = new Image("res/redScraps.png");
		Image img3 = new Image("res/yellowScraps.png");
		for(int i = 0, coin1size = img.getHeight(), scrapSize = img2.getHeight(); i < 4; i++){
			coin1Sprites[i] = img.getSubImage(i * coin1size, 0, coin1size, coin1size);
			redSprites[i] = img2.getSubImage(i * scrapSize, 0, scrapSize, scrapSize);
			yellowSprites[i] = img3.getSubImage(i * scrapSize, 0, scrapSize, scrapSize);
		}
		coin1 = new Animation(coin1Sprites, 100, true);
		redScraps = new Animation(redSprites, 100, true);
		yellowScraps = new Animation(yellowSprites, 100, true);
		
	}
}