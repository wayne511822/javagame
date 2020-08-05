package com.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import com.behavior.Tempo;
import com.objects.Knight;
import com.objects.MonstorController;
import com.objects.Creation;
import com.objects.Skeleton;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * �C����j�p
	 */
	private static final int PER_UNIT_SIZE = 65;
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
	 * ��������
	 */
	private static Knight knight = new Knight();
	/**
	 * �s��ĤH�����X
	 */
	private static MonstorController mc = new MonstorController();
	/**
	 * �ΨӦs�x����b�ѽL��m�аO���G���}�C
	 */
	private Creation[][] location = new Creation[11][11];

	/**
	 * �P�_�C���O�_�}�l
	 */
	private static boolean isStart = true;
	/**
	 * �P�_���a���O�O�_�����
	 */
	private static boolean isActionable = true;

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
					if (isStart) {
						new Thread(tempo).start();
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
			/*
			 * ø�s�M�h
			 */
			knight.setX(xBegin + PER_UNIT_SIZE * 5);
			knight.setY(yBegin + PER_UNIT_SIZE * 5);
			g.setColor(Color.GREEN);
			g.fillRect(knight.getX(), knight.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);

		} else {
			/*
			 * ø�s�M�h
			 */
			g.setColor(Color.YELLOW);
			g.fillRect(knight.getX(), knight.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
			/*
			 * ø�s�ĤH
			 */
//			g.setColor(Color.RED);
//			for (int i = 0; i < monsters.size(); i++) {
//				Skeleton s = monsters.get(i);
//				g.fillRect(s.getX(), s.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
//			}

			/*
			 * ��ܸ`��
			 */
			g.setColor(tempo.color);
			g.fillOval(20, 20, PER_UNIT_SIZE, PER_UNIT_SIZE);

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
		knight.setTempo(tempo);
		knight.setCol(5);
		knight.setRow(5);
		location[5][5] = knight;
	}

	/**
	 * ��s�ѽL�аO
	 */
	private void updateLocation() {
		int xKnight;
		int yKnight;
		
		for (int col = 0; col <location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				if (location[col][row] != null) {
					Creation c = location[col][row]; // ���o�Ӧ�m������
					location[col][row] = null; // �M�ŭ쥻��m
					location[c.getCol()][c.getRow()] = c; // ���s�K�[��s����m
					
					if (c.equals(knight)) {
						x
					}
				}
			}
		}
	}

	/**
	 * �P�_���󪺦欰��O
	 */
	private void checkAbility() {
		
		for (int col = 0; col <location.length; col++) {
			for (int row = 0; row < location[col].length; row++) {
				boolean canUp = true;
				boolean canDown = true;
				boolean canLeft = true;
				boolean canRight = true;
				if (location[col][row] != null) {
					Creation c = location[col][row]; //���o�Ӧ�m������
					/*
					 * �P�_�Ӫ���O�_�b���
					 * col = 0 or 10
					 * row = 0 or 10
					 */
					if (col == 0) canLeft = false;
					if (col == 10) canRight = false;
					if (row == 0) canUp = false;
					if (row == 10) canDown = false;
					/*
					 * �P�_�Ӫ���W�U���k���S������
					 */
					if (canUp && location[col][row - 1] != null) canUp = false;
					if (canDown && location[col][row + 1] != null) canDown = false;
					if (canLeft && location[col - 1][row] != null) canLeft = false;
					if (canRight && location[col + 1][row]!= null) canRight = false;
					
					c.setAbility(canUp, canDown, canLeft, canRight);
				}
				
			}
		}

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
	 * �K�[�ĤH����k,�w�H�u�|�b��ɥͦ�
	 */
	private void addMonster() {
		
		
		Random r = new Random();
		//�|���H���ƹ����|�����
		int direction = r.nextInt(4);
		//11���H���ƹ����ĴX��ΦC
		int posntion = r.nextInt(11);
		switch (direction) {
		case 0: //�W��� row = 0
			s = new Skeleton(colToX(posntion), rowToY(0), posntion, 0, tempo);
			location[posntion][0] = s;
			break;
		case 1: //�U��� row = 10
			s = new Skeleton(colToX(posntion), rowToY(10), posntion, 10, tempo);
			location[posntion][0] = s;
			break;
		case 2: //����� col = 0
			s = new Skeleton(colToX(0), rowToY(posntion), 0, posntion, tempo);
			location[posntion][0] = s;
			break;
		case 4: //�k��� col = 10
			s = new Skeleton(colToX(10), rowToY(posntion), 10, posntion, tempo);
			location[posntion][0] = s;
			break;
		}
		if (s != null) {
			new Thread(s).start();
			monsters.add(s);
		}
	}

	@Override
	public void run() {
		while (true) {
			updateLocation();
			checkAbility();
			repaint();	
		}
	}

}
