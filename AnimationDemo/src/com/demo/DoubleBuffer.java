package com.demo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * �����w�� ��ְʵe�{�{
 * 
 * @author wayne
 *
 */
public class DoubleBuffer extends Frame// �D���~��Frame��
{
	public paintThread1 pT;// ø�ϰ����
	public int ypos = -80; // �p�ꥪ�W�����a�y��

	private Image iBuffer;
	private Graphics gBuffer;

	public DoubleBuffer()// �غc�禡
	{
		pT = new paintThread1(this);
		this.setResizable(false);
		this.setSize(300, 300); // �]�w����������j�p
		this.setVisible(true); // ��ܵ���
		pT.start();// ø�ϰ�����Ұ�
	}

	public void paint(Graphics scr) // �L��ø�Ϩ禡
	{
		if (iBuffer == null) {
			iBuffer = createImage(this.getSize().width, this.getSize().height);
			gBuffer = iBuffer.getGraphics();
		}
		gBuffer.setColor(getBackground());
		gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		gBuffer.setColor(Color.RED);
		gBuffer.fillOval(90, ypos, 80, 80);
		scr.drawImage(iBuffer, 0, 0, this);
	}

	public void update(Graphics scr) {
		paint(scr);
	}

	public static void main(String[] args) {
		DoubleBuffer DB = new DoubleBuffer();// �إߥD��������
		DB.addWindowListener(new WindowAdapter()// �s�W���������B�z�禡
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

class paintThread1 extends Thread// ø�ϰ������
{
	DoubleBuffer DB;

	public paintThread1(DoubleBuffer DB) // �غc�禡
	{
		this.DB = DB;
	}

	public void run()// �L��run()�禡
	{
		while (true)// ����������L���j��
		{
			try {
				sleep(30); // �������v30ms
			} catch (InterruptedException e) {
			}
			DB.ypos += 5; // �ק�p�ꥪ�W�����a�y��
			if (DB.ypos > 300) // �p�����}�����᭫�]���W�����a�y��
				DB.ypos = -80;
			DB.repaint();// ������ø
		}
	}

}