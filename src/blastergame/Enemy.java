package blastergame;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Enemy extends Ship{
	int score;
	int enemyID;
	int spawnTime;
	Image defaultSprite, hitSprite;
	
	boolean isHit;
	
	public Enemy(int enemyID, int startX, int startY, int spawnTime) throws SlickException{
		super(startX, startY, 0, 0);
		this.spawnTime = spawnTime;
		this.enemyID = enemyID;
		
		defaultSprite = Sprites.eSprites.get(enemyID);
		Random r = new Random();
		
		switch(this.enemyID){
		case 0:
			speed = 5;
			hp = 1;
			score = 100;
			break;
			
		case 1:
			speed = r.nextInt(3)+2;
			hp = 4;
			score = 150;
			hitSprite = Sprites.eSprites.get(2);
			break;
			
		default:
			speed = 1;
			hp = 1;
			score = 5;
			break;
		}
		
		new Sprites();
		sprite = defaultSprite;
		isAlive = true;
	}
	
	public void fire() throws SlickException{
		EnemyBullet b = new EnemyBullet(xpos, ypos, 1, 5);
		Play.enemyBullets.add(b);
	}
	
	@Override
	public void move(){
		ypos += speed;
	}
	
	@Override
	public void hit(float dmg) throws SlickException{
		super.hit(dmg);
		isHit = true;
	}
	
	public void die() throws SlickException{
		super.die();
		Player.scoreToAdd += score;
		explode.stopAt(9);
		Play.scoreTexts.add(new ScoreText(xpos, ypos, score));
		
		Random r = new Random();
		
		switch(r.nextInt(3)){
		case 0:
			Play.scraps.add(new Coin(xpos, ypos, 0));
			break;
			
		case 1:
			Play.scraps.add(new ColoredScraps(xpos, ypos, 1));
			break;
			
		case 2:
			Play.scraps.add(new ColoredScraps(xpos, ypos, 2));
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public Rectangle getBounds(){
		Rectangle bounds;
		if(enemyID == 1)
			bounds = new Rectangle(xpos + 15, ypos + 10, sprite.getWidth() - 30, sprite.getHeight() - 20);
		else
			bounds = new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
		
		return bounds;
	}
}