package com.objects;

import java.util.Random;

import com.behavior.Tempo;

public class Skeleton extends Creation {

	/**
	 * 騎士所在的x座標
	 */
	private int xKinght;
	/**
	 * 騎士所在的y座標
	 */
	private int yKinght;
	/**
	 * 追蹤騎士狀態
	 */
	public static final int STATUS_TRACK = 6;
	/**
	 * 節拍器
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
		//玩家不能行動時換敵人行動
		if (!tempo.isActionAble()) {
			switch (status) {
			case STATUS_TRACK:
				track();
				break;
			}
			
		}
	}
	/**
	 * 判斷敵人該往哪個方向走
	 */
	private void track() {
		
		//生成隨機數,用來隨機往x或y方向移動
		Random random = new Random();
		int xOry = random.nextInt(2);
		
		 //以騎士座標為中心會有四個象限, 以及位於xy軸上
		if (x > xKinght && y > yKinght) { //第一象限
			
			direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_LEFT; //向下或向左走
			
		} else if (x < xKinght && y > yKinght) { //第二象限
			
			direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_RIGHT; //向下或向右走
			
		} else if (x < xKinght && y > yKinght) { //第三象限
			
			direction = xOry == 0 ? DIRECTION_UP : DIRECTION_RIGHT; //向下或向右走
			
		} else if (x < xKinght && y > yKinght) { //第四象限
			
			direction = xOry == 0 ? DIRECTION_UP : DIRECTION_LEFT; //向上或向左走
			
		} else if (x == xKinght) { //位在y軸上
			
			direction = y > yKinght ? DIRECTION_UP : DIRECTION_DOWN; //向上或向下走
			
		} else if (y == yKinght) { //位在x軸上
			
			direction = x > xKinght ? DIRECTION_LEFT : DIRECTION_RIGHT; //向左或向右走
		}
		move(direction);
	}
}
