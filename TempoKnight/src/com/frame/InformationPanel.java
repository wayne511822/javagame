package com.frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class InformationPanel extends JPanel implements Runnable {

	public InformationPanel() {
		setBackground(Color.RED);
		//�]�m�e���j�p
		setPreferredSize(new Dimension(200, 800));    
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
