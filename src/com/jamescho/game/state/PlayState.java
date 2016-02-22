package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Block;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Player;

public class PlayState extends State {
	
	private Player player;
	private ArrayList<Block> blocks;
	private Cloud cloud, cloud2;
	
	private Font scoreFont;
	private int playerScore;
	
	private static final int BLOCK_HEIGHT = 50;
	private static final int BLOCK_WIDTH = 20;
	private int blockSpeed = -200;
	
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;

	@Override
	public void init() {
		player = new Player(160, GameMain.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		blocks = new ArrayList<Block>();
		cloud = new Cloud(100, 100);
		cloud2 = new Cloud(500, 50);
		scoreFont = new Font("SansSerif",Font.BOLD,25);
		
		for(int i = 0; i < 5; i++){
			Block b = new Block(i * 200, GameMain.GAME_HEIGHT - 95, BLOCK_WIDTH, BLOCK_HEIGHT);
			blocks.add(b);
		}
	}

	@Override
	public void update(float delta) {
		if(!player.isAlive()){
			setCurrentState(new GameOverState( playerScore / 100));
		}
		
		playerScore += 1;
		if(playerScore % 500 == 0 && blockSpeed > - 280){
			blockSpeed -= 10;
		}
		
		cloud.update(delta);
		cloud2.update(delta);
		Resources.runAnim.update(delta);
		player.update(delta);
		updateBlocks(delta);
	}

	private void updateBlocks(float delta) {
		for(Block b: blocks){
			b.update(delta, blockSpeed);
			
			if(b.isVisible()){
				if(player.isDucked() && b.getRect().intersects(player.getDuckRect())){
					b.onCollide(player);
				}else if(!player.isDucked() && b.getRect().intersects(player.getRect())){
					b.onCollide(player);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Resources.skyBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		renderPlayer(g);
		renderBlocks(g);
		renderSun(g);
		renderClouds(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
	}

	private void renderScore(Graphics g) {
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString("" + playerScore / 100, 20, 30);
	}
	
	private void renderPlayer(Graphics g) {
		if(player.isGround()){
			if(player.isDucked()){
				g.drawImage(Resources.duck, (int) player.getX(), (int) player.getY(), null);
			}else{
				Resources.runAnim.render(g, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
			}
		}else{
			g.drawImage(Resources.jump, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight(), null);
		}
	}

	private void renderBlocks(Graphics g) {
		for(Block b : blocks){
			if(b.isVisible()){
				g.drawImage(Resources.block, (int) b.getX(), (int) b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT, null);
			}
		}
	}
	
	private void renderSun(Graphics g) {
		g.setColor(Color.orange);
		g.fillOval(715, -85, 170, 170);
		g.setColor(Color.yellow);
		g.fillOval(725, -75, 150, 150);
	}

	private void renderClouds(Graphics g) {
		g.drawImage(Resources.cloud1, (int) cloud.getX(), (int) cloud.getY(), 100, 60, null);
		g.drawImage(Resources.cloud2, (int) cloud2.getX(), (int) cloud2.getY(), 100, 60, null);
	}

	@Override
	public void onClick(MouseEvent e) {
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			player.jump();
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			player.duck();
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		
	}
	
}
