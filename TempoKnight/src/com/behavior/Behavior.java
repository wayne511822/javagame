package com.behavior;

public interface Behavior {
	/**
	 * �w�q������D��Y�b�첾�q
	 */
	int[] jumpCoefficient = {2,6,10,10,7,7,5,3,1,1,-1,-1,-3,-5,-7,-7,-10,-10,-6,-2};
	int[] moveCoefficient = {4,4,4,4,4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};
	
	public void jump();
	public void move(int direction);
}
