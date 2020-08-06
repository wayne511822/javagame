package com.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonstorController extends Controller {

	/**
	 * �M�h�Ҧb��x�y��
	 */
	private int xKinght;
	/**
	 * �M�h�Ҧb��y�y��
	 */
	private int yKinght;

	/*
	 * �s��Ǫ������X
	 */
	// public List<Monster> monsters = new ArrayList<>();
	public CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<Monster>();

	/**
	 * �`�羹��H
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
		 * ø�s�ĤH
		 */
		g.setColor(Color.RED);
		for (Monster m : monsters) {
			g.fillRect(m.getX(), m.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
		}
	}

	@Override
	public synchronized void action() {
		// ���a�����ʮɴ��ĤH���
		if (!tempo.isActionAble()) {
			// �M�����X,�Ǫ��̪��A���
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
	 * �P�_�ĤH�ө����Ӥ�V��
	 */
	public void track(Creation m) {

		// �ͦ��H����,�Ψ��H����x��y��V����
		Random random = new Random();
		int xOry = random.nextInt(2);

		// �H�M�h�y�Ь����߷|���|�Ӥ��, �H�Φ��xy�b�W
		if (m.x > xKinght && m.y > yKinght) { // �b�M�h�k�U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // �V�U�ΦV����

		} else if (m.x < xKinght && m.y > yKinght) { // �b�M�h���U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x < xKinght && m.y < yKinght) { // �b�M�h���W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x > xKinght && m.y < yKinght) { // �b�M�h�k�W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // �V�W�ΦV����

		} else if (m.x == xKinght) { // ��by�b�W

			m.direction = m.y > yKinght ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // �V�W�ΦV�U��

		} else if (m.y == yKinght) { // ��bx�b�W

			m.direction = m.x > xKinght ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // �V���ΦV�k��
		}
		move(m, m.direction);
		
	}

	/*
	 * ���󲾰ʰʧ@, �ǤJ���ʤ�V
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
	 * �Ҧ��Ǫ��@�_��ʦӤ��O�@���ʤ@�� 
	 * 1.����Ѱʧ@���`�����̥~�h 
	 * 2.�M���Ҧ��Ǫ� 
	 * 3.�P�_�Ǫ������ ���y���������k
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
