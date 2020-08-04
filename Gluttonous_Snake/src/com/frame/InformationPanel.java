package com.frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel implements Runnable {
	/**
	 * �Ыؤ@�ӫ������l�e��
	 */
	private Box box = Box.createVerticalBox();
	/**
	 * ������U�T��
	 */
	private JLabel[] help = new JLabel[5];
	/**
	 * ��ܤ���
	 */
	private JLabel score = new JLabel("���� : ");
	/**
	 * ��ܰT��
	 */
	private JLabel show = new JLabel();
	
	public InformationPanel() {
		//��l�ư}�C
		for (int i = 0; i < help.length; i++) {
			help[i] = new JLabel();
		}
		
		//�t�m�r��
		Font font1 = new Font("DialogInput", Font.BOLD, 20);
		Font font2 = new Font("DialogInput", Font.BOLD + Font.ITALIC, 25);
		
		score.setFont(font2);
		score.setForeground(Color.BLUE);
		show.setFont(font2);
		show.setForeground(Color.RED);
		//��l�ư}�C
		for (int i = 0; i < help.length; i++) {
			help[i] = new JLabel();
			help[i].setFont(font1);
		}
		help[0].setText("Enter�}�l�C��");
		help[1].setText("��V�䲾�ʳD");
		help[2].setText("�ť���Ȱ��C��");
		help[3].setText("����F1~F5�ո`�t��");
		help[4].setText("��Enter�i���s�}�l�C��");
		
		//�t�m�T�����O
		this.add(box);
		box.add(Box.createVerticalStrut(150)); //�w�d�ťժŶ�
		for (int i = 0; i < help.length; i++) {
			box.add(help[i]);
			box.add(Box.createVerticalStrut(10)); //�r��r����
		}
		box.add(Box.createVerticalStrut(90));
		box.add(score);
		box.add(Box.createVerticalStrut(150));
		box.add(show);
	}
	
	@Override
	public void run() {
		while (true) {
			String str1 = "���� : " + GamePanel.getScore();
			score.setText(str1);
			
			String str2 = null;
			switch (GamePanel.getInformation()) {
			case 0: break;
			case 1: 
				str2 = "��������F!";
				break;
			case 2:
				str2 = "�Y��ۤv�F!";
				break;
			}
			show.setText(str2);
		}
		
	}

}
