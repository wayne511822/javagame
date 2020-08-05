package com.objects;

import com.behavior.Tempo;

public class Monster {
	/**
	 * ����Ҧb��X�y��
	 */
	protected int x;
	/**
	 * ����Ҧb��Y�y��
	 */
	protected int y;
	/**
	 * ����Ҧb���C��
	 */
	protected int col;
	/**
	 * ����Ҧb�����
	 */
	protected int row;
	/**
	 * �{�b���A
	 */
	protected int status = 0;
	/**
	 * �l���M�h���A
	 */
	public static final int STATUS_TRACK = 0;
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
	protected int direction = DIRECTION_DOWN;
	/**
	 * �ϧ_�i�V�W����
	 */
	private boolean canUp = true;
	/**
	 * �ϧ_�i�V�U����
	 */
	private boolean canDown = true;
	/**
	 * �ϧ_�i�V������
	 */
	private boolean canLeft = true;
	/**
	 * �ϧ_�i�V�k����
	 */
	private boolean canRight = true;
	
	public Monster(int x, int y, int col, int row) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
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
}
