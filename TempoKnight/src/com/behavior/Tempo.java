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
	public static Color color;
	
	private Tempo() {
		
	}
	
	/**
	 * 設置節拍頻率,符合節拍玩家的操作才能被執行
	 */
	private void tempo() {
		try {
			time++;
			actionAble = false;
			color = Color.GREEN;
			//System.out.println("temp---" + actionAble);
			Thread.sleep(600);
			actionAble = true;
			color  = Color.BLACK;
			//System.out.println("temp---" + actionAble);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Tempo getInstance() {
		if (tempo == null) {
			return new Tempo();
		}
		return tempo;
	}
	
	public String getTime() {
		return time + "";
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
		}
		
	}
	
}
