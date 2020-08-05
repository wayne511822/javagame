package com.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonstorController extends Controller {

	/**
	 * 騎士所在的x座標
	 */
	private int xKinght;
	/**
	 * 騎士所在的y座標
	 */
	private int yKinght;

	/*
	 * 存放怪物的集合
	 */
	// public List<Monster> monsters = new ArrayList<>();
	 public CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<Monster>();
	/**
	 * 節拍器對象
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
		// 玩家不能行動時換敵人行動
		if (!tempo.isActionAble()) {
			// 遍歷集合,怪物依狀態行動
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
	 * 判斷敵人該往哪個方向走
	 */
	private void track(Creation m) {

		// 生成隨機數,用來隨機往x或y方向移動
		Random random = new Random();
		int xOry = random.nextInt(2);

		// 以騎士座標為中心會有四個方位, 以及位於xy軸上
		if (m.x > xKinght && m.y > yKinght) { // 在騎士右下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_LEFT; // 向下或向左走

		} else if (m.x < xKinght && m.y > yKinght) { // 在騎士左下

			m.direction = xOry == 0 ? Creation.DIRECTION_UP : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x < xKinght && m.y < yKinght) { // 在騎士左上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_RIGHT; // 向下或向右走

		} else if (m.x > xKinght && m.y < yKinght) { // 在騎士右上

			m.direction = xOry == 0 ? Creation.DIRECTION_DOWN : Creation.DIRECTION_LEFT; // 向上或向左走

		} else if (m.x == xKinght) { // 位在y軸上

			m.direction = m.y > yKinght ? Creation.DIRECTION_UP : Creation.DIRECTION_DOWN; // 向上或向下走

		} else if (m.y == yKinght) { // 位在x軸上

			m.direction = m.x > xKinght ? Creation.DIRECTION_LEFT : Creation.DIRECTION_RIGHT; // 向左或向右走
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
