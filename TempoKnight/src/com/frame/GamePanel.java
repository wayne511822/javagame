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
	 * �C����j�p
	 */
	private static final int PER_UNIT_SIZE = 60;
	/**
	 * �C��
	 */
	private static final int COLS = 11;
	/**
	 * ���
	 */
	private static final int ROWS = 11;
	/**
	 * �e���e��
	 */
	private static final int PANEL_WIDTH = 1000;
	/**
	 * �e������
	 */
	private static final int PANEL_HIGHT = 800;
	/**
	 * �ѽL�W���
	 */
	private int border_Top = PANEL_HIGHT / 2 - PER_UNIT_SIZE * 6;
	/**
	 * �ѽL�U���
	 */
	private int border_Down = PANEL_HIGHT / 2 + PER_UNIT_SIZE * 5;
	/**
	 * �ѽL�����
	 */
	private int border_Left = PANEL_WIDTH / 2 - PER_UNIT_SIZE * 6;
	/**
	 * �ѽL�k���
	 */
	private int border_Right = PANEL_WIDTH / 2 + PER_UNIT_SIZE * 5;
	/**
	 * �`�羹��H
	 */
	private static Tempo tempo;
	/**
	 * �M�h��H
	 */
	private static Knight knight;
	/*
	 * �s��Ǫ������X
	 */
	private CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>();

	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	/**
	 * �ΨӦs�x����b�ѽL��m�аO���G���}�C
	 */
	private Creation[][] location = new Creation[11][11];

	/**
	 * �P�_�C���O�_�}�l
	 */
	private static boolean isStart = false;
	/**
	 * �P�_���a���O�O�_�����
	 */
	private static boolean isActionable = false;

	/**
	 * �C���e���t�m �]�m��ť
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
	 * ø�s�ѽL
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// �ѽLø�s�_�I
		int xBegin = border_Left;
		int yBegin = border_Top;
		/*
		 * �e�C
		 */
		g.setColor(Color.WHITE);
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(xBegin + PER_UNIT_SIZE * i, yBegin, xBegin + PER_UNIT_SIZE * i, yBegin + ROWS * PER_UNIT_SIZE);
		}

		/*
		 * �e��
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
	 * ��l��
	 */
	private void init() {
		/*
		 * �����l��
		 */
		setFocusable(true);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HIGHT));
		setBackground(Color.BLACK);
		/*
		 * �����H��l��
		 */
		tempo = Tempo.getInstance();

	}

	/**
	 * ��s�ѽL�аO
	 */
	private void updateLocation() {

		int xKnight = 0;
		int yKnight = 0;

		rwl.writeLock().lock();
		for (int col = 0; col < location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				if (location[col][row] != null) {
					Creation c = location[col][row]; // ���o�Ӧ�m������
					location[col][row] = null; // �M�ŭ쥻��m
					location[c.getCol()][c.getRow()] = c; // ���s�K�[��s����m

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
	 * �P�_���󪺦欰��O
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

					Creation c = location[col][row]; // ���o�Ӧ�m������
					/*
					 * �P�_�Ӫ���O�_�b��� col = 0 or 10 row = 0 or 10
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
					 * �P�_�Ӫ���W�U���k���S������
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
	 * �N�C���ഫ��x�y��
	 */
	private int colToX(int col) {
		return border_Left + col * PER_UNIT_SIZE;
	}

	/**
	 * �N����ഫ��y�y��
	 */
	private int rowToY(int row) {
		return border_Top + row * PER_UNIT_SIZE;
	}

	/**
	 * �K�[�Ǫ�����k,�Ǫ��u�|�b��ɥͦ�
	 */
	private void addMonster() {
		Monster m = null;

		Random r = new Random();
		// �|���H���ƹ����|�����
		int direction = r.nextInt(4);
		// 11���H���ƹ����ĴX��ΦC
		int posntion = r.nextInt(11);

		rwl.writeLock().lock();

		switch (direction) {
		case 0: // �W��� row = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(0), posntion, 0);
				location[posntion][0] = m;
			}
			break;
		case 1: // �U��� row = 10
			if (location[posntion][0] == null) {
				m = new Monster(colToX(posntion), rowToY(10), posntion, 10);
				location[posntion][0] = m;
			}
			break;
		case 2: // ����� col = 0
			if (location[posntion][0] == null) {
				m = new Monster(colToX(0), rowToY(posntion), 0, posntion);
				location[posntion][0] = m;
			}
			break;
		case 4: // �k��� col = 10
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
	 * �Ҧ��Ǫ��@�_��ʦӤ��O�@���ʤ@�� 1.����Ѱʧ@���`�����̥~�h 2.�M���Ҧ��Ǫ� 3.�P�_�Ǫ������ ���y���������k
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
	 * ������D�ʧ@
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
	 * ���󲾰ʰʧ@, �ǤJ���ʤ�V
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
	 * �P�_�Ǫ��ө����Ӥ�V��
	 */
	public void track(Monster m) {

		// �ͦ��H����,�Ψ��H����x��y��V����
		Random random = new Random();
		int xOry = random.nextInt(2);

		// �H�M�h�y�Ь����߷|���|�Ӥ��, �H�Φ��xy�b�W
		if (m.x > m.getxKinght() && m.y > m.getyKnight()) { // �b�M�h�k�U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // �V�U�ΦV����

		} else if (m.x < m.getxKinght() && m.y > m.getyKnight()) { // �b�M�h���U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x < m.getxKinght() && m.y < m.getyKnight()) { // �b�M�h���W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x > m.getxKinght() && m.y < m.getyKnight()) { // �b�M�h�k�W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // �V�W�ΦV����

		} else if (m.x == m.getxKinght()) { // ��by�b�W

			m.direction = m.y > m.getyKnight() ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // �V�W�ΦV�U��

		} else if (m.y == m.getyKnight()) { // ��bx�b�W

			m.direction = m.x > m.getxKinght() ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // �V���ΦV�k��
		}
	}

	/*
	 * �Ǫ����ʤ�k
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
				if (tempo.isActionAble()) { //�`�羹true / false�U�@��
					if (monsterRun) {
						//����@���N������
						monsterRun = false;
						MonsterAction();
						if (tempo.getTime() % 2 == 0) {
							addMonster();
						}
						knightRun = true;
					}
				} else {
					if (knightRun) {
						//����@���N������
						knightRun = false;
						knightAction();
						//�������槹�Ǫ��~����
						monsterRun = true;
					}
				}

			}
			// Thread.sleep(1000);
		}
	}

}
