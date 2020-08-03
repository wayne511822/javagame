package com.demo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * �L�ϥ����w�� �ʵe�{�{
 * 
 * @author wayne
 *
 */
public class NoDoubleBuffer extends Frame//�D���~��Frame��
{
	public paintThread pT;//ø�ϰ����
	public int ypos=-80; //�p�ꥪ�W�����a�y��
	
	public NoDoubleBuffer()//�غc�禡
	{
		pT=new paintThread(this);
		this.setResizable(false);
		this.setSize(300,300); //�]�w����������j�p
		this.setVisible(true); //��ܵ���
		pT.start();//ø�ϰ�����Ұ�
	}
	
	public void paint(Graphics scr) //�L��ø�Ϩ禡
	{
		scr.setColor(Color.RED);//�]�w�p���C��
		scr.fillOval(90,ypos,80,80); //ø�s�p��
	}
	
	public static void main(String[] args)
	{
		NoDoubleBuffer DB=new NoDoubleBuffer();//�إߥD��������
		DB.addWindowListener(new WindowAdapter()//�s�W���������B�z�禡
				{
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
	}
}
class paintThread extends Thread//ø�ϰ������
{
	NoDoubleBuffer DB;
	public paintThread(NoDoubleBuffer DB) //�غc�禡
	{
		this.DB=DB;
	}
	public void run()//�L��run()�禡
	{
		while(true)//����������L���j��
		{
			try{
				sleep(30); //�������v30ms
			}catch(InterruptedException e){}
			DB.ypos+=5; //�ק�p�ꥪ�W�����a�y��
			if(DB.ypos>300) //�p�����}�����᭫�]���W�����a�y��
				DB.ypos=-80;
			DB.repaint();//������ø
		}
	}
}