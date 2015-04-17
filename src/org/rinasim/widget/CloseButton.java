package org.rinasim.widget;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * �رհ�ť
 * 58*25
 * @author ������
 * @date 2015��3��17�� ����8:19:52
 * @since v1.0
 */
public class CloseButton extends JButton{

	private static final long serialVersionUID = 2526753140915382574L;
	
	private Window frame;
	private ActionListener current;
	
	/**
	 * ��ʼ��
	 * @date 2015��3��17�� ����8:20:12
	 * @since v1.0
	 */
	public CloseButton(Window frame) {
		this.frame=frame;
		setIcon(new ImageIcon(".\\res\\close.png"));
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setRolloverIcon(new ImageIcon(".\\res\\close_focus.png"));
		addActionListener(current=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=frame.getHeight();i>=0;i--){
					frame.setSize(frame.getWidth(), i);
				}
				frame.dispose();
			}
		});
	}
	
	/**
	 * �趨�رղ���
	 * @author ������
	 * @date 2015��3��18�� ����10:10:53
	 * @since v1.0
	 * @param listener
	 */
	public void setActionListener(ActionListener listener){
		removeActionListener(current);
		addActionListener(current=listener);
	}
	
	/**
	 * �ָ�Ĭ�Ϲرղ���
	 * @author ������
	 * @date 2015��3��18�� ����10:11:03
	 * @since v1.0
	 */
	public void setDefaultAction(){
		removeActionListener(current);
		addActionListener(current=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=frame.getHeight();i>=0;i--){
					frame.setSize(frame.getWidth(), i);
				}
				frame.dispose();
			}
		});
	}
}
