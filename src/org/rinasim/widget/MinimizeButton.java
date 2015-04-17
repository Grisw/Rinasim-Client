package org.rinasim.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 最小化窗口
 * 34*25
 * @author 刘旭涛
 * @date 2015年3月17日 下午8:31:07
 * @since v1.0
 */
public class MinimizeButton extends JButton{

	private static final long serialVersionUID = 2526753140915382574L;
	
	/**
	 * 初始化
	 * @date 2015年3月17日 下午8:31:29
	 * @since v1.0
	 * @param parent
	 */
	public MinimizeButton(JFrame parent) {
		setIcon(new ImageIcon(".\\res\\minimize.png"));
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setRolloverIcon(new ImageIcon(".\\res\\minimize_focus.png"));
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.setExtendedState(JFrame.ICONIFIED);
			}
		});
	}

}
