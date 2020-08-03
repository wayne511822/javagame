package com.demo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * �����w�� ��ְʵe�{�{ �覡�G
 * 
 * @author wayne
 *
 */
public class DoubleBuffer2 extends Frame// �D���~��Frame��
{
	public paintThread2 pT;// ø�ϰ����
	public int ypos = -80; // �p�ꥪ�W�����a�y��

	private Image iBuffer;
	private Graphics gBuffer;

	public DoubleBuffer2()// �غc�禡
	{
		pT = new paintThread2(this);
		this.setResizable(false);
		this.setSize(300, 300); // �]�w����������j�p
		this.setVisible(true); // ��ܵ���
		pT.start();// ø�ϰ�����Ұ�
	}

	// �L��paint(Graphics scr)�禡�G
	public void paint(Graphics scr) {
		scr.setColor(Color.RED);
		scr.fillOval(90, ypos, 80, 80);
	}

	// �L��update(Graphics scr)�禡�G
	@Override
	public void update(Graphics scr) {
		if (iBuffer == null) {
			iBuffer = createImage(this.getSize().width, this.getSize().height);
			gBuffer = iBuffer.getGraphics();
		}
		gBuffer.setColor(getBackground());
		gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(gBuffer);
		scr.drawImage(iBuffer, 0, 0, this);
	}

	public static void main(String[] args) {
		DoubleBuffer2 DB = new DoubleBuffer2();// �إߥD��������
		DB.addWindowListener(new WindowAdapter()// �s�W���������B�z�禡
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

class paintThread2 extends Thread// ø�ϰ������
{
	DoubleBuffer2 DB;

	public paintThread2(DoubleBuffer2 DB) // �غc�禡
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