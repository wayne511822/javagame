package com.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonstorController extends Controller {

	/**
	 * 騎士所在的x座標
	 */
	private int xKinght;
	/**
	 * 騎士所在的y座標
	 */
	private int yKinght;

	/*
	 * 存放怪物的集合
	 */
	// public List<Monster> monsters = new ArrayList<>();
	public CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<Monster>();

	/**
	 * 節拍器對象
	 */

	public MonstorController() {
		super();
	}

	public void setxKinght(int xKinght) {
		this.xKinght = xKinght;
	}

	public void setyKinght(int yKinght) {
		this.yKinght = yKinght;
	}

	public void addMonster(Monster m) {
		monsters.add(m);
		if (!tempo.isActionAble()) {
		}
	}

	public void draw(Graphics g) {
		/*
		 * 繪製敵人
		 */
		g.setColor(Color.RED);
		for (Monster m : monsters) {
			g.fillRect(m.getX(), m.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
		}
	}

	@Override
	public synchronized void action() {
		// 玩家不能行動時換敵人行動
		if (!tempo.isActionAble()) {
			// 遍歷集合,怪物依狀態行動
			for (Monster m : monsters) {
				switch (m.status) {

				case Monster.STATUS_TRACK:
					track(m);
					break;
				default:

				}
			}
		}
//		repaint();
	}

	/**
	 * 判斷敵人該往哪個方向走
	 */
	public void track(Creation m) {

		// 生成隨機數,用來隨機往x或y方向移動
		Random random = new Random();
		int xOry = random.nextInt(2);

		// 以騎士座標為中心會有四個方位, 以及位於xy軸上
		if (m.x > xKinght && m.y > yKinght) { // 在騎士右下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // 向下或向左走

		} else if (m.x < xKinght && m.y > yKinght) { // 在騎士左下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x < xKinght && m.y < yKinght) { // 在騎士左上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x > xKinght && m.y < yKinght) { // 在騎士右上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // 向上或向左走

		} else if (m.x == xKinght) { // 位在y軸上

			m.direction = m.y > yKinght ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // 向上或向下走

		} else if (m.y == yKinght) { // 位在x軸上

			m.direction = m.x > xKinght ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // 向左或向右走
		}
		move(m, m.direction);
		
	}

	/*
	 * 物件移動動作, 傳入移動方向
	 */
	public void move(Creation c, int direction) {
		c.canSetStatue = false;
		try {
			switch (direction) {
			case Creation.DIRECTION_UP:
				if (c.canUp) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] - Creation.moveCoefficient[i]);
						Thread.sleep(30);
					}
					c.setRow(c.row - 1);
				}
				break;
			case Creation.DIRECTION_DOWN:
				if (c.canDown) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] + Creation.moveCoefficient[i]);
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

	@Override
	public void run() {
		while (true) {

			monsterAction();
		}
	}

	/**
	 * 所有怪物一起行動而不是一次動一隻 
	 * 1.把分解動作的循環放到最外層 
	 * 2.遍歷所有怪物 
	 * 3.判斷怪物的行動 分流到相應的方法
	 */
	public void monsterAction() {
		if (!tempo.isActionAble()) {
			// 1.
			for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
				// 2.
				for (Monster m : monsters) {
					// 3.
					switch (m.status) {
					case Monster.STATUS_TRACK:
						track(m);
						moveAll(m, m.direction, i);
						break;
					}
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//repaint();
			}
			
		}
	}

	public void moveAll(Creation c, int direction, int i) {
		c.canSetStatue = false;

		switch (direction) {
		case Creation.DIRECTION_UP:
			if (c.canUp) {
				c.setY(c.y - Creation.jumpCoefficient[i] - Creation.moveCoefficient[i]);
//				c.setRow(c.row - 1);
			}
			break;
		case Creation.DIRECTION_DOWN:
			if (c.canDown) {
				c.setY(c.y - Creation.jumpCoefficient[i] + Creation.moveCoefficient[i]);
//				c.setRow(c.row + 1);
			}
			break;
		case Creation.DIRECTION_LEFT:
			if (c.canLeft) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x - Creation.moveCoefficient[i]);
//				c.setCol(c.col - 1);
			}
			break;
		case Creation.DIRECTION_RIGHT:
			if (c.canRight) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x + Creation.moveCoefficient[i]);
//				c.setCol(c.col + 1);
			}
			break;
		}
		c.canSetStatue = true;
	}

}
