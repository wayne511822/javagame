package com.objects;

import com.behavior.Behavior;
import com.behavior.Tempo;

public class Controller implements Runnable, Behavior {

	/**
	 * 物件所在的X座標
	 */
	protected int x;
	/**
	 * 物件所在的Y座標
	 */
	protected int y;
	/**
	 * 物件所在的列數
	 */
	protected int col;
	/**
	 * 物件所在的行數
	 */
	protected int row;
	/**
	 * 現在狀態
	 */
	protected int status = 0;
	/**
	 * 預設狀態,物件跳躍
	 */
	public static final int STATUS_JUMP = 0;
	/**
	 * 移動狀態
	 */
	public static final int STATUS_MOVE = 1;
	/**
	 * 向上移動
	 */
	public static final int DIRECTION_UP = 2;
	/**
	 * 向下移動
	 */
	public static final int DIRECTION_DOWN = 3;
	/**
	 * 向左移動
	 */
	public static final int DIRECTION_LEFT = 4;
	/**
	 * 向右移動
	 */
	public static final int DIRECTION_RIGHT = 5;
	/**
	 * 現在的方向
	 */
	protected int direction = DIRECTION_DOWN;
	/**
	 * 節拍器對象
	 */
	private Tempo tempo;
	/**
	 * 用來判斷物件是否處於某個動作指令中 若動作未完成,則無法執行其他動作
	 */
	protected boolean canSetStatue;
	/**
	 * 使否可向上移動
	 */
	private boolean canUp = true;
	/**
	 * 使否可向下移動
	 */
	private boolean canDown = true;
	/**
	 * 使否可向左移動
	 */
	private boolean canLeft = true;
	/**
	 * 使否可向右移動
	 */
	private boolean canRight = true;


	public Controller() {
		super();

	}

	public Controller(Tempo tempo) {
		this.tempo = tempo;
	}

	public Controller(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public Controller(int x, int y, int col, int row, Tempo tempo) {
		super();
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
		this.tempo = tempo;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	/**
	 * 設置當前物件的可做的行為
	 */
	public void setAbility(boolean canUp, boolean canDown, boolean canLeft, boolean canRight) {
		this.canUp = canUp;
		this.canDown = canDown;
		this.canLeft = canLeft;
		this.canRight = canRight;
	}
	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		if (canSetStatue) {
			this.status = status;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Controller other = (Controller) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public void run() {
		while (true) {
			action();
		}
	}

	/**
	 * 依傳入指令調用相應方法
	 */
	protected synchronized void action() {
		if (tempo.isActionAble()) {
			switch (status) {
			case STATUS_MOVE:
				move(direction);
				break;
			default:
				jump();
			}
//			System.out.println(col + " : " + row);
//			System.out.println(canUp+":"+canDown+":"+canLeft+":"+canRight);
		}

	}

	/*
	 * 物件跳躍動作
	 */
	public void jump() {
		canSetStatue = false;
		try {

			for (int i = 0; i < jumpCoefficient.length; i++) {
				setY(getY() - jumpCoefficient[i]);
				setX(getX());
				Thread.sleep(30);
			}
			canSetStatue = true;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 物件移動動作, 傳入移動方向
	 */
	public void move(int direction) {
		canSetStatue = false;
		try {
			switch (direction) {
			case DIRECTION_UP:
				if (canUp) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(y - jumpCoefficient[i] - moveCoefficient[i]);
						Thread.sleep(30);
					}
					setRow(row - 1);
				}
				break;
			case DIRECTION_DOWN:
				if (canDown) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(y - jumpCoefficient[i] + moveCoefficient[i]);
						Thread.sleep(30);
					}
					setRow(row + 1);
				}
				break;
			case DIRECTION_LEFT:
				if (canLeft) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(y - jumpCoefficient[i]);
						setX(x - moveCoefficient[i]);
						Thread.sleep(30);
					}
					setCol(col - 1);
				}
				break;
			case DIRECTION_RIGHT:
				if (canRight) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(y - jumpCoefficient[i]);
						setX(x + moveCoefficient[i]);
						Thread.sleep(30);
					}
					setCol(col + 1);
				}
				break;
			}
			canSetStatue = true;
			setStatus(STATUS_JUMP);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
