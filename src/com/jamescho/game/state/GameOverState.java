package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;

public class GameOverState extends State{
	
	private String playerScore;
	private Font font;
	
	public GameOverState(int playerScore){
		this.playerScore = "" + playerScore;
		font = new Font("SansSerif", Font.BOLD, 50);
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		g.setColor(Color.DARK_GRAY);
		g.setFont(font);
		g.drawString("GAME_OVER", 257, 175);
		g.drawString(playerScore, 385, 250);
		g.drawString("Press any key.", 240, 350);
	}

	@Override
	public void onClick(MouseEvent e) {
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		setCurrentState(new MenuState());
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		
	}
	
}
