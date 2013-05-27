package blastergame;

import org.newdawn.slick.Color;

public class ScoreText {
	float x, y, yTarget;
	int value;
	boolean visible;
	public Color color;
	
	public ScoreText(float xpos, float ypos, int value){
		this.x = xpos;
		this.y = ypos;
		this.value = value;
		
		yTarget = ypos - 40;
		if(value == 50)
			color = new Color(252, 255, 0);
		else
			color = Color.white;
		visible = true;
	}
	
	public void move(){
		y += (yTarget - y) * 0.1;
		if((int)y <= yTarget || y < 0)
			visible = false;
	}
}