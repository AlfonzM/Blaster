package blastergame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Coin extends Pickable{
	boolean goUp, picked;
	int value;
	float boundX, boundY, boundWidth, boundHeight;

	public Coin(float x, float y, int id) throws SlickException {
		super(x, y, id);

		animation = Sprites.coin1;
		sprite = animation.getImage(0);
		yTarget = ypos - 30;
		goUp = true;
		
		int boundSize = 6;
		
		boundX = sprite.getWidth() * (boundSize/2) + sprite.getWidth()/2;
		boundY = sprite.getHeight() * (boundSize/2) + sprite.getHeight()/2;
		
		boundWidth = sprite.getWidth() * boundSize;
		boundHeight = sprite.getHeight() * boundSize;
		
		switch(id){
		case 0:
			value = 50;
			break;
		case 1:
			value = 100;
			break;
		case 2:
			value = 500;
			break;
		}
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
		if(picked && p.isAlive){
			int cspeed = (int) (p.speed + 2);
			xpos += (p.xpos + p.sprite.getWidth() / 2 > xpos + sprite.getWidth()/2)? cspeed : -cspeed; 
			ypos += (p.ypos + p.sprite.getHeight() / 2 > ypos - sprite.getHeight()/2)? cspeed : -cspeed;
		}
		else
			super.move();
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(xpos - boundX, ypos - boundY, boundWidth, boundHeight);		
	}
	
	public Rectangle getRealBounds(){
		return new Rectangle(xpos, ypos, sprite.getWidth(), sprite.getHeight());
	}

}
