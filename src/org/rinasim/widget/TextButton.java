package org.rinasim.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * �ı���ť
 * һ�����ָ�15.5����15.5������ȡ����
 * @author ������
 * @date 2015��3��17�� ����6:45:27
 * @since v1.0
 */
public class TextButton extends JButton{

	private static final long serialVersionUID = 2526753140915382574L;
	
	/**
	 * ���찴ť
	 * @date 2015��3��17�� ����6:47:10
	 * @since v1.0
	 * @param text
	 */
	public TextButton(String text) {
		super(text);
		setForeground(new Color(46, 184, 230));
		setFont(new Font("΢���ź�", Font.PLAIN, 14));
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setMargin(new Insets(0,0,0,0));
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(new Color(66, 204, 250));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(new Color(46, 184, 230));
			}
		});
	}

}
