package org.rinasim.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import org.rinasim.network.Client;
import org.rinasim.object.Message;
import org.rinasim.object.User;
import org.rinasim.util.FileOperator;
import org.rinasim.widget.CloseButton;
import org.rinasim.widget.FriendPanel;
import org.rinasim.widget.MinimizeButton;
import org.rinasim.widget.Toast;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.Box;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * 主窗体
 * @date 2015年4月7日 下午12:08:36
 * @since v1.0
 */
public class ClientUI extends JFrame {

	private static final long serialVersionUID = 627778846755628358L;
	
	/**
	 * 当前UI
	 */
	public static ClientUI current;
	
	private CloseButton cbtn;
	private JTextField search;
	private JProgressBar progressBar;
	private JScrollPane scrollPane;

	private int mouseX;
	private int mouseY;
	private JPanel FrdPane;
	
	private Map<Integer, Chat> chatPane=new HashMap<Integer, Chat>();
	private List<FriendPanel> list;
	
	/**
	 * 初始化窗体
	 */
	public ClientUI(User user) {
		
		//初始化
		current=this;
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(360,644);
		this.setTitle(user.getName());
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(248,248,248));
		((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 1, true));
		
		cbtn = new CloseButton(this);
		cbtn.setLocation(302, 0);
		cbtn.setSize(58, 25);
		getContentPane().add(cbtn);
		
		MinimizeButton mbtn = new MinimizeButton(this);
		mbtn.setLocation(268, 0);
		mbtn.setSize(34, 25);
		getContentPane().add(mbtn);
		
		//信息面板
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 360, 190);
		panel.setBackground(new Color(46, 184, 230));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel portrait = new JLabel(user.getPortrait());
		portrait.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				portrait.setBorder(new LineBorder(new Color(240, 240, 240), 2, true));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				portrait.setBorder(new LineBorder(Color.WHITE, 2, true));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				UserInfo frame=new UserInfo(user);
				frame.setVisible(true);
				for(int i=0;i<=520;i+=2){
					frame.setSize(frame.getWidth(), i);
				}
				Graphics g=frame.getGraphics();
				frame.paintAll(g);
				g.dispose();
			}
		});
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
		
		JLabel broad = new JLabel("\u516C\u544A");
		broad.setForeground(Color.WHITE);
		broad.setBorder(new LineBorder(Color.WHITE, 2, false));
		broad.setHorizontalAlignment(SwingConstants.CENTER);
		broad.setBounds(-1, 161, 92, 30);
		panel.add(broad);
		
		broad.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				broad.setBackground(Color.WHITE);
				broad.setForeground(new Color(46, 184, 230));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				broad.setBackground(new Color(46, 184, 230));
				broad.setForeground(Color.WHITE);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String msg=Client.getBroadCast();
					new Dialog(msg, Dialog.TYPE_MESSAGE, null).setVisible(true);
					FileOperator.writeBroadCast(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
					new Toast(new ImageIcon(".\\res\\error.png"), "获取失败").show(getLayeredPane(), broad, 1000);
				}
			}
		});
		
		JLabel update = new JLabel("\u68C0\u67E5\u66F4\u65B0");
		update.setHorizontalAlignment(SwingConstants.CENTER);
		update.setForeground(Color.WHITE);
		update.setBorder(new LineBorder(Color.WHITE, 2, false));
		update.setBounds(89, 161, 92, 30);
		panel.add(update);
		
		JLabel editPassword = new JLabel("\u4FEE\u6539\u5BC6\u7801");
		editPassword.setHorizontalAlignment(SwingConstants.CENTER);
		editPassword.setForeground(Color.WHITE);
		editPassword.setBorder(new LineBorder(Color.WHITE, 2, false));
		editPassword.setBounds(179, 161, 92, 30);
		panel.add(editPassword);
		
		JLabel option = new JLabel("\u8BBE\u7F6E");
		option.setHorizontalAlignment(SwingConstants.CENTER);
		option.setForeground(Color.WHITE);
		option.setBorder(new LineBorder(Color.WHITE, 2, false));
		option.setBounds(269, 161, 92, 30);
		panel.add(option);

		//添加搜索框
		search = new JTextField(FileOperator.getID());
		search.setBounds(14, 203, 332, 24);
		search.setForeground(Color.GRAY);
		search.setText("搜索账号");
		
		JProgressBar searchProgress = new JProgressBar();
		searchProgress.setBounds(14, 203, 332, 24);
		searchProgress.setStringPainted(true);
		searchProgress.setString("正在搜索");
		searchProgress.setIndeterminate(true);
		searchProgress.setVisible(false);
		getContentPane().add(searchProgress);
		
		search.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(search.getText().length()==0){
					search.setForeground(Color.GRAY);
					search.setText("搜索账号");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(search.getText().equals("搜索账号")){
					search.setText("");
					search.setForeground(Color.BLACK);
				}
			}		
		});
		search.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(search.getText().isEmpty()){
					return;
				}
				
				searchProgress.setVisible(true);
				search.setVisible(false);
				
				Graphics g;
				g=search.getGraphics();
				search.paintAll(g);
				g.dispose();
				g=searchProgress.getGraphics();
				searchProgress.paintAll(g);
				g.dispose();
				
				//搜索
				new Thread(){

					@Override
					public void run() {
						Toast toast=null;
						try {
							User frd=Client.getUser(Integer.parseInt(search.getText()));
							searchProgress.setVisible(false);
							search.setVisible(true);
							Graphics g;
							g=search.getGraphics();
							search.paintAll(g);
							g.dispose();
							g=searchProgress.getGraphics();
							searchProgress.paintAll(g);
							g.dispose();
							if(frd!=null){
								FriendInfo frame=new FriendInfo(frd, true);
								frame.setVisible(true);
							}else{
								toast=new Toast(new ImageIcon(".\\res\\about.png"), "没有找到该用户");
							}
						} catch (NumberFormatException e) {
							searchProgress.setVisible(false);
							search.setVisible(true);
							Graphics g;
							g=search.getGraphics();
							search.paintAll(g);
							g.dispose();
							g=searchProgress.getGraphics();
							searchProgress.paintAll(g);
							g.dispose();
							e.printStackTrace();
							toast=new Toast(new ImageIcon(".\\res\\error.png"), "请输入正确的账号");
						} catch (IOException e) {
							searchProgress.setVisible(false);
							search.setVisible(true);
							Graphics g;
							g=search.getGraphics();
							search.paintAll(g);
							g.dispose();
							g=searchProgress.getGraphics();
							searchProgress.paintAll(g);
							g.dispose();
							e.printStackTrace();
							toast=new Toast(new ImageIcon(".\\res\\error.png"), "无法连接到服务器");
						} finally {
							if(toast!=null)
								toast.show(getLayeredPane(), search, 1000);
							refresh();
						}
					}
				}.start();
			}
		});
		getContentPane().add(search);
		
		//配置联系人面板
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 240, 332, 385);
		getContentPane().add(scrollPane);
		
		FrdPane = new JPanel();
		scrollPane.setViewportView(FrdPane);
		FrdPane.setLayout(new BoxLayout(FrdPane, BoxLayout.Y_AXIS));
		
		JPanel headPane = new JPanel();
		scrollPane.setColumnHeaderView(headPane);
		headPane.setLayout(new BoxLayout(headPane, BoxLayout.Y_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		headPane.add(horizontalBox);
		
		Component rigidArea = Box.createRigidArea(new Dimension(35, 20));
		horizontalBox.add(rigidArea);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);
		
		JLabel label = new JLabel("\u8054 \u7CFB \u4EBA");
		horizontalBox.add(label);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		
		JLabel refresh = new JLabel(new ImageIcon(".\\res\\refresh.png"));
		refresh.setToolTipText("\u5237\u65B0");
		refresh.setAlignmentX(Component.CENTER_ALIGNMENT);
		refresh.setBorder(new LineBorder(headPane.getBackground(), 2, true));
		refresh.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				refresh.setBorder(new LineBorder(new Color(46, 184, 230), 2, true));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				refresh.setBorder(new LineBorder(new Color(66, 204, 250), 2, true));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				refresh.setBorder(new LineBorder(headPane.getBackground(), 2, true));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				refresh.setBorder(new LineBorder(new Color(46, 184, 230), 2, true));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				refresh();
			}
		});
		horizontalBox.add(refresh);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(15, 20));
		horizontalBox.add(rigidArea_2);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("正在刷新");
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		headPane.add(progressBar);
		
		JSeparator separator = new JSeparator();
		headPane.add(separator);
		
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
		
		//结束程序
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				System.exit(0);
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Map<Integer, Boolean> frdStatus=Client.getFrdStatus();
							for(int i=0;i<list.size();i++){
								if(frdStatus.containsKey(list.get(i).getId())){
									list.get(i).setIsOnline(frdStatus.get(list.get(i).getId()));
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		//更新
		refresh();
		
		//获取公告
		new Thread(){

			@Override
			public void run() {
				String msg="Unknown";
				try {
					msg=Client.getBroadCast();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(!msg.equals(FileOperator.getBroadCast())){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Dialog d=new Dialog(msg, Dialog.TYPE_MESSAGE, null);
					d.setVisible(true);
					FileOperator.writeBroadCast(msg);
				}
			}
		}.start();
	}
	
	/**
	 * 刷新好友列表
	 * @author 刘旭涛
	 * @date 2015年4月12日 下午1:42:39
	 * @since v1.0
	 */
	private void refresh(){
		progressBar.setVisible(true);
		new Thread(){
			
			@Override
			public void run() {
				try {
					list=Client.getFrdTable();
					FrdPane.removeAll();
					for(FriendPanel p:list){
						p.setHasMsg();
						p.setListeners(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								new Thread(){
									
									@Override
									public void run() {
										try {
											User frd = Client.getUser(p.getId());
											FriendInfo frame=new FriendInfo(frd, false);
											frame.setVisible(true);
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}.start();
							}
						}, new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								Dialog d=new Dialog("输入对Ta的备注：", Dialog.TYPE_INPUT, null);
								d.setVisible(true);
								String str=Dialog.getInput();
								if(str!=null){
									new Thread(){
										
										@Override
										public void run() {
											try {
												if(str.isEmpty()){
													if(!Client.setFrdNote(p.getId(), Client.getUser(p.getId()).getName())){
														new Toast(new ImageIcon(".\\res\\error.png"), "修改失败").show(getLayeredPane(), scrollPane, 1000);
													}else{
														refresh();
													}
												}else{
													if(!Client.setFrdNote(p.getId(), str)){
														new Toast(new ImageIcon(".\\res\\error.png"), "修改失败").show(getLayeredPane(), scrollPane, 1000);
													}else{
														refresh();
													}
												}
											} catch (IOException e) {
												e.printStackTrace();
												new Toast(new ImageIcon(".\\res\\error.png"), "修改失败").show(getLayeredPane(), scrollPane, 1000);
											}
										}
									}.start();
								}
							}
						}, new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								Dialog d=new Dialog("确认删除 "+p.getName()+" 吗？", Dialog.TYPE_COMFIRM, new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										new Thread(){
											
											@Override
											public void run() {
												try {
													if(Client.deleteFrd(p.getId())){
														refresh();
													}else{
														new Toast(new ImageIcon(".\\res\\error.png"), "删除失败").show(getLayeredPane(), scrollPane, 1000);
													}
												} catch (IOException e) {
													new Toast(new ImageIcon(".\\res\\error.png"), "删除失败").show(getLayeredPane(), scrollPane, 1000);
													e.printStackTrace();
												}
											}
										}.start();
									}
								});
								d.setVisible(true);
							}
						}, new MouseAdapter() {

							@Override
							public void mouseClicked(MouseEvent e) {
								if(chatPane.get(p.getId())!=null&&chatPane.get(p.getId()).isVisible()){
									chatPane.get(p.getId()).setState(Frame.NORMAL);
									chatPane.get(p.getId()).toFront();
								}else{
									Chat c=new Chat(p.getId(), p.getName(), p.getPortrait());
									chatPane.put(p.getId(),c);
									c.setVisible(true);
								}
								Client.readMessage(p.getId());
							}
						});
						FrdPane.add(p);
					}
					progressBar.setVisible(false);
				} catch (IOException e) {
					e.printStackTrace();
					progressBar.setVisible(false);
					new Toast(new ImageIcon(".\\res\\error.png"), "刷新失败").show(getLayeredPane(), scrollPane, 1000);
				}
			}
		}.start();
	}
	
	private Timer timer=new Timer(500, new ActionListener() {
		
		private String title;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(getTitle().isEmpty()){
				setTitle(title);
			}else{
				title=getTitle();
				setTitle("");
			}
		}
	});
	
	/**
	 * 设置新消息
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午9:20:24
	 * @since v1.0
	 * @param hasMsg
	 */
	public void setHasMsg(boolean hasMsg, int id){
		if(chatPane.get(id)!=null){
			Chat c=chatPane.get(id);
			if(hasMsg&&!c.isFocused()){
				for(FriendPanel fp:list){
					if(fp.getId()==id){
						fp.setHasMsg(hasMsg);
						break;
					}
				}
				c.setHasMsg(hasMsg);
			}else{
				for(FriendPanel fp:list){
					if(fp.getId()==id){
						fp.setHasMsg(false);
						break;
					}
				}
				c.setHasMsg(false);
			}
		}else{
			for(FriendPanel fp:list){
				if(fp.getId()==id){
					fp.setHasMsg(hasMsg);
					break;
				}
			}
			if(hasMsg&&!isFocused()){
				if(!timer.isRunning()){
					timer.start();
					addWindowFocusListener(new WindowFocusListener() {
						
						@Override
						public void windowLostFocus(WindowEvent e) {}
						
						@Override
						public void windowGainedFocus(WindowEvent e) {
							if(timer.isRunning())
								timer.stop();
							removeWindowFocusListener(this);
						}
					});
				}
			}else{
				if(timer.isRunning())
					timer.stop();
			}
		}
	}
	
	/**
	 * 添加消息
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午10:30:57
	 * @since v1.0
	 * @param msg
	 * @param id
	 */
	public void addMessage(Message msg, int id){
		if(chatPane.get(id)!=null){
			chatPane.get(id).addMessage(msg);
		}
	}
}
