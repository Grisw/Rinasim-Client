package org.rinasim.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.rinasim.widget.Button;
import org.rinasim.widget.CloseButton;

import javax.swing.SwingConstants;

/**
 * 对话框
 * @author 刘旭涛
 * @date 2015年4月13日 下午7:23:22
 * @since v1.0
 */
public class Dialog extends JDialog {

	private static final long serialVersionUID = 8884282346670517107L;

	/**
	 * 显示信息
	 */
	public static final int TYPE_MESSAGE=1;
	
	/**
	 * 确认窗口
	 */
	public static final int TYPE_COMFIRM=2;
	
	/**
	 * 获取输入
	 */
	public static final int TYPE_INPUT=3;
	
	private CloseButton cbtn;
	private JTextField textField;

	private int mouseX;
	private int mouseY;
	
	private static String input;
	
	/**
	 * 创建窗体
	 * @date 2015年4月13日 下午1:01:50
	 * @since v1.0
	 */
	public Dialog(String title, int type, ActionListener comfirm) {

		//初始化
		this.setUndecorated(true);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(248,248,248));
		((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 1, true));
		
		cbtn = new CloseButton(this);
		cbtn.setLocation(302, 0);
		cbtn.setSize(58, 25);
		getContentPane().add(cbtn);
		
		//信息面板
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 360, 240);
		panel.setBackground(new Color(46, 184, 230));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("<html>"+title+"<html />");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label.setBounds(14, 38, 332, 85);
		panel.add(label);

		if(type==TYPE_MESSAGE){
			this.setSize(360,175);
			Button yes=new Button("确定",true);
			yes.setBounds(0, 140, 360, 36);
			panel.add(yes);
			yes.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=getHeight();i>=0;i--){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					dispose();
				}
			});
		}else if(type==TYPE_COMFIRM){
			this.setSize(360,175);
			Button yes=new Button("确定",true);
			yes.setBounds(0, 140, 180, 36);
			panel.add(yes);
			yes.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					comfirm.actionPerformed(e);
					for(int i=getHeight();i>=0;i--){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					dispose();
				}
			});
			
			Button no = new Button("取消",true);
			no.setBounds(180, 140, 180, 36);
			panel.add(no);
			no.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=getHeight();i>=0;i--){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					dispose();
				}
			});
		}else if(type==TYPE_INPUT){
			this.setSize(360,240);
			Button yes=new Button("确定",true);
			yes.setBounds(0, 204, 180, 36);
			panel.add(yes);
			yes.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					input=textField.getText();
					for(int i=getHeight();i>=0;i--){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					dispose();
				}
			});
			
			Button no = new Button("取消",true);
			no.setBounds(180, 204, 180, 36);
			panel.add(no);
			no.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=getHeight();i>=0;i--){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					dispose();
				}
			});
			
			textField = new JTextField();
			textField.setBounds(14, 155, 332, 24);
			panel.add(textField);
		}

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
	
	/**
	 * 读取字符串，只能读取一次！！
	 * @author 刘旭涛
	 * @date 2015年4月13日 下午9:13:38
	 * @since v1.0
	 * @return
	 */
	public static String getInput(){
		String t=input;
		input=null;
		return t;
	}
}
