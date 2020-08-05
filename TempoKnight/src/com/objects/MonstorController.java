package com.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonstorController extends Controller {

	/**
	 * �M�h�Ҧb��x�y��
	 */
	private int xKinght;
	/**
	 * �M�h�Ҧb��y�y��
	 */
	private int yKinght;

	/*
	 * �s��Ǫ������X
	 */
	// public List<Monster> monsters = new ArrayList<>();
	 public CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<Monster>();
	/**
	 * �`�羹��H
	 */

	public MonstorController() {
		super();
	}

	public void setxKinght(int xKinght) {
		this.xKinght = xKinght;
	}

	public void setyKinght(int yKinght) {
		this.yKinght = yKinght;
	}
	
	public void addMonster(Monster m) {
		monsters.add(m);
		if (!tempo.isActionAble()) {
		}
	}

	@Override
	public synchronized void action() {
		// ���a�����ʮɴ��ĤH���
		if (!tempo.isActionAble()) {
			// �M�����X,�Ǫ��̪��A���
			for (Monster m : monsters) {
				switch (m.status) {
				
				case Monster.STATUS_TRACK:
					track(m);
					break;
				default:
					
				}
			}
		}
	}

	/**
	 * �P�_�ĤH�ө����Ӥ�V��
	 */
	private void track(Creation m) {

		// �ͦ��H����,�Ψ��H����x��y��V����
		Random random = new Random();
		int xOry = random.nextInt(2);

		// �H�M�h�y�Ь����߷|���|�Ӥ��, �H�Φ��xy�b�W
		if (m.x > xKinght && m.y > yKinght) { // �b�M�h�k�U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // �V�U�ΦV����

		} else if (m.x < xKinght && m.y > yKinght) { // �b�M�h���U

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x < xKinght && m.y < yKinght) { // �b�M�h���W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // �V�U�ΦV�k��

		} else if (m.x > xKinght && m.y < yKinght) { // �b�M�h�k�W

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // �V�W�ΦV����

		} else if (m.x == xKinght) { // ��by�b�W

			m.direction = m.y > yKinght ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // �V�W�ΦV�U��

		} else if (m.y == yKinght) { // ��bx�b�W

			m.direction = m.x > xKinght ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // �V���ΦV�k��
		}
		move(m, m.direction);
	}
	
	@Override
	public void run() {
		while (true) {
			
			action();
		}
	}

}
