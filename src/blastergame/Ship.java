package blastergame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Ship {
	float xpos, ypos;
	float speed;
	int hp;
	boolean isAlive;
	boolean visible;
	Image sprite;
	float damage;
	
	Animation explode;
	
	public Ship(int startX, int startY, float speed, int hp){
		xpos = startX;
		ypos = startY;
		this.hp = hp;
		this.speed = speed;
		isAlive = true;
		visible = true;
		explode = Sprites.explode;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
	}
	
	public void hit(float damage){
		hp -= damage;
		if (hp <= 0){
			die();
		}
	}
	
	public void move(){
		
	}
	
	public void die(){
		isAlive = false;
		Sounds.explode.play();
	}
}
