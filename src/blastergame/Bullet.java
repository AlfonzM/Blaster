package blastergame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Bullet {
	public float bx, by;
	int bSpeed = 20;
	Image sprite;
	
	public boolean visible;
	
	public Bullet(float bx, float by, int id) throws SlickException{
		this.bx = bx;
		this.by = by;
		visible = true;
		sprite = Sprites.bSprites.get(id);
	}
	
	public void move(){
		by -= bSpeed;
		
		if(by < 0){
			visible = false;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(bx, by, sprite.getWidth(),sprite.getHeight());
	}
	
	public void hit(){
		visible = false;
	}
}