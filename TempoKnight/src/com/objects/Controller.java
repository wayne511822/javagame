package com.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.behavior.Tempo;

public class Controller implements Runnable {
	/**
	 * 每格方格大小
	 */
	protected static final int PER_UNIT_SIZE = 65;
	/**
	 * 節拍器對象
	 */
	public Tempo tempo;
	
	/**
	 * 存放角色的容器
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
	 * 將角色對象放入集合中
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
	 * 依傳入指令調用相應方法
	 */
	public synchronized void action() {
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

}
