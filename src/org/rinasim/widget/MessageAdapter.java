package org.rinasim.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.rinasim.network.Client;
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
	private MessageBox l;
	private JLabel t;
	
	/**
	 * 构造消息
	 * @date 2015年4月14日 下午1:08:45
	 * @since v1.0
	 * @param msg
	 */
	public MessageAdapter(Message msg){
		setBackground(Color.WHITE);
		this.msg=msg;
		l=new MessageBox();
		setLayout(null);
		t=new JLabel(msg.getTime());
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
	 * 设置进度条
	 * @author 刘旭涛
	 * @date 2015年4月21日 上午10:56:27
	 * @since v1.0
	 * @param isShow
	 */
	public void showProgressBar(boolean isShow){
		if(isShow){
			l.pro.setValue(0);
			l.pro.setString(null);
			l.download.setVisible(false);
			l.pro.setVisible(true);
			l.panel.setSize(Math.max(Math.max(l.name.getPreferredSize().width, l.length.getPreferredSize().width), l.pro.getPreferredSize().width)+10, l.pro.getY()+l.pro.getPreferredSize().height);
		}else{
			l.download.setVisible(true);
			l.pro.setVisible(false);
			l.panel.setSize(Math.max(Math.max(l.name.getPreferredSize().width, l.length.getPreferredSize().width), l.download.getPreferredSize().width)+10, l.download.getY()+l.download.getPreferredSize().height);
		}
		l.setSize(l.panel.getWidth()+14,l.panel.getHeight()+10);
		setSize(l.getWidth()>t.getWidth()?l.getWidth():t.getWidth(), l.getHeight()+t.getHeight()+10);
		invalidate();
	}
	
	/**
	 * 获取进度条对象
	 * @author 刘旭涛
	 * @date 2015年4月21日 上午10:57:03
	 * @since v1.0
	 * @return
	 */
	public JProgressBar getProgressBar(){
		return l.pro;
	}
	
	/**
	 * 消息标签
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午1:08:56
	 * @since v1.0
	 */
	private class MessageBox extends JPanel{

		private static final long serialVersionUID = 4641422270168575453L;
		
		private JPanel panel;
		private Button download;
		private JProgressBar pro;
		private JLabel name;
		private JLabel length;
		
		/**
		 * 创建文字面板
		 */
		public MessageBox() {
			super(null);
			setOpaque(false);
			
			if(!msg.isFile()){
				StyledDocument sd=msg.getMessage();
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setAlignment(attr, 1);
				sd.setParagraphAttributes(0, sd.getLength(), attr, false);
				
				JTextPane msgPane=new JTextPane(sd);
				msgPane.setEditable(false);
				msgPane.setBorder(null);
				msgPane.setOpaque(false);
				msgPane.setSize(msgPane.getPreferredSize().width+10, msgPane.getPreferredSize().height);
				msgPane.setLocation(0, 0);
				msgPane.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				
				panel=new JPanel(null);
				panel.setOpaque(false);
				if(msg.getType()==Message.TO){
					panel.setLocation(5, 5);
				}else{
					panel.setLocation(14, 5);
				}
				panel.setSize(msgPane.getPreferredSize().width+10, msgPane.getPreferredSize().height);
				panel.add(msgPane);
			}else{
				name=new JLabel(msg.getFileName());
				name.setIcon(new ImageIcon(".\\res\\download.png"));
				name.setOpaque(false);
				name.setSize(name.getPreferredSize().width+10,name.getPreferredSize().height);
				name.setLocation(0, 0);
				name.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				
				length=new JLabel(new DecimalFormat("0.###").format(msg.getFileLength()/(1024.0*1024.0))+"MB");
				length.setOpaque(false);
				length.setSize(length.getPreferredSize().width+10,length.getPreferredSize().height);
				length.setLocation(0, name.getPreferredSize().height+10);
				length.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				
				download=new Button("下载");
				download.setSize(download.getPreferredSize());
				download.setLocation(((Math.max(Math.max(name.getPreferredSize().width, length.getPreferredSize().width), download.getPreferredSize().width)+10)/2)-(download.getWidth()/2), length.getY()+length.getPreferredSize().height+10);
				
				panel=new JPanel(null);
				panel.setOpaque(false);
				if(msg.getType()==Message.TO){
					panel.setLocation(5, 5);
				}else{
					panel.setLocation(14, 5);
				}
				panel.setSize(Math.max(Math.max(name.getPreferredSize().width, length.getPreferredSize().width), download.getPreferredSize().width)+10, download.getY()+download.getPreferredSize().height);
				
				panel.add(name);
				panel.add(length);
				panel.add(download);
				
				pro=new JProgressBar(0, msg.getFileLength());
				pro.setStringPainted(true);
				pro.setVisible(false);
				pro.setLocation(0, length.getY()+length.getPreferredSize().height+10);
				pro.setSize(pro.getPreferredSize());
				
				panel.add(pro);
				
				download.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser =new JFileChooser();
						chooser.setDialogTitle("保存文件");
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setMultiSelectionEnabled(false);
						if(chooser.showSaveDialog(MessageAdapter.this)==JFileChooser.APPROVE_OPTION){
							File file=chooser.getSelectedFile();
							showProgressBar(true);
							new Thread(){
								
								@Override
								public void run() {
									try {
										if(Client.downloadFile(file, msg.getFileName(), msg.getFileLength(), pro)){
											pro.setString("下载成功");
											try {
												Thread.sleep(1000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											showProgressBar(false);
										}else{
											pro.setString("下载失败");
											try {
												Thread.sleep(1000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											showProgressBar(false);
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}.start();
						}
					}
				});
			}
			add(panel);
			setSize(panel.getWidth()+14,panel.getHeight()+10);
		}
		
		@Override
		public void paint(Graphics g) {
			Graphics2D g2d=(Graphics2D) g;
			g2d.setColor(new Color(172, 216, 230));
			if(msg.getType()==Message.TO){
				g2d.fillRoundRect(0, 0, (int) (panel.getWidth()+9), getHeight(), 8, 8);
				g2d.fillPolygon(new int[]{getWidth()-9,getWidth(),getWidth()-9}, new int[]{(getHeight()-2)/2-5,(getHeight()-2)/2,(getHeight()-2)/2+5}, 3);
			}else{
				g2d.fillRoundRect(9, 0, (int) (panel.getWidth()+9), getHeight(), 8, 8);
				g2d.fillPolygon(new int[]{9,1,9}, new int[]{(getHeight()-2)/2-5,(getHeight()-2)/2,(getHeight()-2)/2+5}, 3);
			}
			super.paint(g2d);
			g2d.dispose();
		}
	}
}
