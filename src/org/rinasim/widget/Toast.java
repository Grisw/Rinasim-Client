package org.rinasim.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

/**
 * 消息提示
 * @author 刘旭涛
 * @date 2015年3月19日 下午12:46:35
 * @since v1.0
 */
public class Toast extends JLabel{

	private static final long serialVersionUID = -3473125185955124377L;
	
	/**
	 * 初始化Toast
	 * @date 2015年3月19日 下午12:47:17
	 * @since v1.0
	 * @param icon
	 * @param text
	 */
	public Toast(Icon icon, String text){
		super(icon);
		setText(text);
		setVerticalAlignment(SwingConstants.TOP);
		if(icon!=null){
			setSize((int) Math.ceil(text.length()*15.5+icon.getIconWidth()), icon.getIconHeight()+12);
		}else{
			setSize((int) Math.ceil(text.length()*15.5), 16+12);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setColor(new Color(46, 184, 230));
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-9, 8, 8);
		g2d.drawLine(10, getHeight()-9, 15, getHeight()-1);
		g2d.drawLine(15, getHeight()-1, 20, getHeight()-9);
		g2d.setColor(Color.WHITE);
		g2d.drawLine(11, getHeight()-9, 19, getHeight()-9);
		g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-9, 8, 8);
		g2d.fillPolygon(new int[]{10,15,20}, new int[]{getHeight()-9,getHeight()-1,getHeight()-9}, 3);
		super.paintComponent(g2d);
		g2d.dispose();
	}
	
	/**
	 * 显示Toast
	 * @author 刘旭涛
	 * @date 2015年3月19日 下午1:15:58
	 * @since v1.0
	 * @param parent 父容器
	 * @param where 显示位置，将在其上方显示
	 * @param length 显示时间，0为不消失
	 */
	public void show(JLayeredPane parent,Component where, long length){
		this.setLocation(where.getX(), where.getY()-getHeight());
		parent.setLayer(this, parent.highestLayer()+1);
		parent.add(this);
		if(length!=0){
			new Thread(){

				@Override
				public void run() {
					try {
						Thread.sleep(length);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Toast.this.setVisible(false);
				}
			}.start();
		}
	}

}
