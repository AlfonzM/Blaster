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
	static Animation explode;
	private int explodeDelay;
	
	public Sprites() throws SlickException{
		bSprites = new ArrayList<Image>();
		eSprites = new ArrayList<Image>();
		powerupSprites = new ArrayList<Image>();
		explosions = new Image[10];
		
		explodeDelay = 40;
		
		sprites = new Image("res/spritesheet.png");
		playerSprites = new Image("res/playerSprite.png");
		explosionspritesheet = new Image("res/explosion_sprite.png");
		
		
		bSprites.add(sprites.getSubImage(0, 0, 8, 18));
		bSprites.add(sprites.getSubImage(9, 0, 10, 10));
		
		powerupSprites.add(sprites.getSubImage(51, 0, 22, 22));
		
		eSprites.add(new Image("res/enemy1.png"));
		
		for(int i = 0; i < explosions.length; i++){
			explosions[i] = explosionspritesheet.getSubImage(i*84, 0, 84, 64);
		}
		
		explode = new Animation(explosions, explodeDelay, true);
	}
}