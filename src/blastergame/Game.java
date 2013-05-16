package blastergame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.AppGameContainer;

public class Game extends StateBasedGame{

	public static final String gamename = "Blaster 2.0";
	public static final int GWIDTH = 600,
							GHEIGHT = 640;
	
	public static final int menu = 0;
	public static final int play = 1;
	public static final int help = 2;
	
	public Game() throws SlickException{
		super("Blaster 2.0");
		new Sounds();
		Sounds.music.loop(1, 0.5f);

		this.addState(new Menu());
		this.addState(new Play());		
		this.addState(new Help());
		this.enterState(menu); // first state to show
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
	}
	
	public static void main(String[] args) throws SlickException{
		System.out.println("main");
		AppGameContainer appgc = new AppGameContainer(new Game()); // make a window that holds Game
		appgc.setDisplayMode(GWIDTH, GHEIGHT, false); // width, height, fullscreen
		appgc.setTargetFrameRate(60);
		appgc.setShowFPS(false);
		appgc.start();
	}
}
