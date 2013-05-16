package blastergame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Powerup {
	int powerupID;
	Image sprite;
	int xpos, ypos;
	boolean visible;
	
	public Powerup(int x, int y, int id) throws SlickException{
		new Sprites();
		xpos = x;
		ypos = y;
		powerupID = id;
		visible = true;
		sprite = Sprites.powerupSprites.get(id);
	}
	
	public void move(){
		ypos += 3;
	}

	public Rectangle getBounds() {
		return new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
	}
}
