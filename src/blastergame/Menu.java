package blastergame;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{
	
	public int stateID = 0;
	private long blink;
	private boolean show = true;
	private Starfield bg;
	TrueTypeFont h1, h2, h3;
	int x, y;
	int rectWidth, rectHeight;
	
	Rectangle rect;
	
	int nav;
	
	public Menu(){
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nav = 0;
		bg = new Starfield();
		h1 = new TrueTypeFont(new java.awt.Font("04b03", Font.PLAIN, 48), false);
		h2 = new TrueTypeFont(new java.awt.Font("04b03", Font.PLAIN, 24), false);
		h3 = new TrueTypeFont(new java.awt.Font("Munro Small", Font.PLAIN, 10), false);
		x = 150;
		y = 120;
		
		rectWidth = 294;
		rectHeight = 30;
		
		rect = new Rectangle((Game.GWIDTH - rectWidth)/2, y + 166, rectWidth, rectHeight);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg.bg, 0, Starfield.bgY);
		g.drawImage(bg.bg, 0, Starfield.bgY-640);
		bg.move();
		
		h1.drawString(Play.centerX("BLASTER 2.0", h1), y, "BLASTER 2.0");
		
		g.setFont(h2);

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
		g.setFont(h3);
		g.drawString("Version 0.1", 10, Game.GHEIGHT - 20);
		
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
