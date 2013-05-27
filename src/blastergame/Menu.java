package blastergame;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{
	
	public int stateID = 0;
	private long blink;
	private boolean show = true;
	private Starfield bg;
	public static AngelCodeFont font40, font24, font8;
	int x, y;
	int rectWidth, rectHeight;
	
	Rectangle rect;
	
	int nav;
	
	public Menu(){
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nav = 0;
		bg = new Starfield();
		font40 = new AngelCodeFont("res/fonts/04b03.fnt", new Image("res/fonts/04b03_0.png"));
		font24 = new AngelCodeFont("res/fonts/04b03_24.fnt", new Image("res/fonts/04b03_24_0.png"));
		font8 = new AngelCodeFont("res/fonts/04b03_8.fnt", new Image("res/fonts/04b03_8_0.png"));
		y = 120;
		
		rectWidth = 294;
		rectHeight = 30;
		x = (Game.GWIDTH - rectWidth)/2;
		
		rect = new Rectangle(x, y + 166, rectWidth, rectHeight);

		Sounds.music.loop(1, 0.5f);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg.bg, 0, Starfield.bgY);
		g.drawImage(bg.bg, 0, Starfield.bgY-640);
		bg.move();
		
//		h1.drawString(Play.centerX("BLASTER 2.0", h1), y, "BLASTER 2.0");
		font40.drawString(Play.centerX("BLASTER", font40), 100, "BLASTER");
		
		g.setFont(font24);

		rect.setY((286 + 35*nav));
		g.fill(rect);
		g.setColor((nav==0)?Color.black:Color.white); g.drawString("New Game", x + 10, y + 170);
		g.setColor((nav==1)?Color.black:Color.white); g.drawString("Help", x + 10, y + 205);
		g.setColor((nav==2)?Color.black:Color.white); g.drawString("Quit", x + 10, y + 240);
//				
//		if(nav==0){
//			g.fill(rect);
//			g.setColor(Color.black);
//			g.drawString("New Game", x + 10, y + 170);
//	
//			g.setColor(Color.white);
//			g.drawString("Help", x + 10, y + 205);
//		}
//		else if(nav==1){
//			g.fillRect(x, y + 201, 294, 30);
//			g.setColor(Color.white);
//			g.drawString("New Game", x + 10, y + 170);
//	
//			g.setColor(Color.black);
//			g.drawString("Help", x + 10, y + 205);
//		}
//		else if(nav == 2){
//			g.fillRect(x, y + 236, 294, 30);
//			g.setColor(Color.white);
//			g.drawString("Quit", x + 10, y + 240);
//		}

		g.setColor(Color.white);
		font8.drawString(10, Game.GHEIGHT - 16, "VERSION 0.2");
		
		g.setColor(Color.white);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_DOWN)){
			nav++;
			if(nav > 2)
				nav = 0; 
			
			playSelect();
		}
		else if(input.isKeyPressed(Input.KEY_UP)){
			nav--;
			if(nav < 0)
				nav = 2;
			playSelect();
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)){
			Sounds.bleep.play();
			switch(nav){
			case 0:
				sbg.enterState(1);
				break;
				
			case 1:
				sbg.enterState(2);
				break;
				
			case 2:
				gc.exit();
			}
		}
		
		blink += delta;
		if(blink >= 500){
			blink = 0;
			show = (show==true)? false:true;
		}
	}
	
	public void playSelect(){
		Sounds.select.play(1, 2);
	}
	
	public int getID(){
		return stateID;
	}
}
