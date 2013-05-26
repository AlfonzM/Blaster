package blastergame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Missile extends Bullet{

	public Missile(float bx, float by) throws SlickException {
		super(bx, by, 2);
		bSpeed = 10;
	}
	
	@Override
	public void hit(){
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(bx - 10, by, sprite.getWidth() + 20, sprite.getHeight());
	}
	
}
