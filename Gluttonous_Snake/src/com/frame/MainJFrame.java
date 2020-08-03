package com.frame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class MainJFrame extends JFrame {

	private static GamePanel gamePanel = new GamePanel();
	private static InformationPanel infoPanel = new InformationPanel();
	
	public MainJFrame() {
		setLayout(new BorderLayout());
		add(gamePanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
		
	}
	
	public static void main(String[] args) {
		JFrame mainJFrame = new MainJFrame();
		mainJFrame.setTitle("JAVA貪吃蛇");
		mainJFrame.setSize(1100, 800);
		mainJFrame.setVisible(true);
		mainJFrame.setBackground(Color.red);
		mainJFrame.setResizable(false); //不可改變視窗大小
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setLocationRelativeTo(null); //設置居中於顯示器
		
		new Thread(gamePanel).start();
		new Thread(infoPanel).start();
	}
}


