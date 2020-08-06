package com.objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Monster extends Creation {
	
	public static final int STATUS_TRACK = 0;
	/**
	 * 騎士所在的x座標
	 */
	private int xKnight;
	/**
	 * 騎士所在的y座標
	 */
	private int yKnight;
	Image walter;
	public Monster() {
	}

	public Monster(int x, int y, int col, int row) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
		walter =  new ImageIcon("res/walter.png").getImage();
	}

	public int getxKinght() {
		return xKnight;
	}

	public void setxKnight(int xKnight) {
		this.xKnight = xKnight;
	}

	public int getyKnight() {
		return yKnight;
	}

	public void setyKnight(int yKnight) {
		this.yKnight = yKnight;
	}

	public void drawSelf(Graphics g) {
		g.drawImage(walter, x, y, null);
	}
}
