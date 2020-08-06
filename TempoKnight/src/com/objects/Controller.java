package com.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.behavior.Tempo;

public class Controller implements Runnable {
	/**
	 * �C����j�p
	 */
	protected static final int PER_UNIT_SIZE = 65;
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
