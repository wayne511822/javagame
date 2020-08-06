package com.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
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
	static {

	}
	/**
	 * 每格方格大小
	 */
	private static final int PER_UNIT_SIZE = 60;
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
	 * 騎士對象
	 */
	private static Knight knight;
	/*
	 * 存放怪物的集合
	 */
	private CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>();

	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	/**
	 * 用來存儲物件在棋盤位置標記的二維陣列
	 */
	private Creation[][] location = new Creation[11][11];

	/**
	 * 判斷遊戲是否開始
	 */
	private static boolean isStart = false;
	/**
	 * 判斷玩家指令是否能執行
	 */
	private static boolean isActionable = false;

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

					knight = new Knight(colToX(5), rowToY(5), 5, 5);
					location[5][5] = knight;
					isStart = true;
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
				case KeyEvent.VK_F1:
					addMonster();
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

			g.setColor(tempo.color);
			g.fillOval(20, 20, PER_UNIT_SIZE, PER_UNIT_SIZE);

			g.setColor(Color.GREEN);
			//g.fillRect(knight.getX(), knight.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
			knight.drawSelf(g);
			
			g.setColor(Color.RED);
			for (Monster m : monsters) {
				m.drawSelf(g);
			}

		} else {

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

	}

	/**
	 * 更新棋盤標記
	 */
	private void updateLocation() {

		int xKnight = 0;
		int yKnight = 0;

		rwl.writeLock().lock();
		for (int col = 0; col < location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				if (location[col][row] != null) {
					Creation c = location[col][row]; // 取得該位置的物件
					location[col][row] = null; // 清空原本位置
					location[c.getCol()][c.getRow()] = c; // 重新添加到新的位置

					if (c.equals(knight)) {
						xKnight = c.getX();
						yKnight = c.getY();
					}
				}
			}
		}

		for (Monster m : monsters) {
			m.setxKnight(xKnight);
			m.setyKnight(yKnight);
		}
		rwl.writeLock().unlock();
	}

	/**
	 * 判斷物件的行為能力
	 */
	private void checkAbility() {

		rwl.readLock().lock();
		for (int col = 0; col < location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				boolean canUp = true;
				boolean canDown = true;
				boolean canLeft = true;
				boolean canRight = true;
				if (location[col][row] != null) {

					Creation c = location[col][row]; // 取得該位置的物件
					/*
					 * 判斷該物件是否在邊界 col = 0 or 10 row = 0 or 10
					 */
					if (col == 0)
						canLeft = false;
					if (col == 10)
						canRight = false;
					if (row == 0)
						canUp = false;
					if (row == 10)
						canDown = false;
					/*
					 * 判斷該物件上下左右有沒有物體
					 */
					if (canUp && location[col][row - 1] != null)
						canUp = false;
					if (canDown && location[col][row + 1] != null)
						canDown = false;
					if (canLeft && location[col - 1][row] != null)
						canLeft = false;
					if (canRight && location[col + 1][row] != null)
						canRight = false;

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
		Monster m = null;

		Random r = new Random();
		// 四個隨機數對應四個邊界
		int direction = r.nextInt(4);
		// 11個隨機數對應第幾行或列
		int posntion = r.nextInt(11);

		rwl.writeLock().lock();

		switch (direction) {
		case 0: // 上邊界 row = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(0), posntion, 0);
				location[posntion][0] = m;
			}
			break;
		case 1: // 下邊界 row = 10
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(10), posntion, 10);
				location[posntion][0] = m;
			}
			break;
		case 2: // 左邊界 col = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(0), rowToY(posntion), 0, posntion);
				location[posntion][0] = m;
			}
			break;
		case 4: // 右邊界 col = 10
			if (location[posntion][0] == null) {
				m = new Monster(colToX(10), rowToY(posntion), 10, posntion);
				location[posntion][0] = m;
			}
			break;
		}

		rwl.writeLock().unlock();

		if (m != null) {
			monsters.add(m);
		}
	}

	private void knightAction() {

		switch (knight.status) {
		case Creation.STATUS_MOVE:
			move(knight, knight.direction);
			break;
		default:
			jump(knight);
		}

	}

	/*
	 * 所有怪物一起行動而不是一次動一隻 1.把分解動作的循環放到最外層 2.遍歷所有怪物 3.判斷怪物的行動 分流到相應的方法
	 */
	private void MonsterAction() {	
		for (Monster m : monsters) {
			track(m);
		}
		
		try {
			// Thread.sleep(200);
			for (int i = 0; i < 10; i++) {
				// 2.
				for (Monster m : monsters) {
					// 3.
					//Thread.sleep(10);
					switch (m.status) {
					case Monster.STATUS_TRACK:
						
//						move(m, m.direction);
						break;
					}
					monsterMove(m, m.direction, i);
					repaint();
					Thread.sleep(10);
				}
			}
			// Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}

	/*
	 * 物件跳躍動作
	 */
	private void jump(Creation c) {
		c.canSetStatue = false;
		try {

			for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x);
				repaint();
				Thread.sleep(30);
			}
			c.canSetStatue = true;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 物件移動動作, 傳入移動方向
	 */
	private void move(Creation c, int direction) {
		c.canSetStatue = false;
		try {
			switch (direction) {
			case Creation.DIRECTION_UP:
				if (c.canUp) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] - Creation.moveCoefficient[i]);
						repaint();
						Thread.sleep(30);
					}
					c.setRow(c.row - 1);
				}
				break;
			case Creation.DIRECTION_DOWN:
				if (c.canDown) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] + Creation.moveCoefficient[i]);
						repaint();
						Thread.sleep(30);
					}
					c.setRow(c.row + 1);
				}
				break;
			case Creation.DIRECTION_LEFT:
				if (c.canLeft) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i]);
						c.setX(c.x - Creation.moveCoefficient[i]);
						repaint();
						Thread.sleep(30);
					}
					c.setCol(c.col - 1);
				}
				break;
			case Creation.DIRECTION_RIGHT:
				if (c.canRight) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i]);
						c.setX(c.x + Creation.moveCoefficient[i]);
						repaint();
						Thread.sleep(30);
					}
					c.setCol(c.col + 1);
				}
				break;
			}
			c.canSetStatue = true;
			c.setStatus(Creation.STATUS_JUMP);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 判斷怪物該往哪個方向走
	 */
	public void track(Monster m) {

		// 生成隨機數,用來隨機往x或y方向移動
		Random random = new Random();
		int xOry = random.nextInt(2);

		// 以騎士座標為中心會有四個方位, 以及位於xy軸上
		if (m.x > m.getxKinght() && m.y > m.getyKnight()) { // 在騎士右下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // 向下或向左走

		} else if (m.x < m.getxKinght() && m.y > m.getyKnight()) { // 在騎士左下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x < m.getxKinght() && m.y < m.getyKnight()) { // 在騎士左上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x > m.getxKinght() && m.y < m.getyKnight()) { // 在騎士右上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // 向上或向左走

		} else if (m.x == m.getxKinght()) { // 位在y軸上

			m.direction = m.y > m.getyKnight() ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // 向上或向下走

		} else if (m.y == m.getyKnight()) { // 位在x軸上

			m.direction = m.x > m.getxKinght() ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // 向左或向右走
		}
	}

	/*
	 * 怪物移動方法
	 */
	public void monsterMove(Creation c, int direction, int i) {
		c.canSetStatue = false;

		switch (direction) {
		case Creation.DIRECTION_UP:
			if (c.canUp) {
				c.setY(c.y - Creation.jumpCoefficient[i] - 6);
//				c.setRow(c.row - 1);
			}
			break;
		case Creation.DIRECTION_DOWN:
			if (c.canDown) {
				c.setY(c.y - Creation.jumpCoefficient[i] + 6);
//				c.setRow(c.row + 1);
			}
			break;
		case Creation.DIRECTION_LEFT:
			if (c.canLeft) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x - 6);
//				c.setCol(c.col - 1);
			}
			break;
		case Creation.DIRECTION_RIGHT:
			if (c.canRight) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x + 6);
//				c.setCol(c.col + 1);
			}
			break;
		}
		c.canSetStatue = true;
	}

	private boolean knightRun = true;
	private boolean monsterRun = false;

	@Override
	public void run() {
		while (true) {
			checkAbility();
			updateLocation();
			if (isStart) {
				if (tempo.isActionAble()) { //節拍器true / false各一秒
					if (monsterRun) {
						//執行一次就不執行
						monsterRun = false;
						MonsterAction();
						if (tempo.getTime() % 2 == 0) {
							addMonster();
						}
						knightRun = true;
					}
				} else {
					if (knightRun) {
						//執行一次就不執行
						knightRun = false;
						knightAction();
						//全部執行完怪物才能行動
						monsterRun = true;
					}
				}

			}
			// Thread.sleep(1000);
		}
	}

}
