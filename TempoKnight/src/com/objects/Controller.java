package com.objects;

import com.behavior.Behavior;
import com.behavior.Tempo;

public class Controller implements Runnable, Behavior {

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
	protected int direction = DIRECTION_DOWN;
	/**
	 * �`�羹��H
	 */
	private Tempo tempo;
	/**
	 * �ΨӧP�_����O�_�B��Y�Ӱʧ@���O�� �Y�ʧ@������,�h�L�k�����L�ʧ@
	 */
	protected boolean canSetStatue;
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
	 * �]�m��e���󪺥i�����欰
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
	 * �̶ǤJ���O�եά�����k
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
	 * ������D�ʧ@
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
	 * ���󲾰ʰʧ@, �ǤJ���ʤ�V
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
