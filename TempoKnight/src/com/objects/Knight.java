package com.objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Knight extends Creation {
	Image jake;
	
	public Knight(int x, int y, int col, int row) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
		jake =  new ImageIcon("res/jake.png").getImage();
	}
	
	public void drawSelf(Graphics g) {
		g.drawImage(jake, x, y, null);
	}
}
