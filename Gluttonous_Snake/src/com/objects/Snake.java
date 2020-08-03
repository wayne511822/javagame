package com.objects;

import java.util.LinkedList;

public class Snake {

	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;
	public static final int SPEED_1 = 300;
	public static final int SPEED_2 = 200;
	public static final int SPEED_3 = 150;
	public static final int SPEED_4 = 100;
	public static final int SPEED_5 = 30;
	
	private int direction = DIRECTION_RIGHT;
	private int speed = SPEED_3;
	private Node head = new Node();
	private LinkedList<Node> body = new LinkedList<>();
	
	public Snake() {
	}

	public Node getHead() {
		return head;
	}
	
	public LinkedList<Node> getBody() {
		return body;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setBody(LinkedList<Node> body) {
		this.body = body;
	}
	
}
