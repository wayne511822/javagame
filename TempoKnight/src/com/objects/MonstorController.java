package com.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.behavior.Behavior;
import com.behavior.Tempo;

public class MonstorController extends Creation implements Runnable, Behavior {

	/**
	 * 騎士所在的x座標
	 */
	private int xKinght;
	/**
	 * 騎士所在的y座標
	 */
	private int yKinght;
	/**
	 * 存放怪物的容器
	 */
	private static List<Monster> monsters = new ArrayList<>();
	/**
	 * 節拍器
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
		//玩家不能行動時換敵人行動
		if (!tempo.isActionAble()) {
			//遍歷集合,怪物依狀態行動
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
	 * 判斷敵人該往哪個方向走
	 */
	private void track(Monster m) {
		
		//生成隨機數,用來隨機往x或y方向移動
		Random random = new Random();
		int xOry = random.nextInt(2);
		
		 //以騎士座標為中心會有四個方位, 以及位於xy軸上
		if (m.x > xKinght && m.y > yKinght) { //在騎士右下
			
			m.direction = xOry == 0 ? DIRECTION_UP : DIRECTION_LEFT; //向下或向左走
			
		} else if (m.x < xKinght && m.y > yKinght) { //在騎士左下
			
			m.direction = xOry == 0 ? DIRECTION_UP : DIRECTION_RIGHT; //向下或向右走
			
		} else if (m.x < xKinght && m.y < yKinght) { //在騎士左上
			
			m.direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_RIGHT; //向下或向右走
			
		} else if (m.x > xKinght && m.y < yKinght) { //在騎士右上
			
			m.direction = xOry == 0 ? DIRECTION_DOWN : DIRECTION_LEFT; //向上或向左走
			
		} else if (m.x == xKinght) { //位在y軸上
			
			m.direction = m.y > yKinght ? DIRECTION_UP : DIRECTION_DOWN; //向上或向下走
			
		} else if (m.y == yKinght) { //位在x軸上
			
			m.direction = m.x > xKinght ? DIRECTION_LEFT : DIRECTION_RIGHT; //向左或向右走
		}
		move(m, m.direction);
	}
	
	/*
	 * 物件跳躍動作
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
	 * 物件移動動作, 傳入移動方向
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
