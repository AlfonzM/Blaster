package blastergame;

public class ScoreText {
	float x, y, yTarget;
	int value;
	boolean visible;
	
	public ScoreText(float xpos, float ypos, int value){
		this.x = xpos;
		this.y = ypos;
		this.value = value;
		
		yTarget = ypos - 40;
		
		visible = true;
	}
	
	public void move(){
		y += (yTarget - y) * 0.1;
		if((int)y <= yTarget || y < 0)
			visible = false;
	}
}