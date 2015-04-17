package org.rinasim.main;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.rinasim.frame.Login;

/**
 * ������������
 * @since v1.0
 * @date 2015��3��14�� ����1:39:56
 */
public class Main {

	private static final Font FONT=new Font("΢���ź�", java.awt.Font.PLAIN, 14);

	/**
	 * ���������
	 */
	public static final char PASSWORD_OPERATOR='%';
	
	/**
	 * ������
	 * @date 2015��3��14�� ����1:42:41
	 * @since v1.0
	 */
	public static void main(String[] args) {
		
		//�趨������
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Enumeration<Object> elements=UIManager.getDefaults().keys();
			while(elements.hasMoreElements()){
				Object key = elements.nextElement();
				if(UIManager.get(key) instanceof Font){
					UIManager.put(key, FONT);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		//������½����
		Login frame=new Login();
		frame.setVisible(true);
		for(int i=1;i<=380;i++){
			if(i%2==0){
				frame.setSize(frame.getWidth(), i);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Graphics g=frame.getGraphics();
		frame.paintAll(g);
		g.dispose();
	}

}
