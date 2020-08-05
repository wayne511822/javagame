package com.objects;

import java.util.ArrayList;
import java.util.List;

import com.behavior.Tempo;

public class Controller implements Runnable {
	/**
	 * �`�羹��H
	 */
	public Tempo tempo;
	
	/**
	 * �s�񨤦⪺�e��
	 */
	public List<Creation> creations = new ArrayList<>();
	
	public Controller() {
		super();

	}

	public Controller(Tempo tempo) {
		this.tempo = tempo;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
	/**
	 * �N�����H��J���X��
	 */
	public void addCreation(Creation c) {
		creations.add(c);
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
			for (Creation c : creations) {
				switch (c.status) {
				case Creation.STATUS_MOVE:
					move(c, c.direction);
					break;
				default:
					jump(c);
				}
				
			}
//			System.out.println(col + " : " + row);
//			System.out.println(canUp+":"+canDown+":"+canLeft+":"+canRight);
		}

	}

	/*
	 * ������D�ʧ@
	 */
	public void jump(Creation c) {
		c.canSetStatue = false;
		try {

			for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
				c.setY(c.y - Creation.jumpCoefficient[i]);
				c.setX(c.x);
				Thread.sleep(30);
			}
			c.canSetStatue = true;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ���󲾰ʰʧ@, �ǤJ���ʤ�V
	 */
	public void move(Creation c, int direction) {
		c.canSetStatue = false;
		try {
			switch (direction) {
			case Creation.DIRECTION_UP:
				if (c.canUp) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] - Creation.moveCoefficient[i]);
						Thread.sleep(30);
					}
					c.setRow(c.row - 1);
				}
				break;
			case Creation.DIRECTION_DOWN:
				if (c.canDown) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i] + Creation.moveCoefficient[i]);
						Thread.sleep(30);
					}
					c.setRow(c.row + 1);
				}
				break;
			case Creation.DIRECTION_LEFT:
				if (c.canLeft) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i]);
						c.setX(c.x - Creation.moveCoefficient[i]);
						Thread.sleep(30);
					}
					c.setCol(c.col - 1);
				}
				break;
			case Creation.DIRECTION_RIGHT:
				if (c.canRight) {
					for (int i = 0; i < Creation.jumpCoefficient.length; i++) {
						c.setY(c.y - Creation.jumpCoefficient[i]);
						c.setX(c.x + Creation.moveCoefficient[i]);
						Thread.sleep(30);
					}
					c.setCol(c.col + 1);
				}
				break;
			}
			c.canSetStatue = true;
			c.setStatus(Creation.STATUS_JUMP);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
