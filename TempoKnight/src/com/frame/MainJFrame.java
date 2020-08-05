package com.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainJFrame extends JFrame {
	
	private static GamePanel gamePanel = new GamePanel();
	private InformationPanel infoPanel = new  InformationPanel();

	protected MainJFrame() {
		
		setLayout(new BorderLayout());
		add(gamePanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
		
		setSize(1200, 800);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFrame jFrame = new MainJFrame();
		new Thread(gamePanel).start();
		
	}
}
