package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.framework.util.RandomNumberGenerator;

public class Block {
	private float x, y;
	private int width, height;
	private Rectangle rect;
	private boolean visible;
	
	private static final int UPPER_Y = 275;
	private static final int LOWER_Y = 355;
	
	public Block(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rectangle((int) x, (int) y, width, height);
		visible = false;
	}
	
	public void update(float delta, float velX){
		x += velX * delta;
		if(x <= -50){
			reset();
		}
		updateRect();
	}
	
	public void updateRect(){
		rect.setBounds((int) x, (int) y, width, height);
	}
	
	public void reset(){
		visible = true;
		if(RandomNumberGenerator.getRandInt(3) == 0){
			y = UPPER_Y;
		}else{
			y = LOWER_Y;
		}
		
		x += 1000;
	}
	
	public void onCollide(Player p){
		visible = false;
		p.pushBack(30);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public Rectangle getRect(){
		return rect;
	}

}
