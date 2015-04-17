package org.rinasim.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * ��С������
 * 34*25
 * @author ������
 * @date 2015��3��17�� ����8:31:07
 * @since v1.0
 */
public class MinimizeButton extends JButton{

	private static final long serialVersionUID = 2526753140915382574L;
	
	/**
	 * ��ʼ��
	 * @date 2015��3��17�� ����8:31:29
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
