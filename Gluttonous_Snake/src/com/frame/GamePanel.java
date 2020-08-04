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
	 * 單位長度
	 */
	public static final int PER_UNIT_LENGTH = 20;
	/**
	 * 倍數
	 */
	public static final int MULTIPLE = 15;
	/**
	 * 遊戲邊框一半長度
	 */
	public static final int HALF_SIDE = MULTIPLE * PER_UNIT_LENGTH;
	/**
	 * 判斷是否初始化
	 */
	private boolean isFirstRun = true;
	/**
	 * 判斷是否開始
	 */
	private boolean isStarted = false;
	/**
	 * 判斷是否暫停
	 */
	private boolean isPaused = false;
	/**
	 * 遊戲分數
	 */
	private static int score = 0;
	/**
	 * 傳遞遊戲資訊
	 */
	private static int information = 0;

	private Snake snake = new Snake();
	private Node egg = new Node();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 獲取座標
		int xCenter = getWidth() / 2;
		int yCenter = getHeight() / 2;

		// 獲取隨機點的座標
		int xRandom, yRandom;

		if (isFirstRun) {
			isFirstRun = false;

			// 繪製遊戲邊框
			g.drawRect(xCenter - HALF_SIDE, yCenter - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);

			// 初始化蛇頭
			Node head = snake.getHead();
			head.setX(xCenter);
			head.setY(yCenter);
			g.setColor(Color.ORANGE);
			g.fillRect(head.getX(), head.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

			// 初始化蛋
			do {
				xRandom = xCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
				yRandom = yCenter - HALF_SIDE + ((int) (Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
			} while (xRandom == head.getX() && yRandom == head.getY());
			egg.setX(xRandom);
			egg.setY(yRandom);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(egg.getX(), egg.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

		} else {
			// 繪製遊戲邊框
			g.drawRect(xCenter - HALF_SIDE, yCenter - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);

			// 繪製蛇身
			g.setColor(Color.MAGENTA);
			LinkedList<Node> body = snake.getBody();
			for (int i = 0; i < body.size(); i++) {
				g.fillRect(body.get(i).getX(), body.get(i).getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			}

			// 繪製蛇頭
			Node head = snake.getHead();
			g.setColor(Color.ORANGE);
			g.fillRect(head.getX(), head.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);

			// 蛋被吃到則更新蛋
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

			// 遊戲結束,則繪製GAME OVER
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
		// 註冊鍵盤監聽
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
					if (isCrushed()) { // 如果遊戲結束, 重置數據並重新開始遊戲
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
	 * 感變蛇的位置訊息並重畫
	 */
	public synchronized void changeSnakeLocation() {
		// 存儲蛇頭籤前的訊息,為蛇身提供更新依據
		int xPrevious = snake.getHead().getX();
		int yPrevious = snake.getHead().getY();

		// 更新蛇頭位置
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
		// 根據蛇頭信息和是否吃到點心更新蛇身位置
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
	 * 判斷是否吃到蛋
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
	 * 判斷是否遊戲結束
	 * 
	 * @return
	 */
	public boolean isCrushed() {
		// 判斷是否碰到邊框
		boolean isCrushByBorder = snake.getHead().getX() >= getWidth() / 2 + HALF_SIDE
							|| snake.getHead().getX() < getWidth() / 2 - HALF_SIDE
							|| snake.getHead().getY() >= getHeight() / 2 + HALF_SIDE
							|| snake.getHead().getY() < getHeight() / 2 - HALF_SIDE;

		if (isCrushByBorder) {
			information = 1;
			return true;
		}

		// 判斷是否自身碰到
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
