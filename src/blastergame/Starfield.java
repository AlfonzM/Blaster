package blastergame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Starfield {

	public Image bg;
	public static float bgY = 0, speed;
	
	public Starfield() throws SlickException{
		bg = new Image("res/starfield.png");
		speed = 3;
	}
	
	public void move(){
//		if(speed <= 12)	speed += 0.002;
		
		bgY += speed;
		if(bgY > 640) bgY = 0;
	}
}