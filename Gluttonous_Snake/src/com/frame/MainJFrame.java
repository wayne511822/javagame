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
		mainJFrame.setTitle("JAVA�g�Y�D");
		mainJFrame.setSize(1100, 800);
		mainJFrame.setVisible(true);
		mainJFrame.setBackground(Color.red);
		mainJFrame.setResizable(false); //���i���ܵ����j�p
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setLocationRelativeTo(null); //�]�m�~������ܾ�
		
		new Thread(gamePanel).start();
		new Thread(infoPanel).start();
	}
}


