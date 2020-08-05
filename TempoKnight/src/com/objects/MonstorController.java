package com.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.behavior.Behavior;
import com.behavior.Tempo;

public class MonstorController extends Creation implements Runnable, Behavior {

	/**
	 * �M�h�Ҧb��x�y��
	 */
	private int xKinght;
	/**
	 * �M�h�Ҧb��y�y��
	 */
	private int yKinght;
	/**
	 * �s��Ǫ����e��
	 */
	private static List<Monster> monsters = new ArrayList<>();
	/**
	 * �`�羹
	 */
	private Tempo tempo;
	
	public MonstorController() {
		super();
	}
	
	public MonstorController(Tempo tempo) {
		this.tempo = tempo;
	}
	
	public void setxKinght(int xKinght) {
		this.xKinght = xKinght;
	}

	public void setyKinght(int yKinght) {
		this.yKinght = yKinght;
	}
	
	public void addMonster(Monster monster) {
		
	}

	@Override
	protected synchronized void action() {
		//���a�����ʮɴ��ĤH���
		if (!tempo.isActionAble()) {
			//�M�����X,�Ǫ��̪��A���
			for(Monster m : monsters) {
				switch (m.status) {
				case Monster.STATUS_TRACK:
					track(m);
					break;
				}
				
			}
		}
	}
	/**
	 * �P�_�ĤH�ө����Ӥ�V��
	 */
	private void track(Monster m) {
		
		//�ͦ��H����,�Ψ��H����x��y��V����
		Random random = new Random();
		int xOry = random.nextInt(2);
		
		 //�H�M�h�y�Ь����߷|���|�Ӥ��, �H�Φ��xy�b�W
		if (m.x > xKinght && m.y > yKinght) { //�b�M�h�k�U
			
			m.direction = xOry == 0 ? DIRECTION_UP : DIRECTION_LEFT; //�V�U�ΦV����
			
		} else if (m.x < xKinght && m.y > yKinght) { //�b�M�h���U
			
			m.direction = xOry == 0 ? DIRECTION_UP : DIRECTION_RIGHT; //�V�U�ΦV�k��
			
		} else if (m.x < xKinght && m.y < yKinght) { //�b�M�h���W
			
			m.direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_RIGHT; //�V�U�ΦV�k��
			
		} else if (m.x > xKinght && m.y < yKinght) { //�b�M�h�k�W
			
			m.direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_LEFT; //�V�W�ΦV����
			
		} else if (m.x == xKinght) { //��by�b�W
			
			m.direction = m.y > yKinght ? DIRECTION_UP : DIRECTION_DOWN; //�V�W�ΦV�U��
			
		} else if (m.y == yKinght) { //��bx�b�W
			
			m.direction = m.x > xKinght ? DIRECTION_LEFT : DIRECTION_RIGHT; //�V���ΦV�k��
		}
		move(m, m.direction);
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
	public void move(Monster m, int direction) {
		canSetStatue = false;
		try {
			switch (direction) {
			case DIRECTION_UP:
				if (canUp) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(m.y - jumpCoefficient[i] - moveCoefficient[i]);
						Thread.sleep(30);
					}
					setRow(row - 1);
				}
				break;
			case DIRECTION_DOWN:
				if (canDown) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(m.y - jumpCoefficient[i] + moveCoefficient[i]);
						Thread.sleep(30);
					}
					setRow(row + 1);
				}
				break;
			case DIRECTION_LEFT:
				if (canLeft) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(m.y - jumpCoefficient[i]);
						setX(m.x - moveCoefficient[i]);
						Thread.sleep(30);
					}
					setCol(col - 1);
				}
				break;
			case DIRECTION_RIGHT:
				if (canRight) {
					for (int i = 0; i < jumpCoefficient.length; i++) {
						setY(m.y - jumpCoefficient[i]);
						setX(m.x + moveCoefficient[i]);
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
