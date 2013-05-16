package blastergame;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private long counter = 0, gameTime, playerCounter;
//	private Random random = new Random();
	private Starfield bg;
	public static int level; // current level
	public static TrueTypeFont normal, big;
	private int playState; // 0 = display wave number, 1 = game, 2 = gap between 0 and 1
	private String scoreText;
	
	public static final int bottomBorder = Game.GHEIGHT - 90; // above health
	
	// Player variables
	Player player;
	boolean lost;
	static ArrayList<Powerup> powerups;
	
	// Enemies variables
	static ArrayList<Enemy> enemies;
	static ArrayList<Bullet> enemyBullets;
	static ArrayList<Enemy> enemyQueue;
	
	public Play() throws SlickException{
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		new Sounds();
		new Sprites();

		r = new Random();
		gc.setTargetFrameRate(60);
		
		player = new Player(gc.getWidth()/2, bottomBorder);
		bg = new Starfield();
		
		level = 1;
		
		normal = new TrueTypeFont(new java.awt.Font("04b03", Font.PLAIN, 16), false);
		big = new TrueTypeFont(new java.awt.Font("04b03", Font.PLAIN, 24), false);
		
		initLevel(level);
	}
	
	public void initLevel(int level) throws SlickException{
		lost = false;
		enemies = new ArrayList<Enemy>();
		enemyBullets = new ArrayList<Bullet>();
		powerups = new ArrayList<Powerup>();
		enemyQueue = new ArrayList<Enemy>(){
			private static final long serialVersionUID = 1L;
		{
//			 SPAWN RANDOM SHIT!
//			for(int i = 0; i < 1000; i++)
//				add( new Enemy(0, r.nextInt(400)+100, -10, 5, 1, r.nextInt(300*1000)));
		}};
		
		gameTime = 0;
		playState = 0;
		
		switch(level){
		case 1:
//			ID, x, y, speed, hp, spawnTime
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1000));
			enemyQueue.add(new Enemy(0, 500, -10, 5, 1, 1000));
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1200));
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1400));
			
			enemyQueue.add(new Enemy(0, 130, -10, 5, 1, 2000));
			enemyQueue.add(new Enemy(0, 180, -10, 5, 1, 2200));
			enemyQueue.add(new Enemy(0, 210, -10, 5, 1, 2400));
			enemyQueue.add(new Enemy(0, 240, -10, 5, 1, 2600));
			break;
			
		case 2:
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1000));
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1100));
			enemyQueue.add(new Enemy(0, 100, -10, 5, 1, 1200));
			break;
			
		default:
			int spd = 5 + (level/5);
			System.out.println("Speed " + spd);
			for(int i = 0; i < 5*level; i++)
				enemyQueue.add( new Enemy(0, r.nextInt(400)+100, -10, spd + r.nextInt(2), 1, r.nextInt(level*900)));
			break;
		}
		
		playState = 0;
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
			big.drawString(centerX("WAVE" + level, big), 235, "WAVE " + level);
		}
		
		player.render(g);
		
		g.drawString("SCORE " + Player.score, 10, 10);
		g.drawString("WAVE " + level, gc.getWidth() - 90, 10);
		
		if(!lost) g.drawString("HULL ", 10, gc.getHeight()-25);
		// health bar
		for(int i = 0; i < player.hp; i++){
			if(player.hp < 4)
				g.setColor(Color.green);
			else if(player.hp < 6)
				g.setColor(Color.orange);
			else
				g.setColor(Color.green);
			g.fillRect(60 + i*32, gc.getHeight()-22, 30, 10);
			g.setColor(Color.white);
		}
		
		// Powerups
		for (Iterator<Powerup> iterator = powerups.iterator(); iterator.hasNext(); ) {
			  Powerup p = iterator.next();
			  
			  if(p.visible){
				  p.sprite.draw(p.xpos, p.ypos);
				  p.move();
			  }
			  else
				  iterator.remove();
		}
		
//		// Powerups
//		for(Powerup p : powerups){
//			if(p.visible)
//				p.sprite.draw(p.xpos, p.ypos);
//		}
		
		// Enemies
		for(int j = 0; j < enemies.size(); j++){
			Enemy e = (Enemy) enemies.get(j);
			
			if(e.isAlive){
				e.sprite.draw(e.xpos, e.ypos);
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
			
			if(temp.visible)
				temp.move();
			else
				bullets.remove(i);
		}
		
		for(int i = 0; i < enemyBullets.size(); i++){
			EnemyBullet temp = (EnemyBullet) enemyBullets.get(i);
			temp.sprite.draw(temp.bx, temp.by);
			
			if(temp.visible)
				temp.move(0, 1);
			else
				enemyBullets.remove(i);
		}
		
		// Game Over
		if(lost) {
			g.drawString("GAME OVER", centerX("GAME OVER", normal), 250);

			scoreText = "SCORE " + Player.score;
			g.drawString(scoreText, centerX(scoreText, normal), 270);
			
			g.drawString("Press F5 to play again.", centerX("Press F5 to play again.", normal), 400);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(!lost)
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
					player.speed += 0.5f;
				}
				else if(input.isKeyPressed(Input.KEY_1))
					player.speed -= 0.5f;
				
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
				counter += delta;
				if (input.isKeyDown(Input.KEY_SPACE)){
					if(player.readyToFire){
						player.fire();
						player.readyToFire = false;
						counter = 0;
					}
					else if(counter >= player.bulletDelay)
						player.readyToFire = true;
				}
			}
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
		}

		
		
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
		ArrayList<Bullet> bullets = Player.bullets;
		
		// loop every bullet
		for(int i = 0; i < bullets.size(); i++){
			Bullet bTemp = (Bullet) bullets.get(i);
			Rectangle bulletBounds = bTemp.getBounds();
			
			// loop every live enemy
			for(int j = 0; j < enemies.size(); j++){
				eTemp = (Enemy) enemies.get(j);
				eBounds = eTemp.getBounds();
				
				// check if bullet hits enemy
				if(eBounds.intersects(bulletBounds) && eTemp.isAlive){
					enemies.get(j).hit(player.damage);
					bullets.get(i).hit();
				}
			}
		}
		
		// Ship collisions
		
		// Edges Collision
		if(player.xpos >= Game.GWIDTH-10 - player.sprite.getWidth()) player.xpos = Game.GWIDTH-10 - player.sprite.getWidth(); //right
		if(player.xpos <= 10) player.xpos = 10; //left
		if(player.ypos <= 10) player.ypos = 10; //up
		if(player.ypos >= Game.GHEIGHT-30 - player.sprite.getHeight()) player.ypos = Game.GHEIGHT-30 - player.sprite.getHeight(); //down
		
		// loop every enemy
		for(int i = 0; i < enemies.size(); i++){
			eTemp = (Enemy) enemies.get(i);
			eBounds = eTemp.getBounds();
			
			if(enemyBullets.size() < 1)
//				eTemp.fire();
			
			// check if enemy hits player
			if(p.intersects(eBounds) && player.isAlive && !player.invulnerable && enemies.get(i).isAlive){
				player.hit(1);
				enemies.get(i).die();
				if(player.hp < 1)
					lost = true;
			}
			// check if after explosion or beyond screen
			if(eTemp.explode.isStopped() || eTemp.ypos > bottomBorder)
				enemies.remove(i);
		}
		
		// loop powerups
		for (Iterator<Powerup> iterator = powerups.iterator(); iterator.hasNext(); ) {
			  Powerup pup = iterator.next();
			  if(pup.getBounds().intersects(p)){
				  player.pickPowerup(pup.powerupID);
				  iterator.remove();
			  }
			  else if(pup.ypos > bottomBorder)
				  iterator.remove();
		}
	}
	
	public static int centerX(String string, TrueTypeFont font){
		return (Game.GWIDTH - font.getWidth(string))/2;
	}
	
	public int getID(){
		return stateID;
	}
}
