package blastergame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Help extends BasicGameState{
	private Starfield bg;

	public int stateID = 2;
	
	Image help;
	public Help(){
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Starfield();
		help = new Image("res/help.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg.bg, 0, Starfield.bgY);
		g.drawImage(bg.bg, 0, Starfield.bgY-640);
		bg.move();
		g.drawImage(help, 0, 0);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			sbg.enterState(0);
			Sounds.bleep.play();
		}else if (input.isKeyPressed(Input.KEY_DOWN)){
		}
		else if(input.isKeyPressed(Input.KEY_UP)){
		}
	}
	
	public int getID(){
		return stateID;
	}
}
