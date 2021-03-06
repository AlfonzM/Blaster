package blastergame;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player extends Ship{
	static int score, scoreToAdd;
	int bulletDelay= 120, missileDelay = 500; //delay between bullets
	int respawnTime, invulnerableTime;
	float bulletCounter, missileCounter;
	int bulletLevel, missileLevel;
	int startX, startY;
	int scraps, scrapsToAdd, redScraps, yellowScraps;
	int redMax, redUse, yellowMax, yellowUse;
	boolean readyToFire, readyToRespawn, invulnerable, lost;
	boolean readyToMissile;
	boolean display;
	
	public Image[] sprites;
	
	static ArrayList<Bullet> bullets;
	
	private Random r;
	
	public Player(int x, int y) throws SlickException{
		// x, y, speed, hp
		super(x, y, 6, 3);
		
		r = new Random();
		
		sprites = new Image[3];
		sprites[0] = Sprites.playerSprites.getSubImage(104, 0, 52, 48); // idle
		sprites[1] = Sprites.playerSprites.getSubImage(0, 0, 52, 48); //right
		sprites[2] = Sprites.playerSprites.getSubImage(52, 0, 52, 48); //left
		
		sprite = sprites[0];
		startX = x;
		startY = y;
		respawnPlayer();
		
		score = 0;
		scoreToAdd = 0;
		
		// scraps
		redScraps = 0;
		yellowScraps = 0;
		redMax = 9;
		yellowMax = 10;
		redUse = 3;
		yellowUse = yellowMax;
		
		respawnTime = 3000;
		invulnerableTime = 3000;
		
		bulletLevel = 0;
		missileCounter = 0;
		bulletCounter = 0;
		damage = 1;
		readyToFire = true;
		readyToRespawn = true;
		readyToMissile = true;
		isAlive = true;
		invulnerable = false;
		display = true;
		scraps = 0;
		scrapsToAdd = 0;
		
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
	
	public void fire() throws SlickException{

		if(bulletLevel != 3 && readyToFire){
			switch(bulletLevel){
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
				// 2nd weapon upgrade
				break;
				
			case 2:
				// 3rd weapon upgrade
				break;
			
			default:
				break;
			}
			Sounds.playerBullet.play(1f, 0.5f);
			
			readyToFire = false;
			bulletCounter = 0;
		}
		else if(bulletCounter >= bulletDelay)
			readyToFire = true;
	}
	
	public void fireMissile() throws SlickException{
		if(readyToMissile /*&& redScraps >= redUse */){
			switch(missileLevel){
			case 0:
				bullets.add(new Missile(xpos + sprite.getWidth()/2 - Sprites.bSprites.get(2).getWidth()/2, ypos));				
				break;
			}
			
			// play missile sound
			readyToMissile = false;
			missileCounter = 0;
			redScraps -= redUse;
		}
		else if(missileCounter >= missileDelay)
			readyToMissile = true;
	}
	
	public void move(int dir){
		
		switch(dir){
		case 0: // up
			ypos -= speed;
			break;
			
		case 1: // down
			if(ypos < Play.bottomBorder)
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
	
	public void pickScrap(Scrap s){	
		switch(s.id){
		case 0: // coin
			Coin c = (Coin) s;
			scrapsToAdd += c.value;
			break;
			
		case 1: // red
			if(redScraps < redMax)
				redScraps++;
			break;
			
		case 2: // green
			if(yellowScraps < yellowMax)
				yellowScraps++;
			break;
			
		default: 
			break;
		}
//		scrapsToAdd += ;
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
	public Rectangle getBounds(){
		int x = 16, y= 16;
		return new Rectangle(xpos + x, ypos + y, sprite.getWidth() - x * 2, sprite.getHeight() - y * 2);
	}
	
	@Override
	public void die(){
		if(hp < 1)
			lost = true;
		Sounds.playerExplode.play();
	}
}
