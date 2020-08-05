package com.objects;

import com.behavior.Behavior;
import com.behavior.Tempo;

public class Creation implements Runnable, Behavior {

	/**
	 * ����Ҧb��X�y��
	 */
	protected int x;
	/**
	 * ����Ҧb��Y�y��
	 */
	protected int y;
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
	 * 
	 */
	private Tempo tempo;
	/**
	 * �ΨӧP�_����O�_�B��Y�Ӱʧ@���O�� �Y�ʧ@������,�h�L�k�����L�ʧ@
	 */
	protected boolean canSetStatue;

	public Creation() {
		super();

	}

	public Creation(Tempo tempo) {
		this.tempo = tempo;
	}

	public Creation(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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
		Creation other = (Creation) obj;
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
		}

	}

	/*
	 * ������D�ʧ@
	 */
	@Override
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
	@Override
	public void move(int direction) {
		canSetStatue = false;
		try {
			switch (direction) {
			case DIRECTION_UP:
				for (int i = 0; i < jumpCoefficient.length; i++) {
					setY(getY() - jumpCoefficient[i] - moveCoefficient[i]);
					setX(getX());
					Thread.sleep(30);
				}
				break;
			case DIRECTION_DOWN:
				for (int i = 0; i < jumpCoefficient.length; i++) {
					setY(getY() - jumpCoefficient[i] + moveCoefficient[i]);
					setX(getX());
					Thread.sleep(30);
				}
				break;
			case DIRECTION_LEFT:
				for (int i = 0; i < jumpCoefficient.length; i++) {
					setY(getY() - jumpCoefficient[i]);
					setX(getX() - moveCoefficient[i]);
					Thread.sleep(30);
				}
				break;
			case DIRECTION_RIGHT:
				for (int i = 0; i < jumpCoefficient.length; i++) {
					setY(getY() - jumpCoefficient[i]);
					setX(getX() + moveCoefficient[i]);
					Thread.sleep(30);
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
