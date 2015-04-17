package org.rinasim.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import org.rinasim.network.Client;
import org.rinasim.object.Message;
import org.rinasim.util.Time;
import org.rinasim.widget.Button;
import org.rinasim.widget.CloseButton;
import org.rinasim.widget.MessageAdapter;
import org.rinasim.widget.MinimizeButton;
import org.rinasim.widget.Toast;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;

/**
 * 聊天窗体
 * @author 刘旭涛
 * @date 2015年4月14日 上午9:58:40
 * @since v1.0
 */
public class Chat extends JFrame{

	private static final long serialVersionUID = -2147564873857982263L;
	
	private CloseButton cbtn;

	private int mouseX;
	private int mouseY;
	
	private JTextPane textPane;
	private JPanel msgPane;
	private JScrollPane scrollPane;
	
	private int id;

	/**
	 * 创建窗体
	 * @date 2015年4月14日 上午9:59:09
	 * @since v1.0
	 */
	public Chat(int id,String note,ImageIcon portrait) {

		//初始化
		this.id=id;
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(700,600);
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
		scrollPane.setBounds(14, 163, 672, 300);
		getContentPane().add(scrollPane);
		
		msgPane = new JPanel();
		msgPane.setBackground(Color.WHITE);
		scrollPane.setViewportView(msgPane);
		msgPane.setLayout(null);
		
		Button send = new Button("发送");
		send.setBounds(583,530,103,36);
		getContentPane().add(send);
		
		Button emoji = new Button("\u53D1\u9001");
		
		emoji.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EmojiPanel ep=new EmojiPanel();
				ep.setBounds(emoji.getX(), emoji.getY()-10-350, 350, 350);
				getLayeredPane().add(ep);
				getLayeredPane().setLayer(ep, getLayeredPane().highestLayer()+1);
				addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						if(!ep.contains(e.getPoint())){
							getLayeredPane().remove(ep);
							Graphics g=getLayeredPane().getGraphics();
							getLayeredPane().paintAll(g);
							g.dispose();
							getLayeredPane().removeMouseListener(this);
						}
					}
				});
			}
		});
		emoji.setText("\u8868\u60C5");
		emoji.setBounds(24, 476, 91, 24);
		getContentPane().add(emoji);
		
		Button pic = new Button("\u53D1\u9001");
		pic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
				chooser.setDialogTitle("选择图片");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg","jpeg","png","gif"));
				if(chooser.showOpenDialog(Chat.this)==JFileChooser.APPROVE_OPTION){
					File[] pics=chooser.getSelectedFiles();
					for(File f:pics){
						ImageIcon icon=new ImageIcon(f.getPath());
						if(icon.getIconWidth()<500){
							textPane.insertIcon(icon);
						}else{
							textPane.insertIcon(new ImageIcon(icon.getImage().getScaledInstance(500, icon.getIconHeight()/(icon.getIconWidth()/500), Image.SCALE_SMOOTH)));
						}
					}
				}
			}
		});
		pic.setText("\u56FE\u7247");
		pic.setBounds(129, 476, 91, 24);
		getContentPane().add(pic);
		
		Button file = new Button("\u53D1\u9001");
		file.setText("\u6587\u4EF6");
		file.setBounds(234, 476, 91, 24);
		getContentPane().add(file);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(new LineBorder(new Color(46, 184, 230), 2, true));
		scrollPane_1.setBounds(14, 513, 555, 74);
		getContentPane().add(scrollPane_1);
		
		textPane = new JTextPane();
		textPane.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					sendMessage();
					e.consume();
				}else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
					if(textPane.getCaretPosition()>0){
						try {
							textPane.getStyledDocument().remove(textPane.getStyledDocument().getLength()-1, 1);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					e.consume();
				}
			}
		});
		scrollPane_1.setViewportView(textPane);
		
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
		addOfflineMessage();
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
	}
	
	/**
	 * 添加离线消息
	 * @author 刘旭涛
	 * @date 2015年4月16日 上午9:21:04
	 * @since v1.0
	 */
	private void addOfflineMessage() {
		try {
			List<Message> offlineMsg=Client.getOfflineMessage(id);
			if(offlineMsg!=null){
				for(Message m:offlineMsg){
					addMessage(m);
				}
			}else{
				if(Client.msgMap.get(id)!=null){
					for(Message m:Client.msgMap.get(id)){
						addMessage(m);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int currentHeight=10;
	
	/**
	 * 添加消息
	 * @author 刘旭涛
	 * @date 2015年4月14日 下午9:01:33
	 * @since v1.0
	 * @param msg
	 */
	public void addMessage(Message msg){
		MessageAdapter adapter=new MessageAdapter(msg);
		if(msg.getType()==Message.TO){
			adapter.setLocation(msgPane.getWidth()-adapter.getWidth()-5, currentHeight);
		}else{
			adapter.setLocation(0, currentHeight);
		}
		currentHeight+=adapter.getHeight()+10;
		msgPane.add(adapter);
		
		msgPane.setPreferredSize(new Dimension(scrollPane.getWidth()-scrollPane.getVerticalScrollBar().getWidth(), currentHeight));
		
		Graphics graphics=scrollPane.getGraphics();
		if(graphics!=null){
			scrollPane.paintAll(graphics);
			graphics.dispose();
			JScrollBar bar=scrollPane.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		}
		graphics=scrollPane.getGraphics();
		if(graphics!=null){
			scrollPane.paintAll(graphics);
			graphics.dispose();
		}
	}

	/**
	 * 发送消息
	 * @author 刘旭涛
	 * @date 2015年4月16日 下午6:42:43
	 * @since v1.0
	 */
	private void sendMessage(){
		if(textPane.getText().isEmpty())
			return;
		new Thread(){

			@Override
			public void run() {
				try {
					Client.writeMsg(textPane.getStyledDocument(), id);
					addMessage(new Message(textPane.getStyledDocument(), Time.getDate(), Message.TO));
					textPane.setText("");
				} catch (IOException e) {
					e.printStackTrace();
					new Toast(new ImageIcon(".\\res\\error.png"), "无法连接到服务器，请检查网络").show(getLayeredPane(), textPane, 1000);
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
	public void setHasMsg(boolean hasMsg){
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
						ClientUI.current.setHasMsg(false, id);
						removeWindowFocusListener(this);
					}
				});
			}
		}else{
			if(timer.isRunning())
				timer.stop();
		}
	}
	
	/**
	 * 表情显示面板
	 * @author 刘旭涛
	 * @date 2015年4月16日 下午6:59:05
	 * @since v1.0
	 */
	private class EmojiPanel extends JPanel{

		private static final long serialVersionUID = -4800796398206889889L;
		
		private static final String EMOJI_PATH=".\\res\\emoji";
		
		public EmojiPanel() {
			setBackground(Color.WHITE);
			setBorder(new LineBorder(new Color(46, 184, 230), 2, true));
			setLayout(new GridLayout(6, 6, 10, 10));
			
			File[] files=new File(EMOJI_PATH).listFiles();
			for(File f:files){
				JLabel l=new JLabel(new ImageIcon(f.getPath()));
				l.setBackground(Color.WHITE);
				l.setOpaque(true);
				l.setBorder(new LineBorder(new Color(46, 184, 230), 2, true));
				l.addMouseListener(new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent e) {
						l.setBackground(new Color(240, 240, 240));
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						l.setBackground(Color.WHITE);
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						textPane.insertIcon(new ImageIcon(f.getPath()));
					}
				});
				add(l);
			}
			JLabel l=new JLabel(new ImageIcon(".\\res\\emo_delete_normal.png"));
			l.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					l.setIcon(new ImageIcon(".\\res\\emo_delete_press.png"));
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					l.setIcon(new ImageIcon(".\\res\\emo_delete_normal.png"));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if(textPane.getCaretPosition()>0){
						try {
							textPane.getStyledDocument().remove(textPane.getStyledDocument().getLength()-1, 1);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			add(l);
		}
	}
	
}
