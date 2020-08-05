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
	private static Tempo t;
	/**
	 * ��������
	 */
	private static Knight knight = new Knight();
	/**
	 * �s��ĤH�����X
	 */
	private static List<Skeleton> monsters = new ArrayList<>();
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
			g.setColor(Color.RED);
			for (int i = 0; i < monsters.size(); i++) {
				Skeleton s = monsters.get(i);
				g.fillRect(s.getX(), s.getY(), PER_UNIT_SIZE, PER_UNIT_SIZE);
			}

			/*
			 * ��ܸ`��
			 */
			g.setColor(t.color);
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
		t = Tempo.getInstance();
		knight.setTempo(t);
		/*
		 * �ѽL�аO��l��
		 */
		location[5][5] = knight;
		
	}
	/**
	 * ��s�ѽL�аO
	 */
	private void updateLocation() {
		
	}
	/**
	 * �N�C���ഫ��x�y��
	 */
	private int colToX(int col) {
		return border_Top + col * PER_UNIT_SIZE;
	}
	/**
	 * �N����ഫ��y�y��
	 */
	private int rowToY(int row) {
		return border_Top + row * PER_UNIT_SIZE;
	}
	/**
	 * �K�[�ĤH����k
	 */
	private void addMonster() {
		Skeleton skeleton = new Skeleton();
		monsters.add(skeleton);
	}
	/**
	 * �ΨӧP�_�M�h�O�_�i�H����
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
