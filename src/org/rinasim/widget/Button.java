package org.rinasim.widget;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

/**
 * 自定义按钮
 * @author 刘旭涛
 * @date 2015年3月17日 下午6:07:20
 * @since v1.0
 */
public class Button extends JButton{

	private static final long serialVersionUID = 2526753140915382574L;
	
	private boolean isWhite=false;
	
	/**
	 * 初始化按钮
	 * @date 2015年3月17日 下午6:07:35
	 * @since v1.0
	 * @param text
	 */
	public Button(String text) {
		super(text);
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
	}
	
	/**
	 * 初始化按钮
	 * @date 2015年3月17日 下午6:07:35
	 * @since v1.0
	 * @param text
	 */
	public Button(String text, boolean isWhite) {
		super(text);
		this.isWhite=isWhite;
		setForeground(new Color(46, 184, 230));
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(getModel().isPressed()){
			if(isWhite){
				g.setColor(new Color(221,221,221));
			}else{
				g.setColor(Color.WHITE);
			}
			g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
			setForeground(new Color(46, 184, 230));
		}else if(getModel().isRollover()){
			if(isWhite){
				setForeground(new Color(46, 184, 230));
				g.setColor(new Color(241,241,241));
			}else{
				setForeground(Color.WHITE);
				g.setColor(new Color(66, 204, 250));
			}
			g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
		}else{
			if(isWhite){
				setForeground(new Color(46, 184, 230));
				g.setColor(Color.WHITE);
			}else{
				setForeground(Color.WHITE);
				g.setColor(new Color(46, 184, 230));
			}
			g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
		}
		super.paintComponent(g);
	}

}
