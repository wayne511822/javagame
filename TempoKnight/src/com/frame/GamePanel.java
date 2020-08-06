package com.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JPanel;

import com.behavior.Tempo;
import com.objects.Knight;
import com.objects.Monster;
import com.objects.MonstorController;
import com.objects.Controller;
import com.objects.Creation;

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
	private static Tempo tempo;
	/**
	 * 控制器對象
	 */
	private static Controller controller = new Controller();
	/**
	 * 騎士對象
	 */
	//private Creation knight;
	
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	/**
	 * 存放敵人的集合
	 */
	private static MonstorController mc = new MonstorController();
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
				Creation knight = controller.creations.get(0);
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					if (isStart) {
						new Thread(tempo).start();
						new Thread(controller).start();
						new Thread(mc).start();
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
				case KeyEvent.VK_F1:
					addMonster();
					break;
				case KeyEvent.VK_F2:
					System.out.println(mc.monsters.size());
					int count = 0;
					rwl.readLock().lock();
					for (int col = 0; col <location.length; col++) {
						for (int row = 0; row < location[col].length; row++) {
							if (location[col][row] != null) {
								count++;
							}
						}
					}	
					rwl.readLock().unlock();
					System.out.println(count);
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
		//取得騎士對象
		Creation knight = controller.creations.get(0);
		//取得怪物集合
		CopyOnWriteArrayList<Monster> monsters = mc.monsters;
		
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
			 * 顯示節拍
			 */
			g.setColor(tempo.color);
			g.fillOval(20, 20, PER_UNIT_SIZE, PER_UNIT_SIZE);

			mc.draw(g);
//			g.setColor(Color.RED);
//			for (Monster m : monsters) {
//				g.fillRect(m.getX(), m.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
//			}
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
		tempo = Tempo.getInstance();
		controller.setTempo(tempo);
		mc.setTempo(tempo);
		Creation knight = new Knight(colToX(5), rowToY(5), 5, 5);
		location[5][5] = knight;
		controller.addCreation(knight);
	}

	/**
	 * 更新棋盤標記
	 */
	private void updateLocation() {
		int xKnight;
		int yKnight;
		
		rwl.writeLock().lock();
		for (int col = 0; col <location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				if (location[col][row] != null) {
					Creation c = location[col][row]; // 取得該位置的物件
					location[col][row] = null; // 清空原本位置
					location[c.getCol()][c.getRow()] = c; // 重新添加到新的位置
					
					if (c.equals(controller.creations.get(0))) {
						mc.setxKinght(colToX(col));
						mc.setyKinght(rowToY(row));
					}
				}
			}
		}
		rwl.writeLock().unlock();
	}

	/**
	 * 判斷物件的行為能力
	 */
	private void checkAbility() {
		
		rwl.readLock().lock();
		for (int col = 0; col <location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				boolean canUp = true;
				boolean canDown = true;
				boolean canLeft = true;
				boolean canRight = true;
				if (location[col][row] != null) {
					
					Creation c = location[col][row]; //取得該位置的物件
					/*
					 * 判斷該物件是否在邊界
					 * col = 0 or 10
					 * row = 0 or 10
					 */
					if (col == 0) canLeft = false;
					if (col == 10) canRight = false;
					if (row == 0) canUp = false;
					if (row == 10) canDown = false;
					/*
					 * 判斷該物件上下左右有沒有物體
					 */
					if (canUp && location[col][row - 1] != null) canUp = false;
					if (canDown && location[col][row + 1] != null) canDown = false;
					if (canLeft && location[col - 1][row] != null) canLeft = false;
					if (canRight && location[col + 1][row]!= null) canRight = false;
					
					c.setAbility(canUp, canDown, canLeft, canRight);
					
				}
				
			}
		}
		rwl.readLock().unlock();

	}

	/**
	 * 將列數轉換成x座標
	 */
	private int colToX(int col) {
		return border_Left + col * PER_UNIT_SIZE;
	}

	/**
	 * 將行數轉換成y座標
	 */
	private int rowToY(int row) {
		return border_Top + row * PER_UNIT_SIZE;
	}

	/**
	 * 添加怪物的方法,怪物只會在邊界生成
	 */
	private void addMonster() {
		Thread t = Thread.currentThread();
		System.out.println(t.getName());
		Monster m = null;
		
		Random r = new Random();
		//四個隨機數對應四個邊界
		int direction = r.nextInt(4);
		//11個隨機數對應第幾行或列
		int posntion = r.nextInt(11);
		
		rwl.writeLock().lock();
		
		switch (direction) {
		case 0: //上邊界 row = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(0), posntion, 0);
				location[posntion][0] = m;
			}
			break;
		case 1: //下邊界 row = 10
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(10), posntion, 10);
				location[posntion][0] = m;
			}
			break;
		case 2: //左邊界 col = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(0), rowToY(posntion), 0, posntion);
				location[posntion][0] = m;
			}
			break;
		case 4: //右邊界 col = 10
			if (location[posntion][0] == null) {
				m = new Monster(colToX(10), rowToY(posntion), 10, posntion);
				location[posntion][0] = m;
			}
			break;
		}
		
		rwl.writeLock().unlock();
		
		if (m != null) { 
			mc.addMonster(m);
		}
	}

	private synchronized void action() {
		updateLocation();
		checkAbility();
		repaint();
	}
	
	@Override
	public void run() {
		while (true) {
			action();	
		}
	}

	
}
