package com.objects;

import com.behavior.Tempo;

public class Monster {
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
	 * 追蹤騎士狀態
	 */
	public static final int STATUS_TRACK = 0;
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
	
	public Monster(int x, int y, int col, int row) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
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
}
