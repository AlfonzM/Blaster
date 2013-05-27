package blastergame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Pickable {
	
	/* ID 
	 * 0 = coin
	 * 1 = redScrap
	 * 2 = greenScrap
	 */
	
	int id;
	Image sprite;
	Animation animation;
	float xpos, ypos, yTarget;
	boolean visible;
	
	public Pickable(float x, float y, int id) throws SlickException{
		new Sprites();
		xpos = x;
		ypos = y;
		this.id = id;
		visible = true;
//		sprite = Sprites.powerupSprites.get(id);
	}
	
	public void move(){
		ypos += 4;
	}

	public Rectangle getBounds() {
		return new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
	}
}
