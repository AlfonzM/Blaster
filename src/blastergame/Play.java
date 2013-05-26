package blastergame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
//import java.util.Random;

public class Play extends BasicGameState{

	// Game variables
	Random r;
	private int stateID = 1;
	private long gameTime, playerCounter;
//	private Random random = new Random();
	private Starfield bg;
	public static int level; // current level
	public static AngelCodeFont normal;
	private int playState; // 0 = display wave number, 1 = game, 2 = gap between 0 and 1
	private String scoreText;
	private int scoreToAdd;
	float cellWidth;
	
	public static final int bottomBorder = Game.GHEIGHT - 90; // above health
	
	// Player variables
	static Player player;
	static ArrayList<Pickable> powerups;
	
	// Enemies variables
	static ArrayList<Enemy> enemies;
	static ArrayList<Bullet> enemyBullets;
	static ArrayList<Enemy> enemyQueue;
	static ArrayList<ScoreText> scoreTexts;
	
	public Play() throws SlickException{
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		new Sprites();

		r = new Random();
		gc.setTargetFrameRate(60);
		
		player = new Player(gc.getWidth()/2, bottomBorder);
		bg = new Starfield();
		
		level = 1;
		
		normal = new AngelCodeFont("res/fonts/04b03_16.fnt", new Image("res/fonts/04b03_16_0.png"));
		initLevel(level);
		scoreTexts = new ArrayList<ScoreText>();
		powerups = new ArrayList<Pickable>();
		enemyBullets = new ArrayList<Bullet>();
		player.lost = false;
	}
	
	public void initLevel(int level) throws SlickException{
		
		gameTime = 0;
		playState = 0;
		
		enemies = new ArrayList<Enemy>();
		enemyQueue = new ArrayList<Enemy>(){
			private static final long serialVersionUID = 1L;
		{
//			 SPAWN RANDOM SHIT!
//			for(int i = 0; i < 1000; i++)
//				add( new Enemy(0, r.nextInt(400)+100, -10, 5, 1, r.nextInt(300*1000)));
		}};
		
		switch(level){
		case 1:
//			ID, x, y, speed, hp, spawnTime
			enemyQueue.add(new Enemy(0, 100, -10, 1000));
			enemyQueue.add(new Enemy(0, 500, -10, 1000));
			enemyQueue.add(new Enemy(0, 100, -10, 1200));
			enemyQueue.add(new Enemy(0, 100, -10, 1400));
			
			enemyQueue.add(new Enemy(0, 130, -10, 2000));
			enemyQueue.add(new Enemy(0, 180, -10, 2200));
			enemyQueue.add(new Enemy(0, 210, -10, 2400));
			enemyQueue.add(new Enemy(0, 240, -10, 2600));
			break;
			
		case 2:
			enemyQueue.add(new Enemy(1, r.nextInt(Game.GWIDTH-20-50)+10, -10, 1000));
			enemyQueue.add(new Enemy(1, r.nextInt(Game.GWIDTH-20-50)+10, -10, 1000));
			enemyQueue.add(new Enemy(1, r.nextInt(Game.GWIDTH-20-50)+10, -10, 1000));
			break;
			
		default:
			for(int i = 0; i < 4*level; i++)
				enemyQueue.add( new Enemy(r.nextInt(2), r.nextInt(Game.GWIDTH-20-50)+10, -10, r.nextInt(level*900)));
			break;
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(normal);
		
		// Starfield
		g.drawImage(bg.bg, 0, Starfield.bgY);
		g.drawImage(bg.bg, 0, Starfield.bgY-640);
		bg.move();
		
		// DISPLAY PLAYER COORDINATES
//		g.drawString("(" + player.xpos + ", " + player.ypos + ")", 100, 100);
		
		if(playState == 0){
			Menu.font24.drawString(centerX("WAVE" + level, Menu.font24), 235, "WAVE " + level);
		}
		
		player.render(g);
//		g.fill(player.getBounds());
		
		// Pickables
		for (Iterator<Pickable> iterator = powerups.iterator(); iterator.hasNext(); ) {
			  Pickable p = iterator.next();
			  
			  if(p.visible){
				  if(p.animation != null){
					  p.animation.draw(p.xpos, p.ypos);
					  
				  }
				  else
					  p.sprite.draw(p.xpos, p.ypos);
				  p.move();
			  }
			  else
				  iterator.remove();
		}
		
		// Enemies
		for(int j = 0; j < enemies.size(); j++){
			Enemy e = (Enemy) enemies.get(j);
//			
			if(e.isAlive){
				if(e.isHit && e.hp > 1){
					e.hitSprite.draw(e.xpos, e.ypos);
					e.isHit = false;
				}
				else{
					e.defaultSprite.draw(e.xpos, e.ypos);
				}
				 
				e.move();
			}
			else if(e.visible){
				e.explode.draw(e.xpos, e.ypos);
			}
		}
		
		// Bullets
		ArrayList<Bullet> bullets = Player.bullets;
		for(int i = 0; i < bullets.size(); i++){
			Bullet temp = (Bullet) bullets.get(i);
			g.setColor(Color.white);
			temp.sprite.draw(temp.bx, temp.by);
		}
		
		// Enemy bullets
		for(int i = 0; i < enemyBullets.size(); i++){
			EnemyBullet temp = (EnemyBullet) enemyBullets.get(i);
			temp.sprite.draw(temp.bx, temp.by);
		}
		
		// Game Over
		if(player.lost) {
			g.drawString("GAME OVER", centerX("GAME OVER", normal), 250);

			scoreText = "SCORE " + Player.score;
			g.drawString(scoreText, centerX(scoreText, normal), 270);
			
			g.drawString("Press F5 to play again.", centerX("Press F5 to play again.", normal), 400);
		}
		
		// Display score texts
		for (Iterator<ScoreText> iterator = scoreTexts.iterator(); iterator.hasNext(); ) {
			  ScoreText st = iterator.next();
			  
			  if(st.visible){
				  if(st.value == 50){
					  g.setColor(new Color(243, 219, 83));
				  }
				  g.drawString(""+st.value, st.x, st.y);
				  g.setColor(Color.white);
				  st.move();
			  }
			  else
				  iterator.remove();
		}
		
		// DISPLAY HUD
		g.drawString("SCORE " + Player.score, 10, 10);
		g.drawString("SCRAPS " + player.scraps, 10, 30);
		g.drawString("WAVE " + level, gc.getWidth() - 90, 10);
		
		if(!player.lost){
			g.drawString("HULL ", 10, gc.getHeight()-25);
			
			// health bar
			for(int i = 0; i < player.hp; i++){
				g.setColor(new Color(80, 255, 21));
				g.fillRect(60 + i*32, gc.getHeight()-22, 30, 10);
			}
			
			// COLOR POWERUP BARS
			g.setLineWidth(2);
			int x = 200, y = Game.GHEIGHT - 26;
			int w = 180, h = 16;

			// red
			g.setColor(new Color(255, 21, 21));
			g.drawLine(x, y, x + w, y);
			g.drawLine(x, y, x, y + h);
			g.drawLine(x, y + h, x + w, y + h);
			g.drawLine(x + w, y, x + w, y + h);
			cellWidth = (float) (w - 6 - (player.redMax - 1)) / player.redMax;
			for(int i = 0 ; i < player.redScraps ; i++){
				g.fillRect((x+3) + (cellWidth * i) + i, y + 4, cellWidth, h - 6);
			}
			
			// green
			x = x + w + 20;
			g.setColor(new Color(80, 255, 21));
			g.drawLine(x, y, x + w, y);
			g.drawLine(x, y, x, y + h);
			g.drawLine(x, y + h, x + w, y + h);
			g.drawLine(x + w, y, x + w, y + h);
			cellWidth = (float) (w - 6 - (player.greenMax - 1)) / player.greenMax;
			for(int i = 0 ; i < player.greenScraps ; i++){
				g.fillRect((x+3) + (cellWidth * i) + i, y + 4, cellWidth, h - 6);
			}
		}
		
		g.setColor(Color.white);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(!player.lost)
		{
			
			if(player.isAlive){
				
				if(player.invulnerable){
					playerCounter += delta;
					
					if(playerCounter >= player.invulnerableTime){
						player.invulnerable = false;
						playerCounter = 0;
						player.display = true;
					}
				}
				
				// TESTING ONLY (INC/DEC SPEED)
				if(input.isKeyPressed(Input.KEY_2)){
					player.bulletDelay += 20;
				}
				else if(input.isKeyPressed(Input.KEY_1))
					player.bulletDelay -= 20;
				
				// MOVEMENT
				// if moving
				if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) ||
				   input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT)){
					if(input.isKeyDown(Input.KEY_UP)){ player.move(0); }
					else if(input.isKeyDown(Input.KEY_DOWN)){ player.move(1); }

					if (input.isKeyDown(Input.KEY_LEFT)) {
						player.move(2);
						player.sprite = player.sprites[2];
					}
					else if (input.isKeyDown(Input.KEY_RIGHT)) {
						player.move(3);
						player.sprite = player.sprites[1];
					}		
				}
				// if not moving
				else{
					player.idle();
				}
				
				if(input.isKeyDown(Input.KEY_ESCAPE)) { sbg.pauseUpdate(); }
	
				// Shoot
				player.bulletCounter += delta;
				if (input.isKeyDown(Input.KEY_SPACE)){
					player.fire();
				}
				
				player.missileCounter += delta;
				if(input.isKeyDown(Input.KEY_Q)){
					player.fireMissile();
				}
			} // end of if(player alive)
			else
			{
				// Respawning player...
				if(!player.readyToRespawn){
					playerCounter += delta;
					if(playerCounter >= player.respawnTime){
						player.respawnPlayer();
						playerCounter = 0;
					}
				}
			}
			
			// DETERMINE PLAYSTATES
			gameTime += delta;
			if(playState == 0){
				if(gameTime >= 1000){
					playState = 1;
					gameTime = 0;
				}
			}
			else if(playState == 1){
				if(!enemyQueue.isEmpty() || !enemies.isEmpty()){
					for(Iterator<Enemy> e = enemyQueue.iterator(); e.hasNext(); ){
						Enemy i = e.next();
						if(gameTime >= i.spawnTime){
							enemies.add(i);
							e.remove();
						}
					}				
				}
				else{
					playState = 2;
					gameTime = 0;
				}
			}
			else{
				if(gameTime >= 10){
					initLevel(++level);
				}
			}
		}// end of if(!player.lost)

		
		
		// Spawn enemies
//		time += delta;
//		spawnDelay += delta;
//		if(time >= 1000){
//			if(spawnDelay >= 500){
//				enemies.add(new Enemy(tempX, -10, 5, 0));
//				tempX += 50;
//				spawnDelay = 0;
//			}
//		}
		
		if(input.isKeyDown(Input.KEY_F5)){ init(gc, sbg); }
		
		checkCollisions();
	}
	
	public void checkCollisions() throws SlickException{
		Rectangle eBounds;
		Enemy eTemp;
		Rectangle p = player.getBounds();
		
		// Bullets collision		
		// loop every player bullet
		for(int i = 0; i < Player.bullets.size(); i++){
			Bullet bTemp = (Bullet) Player.bullets.get(i);
			Rectangle bulletBounds = bTemp.getBounds();
			
			// loop every live enemy
			for(int j = 0; j < enemies.size(); j++){
				eTemp = (Enemy) enemies.get(j);
				eBounds = eTemp.getBounds();
				
				// check if bullet hits enemy
				if(eBounds.intersects(bulletBounds) && eTemp.isAlive){
					enemies.get(j).hit(player.damage);
					Player.bullets.get(i).hit();
				}
			}			
			
			if(bTemp.visible)
				bTemp.move();
			else
				Player.bullets.remove(i);
		}
		
		// loop every enemy bullet
		for(int i = 0 ; i < enemyBullets.size(); i++){
			EnemyBullet bTemp = (EnemyBullet) enemyBullets.get(i);
			if(bTemp.getBounds().intersects(player.getBounds())){
				player.hit(1);
				enemyBullets.get(i).hit();
			}
			else if(bTemp.by > Game.GHEIGHT){
				bTemp.visible = false;
			}
			
			if(!bTemp.visible)
				enemyBullets.remove(i);
			else
				bTemp.move(0, 1);
		}
		
		// Ship collisions
		
		if(player.isAlive){
			// Edges Collision
			if(player.xpos >= Game.GWIDTH-10 - player.sprite.getWidth()) player.xpos = Game.GWIDTH-10 - player.sprite.getWidth(); //right
			if(player.xpos <= 10) player.xpos = 10; //left
			if(player.ypos <= 10) player.ypos = 10; //up
			if(player.ypos >= Game.GHEIGHT-30 - player.sprite.getHeight()) player.ypos = Game.GHEIGHT-30 - player.sprite.getHeight(); //down
			
			// loop every enemy
			for(int i = 0; i < enemies.size(); i++){
				eTemp = (Enemy) enemies.get(i);
				eBounds = eTemp.getBounds();
				
				if(eTemp.enemyID == 1 && enemyBullets.size() < 1)
					eTemp.fire();
				
				// check if enemy hits player
				if(p.intersects(eBounds) && !player.invulnerable && enemies.get(i).isAlive){
					player.hit(1);
					enemies.get(i).die();
				}
				// check if after explosion or beyond screen
				if(eTemp.explode.isStopped() || eTemp.ypos > bottomBorder)
					enemies.remove(i);
			}
			
			// loop pickalbes
			for (Iterator<Pickable> iterator = powerups.iterator(); iterator.hasNext(); ) {
				  Pickable pick = iterator.next();
				  if(pick.getBounds().intersects(p)){
					  if(pick.id == 0) { //coin
						  Coin c = (Coin) pick;
						  c.picked = true;
						  
						  if(c.getRealBounds().intersects(p)){
							  iterator.remove();
							  player.pickCoin(c.value);
							  scoreTexts.add(new ScoreText(c.xpos, c.ypos, c.value));
						  }
						  
					  }// end coin
					  else{
						  player.pickPowerup(pick.id);
						  iterator.remove();					  
					  }
				  }
				  else if(pick.ypos - pick.getBounds().getHeight() > bottomBorder)
					  iterator.remove();
			}
		}
		
	}
	
	public static int centerX(String string, TrueTypeFont font){
		return (Game.GWIDTH - font.getWidth(string))/2;
	}
	
	public static int centerX(String string, AngelCodeFont font){
		return (Game.GWIDTH - font.getWidth(string))/2;
	}
	
	public int getID(){
		return stateID;
	}
}
