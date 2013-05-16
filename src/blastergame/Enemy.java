package blastergame;

import org.newdawn.slick.SlickException;

public class Enemy extends Ship{
	int score;
	int enemyID;
	int spawnTime;
	
	public Enemy(int enemyID, int startX, int startY, int speed, int h, int spawnTime) throws SlickException{
		super(startX, startY, speed, h);
		hp = h;
		this.speed = speed;
		this.spawnTime = spawnTime;
		this.enemyID = enemyID;
		score = 100;
		
		new Sprites();
		sprite = Sprites.eSprites.get(enemyID);
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
	
	public void die(){
		super.die();
		Player.score += score;
		explode.stopAt(9);
	}
}