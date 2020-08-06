package com.objects;


import com.behavior.Behavior;

public class Creation implements Behavior {

	/**
	 * 物件所在的X座標
	 */
	public int x;
	/**
	 * 物件所在的Y座標
	 */
	public int y;
	/**
	 * 物件所在的列數
	 */
	public int col;
	/**
	 * 物件所在的行數
	 */
	public int row;
	/**
	 * 現在狀態
	 */
	public int status = 0;
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
	public int direction = DIRECTION_DOWN;
	/**
	 * 用來判斷物件是否處於某個動作指令中 若動作未完成,則無法執行其他動作
	 */
	public boolean canSetStatue;
	/**
	 * 使否可向上移動
	 */
	public boolean canUp = true;
	/**
	 * 使否可向下移動
	 */
	public boolean canDown = true;
	/**
	 * 使否可向左移動
	 */
	public boolean canLeft = true;
	/**
	 * 使否可向右移動
	 */
	public boolean canRight = true;


	public Creation() {
		super();

	}

	public Creation(int x, int y, int col, int row) {
		super();
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		if (canSetStatue) {
			this.status = status;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (canDown ? 1231 : 1237);
		result = prime * result + (canLeft ? 1231 : 1237);
		result = prime * result + (canRight ? 1231 : 1237);
		result = prime * result + (canSetStatue ? 1231 : 1237);
		result = prime * result + (canUp ? 1231 : 1237);
		result = prime * result + col;
		result = prime * result + direction;
		result = prime * result + row;
		result = prime * result + status;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creation other = (Creation) obj;
		if (canDown != other.canDown)
			return false;
		if (canLeft != other.canLeft)
			return false;
		if (canRight != other.canRight)
			return false;
		if (canSetStatue != other.canSetStatue)
			return false;
		if (canUp != other.canUp)
			return false;
		if (col != other.col)
			return false;
		if (direction != other.direction)
			return false;
		if (row != other.row)
			return false;
		if (status != other.status)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
