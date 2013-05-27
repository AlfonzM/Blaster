package blastergame;

import org.newdawn.slick.SlickException;

public class ColoredScraps extends Scrap{
	public ColoredScraps(float x, float y, int colorID) throws SlickException{
		super(x, y, colorID);
		switch(colorID){
		case 1: // red
			animation = Sprites.redScraps;
			break;
			
		case 2: // yellow
			animation = Sprites.yellowScraps;
			break;
			
		default:
			break;
		}
		
		sprite = animation.getImage(0);
		cspeed = (int) (Play.player.speed + 4);
		setBounds();
	}
}
