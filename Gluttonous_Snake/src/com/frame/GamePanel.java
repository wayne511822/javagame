package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JPanel;

import com.objects.Node;
import com.objects.Snake;

public class GamePanel extends JPanel implements Runnable {
	/**
	 * ������
	 */
	public static final int PER_UNIT_LENGTH = 20;
	/**
	 * ����
	 */
	public static final int MULTIPLE = 15;
	/**
	 * �C����ؤ@�b����
	 */
	public static final int HALF_SIDE = MULTIPLE * PER_UNIT_LENGTH;
	/**
	 * �P�_�O�_��l��
	 */
	private boolean isFirstRun = true;
	/**
	 * �P�_�O�_�}�l
	 */
	private boolean isStarted = false;
	/**
	 * �P�_�O�_�Ȱ�
	 */
	private boolean isPaused = false;
	/**
	 * �C������
	 */
	private static int score = 0;
	/**
	 * �ǻ��C����T
	 */
	private static int information = 0;

	private Snake snake = new Snake();
	private Node egg = new Node();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// ����y��
		int xCenter = getWidth() / 2;
		int yCenter = getHeight() / 2;

		// ����H���I���y��
		int xRandom, yRandom;

		if (isFirstRun) {
			isFirstRun = false;

			// ø�s�C�����
			g.drawRect(xCenter - HALF_SIDE, yCenter - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);

			// ��l�ƳD�Y
			Node head = snake.getHead();
			head.setX(xCenter);
			head.setY(yCenter);
			g.setColor(Color.ORANGE);
			g.fillRect(head.getX(), head.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

			// ��l�ƳJ
			do {
				xRandom = xCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
				yRandom = yCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
			} while (xRandom == head.getX() && yRandom == head.getY());
			egg.setX(xRandom);
			egg.setY(yRandom);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(egg.getX(), egg.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

		} else {
			// ø�s�C�����
			g.drawRect(xCenter - HALF_SIDE, yCenter - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);

			// ø�s�D��
			g.setColor(Color.MAGENTA);
			LinkedList<Node> body = snake.getBody();
			for (int i = 0; i < body.size(); i++) {
				g.fillRect(body.get(i).getX(), body.get(i).getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			}

			// ø�s�D�Y
			Node head = snake.getHead();
			g.setColor(Color.ORANGE);
			g.fillRect(head.getX(), head.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

			// �J�Q�Y��h��s�J
			if (isEncountered()) {
				do {
					xRandom = xCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
					yRandom = yCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
				} while (xRandom == head.getX() && yRandom == head.getY());
				egg.setX(xRandom);
				egg.setY(yRandom);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(egg.getX(), egg.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

			// �C������,�hø�sGAME OVER
			if (isCrushed()) {
				g.setColor(Color.BLACK);
				FontMetrics fm = g.getFontMetrics();
				int stringWidth = fm.stringWidth("GAME OVER");
				int stringAscent = fm.getAscent();
				int xCoordinate = xCenter - stringWidth / 2;
				int yCoordinate = yCenter - stringAscent / 2;
				g.drawString("GAME OVER", xCoordinate, yCoordinate);
			}
		}
	}

	public GamePanel() {
		setFocusable(true);
		setFont(new Font("Californian FB", Font.BOLD, 80));
		// ���U��L��ť
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				int direction = snake.getDirection();
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (isStarted && !isPaused && !isCrushed()) {
						if (direction != Snake.DIRECTION_UP && direction != Snake.DIRECTION_DOWN) {
							snake.setDirection(Snake.DIRECTION_UP);
							changeSnakeLocation();
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if (isStarted && !isPaused && !isCrushed()) {
						if (direction != Snake.DIRECTION_UP && direction != Snake.DIRECTION_DOWN) {
							snake.setDirection(Snake.DIRECTION_DOWN);
							changeSnakeLocation();
						}
					}
					break;
				case KeyEvent.VK_LEFT:
					if (isStarted && !isPaused && !isCrushed()) {
						if (direction != Snake.DIRECTION_LEFT && direction != Snake.DIRECTION_RIGHT) {
							snake.setDirection(Snake.DIRECTION_LEFT);
							changeSnakeLocation();
						}
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (isStarted && !isPaused && !isCrushed()) {
						if (direction != Snake.DIRECTION_LEFT && direction != Snake.DIRECTION_RIGHT) {
							snake.setDirection(Snake.DIRECTION_RIGHT);
							changeSnakeLocation();
						}
					}
					break;
				case KeyEvent.VK_ENTER:
					if (isCrushed()) { // �p�G�C������, ���m�ƾڨí��s�}�l�C��
//						snake.setDirection(Snake.DIRECTION_RIGHT);
//						snake.setSpeed(Snake.SPEED_3);
//						snake.setBody(new LinkedList<Node>());
						snake = new Snake();
						isFirstRun = true;
						isStarted = false;
						isPaused = false;

						score = 0;
						information = 0;

						repaint();
					} else {
						isStarted = true;
					}
					break;
				case KeyEvent.VK_SPACE:
					if (isStarted && !isCrushed()) {
						isPaused = !isPaused;
					}
					break;
				case KeyEvent.VK_F1:
					snake.setSpeed(Snake.SPEED_1);
					break;
				case KeyEvent.VK_F2:
					snake.setSpeed(Snake.SPEED_2);
					break;
				case KeyEvent.VK_F3:
					snake.setSpeed(Snake.SPEED_3);
					break;
				case KeyEvent.VK_F4:
					snake.setSpeed(Snake.SPEED_4);
					break;
				case KeyEvent.VK_F5:
					snake.setSpeed(Snake.SPEED_5);
					break;
				}
			}

		});

	}

	@Override
	public void run() {
		while (true) {
			if (isStarted && !isPaused && !isCrushed()) {
				changeSnakeLocation();
			}
			try {
				Thread.sleep(snake.getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �P�ܳD����m�T���í��e
	 */
	public synchronized void changeSnakeLocation() {
		// �s�x�D�Y�ҫe���T��,���D�����ѧ�s�̾�
		int xPrevious = snake.getHead().getX();
		int yPrevious = snake.getHead().getY();

		// ��s�D�Y��m
		switch (snake.getDirection()) {
		case Snake.DIRECTION_UP:
			snake.getHead().setY(yPrevious - PER_UNIT_LENGTH);
			break;
		case Snake.DIRECTION_DOWN:
			snake.getHead().setY(yPrevious + PER_UNIT_LENGTH);
			break;
		case Snake.DIRECTION_LEFT:
			snake.getHead().setX(xPrevious - PER_UNIT_LENGTH);
			break;
		case Snake.DIRECTION_RIGHT:
			snake.getHead().setX(xPrevious + PER_UNIT_LENGTH);
			break;
		}
		// �ھڳD�Y�H���M�O�_�Y���I�ߧ�s�D����m
		if (isEncountered()) {
			score++;
			snake.getBody().addFirst(new Node(xPrevious, yPrevious));
		} else {
			snake.getBody().addFirst(new Node(xPrevious, yPrevious));
			snake.getBody().removeLast();
		}

		repaint();
		requestFocus();
	}

	/**
	 * �P�_�O�_�Y��J
	 * 
	 * @return
	 */
	public boolean isEncountered() {
		if (snake.getHead().equals(egg)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �P�_�O�_�C������
	 * 
	 * @return
	 */
	public boolean isCrushed() {
		// �P�_�O�_�I�����
		boolean isCrushByBorder = snake.getHead().getX() >= getWidth() / 2 + HALF_SIDE
							|| snake.getHead().getX() < getWidth() / 2 - HALF_SIDE
							|| snake.getHead().getY() >= getHeight() / 2 + HALF_SIDE
							|| snake.getHead().getY() < getHeight() / 2 - HALF_SIDE;

		if (isCrushByBorder) {
			information = 1;
			return true;
		}

		// �P�_�O�_�ۨ��I��
		boolean isCrushByItself = false;
		LinkedList<Node> body = snake.getBody();
		for (int i = 0; i < body.size(); i++) {
			if (snake.getHead().equals(body.get(i))) {
				isCrushByItself = true;
			}
		}

		if (isCrushByItself) {
			information = 2;
			return true;
		} else {
			return false;
		}
	}

	public static int getScore() {
		return score;
	}

	public static int getInformation() {
		return information;
	}
}
