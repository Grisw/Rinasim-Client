package org.rinasim.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import org.rinasim.network.Client;
import org.rinasim.object.Message;
import org.rinasim.widget.Button;
import org.rinasim.widget.CloseButton;
import org.rinasim.widget.MessageAdapter;
import org.rinasim.widget.MinimizeButton;
import org.rinasim.widget.Toast;

/**
 * @author 刘旭涛
 * @date 2015年4月21日 下午6:49:22
 * @since v1.0
 */
public class Record extends JFrame {

	private static final long serialVersionUID = -4168475238133232309L;

	private CloseButton cbtn;

	private int mouseX;
	private int mouseY;
	private JPanel msgPane;
	private JScrollPane scrollPane;

	/**
	 * 创建窗体
	 * @date 2015年4月14日 上午9:59:09
	 * @since v1.0
	 */
	public Record(int id,String note,ImageIcon portrait) {

		//初始化
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(700,587);
		this.setTitle(note);
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(248,248,248));
		((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 1, true));
		
		//信息面板
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 700, 150);
		panel.setBackground(new Color(46, 184, 230));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		cbtn = new CloseButton(this);
		cbtn.setBounds(642, 0, 58, 25);
		panel.add(cbtn);
		
		cbtn.setActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=getHeight();i>=0;i--){
					setSize(getWidth(), i);
				}
				setVisible(false);
			}
		});
		
		MinimizeButton mbtn = new MinimizeButton(this);
		mbtn.setBounds(608, 0, 34, 25);
		panel.add(mbtn);
		
		JLabel head = new JLabel(portrait);
		head.setBorder(new LineBorder(Color.WHITE, 2, true));
		head.setBounds(40, 40, 72, 72);
		panel.add(head);
		
		JLabel name = new JLabel(note);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		name.setBounds(135, 51, 551, 24);
		panel.add(name);
		
		JLabel idLabel = new JLabel(id+"");
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		idLabel.setBounds(145, 88, 541, 18);
		panel.add(idLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(14, 163, 672, 370);
		getContentPane().add(scrollPane);
		
		msgPane = new JPanel();
		msgPane.setBackground(Color.WHITE);
		scrollPane.setViewportView(msgPane);
		msgPane.setSize(new Dimension(scrollPane.getWidth()-scrollPane.getVerticalScrollBar().getWidth(), currentHeight));
		msgPane.setLayout(null);
		
		Button clear=new Button("清空");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialog d=new Dialog("确认清空吗？", Dialog.TYPE_COMFIRM, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						new Thread(){

							@Override
							public void run() {
								try {
									if(Client.clearRecord(id)){
										new Toast(new ImageIcon(".\\res\\about.png"), "清空完成").show(getLayeredPane(), clear, 2000);
										msgPane.removeAll();
										Graphics g=scrollPane.getGraphics();
										scrollPane.paintAll(g);
										if(g!=null)
											g.dispose();
									}else{
										new Toast(new ImageIcon(".\\res\\error.png"), "清空失败").show(getLayeredPane(), clear, 2000);
									}
								} catch (IOException e) {
									e.printStackTrace();
									new Toast(new ImageIcon(".\\res\\error.png"), "清空失败").show(getLayeredPane(), clear, 2000);
								}
							}
						}.start();
					}
				});
				d.setVisible(true);
			}
		});
		clear.setBounds(280, 546, 139, 26);
		getContentPane().add(clear);
		
		//窗口移动
		panel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX=e.getX();
				mouseY=e.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(getLocation().x+e.getX()-mouseX, getLocation().y+e.getY()-mouseY);
			}
		});
		
		//初始化消息
		new Thread(){

			@Override
			public void run() {
				try {
					List<Message> list=Client.getRecord(id);
					if(list!=null){
						for(Message m:list){
							addMessage(m);
						}
					}
				} catch (IOException e) {
					new Toast(new ImageIcon(".\\res\\error.png"), "获取失败").show(getLayeredPane(), scrollPane, 2000);
					e.printStackTrace();
				}
			}
		}.start();

	}

	private int currentHeight=10;
	
	/**
	 * 添加消息
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午9:01:33
	 * @since v1.0
	 * @param msg
	 */
	public MessageAdapter addMessage(Message msg){
		MessageAdapter adapter=new MessageAdapter(msg);
		if(msg.getType()==Message.TO){
			adapter.setLocation(msgPane.getWidth()-adapter.getWidth()-5, currentHeight);
		}else{
			adapter.setLocation(0, currentHeight);
		}
		currentHeight+=adapter.getHeight()+10;
		
		msgPane.setPreferredSize(new Dimension(scrollPane.getWidth()-scrollPane.getVerticalScrollBar().getWidth(), currentHeight));
		
		msgPane.add(adapter);
		
		Graphics graphics=scrollPane.getGraphics();
		scrollPane.paintAll(graphics);
		if(graphics!=null)
			graphics.dispose();
		JScrollBar bar=scrollPane.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
		graphics=scrollPane.getGraphics();
		scrollPane.paintAll(graphics);
		if(graphics!=null)
			graphics.dispose();
		return adapter;
	}
}