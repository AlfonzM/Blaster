package blastergame;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Ship{
	static int score;
	int bulletDelay= 120; //delay between bullets
	int respawnTime, invulnerableTime;
	int bulletType;
	int startX, startY;
	int scraps;
	boolean readyToFire, readyToRespawn, invulnerable;
	boolean display;
	
	public Image[] sprites;
	
	static ArrayList<Bullet> bullets;
	
	private Random r;
	
	public Player(int x, int y) throws SlickException{
		// x, y, speed, hp
		super(x, y, 6, 3);
		
		sprites = new Image[3];
		sprites[0] = Sprites.playerSprites.getSubImage(104, 0, 52, 48); // idle
		sprites[1] = Sprites.playerSprites.getSubImage(0, 0, 52, 48); //right
		sprites[2] = Sprites.playerSprites.getSubImage(52, 0, 52, 48); //left
		
		sprite = sprites[0];
		startX = x;
		startY = y;
		respawnPlayer();
		
		score = 0;
		
		respawnTime = 3000;
		invulnerableTime = 3000;
		
		bulletType = 0;
		damage = 1;
		readyToFire = true;
		readyToRespawn = true;
		isAlive = true;
		invulnerable = false;
		display = true;
		scraps = 0;
		
		// initial bullet size
		bullets = new ArrayList<Bullet>();
	}
	
	public void respawnPlayer(){
		xpos = startX - sprite.getWidth()/2;
		ypos = startY;
		readyToRespawn = true;
		isAlive = true;
		invulnerable = true;
	}
	
	public void fire() throws SlickException{		// Increase bullet size per score
		r = new Random();
		switch(bulletType){
		case 0:
			float minX = -5f;
			float maxX = 5f;
			float mod = (float) (r.nextFloat() * (maxX - minX) + minX);
			Bullet b = new Bullet(xpos + mod , ypos, 0);
			Bullet b2 = new Bullet(xpos + 35 + mod, ypos, 0);
			bullets.add(b);
			bullets.add(b2);	
			break;
			
		case 1:	
			break;
			
		default:
			break;
		}
		Sounds.playerBullet.play(1f, 0.5f);
	}
	
	public void move(int dir){
		
		switch(dir){
		case 0: // up
			ypos -= speed;
			break;
			
		case 1: // down
			ypos += speed;
			break;
			
		case 2: // left
			xpos -= speed;
			break;
			
		case 3: // right
			xpos += speed;
			break;
		}
	}
	
	public void idle(){
		sprite = sprites[0];
	}

	public void pickPowerup(int powerupID) {
		// pick up powerup
		Sounds.bleep.play();
	}
	
	public void pickCoin(int value){
		// play coin sound
		scraps += value;
		Sounds.coin1.play();
	}
	
	public void render(Graphics g){
		// Player
		if(isAlive){
			if(display)
				g.drawImage(sprite, xpos, ypos);
			
			if(invulnerable)
				display = (display == true)?false:true;
		}
	}
	
	@Override
	public void hit(float damage){
		hp -= 1;
		readyToRespawn = false;
		isAlive = false;
		die();
	}
	
	@Override
	public void die(){
		Sounds.playerExplode.play();
	}
}
