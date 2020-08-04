package com.frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel implements Runnable {
	/**
	 * 創建一個垂直盒子容器
	 */
	private Box box = Box.createVerticalBox();
	/**
	 * 顯示幫助訊息
	 */
	private JLabel[] help = new JLabel[5];
	/**
	 * 顯示分數
	 */
	private JLabel score = new JLabel("分數 : ");
	/**
	 * 顯示訊息
	 */
	private JLabel show = new JLabel();
	
	public InformationPanel() {
		//初始化陣列
		for (int i = 0; i < help.length; i++) {
			help[i] = new JLabel();
		}
		
		//配置字體
		Font font1 = new Font("DialogInput", Font.BOLD, 20);
		Font font2 = new Font("DialogInput", Font.BOLD + Font.ITALIC, 25);
		
		score.setFont(font2);
		score.setForeground(Color.BLUE);
		show.setFont(font2);
		show.setForeground(Color.RED);
		//初始化陣列
		for (int i = 0; i < help.length; i++) {
			help[i] = new JLabel();
			help[i].setFont(font1);
		}
		help[0].setText("Enter開始遊戲");
		help[1].setText("方向鍵移動蛇");
		help[2].setText("空白鍵暫停遊戲");
		help[3].setText("按鍵F1~F5調節速度");
		help[4].setText("按Enter可重新開始遊戲");
		
		//配置訊息面板
		this.add(box);
		box.add(Box.createVerticalStrut(150)); //預留空白空間
		for (int i = 0; i < help.length; i++) {
			box.add(help[i]);
			box.add(Box.createVerticalStrut(10)); //字跟字間格
		}
		box.add(Box.createVerticalStrut(90));
		box.add(score);
		box.add(Box.createVerticalStrut(150));
		box.add(show);
	}
	
	@Override
	public void run() {
		while (true) {
			String str1 = "分數 : " + GamePanel.getScore();
			score.setText(str1);
			
			String str2 = null;
			switch (GamePanel.getInformation()) {
			case 0: break;
			case 1: 
				str2 = "撞到牆壁了!";
				break;
			case 2:
				str2 = "吃到自己了!";
				break;
			}
			show.setText(str2);
		}
		
	}

}
