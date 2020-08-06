package com.objects;


import com.behavior.Behavior;

public class Creation implements Behavior {

	/**
	 * ����Ҧb��X�y��
	 */
	public int x;
	/**
	 * ����Ҧb��Y�y��
	 */
	public int y;
	/**
	 * ����Ҧb���C��
	 */
	public int col;
	/**
	 * ����Ҧb�����
	 */
	public int row;
	/**
	 * �{�b���A
	 */
	public int status = 0;
	/**
	 * �w�]���A,������D
	 */
	public static final int STATUS_JUMP = 0;
	/**
	 * ���ʪ��A
	 */
	public static final int STATUS_MOVE = 1;
	/**
	 * �V�W����
	 */
	public static final int DIRECTION_UP = 2;
	/**
	 * �V�U����
	 */
	public static final int DIRECTION_DOWN = 3;
	/**
	 * �V������
	 */
	public static final int DIRECTION_LEFT = 4;
	/**
	 * �V�k����
	 */
	public static final int DIRECTION_RIGHT = 5;
	/**
	 * �{�b����V
	 */
	public int direction = DIRECTION_DOWN;
	/**
	 * �ΨӧP�_����O�_�B��Y�Ӱʧ@���O�� �Y�ʧ@������,�h�L�k�����L�ʧ@
	 */
	public boolean canSetStatue;
	/**
	 * �ϧ_�i�V�W����
	 */
	public boolean canUp = true;
	/**
	 * �ϧ_�i�V�U����
	 */
	public boolean canDown = true;
	/**
	 * �ϧ_�i�V������
	 */
	public boolean canLeft = true;
	/**
	 * �ϧ_�i�V�k����
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
	 * �]�m��e���󪺥i�����欰
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
