package blastergame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound playerBullet, explode, select, music, playerExplode, bleep, coin1;
	
	public Sounds() throws SlickException{
		playerBullet = new Sound("res/sounds/shoot3.wav");
		explode= new Sound("res/sounds/explode.wav");
		playerExplode = new Sound("res/sounds/playerExplode.wav");
		select = new Sound("res/sounds/select.wav");
		bleep = new Sound("res/sounds/bleep.wav");
		
		music = new Sound("res/sounds/music.wav");
		coin1 = new Sound("res/sounds/coin.wav");
	}
}