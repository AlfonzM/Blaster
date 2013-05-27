package blastergame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Scrap extends Pickable{
	boolean goUp, picked;
	float boundX, boundY, boundWidth, boundHeight;
	int cspeed;

	public Scrap(float x, float y, int id) throws SlickException {
		super(x, y, id);
		yTarget = ypos - 30;
		goUp = true;
	}
	
	@Override
	public void move(){
//		if(goUp){
//			ypos += (yTarget - ypos) * 0.1;
//			if((int)ypos <= yTarget || ypos < 0)
//				goUp = false;
//		}
//		else
		
		
		Player p = Play.player;
		if(picked && p.isAlive){ // magnet effect
			xpos += (p.xpos + p.sprite.getWidth() / 2 > xpos + sprite.getWidth()/2)? cspeed : -cspeed; 
			ypos += (p.ypos + p.sprite.getHeight() / 2 > ypos - sprite.getHeight()/2)? cspeed : -cspeed;
		}
		else // move down
			super.move();
	}
	
	public void setBounds(){
		int boundSize = 6;
		boundX = sprite.getWidth() * (boundSize/2) + sprite.getWidth()/2;
		boundY = sprite.getHeight() * (boundSize/2) + sprite.getHeight()/2;
		
		boundWidth = sprite.getWidth() * boundSize;
		boundHeight = sprite.getHeight() * boundSize;
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(xpos - boundX, ypos - boundY, boundWidth, boundHeight);		
	}
	
	public Rectangle getRealBounds(){
		return new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
	}

}
