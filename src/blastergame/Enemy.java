package blastergame;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy extends Ship{
	int score;
	int enemyID;
	int spawnTime;
	Image defaultSprite, hitSprite;
	
	Animation animation, hitAnimation;
	
	boolean isHit;
	
	public Enemy(int enemyID, int startX, int startY, int spawnTime) throws SlickException{
		super(startX, startY, 0, 0);
		this.spawnTime = spawnTime;
		this.enemyID = enemyID;
		
		switch(this.enemyID){
		case 0:
			speed = 5;
			hp = 1;
			score = 50;
			animation = new Animation(new Image[] { defaultSprite, defaultSprite }, 1000, true);
			hitAnimation = null;
			break;
			
		case 1:
			speed = 1f;
			hp = 10;
			score = 100;
			hitSprite = Sprites.eSprites.get(2);
			animation = new Animation(new Image[] { defaultSprite, defaultSprite }, 1000, true);
			hitAnimation = new Animation(new Image[] { hitSprite, defaultSprite }, 100, true);
			break;
			
		default:
			speed = 1;
			hp = 1;
			score = 5;
			break;
		}
		
		new Sprites();
		defaultSprite = Sprites.eSprites.get(enemyID);
		sprite = defaultSprite;
		isAlive = true;
	}
	
	public void fire() throws SlickException{
		EnemyBullet b = new EnemyBullet(xpos, ypos, 0, 15);
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
		Player.score += score;
		explode.stopAt(9);
		Play.scoreTexts.add(new ScoreText(xpos, ypos, score));
		
		Random r = new Random();
		
		// coin 50%
		if(r.nextInt(2)==0){
			Play.powerups.add(new Coin(xpos, ypos, 0));
		}
	}
}