package org.rinasim.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.rinasim.object.Message;

/**
 * 显示消息
 * @author 刘旭涛
 * @date 2015年4月14日 上午10:26:25
 * @since v1.0
 */
public class MessageAdapter extends JPanel {

	private static final long serialVersionUID = -1174573321069842905L;
	
	private Message msg;
	
	/**
	 * 构造消息
	 * @date 2015年4月14日 下午1:08:45
	 * @since v1.0
	 * @param msg
	 */
	public MessageAdapter(Message msg){
		setBackground(Color.WHITE);
		this.msg=msg;
		MessageBox l=new MessageBox();
		setLayout(null);
		JLabel t=new JLabel(msg.getTime());
		if(msg.getType()==Message.TO){
			t.setLocation(l.getWidth()>t.getPreferredSize().width?l.getWidth()-t.getPreferredSize().width:0, l.getHeight()+10);
			l.setLocation(l.getWidth()>t.getPreferredSize().width?0:t.getPreferredSize().width-l.getWidth(), 0);
		}else{
			t.setLocation(0, l.getHeight()+10);
			l.setLocation(0, 0);
		}
		t.setSize(t.getPreferredSize());
		setSize(l.getWidth()>t.getWidth()?l.getWidth():t.getWidth(), l.getHeight()+t.getHeight()+10);
		add(l);
		add(t);
	}
	
	/**
	 * 消息标签
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午1:08:56
	 * @since v1.0
	 */
	private class MessageBox extends JPanel{

		private static final long serialVersionUID = 4641422270168575453L;
		
		private JTextPane msgPane;
		
		/**
		 * 创建文字面板
		 */
		public MessageBox() {
			super(null);
			setOpaque(false);
			
			StyledDocument sd=msg.getMessage();
			MutableAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setAlignment(attr, 1);
			sd.setParagraphAttributes(0, sd.getLength(), attr, false);

			msgPane=new JTextPane(sd);
			msgPane.setEditable(false);
			msgPane.setBorder(null);
			msgPane.setOpaque(false);
			msgPane.setSize(msgPane.getPreferredSize().width+10, msgPane.getPreferredSize().height);
			if(msg.getType()==Message.TO){
				msgPane.setLocation(5, 5);
			}else{
				msgPane.setLocation(9, 5);
			}
			msgPane.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			add(msgPane);

			setSize(msgPane.getWidth()+14,msgPane.getHeight()+10);
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2d=(Graphics2D) g;
			g2d.setColor(new Color(172, 216, 230));
			if(msg.getType()==Message.TO){
				g2d.fillRoundRect(0, 0, (int) (msgPane.getWidth()+9), getHeight(), 8, 8);
				g2d.fillPolygon(new int[]{getWidth()-9,getWidth(),getWidth()-9}, new int[]{(getHeight()-2)/2-5,(getHeight()-2)/2,(getHeight()-2)/2+5}, 3);
			}else{
				g2d.fillRoundRect(9, 0, (int) (msgPane.getWidth()+9), getHeight(), 8, 8);
				g2d.fillPolygon(new int[]{9,1,9}, new int[]{(getHeight()-2)/2-5,(getHeight()-2)/2,(getHeight()-2)/2+5}, 3);
			}
			super.paint(g2d);
			g2d.dispose();
		}
	}
}
