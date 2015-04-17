package org.rinasim.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;

/**
 * 联系人面板
 * @author 刘旭涛
 * @date 2015年3月27日 下午12:41:39
 * @since v1.0
 */
public class FriendPanel extends JPanel{

	private static final long serialVersionUID = -6995291838537322713L;

	private int id;
	private String name;
	private boolean hasMsg;
	
	private ImageIcon portrait;
	private JLabel portraitLabel;
	private JLabel nameLabel;
	
	private JPopupMenu popupMenu;
	private JMenuItem frdDetails;
	private JMenuItem setFrdNote;
	private JMenuItem deleteFrd;

	private Timer timer;
	
	/**
	 * 创建视图
	 */
	public FriendPanel(ImageIcon portrait, String name, int id, boolean isOnline, boolean hasMsg) {
		
		//初始化
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		
		this.portrait=portrait;
		this.name=name;
		this.id=id;
		this.hasMsg=hasMsg;
		
		//右键菜单
		popupMenu = new JPopupMenu();
		
		frdDetails = new JMenuItem("\u8BE6\u7EC6\u4FE1\u606F");
		popupMenu.add(frdDetails);
		
		setFrdNote = new JMenuItem("\u7F16\u8F91\u5907\u6CE8");
		popupMenu.add(setFrdNote);
		
		deleteFrd = new JMenuItem("\u5220\u9664");
		popupMenu.add(deleteFrd);
		
		//头像标签
		portraitLabel = new JLabel(portrait);
		add(portraitLabel);
		
		//空白
		Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
		add(rigidArea);
		
		//姓名面板
		JPanel nameAndId=new JPanel();
		nameAndId.setLayout(new BoxLayout(nameAndId, BoxLayout.Y_AXIS));
		nameAndId.setOpaque(false);
		add(nameAndId);
		
		//名称显示
		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		nameLabel.setOpaque(false);
		nameAndId.add(nameLabel);
		
		//ID显示
		JLabel idLabel = new JLabel(id+"");
		idLabel.setFont(new Font("幼圆", Font.PLAIN, 15));
		idLabel.setOpaque(false);
		nameAndId.add(idLabel);
		
		//填充
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);

		//绘制背景
		setBackground(Color.WHITE);
		setIsOnline(isOnline);
	}

	/**
	 * 设置在线状态
	 * @param isOnline true在线
	 */
	public void setIsOnline(boolean isOnline){
		if(isOnline)
			nameLabel.setForeground(new Color(46, 184, 230));
		else
			nameLabel.setForeground(Color.BLACK);
	}
	
	/**
	 * 设置是否有新消息
	 * @param hasMsg
	 */
	public void setHasMsg(boolean hasMsg){
		if(hasMsg&&!this.hasMsg){
			timer=new Timer(500, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(portraitLabel.getIcon().equals(portrait)){
						BufferedImage image = new BufferedImage(portrait.getIconWidth(), portrait.getIconHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = image.createGraphics();
						image = g2d.getDeviceConfiguration().createCompatibleImage(portrait.getIconWidth(), portrait.getIconHeight(), Transparency.TRANSLUCENT);
						g2d.dispose();
						g2d = image.createGraphics();
						g2d.setColor(new Color(255,0,0));
						g2d.setStroke(new BasicStroke(1));
						g2d.dispose();
						portraitLabel.setIcon(new ImageIcon(image));
					}else{
						portraitLabel.setIcon(portrait);
					}
				}
			});
			timer.start();
		}else{
			if(timer!=null){
				timer.stop();
			}
			portraitLabel.setIcon(portrait);
		}
		this.hasMsg=hasMsg;
	}
	
	/**
	 * 设置是否有新消息(默认)
	 */
	public void setHasMsg(){
		if(hasMsg){
			timer=new Timer(500, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(portraitLabel.getIcon().equals(portrait)){
						BufferedImage image = new BufferedImage(portrait.getIconWidth(), portrait.getIconHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = image.createGraphics();
						image = g2d.getDeviceConfiguration().createCompatibleImage(portrait.getIconWidth(), portrait.getIconHeight(), Transparency.TRANSLUCENT);
						g2d.dispose();
						g2d = image.createGraphics();
						g2d.setColor(new Color(255,0,0));
						g2d.setStroke(new BasicStroke(1));
						g2d.dispose();
						portraitLabel.setIcon(new ImageIcon(image));
					}else{
						portraitLabel.setIcon(portrait);
					}
				}
			});
			timer.start();
		}
	}
	
	/**
	 * 获取ID
	 * @return 用户ID
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 获取姓名
	 * @return 姓名
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 获取头像
	 * @return 头像
	 */
	public ImageIcon getPortrait(){
		return portrait;
	}
	
	/**
	 * 设置事件监听
	 * @param details
	 * @param setNote
	 * @param delete
	 * @param click
	 */
	public void setListeners(ActionListener details,ActionListener setNote, ActionListener delete, MouseAdapter click){
		frdDetails.addActionListener(details);
		setFrdNote.addActionListener(setNote);
		deleteFrd.addActionListener(delete);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(240, 240, 240));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(240, 240, 240), new Color(240, 240, 240)));
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBorder(null);
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e){
				if(!e.isPopupTrigger()){
					click.mouseClicked(e);
					setHasMsg(false);
				}
			}
			
			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * 设置备注姓名
	 * @param note
	 */
	public void setNote(String note){
		name=note;
		nameLabel.setText(note);
	}
}