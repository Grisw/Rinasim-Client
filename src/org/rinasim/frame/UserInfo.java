package org.rinasim.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 用户信息
 * @author 刘旭涛
 * @date 2015年4月13日 下午1:01:34
 * @since v1.0
 */
public class UserInfo extends JDialog {

	private static final long serialVersionUID = 4437300462611175502L;

	private CloseButton cbtn;

	private int mouseX;
	private int mouseY;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField email;
	private JTextField occupation;
	private JTextField company;
	private JTextField school;
	private JTextField address;
	
	/**
	 * 创建窗体
	 * @date 2015年4月13日 下午1:01:50
	 * @since v1.0
	 */
	public UserInfo(User user) {
		
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
				JFileChooser chooser=new JFileChooser();
				chooser.setDialogTitle("选择图片");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg","jpeg","png","gif"));
				if(chooser.showOpenDialog(UserInfo.this)==JFileChooser.APPROVE_OPTION){
					File pic=chooser.getSelectedFile();
					ImageIcon icon=new ImageIcon(new ImageIcon(pic.getPath()).getImage().getScaledInstance(72, 72, Image.SCALE_SMOOTH));
					portrait.setIcon(icon);
				}
			}
		});
		portrait.setBorder(new LineBorder(Color.WHITE, 2, true));
		portrait.setBounds(30, 50, 72, 72);
		panel.add(portrait);
		
		JTextField name = new JTextField(user.getName());
		name.setForeground(Color.BLACK);
		name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		name.setBounds(125, 50, 221, 35);
		panel.add(name);
		
		JLabel id = new JLabel(user.getId()+"");
		id.setForeground(Color.WHITE);
		id.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		id.setBounds(135, 98, 211, 18);
		panel.add(id);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setString("正在连接服务器");
		progressBar.setBounds(30, 138, 0, 18);
		panel.add(progressBar);
		Button save = new Button("保存",true);
		save.setBounds(204,129,133,36);
		panel.add(save);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(14, 193, 332, 314);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		tabbedPane.setForeground(new Color(46, 184, 230));
		getContentPane().add(tabbedPane);
		
		JPanel commonInfo = new JPanel();
		tabbedPane.addTab("\u57FA\u672C\u4FE1\u606F", null, commonInfo, null);
		commonInfo.setBackground(Color.WHITE);
		commonInfo.setLayout(null);
		
		JLabel label = new JLabel("\u6027\u522B\uFF1A");
		label.setForeground(new Color(46, 184, 230));
		label.setBounds(43, 17, 45, 18);
		commonInfo.add(label);
		
		JRadioButton male = new JRadioButton("\u7537");
		buttonGroup.add(male);
		male.setBackground(Color.WHITE);
		male.setBounds(88, 13, 43, 27);
		commonInfo.add(male);
		
		JRadioButton female = new JRadioButton("\u5973");
		buttonGroup.add(female);
		female.setBackground(Color.WHITE);
		female.setBounds(137, 13, 43, 27);
		commonInfo.add(female);
		
		JRadioButton secrect = new JRadioButton("\u4FDD\u5BC6");
		buttonGroup.add(secrect);
		secrect.setBackground(Color.WHITE);
		secrect.setBounds(186, 13, 59, 27);
		commonInfo.add(secrect);
		
		if(user.getSex().equals(male.getText())){
			male.setSelected(true);
		}else if(user.getSex().equals(female.getText())){
			female.setSelected(true);
		}else{
			secrect.setSelected(true);
		}
		
		JLabel label_1 = new JLabel("\u5E74\u9F84\uFF1A");
		label_1.setForeground(new Color(46, 184, 230));
		label_1.setBounds(43, 59, 45, 18);
		commonInfo.add(label_1);
		
		JSpinner age = new JSpinner();
		age.setModel(new SpinnerNumberModel(user.getAge(), 0, 100, 1));
		age.setForeground(Color.BLACK);
		age.setBounds(88, 56, 81, 24);
		commonInfo.add(age);
		
		JLabel label_2 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_2.setForeground(new Color(46, 184, 230));
		label_2.setBounds(14, 107, 75, 18);
		commonInfo.add(label_2);
		
		email = new JTextField(user.getEmail());
		email.setForeground(Color.BLACK);
		email.setBounds(88, 103, 225, 27);
		commonInfo.add(email);
		
		JLabel label_3 = new JLabel("\u4E2A\u4EBA\u7B80\u4ECB\uFF1A");
		label_3.setForeground(new Color(46, 184, 230));
		label_3.setBounds(14, 153, 75, 18);
		commonInfo.add(label_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 153, 225, 114);
		commonInfo.add(scrollPane);
		
		JTextArea introduction = new JTextArea(user.getIntroduction());
		introduction.setLineWrap(true);
		introduction.setWrapStyleWord(true);
		scrollPane.setViewportView(introduction);
		
		JPanel detailInfo = new JPanel();
		detailInfo.setBackground(Color.WHITE);
		tabbedPane.addTab("\u8BE6\u7EC6\u4FE1\u606F", null, detailInfo, null);
		detailInfo.setLayout(null);
		
		JLabel label_4 = new JLabel("\u751F\u65E5\uFF1A");
		label_4.setForeground(new Color(46, 184, 230));
		label_4.setBounds(14, 24, 45, 18);
		detailInfo.add(label_4);
		
		JLabel label_5 = new JLabel("\u661F\u5EA7\uFF1A");
		label_5.setForeground(new Color(46, 184, 230));
		label_5.setBounds(14, 66, 45, 18);
		detailInfo.add(label_5);
		
		JLabel label_6 = new JLabel("\u5DE5\u4F5C\uFF1A");
		label_6.setForeground(new Color(46, 184, 230));
		label_6.setBounds(14, 108, 45, 18);
		detailInfo.add(label_6);
		
		JLabel label_7 = new JLabel("\u516C\u53F8\uFF1A");
		label_7.setForeground(new Color(46, 184, 230));
		label_7.setBounds(14, 150, 45, 18);
		detailInfo.add(label_7);
		
		JLabel label_8 = new JLabel("\u5B66\u6821\uFF1A");
		label_8.setForeground(new Color(46, 184, 230));
		label_8.setBounds(14, 192, 45, 18);
		detailInfo.add(label_8);
		
		JLabel label_9 = new JLabel("\u5730\u5740\uFF1A");
		label_9.setForeground(new Color(46, 184, 230));
		label_9.setBounds(14, 234, 45, 18);
		detailInfo.add(label_9);
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(user.getBirthday());

		JTextField day = new JTextField(cal.get(Calendar.DAY_OF_MONTH));
		day.setBounds(216, 21, 45, 24);
		detailInfo.add(day);
		
		JTextField month = new JTextField();
		month.setBounds(147, 21, 45, 24);
		detailInfo.add(month);
		month.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
//				if(Integer.parseInt(month.getText())==2&&((cal.get(Calendar.YEAR)%4==0&&cal.get(Calendar.YEAR)%100!=0)||cal.get(Calendar.YEAR)%400==0)){
//					day.setModel(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 29, 1));
//				}else{
//					switch(cal.get(Calendar.MONTH)){
//					case 1:
//					case 3:
//					case 5:
//					case 7:
//					case 8:
//					case 10:
//					case 12:
//						day.setModel(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
//						break;
//					case 2:
//						day.setModel(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 28, 1));
//						break;
//					default:
//						day.setModel(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 30, 1));
//						break;
//					}
//				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		JTextField year = new JTextField();
		year.setBounds(59, 21, 63, 24);
		detailInfo.add(year);
		
		JComboBox<String> constellation = new JComboBox<String>();
		constellation.setModel(new DefaultComboBoxModel<String>(new String[] {"\u767D\u7F8A\u5EA7", "\u91D1\u725B\u5EA7", "\u53CC\u5B50\u5EA7", "\u5DE8\u87F9\u5EA7", "\u72EE\u5B50\u5EA7", "\u5904\u5973\u5EA7", "\u5929\u79E4\u5EA7", "\u5929\u874E\u5EA7", "\u5C04\u624B\u5EA7", "\u6469\u7FAF\u5EA7", "\u6C34\u74F6\u5EA7", "\u53CC\u9C7C\u5EA7"}));
		constellation.setSelectedItem(user.getConstellation());
		constellation.setBounds(59, 63, 157, 24);
		detailInfo.add(constellation);
		
		occupation = new JTextField(user.getOccupation());
		occupation.setBounds(59, 105, 254, 24);
		detailInfo.add(occupation);
		occupation.setColumns(10);
		
		company = new JTextField(user.getCompany());
		company.setColumns(10);
		company.setBounds(59, 147, 254, 24);
		detailInfo.add(company);
		
		school = new JTextField(user.getSchool());
		school.setColumns(10);
		school.setBounds(59, 189, 254, 24);
		detailInfo.add(school);
		
		address = new JTextField(user.getAddress());
		address.setColumns(10);
		address.setBounds(59, 231, 254, 24);
		detailInfo.add(address);
		
		JLabel label_10 = new JLabel("\u5E74");
		label_10.setBounds(125, 24, 15, 18);
		detailInfo.add(label_10);
		
		JLabel label_11 = new JLabel("\u6708");
		label_11.setBounds(193, 24, 15, 18);
		detailInfo.add(label_11);
		
		JLabel label_12 = new JLabel("\u65E5");
		label_12.setBounds(264, 24, 15, 18);
		detailInfo.add(label_12);

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
		
		//保存
		save.addActionListener(new ActionListener() {
			
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
						save.setEnabled(false);
						User user=new User();
						user.setId(Integer.parseInt(id.getText()));
						user.setAddress(address.getText());
						user.setAge((int) age.getValue());
						Calendar cal=Calendar.getInstance();
//						cal.set((int)year.getValue(), (int)month.getValue(), (int)day.getValue());
						user.setBirthday(cal.getTime());
						user.setCompany(company.getText());
						user.setConstellation((String) constellation.getSelectedItem());
						user.setEmail(email.getText());
						user.setIntroduction(introduction.getText());
						user.setName(name.getText());
						user.setOccupation(occupation.getText());
						user.setPortrait((ImageIcon) portrait.getIcon());
						user.setSchool(school.getText());
						System.out.println(buttonGroup.getSelection().getActionCommand());
//						user.setSex(buttonGroup.getSelection().getActionCommand());
						try {
							if(Client.editUser(user)){
								progressBar.setIndeterminate(false);
								progressBar.setString("修改成功");
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
								new Toast(new ImageIcon(".\\res\\error.png"), "修改失败").show(getLayeredPane(), save, 1000);
							}
						} catch (IOException e) {
							e.printStackTrace();
							for(int i=159;i>=0;i--){
								progressBar.setSize(i, progressBar.getHeight());
							}
							Graphics g=progressBar.getGraphics();
							progressBar.paintAll(g);
							g.dispose();
							new Toast(new ImageIcon(".\\res\\about.png"), "无法连接到服务器").show(getLayeredPane(), save, 1000);
						} finally {
							save.setEnabled(true);
						}
					}
				}.start();
			}
		});
	}
}
