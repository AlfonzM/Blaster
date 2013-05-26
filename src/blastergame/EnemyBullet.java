package blastergame;

import org.newdawn.slick.SlickException;

public class EnemyBullet extends Bullet{
	
	public EnemyBullet(float bx, float by, int id, int speed) throws SlickException {
		super(bx, by, id);
		bSpeed = speed;
	}

	public void move(float xDir, float yDir){
		bx += xDir * bSpeed;
		by += yDir * bSpeed;
		
		if(by > Game.GHEIGHT){
			visible = false;
		}
	}
}