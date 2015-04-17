package org.rinasim.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.rinasim.network.Client;
import org.rinasim.object.User;
import org.rinasim.widget.Button;
import org.rinasim.widget.CloseButton;
import org.rinasim.widget.Toast;

import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JProgressBar;

/**
 * 用户信息
 * @author 刘旭涛
 * @date 2015年4月13日 下午1:01:34
 * @since v1.0
 */
public class FriendInfo extends JDialog {

	private static final long serialVersionUID = 8884282346670517107L;

	private CloseButton cbtn;

	private int mouseX;
	private int mouseY;
	
	/**
	 * 创建窗体
	 * @date 2015年4月13日 下午1:01:50
	 * @since v1.0
	 */
	public FriendInfo(User user, boolean showAdd) {
		
		//初始化
		this.setUndecorated(true);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setSize(360,520);
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(248,248,248));
		((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 1, true));
		
		cbtn = new CloseButton(this);
		cbtn.setLocation(302, 0);
		cbtn.setSize(58, 25);
		getContentPane().add(cbtn);
		
		//信息面板
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 360, 180);
		panel.setBackground(new Color(46, 184, 230));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel portrait = new JLabel(user.getPortrait());
		portrait.setBorder(new LineBorder(Color.WHITE, 2, true));
		portrait.setBounds(30, 50, 72, 72);
		panel.add(portrait);
		
		JLabel name = new JLabel(user.getName());
		name.setForeground(Color.WHITE);
		name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		name.setBounds(125, 61, 221, 24);
		panel.add(name);
		
		JLabel id = new JLabel(user.getId()+"");
		id.setForeground(Color.WHITE);
		id.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		id.setBounds(135, 98, 211, 18);
		panel.add(id);
		
		if(showAdd){
			JProgressBar progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(true);
			progressBar.setString("正在连接服务器");
			progressBar.setBounds(30, 138, 0, 18);
			panel.add(progressBar);
			Button add = new Button("添加",true);
			add.setBounds(204,129,133,36);
			panel.add(add);
			add.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=1;i<=160;i++){
						progressBar.setSize(i, progressBar.getHeight());
					}
					Graphics g=progressBar.getGraphics();
					progressBar.paintAll(g);
					g.dispose();
					
					new Thread(){

						@Override
						public void run() {
							add.setEnabled(false);
							try {
								if(Client.appendFrd(user.getId())){
									progressBar.setIndeterminate(false);
									progressBar.setString("添加成功");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									for(int i=getHeight();i>=0;i--){
										setSize(getWidth(), i);
									}
									dispose();
								}else{
									for(int i=159;i>=0;i--){
										progressBar.setSize(i, progressBar.getHeight());
									}
									Graphics g=progressBar.getGraphics();
									progressBar.paintAll(g);
									g.dispose();
									new Toast(new ImageIcon(".\\res\\about.png"), "Ta已经是你的好友了").show(getLayeredPane(), name, 1000);
								}
							} catch (IOException e) {
								e.printStackTrace();
								for(int i=159;i>=0;i--){
									progressBar.setSize(i, progressBar.getHeight());
								}
								Graphics g=progressBar.getGraphics();
								progressBar.paintAll(g);
								g.dispose();
								new Toast(new ImageIcon(".\\res\\about.png"), "无法连接到服务器").show(getLayeredPane(), add, 1000);
							} finally {
								add.setEnabled(true);
							}
						}
					}.start();
				}
			});
		}
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(14, 193, 332, 314);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		tabbedPane.setForeground(new Color(46, 184, 230));
		getContentPane().add(tabbedPane);
		
		JPanel commonInfo = new JPanel();
		tabbedPane.addTab("\u57FA\u672C\u4FE1\u606F", null, commonInfo, null);
		commonInfo.setBackground(Color.WHITE);
		commonInfo.setLayout(new BoxLayout(commonInfo, BoxLayout.Y_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		commonInfo.add(rigidArea);
		
		Box horizontalBox = Box.createHorizontalBox();
		commonInfo.add(horizontalBox);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox.add(rigidArea_1);
		
		JLabel label = new JLabel("\u6027\u522B\uFF1A");
		label.setForeground(new Color(46, 184, 230));
		horizontalBox.add(label);
		
		JLabel sex = new JLabel("<html>"+user.getSex()+"<html />");
		sex.setForeground(new Color(46, 184, 230));
		horizontalBox.add(sex);
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox.add(rigidArea_11);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		commonInfo.add(rigidArea_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		commonInfo.add(horizontalBox_1);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_1.add(rigidArea_3);
		
		JLabel label_1 = new JLabel("\u5E74\u9F84\uFF1A");
		label_1.setForeground(new Color(46, 184, 230));
		horizontalBox_1.add(label_1);
		
		JLabel age = new JLabel("<html>"+user.getAge()+"<html />");
		age.setForeground(new Color(46, 184, 230));
		horizontalBox_1.add(age);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_1.add(rigidArea_10);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		commonInfo.add(rigidArea_4);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		commonInfo.add(horizontalBox_2);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_2.add(rigidArea_5);
		
		JLabel label_2 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_2.setForeground(new Color(46, 184, 230));
		horizontalBox_2.add(label_2);
		
		JLabel email = new JLabel("<html>"+user.getEmail()+"<html />");
		email.setForeground(new Color(46, 184, 230));
		horizontalBox_2.add(email);
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_2.add(rigidArea_9);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		commonInfo.add(rigidArea_6);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		commonInfo.add(horizontalBox_3);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_3.add(rigidArea_7);
		
		JLabel label_3 = new JLabel("\u4E2A\u4EBA\u7B80\u4ECB\uFF1A");
		label_3.setForeground(new Color(46, 184, 230));
		horizontalBox_3.add(label_3);
		
		JLabel introduction = new JLabel("<html>"+user.getIntroduction()+"<html />");
		introduction.setForeground(new Color(46, 184, 230));
		horizontalBox_3.add(introduction);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_3.add(rigidArea_8);
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(20, 20));
		commonInfo.add(rigidArea_12);
		
		JPanel detailInfo = new JPanel();
		detailInfo.setBackground(Color.WHITE);
		tabbedPane.addTab("\u8BE6\u7EC6\u4FE1\u606F", null, detailInfo, null);
		detailInfo.setLayout(new BoxLayout(detailInfo, BoxLayout.Y_AXIS));
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_13);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_4);
		
		Component rigidArea_14 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_4.add(rigidArea_14);
		
		JLabel label_4 = new JLabel("\u751F\u65E5\uFF1A");
		label_4.setForeground(new Color(46, 184, 230));
		horizontalBox_4.add(label_4);
		
		JLabel label_5 = new JLabel("<html>"+(user.getBirthday()!=null?new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()):"null")+"<html />");
		label_5.setForeground(new Color(46, 184, 230));
		horizontalBox_4.add(label_5);
		
		Component rigidArea_15 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_4.add(rigidArea_15);
		
		Component rigidArea_16 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_16);
		
		Box horizontalBox_5 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_5);
		
		Component rigidArea_17 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_5.add(rigidArea_17);
		
		JLabel label_6 = new JLabel("\u661F\u5EA7\uFF1A");
		label_6.setForeground(new Color(46, 184, 230));
		horizontalBox_5.add(label_6);
		
		JLabel label_7 = new JLabel("<html>"+user.getConstellation()+"<html />");
		label_7.setForeground(new Color(46, 184, 230));
		horizontalBox_5.add(label_7);
		
		Component rigidArea_18 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_5.add(rigidArea_18);
		
		Component rigidArea_19 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_19);
		
		Box horizontalBox_6 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_6);
		
		Component rigidArea_20 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_6.add(rigidArea_20);
		
		JLabel label_8 = new JLabel("\u5DE5\u4F5C\uFF1A");
		label_8.setForeground(new Color(46, 184, 230));
		horizontalBox_6.add(label_8);
		
		JLabel label_9 = new JLabel("<html>"+user.getOccupation()+"<html />");
		label_9.setForeground(new Color(46, 184, 230));
		horizontalBox_6.add(label_9);
		
		Component rigidArea_21 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_6.add(rigidArea_21);
		
		Component rigidArea_22 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_22);
		
		Box horizontalBox_7 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_7);
		
		Component rigidArea_23 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_7.add(rigidArea_23);
		
		JLabel label_10 = new JLabel("\u516C\u53F8\uFF1A");
		label_10.setForeground(new Color(46, 184, 230));
		horizontalBox_7.add(label_10);
		
		JLabel label_11 = new JLabel("<html>"+user.getCompany()+"<html />");
		label_11.setForeground(new Color(46, 184, 230));
		horizontalBox_7.add(label_11);
		
		Component rigidArea_24 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_7.add(rigidArea_24);
		
		Component rigidArea_25 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_25);
		
		Box horizontalBox_8 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_8);
		
		Component rigidArea_26 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_8.add(rigidArea_26);
		
		JLabel label_12 = new JLabel("\u5B66\u6821\uFF1A");
		label_12.setForeground(new Color(46, 184, 230));
		horizontalBox_8.add(label_12);
		
		JLabel label_13 = new JLabel("<html>"+user.getSchool()+"<html />");
		label_13.setForeground(new Color(46, 184, 230));
		horizontalBox_8.add(label_13);
		
		Component rigidArea_27 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_8.add(rigidArea_27);
		
		Component rigidArea_28 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_28);
		
		Box horizontalBox_9 = Box.createHorizontalBox();
		detailInfo.add(horizontalBox_9);
		
		Component rigidArea_29 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_9.add(rigidArea_29);
		
		JLabel label_14 = new JLabel("\u5730\u5740\uFF1A");
		label_14.setForeground(new Color(46, 184, 230));
		horizontalBox_9.add(label_14);
		
		JLabel lbljfkjfk = new JLabel("<html>"+user.getAddress()+"<html />");
		lbljfkjfk.setForeground(new Color(46, 184, 230));
		horizontalBox_9.add(lbljfkjfk);
		
		Component rigidArea_30 = Box.createRigidArea(new Dimension(20, 20));
		horizontalBox_9.add(rigidArea_30);
		
		Component rigidArea_31 = Box.createRigidArea(new Dimension(20, 20));
		detailInfo.add(rigidArea_31);

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
	}
}
