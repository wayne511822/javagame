package com.behavior;

import java.awt.Color;

/**
 * 設置節拍頻率,符合節拍玩家的操作才能被執行
 * 
 * @author wayne
 *
 */
public class Tempo implements Runnable {

	private static int time = 0;
	private boolean actionAble = true;
	private static Tempo tempo = null;
	public Color color;

	private Tempo() {
		new Thread(this).start();
	}

	/**
	 * 設置節拍頻率,符合節拍玩家的操作才能被執行
	 */
	private void tempo() {
		time++;
		actionAble = !actionAble;
		if (actionAble) {
			color = Color.RED;
		} else {
			color = Color.GREEN;
		}

	}

	public static Tempo getInstance() {
		if (tempo == null) {
			return new Tempo();
			
		}
		return tempo;
	}

	public int getTime() {
		return time;
	}

	public boolean isActionAble() {
		return actionAble;
	}

	public void setActionAble(boolean actionAble) {
		this.actionAble = actionAble;
	}

	@Override
	public void run() {
		while (true) {
			tempo();
			try {

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
