package blastergame;

import org.newdawn.slick.SlickException;

public class Coin extends Scrap{
	int value;
	
	public Coin(float x, float y, int coinID) throws SlickException{
		super(x, y, 0);
		
		switch(coinID){
		case 0: // coin
			value = 50;
			break;
			
		case 1:
			value = 100;
			break;
			
		case 2:
			value = 300;			
			break;
			
		default:
			break;
		}
	
		animation = Sprites.coin1;
		sprite = animation.getImage(0);
		cspeed = (int) (Play.player.speed + 2);
		setBounds();
	}
	
	
}
