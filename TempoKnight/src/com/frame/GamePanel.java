package com.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.behavior.Tempo;
import com.objects.Knight;
import com.objects.Creation;
import com.objects.Skeleton;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * 每格方格大小
	 */
	private static final int PER_UNIT_SIZE = 65;
	/**
	 * 列數
	 */
	private static final int COLS = 11;
	/**
	 * 行數
	 */
	private static final int ROWS = 11;
	/**
	 * 畫面寬度
	 */
	private static final int PANEL_WIDTH = 1000;
	/**
	 * 畫面高度
	 */
	private static final int PANEL_HIGHT = 800;
	/**
	 * 棋盤上邊界
	 */
	private int border_Top = PANEL_HIGHT / 2 - PER_UNIT_SIZE * 6;
	/**
	 * 棋盤下邊界
	 */
	private int border_Down = PANEL_HIGHT / 2 + PER_UNIT_SIZE * 5;
	/**
	 * 棋盤左邊界
	 */
	private int border_Left = PANEL_WIDTH / 2 - PER_UNIT_SIZE * 6;
	/**
	 * 棋盤右邊界
	 */
	private int border_Right = PANEL_WIDTH / 2 + PER_UNIT_SIZE * 5;
	/**
	 * 節拍器對象
	 */
	private static Tempo t;
	/**
	 * 模擬物件
	 */
	private static Knight knight = new Knight();
	/**
	 * 存放敵人的集合
	 */
	private static List<Skeleton> monsters = new ArrayList<>();
	/**
	 * 用來存儲物件在棋盤位置標記的二維陣列
	 */
	private Creation[][] location = new Creation[11][11];
	
	/**
	 * 判斷遊戲是否開始
	 */
	private static boolean isStart = true;
	/**
	 * 判斷玩家指令是否能執行
	 */
	private static boolean isActionable = true;

	/**
	 * 遊戲畫面配置 設置監聽
	 */
	public GamePanel() {
		init();
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					if (isStart) {
						new Thread(t).start();
						new Thread(knight).start();
						isStart = false;
					}
					break;
				case KeyEvent.VK_UP:
					knight.setDirection(Creation.DIRECTION_UP);
					knight.setStatus(Creation.STATUS_MOVE);
					break;
				case KeyEvent.VK_DOWN:
					knight.setDirection(Creation.DIRECTION_DOWN);
					knight.setStatus(Creation.STATUS_MOVE);
					break;
				case KeyEvent.VK_LEFT:
					knight.setDirection(Creation.DIRECTION_LEFT);
					knight.setStatus(Creation.STATUS_MOVE);
					break;
				case KeyEvent.VK_RIGHT:
					knight.setDirection(Creation.DIRECTION_RIGHT);
					knight.setStatus(Creation.STATUS_MOVE);
					break;
				}
			}

		});
	}

	/**
	 * 繪製棋盤
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 棋盤繪製起點
		int xBegin = border_Left;
		int yBegin = border_Top;
		/*
		 * 畫列
		 */
		g.setColor(Color.WHITE);
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(xBegin + PER_UNIT_SIZE * i, yBegin, xBegin + PER_UNIT_SIZE * i, yBegin + ROWS * PER_UNIT_SIZE);
		}

		/*
		 * 畫行
		 */
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(xBegin, yBegin + PER_UNIT_SIZE * i, xBegin + COLS * PER_UNIT_SIZE, yBegin + PER_UNIT_SIZE * i);
		}

		if (isStart) {
			/*
			 * 繪製騎士
			 */
			knight.setX(xBegin + PER_UNIT_SIZE * 5);
			knight.setY(yBegin + PER_UNIT_SIZE * 5);
			g.setColor(Color.GREEN);
			g.fillRect(knight.getX(), knight.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);

		} else {
			/*
			 * 繪製騎士
			 */
			g.setColor(Color.YELLOW);
			g.fillRect(knight.getX(), knight.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
			/*
			 * 繪製敵人
			 */
			g.setColor(Color.RED);
			for (int i = 0; i < monsters.size(); i++) {
				Skeleton s = monsters.get(i);
				g.fillRect(s.getX(), s.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
			}

			/*
			 * 顯示節拍
			 */
			g.setColor(t.color);
			g.fillOval(20, 20, PER_UNIT_SIZE, PER_UNIT_SIZE);

		}
	}
	/**
	 * 初始化
	 */
	private void init() {
		/*
		 * 窗體初始化
		 */
		setFocusable(true);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HIGHT));
		setBackground(Color.BLACK);
		/*
		 * 物件對象初始化
		 */
		t = Tempo.getInstance();
		knight.setTempo(t);
		/*
		 * 棋盤標記初始化
		 */
		location[5][5] = knight;
		
	}
	/**
	 * 更新棋盤標記
	 */
	private void updateLocation() {
		
	}
	/**
	 * 將列數轉換成x座標
	 */
	private int colToX(int col) {
		return border_Top + col * PER_UNIT_SIZE;
	}
	/**
	 * 將行數轉換成y座標
	 */
	private int rowToY(int row) {
		return border_Top + row * PER_UNIT_SIZE;
	}
	/**
	 * 添加敵人的方法
	 */
	private void addMonster() {
		Skeleton skeleton = new Skeleton();
		monsters.add(skeleton);
	}
	/**
	 * 用來判斷騎士是否可以移動
	 */
	private void moveAble() {
		
	}

	@Override
	public void run() {
		while (true) {
			updateLocation();
			moveAble();
			repaint();
		}
	}

}
