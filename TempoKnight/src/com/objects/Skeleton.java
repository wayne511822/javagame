package com.objects;

import java.util.Random;

import com.behavior.Tempo;

public class Skeleton extends Creation {

	/**
	 * �M�h�Ҧb��x�y��
	 */
	private int xKinght;
	/**
	 * �M�h�Ҧb��y�y��
	 */
	private int yKinght;
	/**
	 * �l���M�h���A
	 */
	public static final int STATUS_TRACK = 6;
	/**
	 * �`�羹
	 */
	private Tempo tempo;
	
	public Skeleton() {
		super();
	}
	
	public Skeleton(Tempo tempo) {
		this.tempo = tempo;
	}

	public void setxKinght(int xKinght) {
		this.xKinght = xKinght;
	}

	public void setyKinght(int yKinght) {
		this.yKinght = yKinght;
	}

	@Override
	protected synchronized void action() {
		//���a�����ʮɴ��ĤH���
		if (!tempo.isActionAble()) {
			switch (status) {
			case STATUS_TRACK:
				track();
				break;
			}
			
		}
	}
	/**
	 * �P�_�ĤH�ө����Ӥ�V��
	 */
	private void track() {
		
		//�ͦ��H����,�Ψ��H����x��y��V����
		Random random = new Random();
		int xOry = random.nextInt(2);
		
		 //�H�M�h�y�Ь����߷|���|�ӶH��, �H�Φ��xy�b�W
		if (x > xKinght && y > yKinght) { //�Ĥ@�H��
			
			direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_LEFT; //�V�U�ΦV����
			
		} else if (x < xKinght && y > yKinght) { //�ĤG�H��
			
			direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_RIGHT; //�V�U�ΦV�k��
			
		} else if (x < xKinght && y > yKinght) { //�ĤT�H��
			
			direction = xOry == 0 ? DIRECTION_UP : DIRECTION_RIGHT; //�V�U�ΦV�k��
			
		} else if (x < xKinght && y > yKinght) { //�ĥ|�H��
			
			direction = xOry == 0 ? DIRECTION_UP : DIRECTION_LEFT; //�V�W�ΦV����
			
		} else if (x == xKinght) { //��by�b�W
			
			direction = y > yKinght ? DIRECTION_UP : DIRECTION_DOWN; //�V�W�ΦV�U��
			
		} else if (y == yKinght) { //��bx�b�W
			
			direction = x > xKinght ? DIRECTION_LEFT : DIRECTION_RIGHT; //�V���ΦV�k��
		}
		move(direction);
	}
}
