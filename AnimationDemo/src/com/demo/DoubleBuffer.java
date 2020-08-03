package com.demo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 用雙緩衝 減少動畫閃爍
 * 
 * @author wayne
 *
 */
public class DoubleBuffer extends Frame// 主類繼承Frame類
{
	public paintThread1 pT;// 繪圖執行緒
	public int ypos = -80; // 小圓左上角的縱座標

	private Image iBuffer;
	private Graphics gBuffer;

	public DoubleBuffer()// 建構函式
	{
		pT = new paintThread1(this);
		this.setResizable(false);
		this.setSize(300, 300); // 設定視窗的首選大小
		this.setVisible(true); // 顯示視窗
		pT.start();// 繪圖執行緒啟動
	}

	public void paint(Graphics scr) // 過載繪圖函式
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
		DoubleBuffer DB = new DoubleBuffer();// 建立主類的物件
		DB.addWindowListener(new WindowAdapter()// 新增視窗關閉處理函式
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

class paintThread1 extends Thread// 繪圖執行緒類
{
	DoubleBuffer DB;

	public paintThread1(DoubleBuffer DB) // 建構函式
	{
		this.DB = DB;
	}

	public void run()// 過載run()函式
	{
		while (true)// 執行緒中的無限迴圈
		{
			try {
				sleep(30); // 執行緒休眠30ms
			} catch (InterruptedException e) {
			}
			DB.ypos += 5; // 修改小圓左上角的縱座標
			if (DB.ypos > 300) // 小圓離開視窗後重設左上角的縱座標
				DB.ypos = -80;
			DB.repaint();// 視窗重繪
		}
	}

}