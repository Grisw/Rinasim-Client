package org.rinasim.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.rinasim.main.Main;
import org.rinasim.network.Client;
import org.rinasim.object.LoginFailedException;
import org.rinasim.object.User;
import org.rinasim.util.FileOperator;
import org.rinasim.widget.Button;
import org.rinasim.widget.CloseButton;
import org.rinasim.widget.MinimizeButton;
import org.rinasim.widget.TextButton;
import org.rinasim.widget.Toast;

/**
 * �ͻ��˵�½����
 * @author ������
 * @since v1.0
 * @date 2015��3��17�� ����7:32:28
 */
public class Login extends JFrame implements ActionListener{

	private static final long serialVersionUID = 2316838811110802194L;
	
	private CloseButton cbtn;
	
	private JLabel lbl;
	private JTextField id;
	private JPasswordField passwordField;
	private JCheckBox cbx;
	
	private JLayeredPane loginPane;
	private JLayeredPane forgetPane;
	private JLayeredPane serverPane;
	private JLayeredPane registerPane;
	
	private int mouseX;
	private int mouseY;
	
	/**
	 * ��ʼ������
	 */
	public Login() {
		
		//��ʼ��
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(435,1);
		this.setTitle("��¼");
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(248,248,248));
		((JPanel)this.getContentPane()).setBorder(new LineBorder(Color.BLACK, 1, true));
		
		cbtn = new CloseButton(this);
		cbtn.setLocation(377, 0);
		cbtn.setSize(58, 25);
		getContentPane().add(cbtn);
		
		MinimizeButton mbtn = new MinimizeButton(this);
		mbtn.setLocation(343, 0);
		mbtn.setSize(34, 25);
		getContentPane().add(mbtn);
		
		loginPane=new JLayeredPane();
		loginPane.setLayout(null);
		loginPane.setBounds(2, 190, 432, 187);
		loginPane.setBackground(new Color(248,248,248));
		getContentPane().add(loginPane);
		
		//���ͼƬ...
		lbl = new JLabel(new ImageIcon(".\\res\\rinasim.png"));
		lbl.setOpaque(true);
		lbl.setBounds(0, 0, 435, 190);
		getContentPane().add(lbl);

		//��ʼ��
		initLogin();
		initForgetPassword();
		initSetServer();
		initRegister();
	}
	
	/**
	 * ��½����
	 * @author ������
	 * @since v1.0
	 * @date 2015��3��17�� ����7:32:28
	 */
	private void initLogin(){
		TextButton lblRinasimClientV = new TextButton("Rinasim v1.0");
		lblRinasimClientV.setBounds(340, 351-190, 93, 16);
		loginPane.add(lblRinasimClientV);
		
		//���ͷ��
		JLabel portrait = new JLabel(FileOperator.getPortrait());
		portrait.setBackground(Color.WHITE);
		portrait.setOpaque(true);
		portrait.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		portrait.setBounds(30, 218-190, 64, 64);
		loginPane.add(portrait);
		
		cbx = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
		cbx.setFocusPainted(false);
		cbx.setOpaque(false);
		cbx.setBounds(10, 344-190, 89, 33);
		loginPane.add(cbx);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(108, 259-190, 212, 24);
		if(FileOperator.getPassword()!=null){
			char[] p=FileOperator.getPassword();
			for(int i=0;i<p.length;i++){
				p[i]=(char) (p[i]^Main.PASSWORD_OPERATOR);
			}
			passwordField.setText(String.valueOf(p));
			cbx.setSelected(true);
		}else{
			passwordField.setEchoChar((char) 0);
			passwordField.setForeground(Color.GRAY);
			passwordField.setText("����");
		}
		loginPane.add(passwordField);
		
		TextButton register = new TextButton("ע���˺�");
		register.setSize(62, 16);
		register.setLocation(337, 220-190);
		loginPane.add(register);
		
		TextButton forgotPassword = new TextButton("��������");
		forgotPassword.setSize(62, 16);
		forgotPassword.setLocation(337, 262-190);
		loginPane.add(forgotPassword);
		
		Button login = new Button("��¼");
		login.setBounds(108,305-190,212,36);
		loginPane.add(login);
		
		id = new JTextField(FileOperator.getID());
		if(id.getText().isEmpty()){
			id.setForeground(Color.GRAY);
			id.setText("�˺�");
		}
		id.setBounds(108, 217-190, 212, 24);
		loginPane.add(id);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX=e.getX();
				mouseY=e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(getLocation().x+e.getX()-mouseX, getLocation().y+e.getY()-mouseY);
			}
		});
		
		passwordField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(passwordField.getPassword().length==0){
					passwordField.setEchoChar((char) 0);
					passwordField.setForeground(Color.GRAY);
					passwordField.setText("����");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(passwordField.getEchoChar()==0) {
					passwordField.setText("");
					passwordField.setForeground(Color.BLACK);
					passwordField.setEchoChar('*');
				}
			}
		});
		passwordField.addActionListener(this);
		
		id.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(id.getText().length()==0){
					id.setForeground(Color.GRAY);
					id.setText("�˺�");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(id.getText().equals("�˺�")){
					id.setText("");
					id.setForeground(Color.BLACK);
				}
			}		
		});
		id.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		
		forgotPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=431;i>=0;i--){
					forgetPane.setSize(432-i, forgetPane.getHeight());
					if(i>=2){
						forgetPane.setLocation(i, forgetPane.getY());
					}
					if(i%2==0){
						Graphics g=forgetPane.getGraphics();
						forgetPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(false);
			}
		});
		
		lblRinasimClientV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=431;i>=0;i--){
					serverPane.setSize(432-i, serverPane.getHeight());
					if(i>=2){
						serverPane.setLocation(i, serverPane.getY());
					}
					if(i%2==0){
						Graphics g=serverPane.getGraphics();
						serverPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(false);
			}
		});

		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginPane.setVisible(false);
				Graphics g=getGraphics();
				paintAll(g);
				g.dispose();
				for(int i=380;i<=570;i++){
					setSize(getWidth(), i);
					registerPane.setSize(registerPane.getWidth(), i-380);
					if(i%2==0){
						g=registerPane.getGraphics();
						registerPane.paintAll(g);
						g.dispose();
						g=getGraphics();
						paintAll(g);
						g.dispose();
					}
				}
				for(int i=190;i<=380;i++){
					registerPane.setSize(registerPane.getWidth(), i);
					if(i%2==0){
						g=registerPane.getGraphics();
						registerPane.paintAll(g);
						g.dispose();
					}
				}
			}
		});
		
		login.addActionListener(this);
	}
	
	/**
	 * �������봦�����
	 * @author ����
	 * @date 2015��3��19�� ����3:36:36
	 * @since v1.0
	 */
	private void initForgetPassword(){
		forgetPane = new JLayeredPane();
		forgetPane.setLayout(null);
		forgetPane.setBounds(432, 190, 0, 187);
		forgetPane.setBackground(new Color(248,248,248));
		getLayeredPane().setLayer(forgetPane, JLayeredPane.getLayer(loginPane)+1);
		getLayeredPane().add(forgetPane);
		
		TextButton pre=new TextButton("����");
		pre.setIcon(new ImageIcon(".\\res\\previous.png"));
		pre.setBounds(0, 0, 68, 32);
		forgetPane.add(pre);
		
		//�����
		JTextField emailaddress = new JTextField();
		emailaddress.setBounds(108, 259-190, 212, 24);
		emailaddress.setForeground(Color.GRAY);
		emailaddress.setText("Email��ַ");
		forgetPane.add(emailaddress);
		
		Button sendpassward = new Button("��������������");
		sendpassward.setBounds(108,305-190,212,36);
		forgetPane.add(sendpassward);
			
		JTextField idEmail = new JTextField(FileOperator.getID());
		if(idEmail.getText().isEmpty()){
			idEmail.setForeground(Color.GRAY);
			idEmail.setText("�˺�");
		}
		idEmail.setBounds(108, 217-190, 212, 24);
		forgetPane.add(idEmail);

		emailaddress.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(emailaddress.getText().length()==0){
					emailaddress.setForeground(Color.GRAY);
					emailaddress.setText("Email��ַ");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(emailaddress.getText().equals("Email��ַ")) {
					emailaddress.setText("");
					emailaddress.setForeground(Color.BLACK);
				}
			}
		});
		
		idEmail.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(idEmail.getText().length()==0){
					idEmail.setForeground(Color.GRAY);
					idEmail.setText("�˺�");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(idEmail.getText().equals("�˺�")){
					idEmail.setText("");
					idEmail.setForeground(Color.BLACK);
				}
			}
		});
		
		idEmail.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		
		//���������ѡ����Ӧ
		sendpassward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(idEmail.getText().equals("�˺�")){
					new Toast(new ImageIcon(".\\res\\error.png"), "�˺Ų���Ϊ�գ�").show(forgetPane, idEmail, 2000);
					return;
				}
				if(emailaddress.getText().equals("Email��ַ")){
					new Toast(new ImageIcon(".\\res\\error.png"), "���䲻��Ϊ�գ�").show(forgetPane, emailaddress, 2000);
					return;
				}
			}
		});
		
		pre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=2;i<=432;i++){
					forgetPane.setSize(432-i, forgetPane.getHeight());
					forgetPane.setLocation(i, forgetPane.getY());
					if(i%2==0){
						Graphics g=forgetPane.getGraphics();
						forgetPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(true);
			}
		});	
	}

	/**
	 * ���÷���������
	 * @author ������
	 * @date 2015��3��19�� ����9:31:36
	 * @since v1.0
	 */
	private void initSetServer(){
		serverPane = new JLayeredPane();
		serverPane.setLayout(null);
		serverPane.setBounds(432, 190, 0, 187);
		serverPane.setBackground(new Color(248,248,248));
		getLayeredPane().setLayer(serverPane, JLayeredPane.getLayer(loginPane)+1);
		getLayeredPane().add(serverPane);
		
		TextButton preServer=new TextButton("����");
		preServer.setIcon(new ImageIcon(".\\res\\previous.png"));
		preServer.setBounds(0, 0, 68, 32);
		serverPane.add(preServer);
		
		JTextField port = new JTextField(FileOperator.getServerPort()+"");
		port.setBounds(108, 259-190, 212, 24);
		if(port.getText().isEmpty()){
			port.setForeground(Color.GRAY);
			port.setText("�������˿�");
		}
		serverPane.add(port);
		
		Button setServer = new Button("��������");
		setServer.setBounds(108,305-190,212,36);
		serverPane.add(setServer);
			
		JTextField ip = new JTextField(FileOperator.getServerIp());
		if(ip.getText().isEmpty()){
			ip.setForeground(Color.GRAY);
			ip.setText("������IP��ַ");
		}
		ip.setBounds(108, 217-190, 212, 24);
		serverPane.add(ip);

		port.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(port.getText().length()==0){
					port.setForeground(Color.GRAY);
					port.setText("�������˿�");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(port.getText().equals("�������˿�")) {
					port.setText("");
					port.setForeground(Color.BLACK);
				}
			}
		});
		
		ip.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(ip.getText().length()==0){
					ip.setForeground(Color.GRAY);
					ip.setText("������IP��ַ");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(ip.getText().equals("������IP��ַ")){
					ip.setText("");
					ip.setForeground(Color.BLACK);
				}
			}
		});
		
		ip.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789."+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		
		port.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		
		setServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ip.getText().equals("������IP��ַ")){
					new Toast(new ImageIcon(".\\res\\error.png"), "������IP����Ϊ�գ�").show(forgetPane, ip, 2000);
					return;
				}
				if(port.getText().equals("�������˿�")){
					new Toast(new ImageIcon(".\\res\\error.png"), "�������˿ڲ���Ϊ�գ�").show(forgetPane, port, 2000);
					return;
				}
				if(!ip.getText().matches(
						"^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
						   "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
						   "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
						   "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")){
					new Toast(new ImageIcon(".\\res\\error.png"), "������IP��ʽ����").show(forgetPane, ip, 2000);
					return;
				}
				if(port.getText().length()>5){
					new Toast(new ImageIcon(".\\res\\error.png"), "�������˿ڲ�����5λ����").show(forgetPane, port, 2000);
					return;
				}
				FileOperator.setServerIp(ip.getText());
				FileOperator.setServerPort(Integer.parseInt(port.getText()));
				for(int i=2;i<=432;i++){
					serverPane.setSize(432-i, serverPane.getHeight());
					serverPane.setLocation(i, serverPane.getY());
					if(i%2==0){
						Graphics g=serverPane.getGraphics();
						serverPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(true);
			}
		});
		
		preServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=2;i<=432;i++){
					serverPane.setSize(432-i, serverPane.getHeight());
					serverPane.setLocation(i, serverPane.getY());
					if(i%2==0){
						Graphics g=serverPane.getGraphics();
						serverPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(true);
			}
		});
	}

	/**
	 * ע���˺Ž���
	 * @author ������
	 * @since v1.0
	 * @date 2015��3��17�� ����7:32:28
	 */
	private void initRegister(){
		registerPane = new JLayeredPane();
		registerPane.setLayout(null);
		registerPane.setBounds(2, 190, 432, 0);
		registerPane.setBackground(new Color(248,248,248));
		getLayeredPane().setLayer(registerPane, JLayeredPane.getLayer(loginPane)+1);
		getLayeredPane().add(registerPane);
		
		JTextField user_name = new JTextField();
		user_name.setBounds(154, 43, 211, 24);
		registerPane.add(user_name);
		user_name.setColumns(10);
		
		user_name.addFocusListener(new FocusAdapter() {

			private Toast toast;
			
			@Override
			public void focusGained(FocusEvent e) {
				if(toast==null){
					toast=new Toast(new ImageIcon(".\\res\\about.png"), "18�����ڵ��û���");
					toast.show(registerPane, user_name, 0);
				}else{
					toast.setVisible(true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				toast.setVisible(false);
				registerPane.validate();
			}
		});
		
		JTextField idRegister = new JTextField();
		idRegister.setBounds(154, 97, 210, 24);
		registerPane.add(idRegister);
		idRegister.setColumns(10);
		
		idRegister.addFocusListener(new FocusAdapter() {

			private Toast toast;
			
			@Override
			public void focusGained(FocusEvent e) {
				if(toast==null){
					toast=new Toast(new ImageIcon(".\\res\\about.png"), "����10���ڵ�ID�����ڵ�¼");
					toast.show(registerPane, idRegister, 0);
				}else{
					toast.setVisible(true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				toast.setVisible(false);
				registerPane.validate();
			}
		});
		
		JPasswordField password = new JPasswordField();
		password.setBounds(154, 147, 212, 24);
		registerPane.add(password);
		
		password.addFocusListener(new FocusAdapter() {

			private Toast toast;
			
			@Override
			public void focusGained(FocusEvent e) {
				if(toast==null){
					toast=new Toast(new ImageIcon(".\\res\\about.png"), "����6λ�����20λ");
					toast.show(registerPane, password, 0);
				}else{
					toast.setVisible(true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				toast.setVisible(false);
				registerPane.validate();
			}
		});
		
		JPasswordField password_again = new JPasswordField();
		password_again.setBounds(154, 201, 212, 24);
		registerPane.add(password_again);	
		
		password_again.addFocusListener(new FocusAdapter() {

			private Toast toast;
			
			@Override
			public void focusGained(FocusEvent e) {
				if(toast==null){
					toast=new Toast(new ImageIcon(".\\res\\about.png"), "�ظ���������");
					toast.show(registerPane, password_again, 0);
				}else{
					toast.setVisible(true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				toast.setVisible(false);
				registerPane.validate();
			}
		});
		
		JTextField e_mail = new JTextField();
		e_mail.setBounds(154, 255, 213, 24);
		registerPane.add(e_mail);
		e_mail.setColumns(10);
		
		e_mail.addFocusListener(new FocusAdapter() {

			private Toast toast;
			
			@Override
			public void focusGained(FocusEvent e) {
				if(toast==null){
					toast=new Toast(new ImageIcon(".\\res\\about.png"), "��Ч�������ַ�����������һ�");
					toast.show(registerPane, e_mail, 0);
				}else{
					toast.setVisible(true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				toast.setVisible(false);
				registerPane.validate();
			}
		});
		
		JLabel lbl_user_name = new JLabel("\u6635\u79F0     :");
		lbl_user_name.setBounds(54, 43, 88, 24);
		registerPane.add(lbl_user_name);
		
		JLabel lbl_id = new JLabel("\u8D26\u53F7     :");
		lbl_id.setBounds(54, 97, 88, 24);
		registerPane.add(lbl_id);
		
		JLabel lbl_password = new JLabel("\u5BC6\u7801     :");
		lbl_password.setBounds(54, 147, 88, 24);
		registerPane.add(lbl_password);
		
		JLabel lbl_password_again = new JLabel("\u786E\u8BA4\u5BC6\u7801 :");
		lbl_password_again.setBounds(54, 201, 88, 24);
		registerPane.add(lbl_password_again);
		
		JLabel lbl_e_mail = new JLabel("\u90AE\u7BB1     :");
		lbl_e_mail.setBounds(54, 255, 88, 24);
		registerPane.add(lbl_e_mail);
		
		Button registerRegister = new Button("����ע��");
		registerRegister.setBounds(116,311,212,36);
		registerPane.add(registerRegister);	
		
		TextButton preRegister=new TextButton("����");
		preRegister.setIcon(new ImageIcon(".\\res\\previous.png"));
		preRegister.setBounds(0, 0, 68, 32);
		registerPane.add(preRegister);
		
		preRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=570;i>=380;i--){
					setSize(getWidth(), i);
					registerPane.setSize(registerPane.getWidth(), i-190);
					if(i%2==0){
						Graphics g=registerPane.getGraphics();
						registerPane.paintAll(g);
						g.dispose();
					}
				}
				for(int i=190;i>=0;i--){
					registerPane.setSize(registerPane.getWidth(), i);
					if(i%2==0){
						Graphics g=registerPane.getGraphics();
						registerPane.paintAll(g);
						g.dispose();
					}
				}
				loginPane.setVisible(true);
			}
		});
		
		idRegister.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0||idRegister.getText().length()>9){
					e.consume();	
				}
			}
		});
		
		user_name.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(user_name.getText().length()>17){
					e.consume();	
				}
			}
		});
		
		password.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(password.getPassword().length>49){
					e.consume();	
				}
			}
		});
		
		password_again.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(password_again.getPassword().length>49){
					e.consume();	
				}
			}
		});
		
		registerRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				User user=new User();
				if(idRegister.getText().isEmpty()){
					new Toast(new ImageIcon(".\\res\\error.png"), "�˺Ų���Ϊ�գ�").show(registerPane, idRegister, 2000);
					return;
				}
				user.setId(Integer.parseInt(idRegister.getText()));
				if (!user.setPassword(String.valueOf(password.getPassword()))) {
					new Toast(new ImageIcon(".\\res\\error.png"), "���볤�Ȳ�С��6���ַ���").show(registerPane, password, 2000);
					return;
				}
				if(!Arrays.equals(password.getPassword(), password_again.getPassword())){
					new Toast(new ImageIcon(".\\res\\error.png"), "���벻һ�£�").show(registerPane, password_again, 2000);
					return;
				}
				if(!user.setName(user_name.getText())) {
					new Toast(new ImageIcon(".\\res\\error.png"), "�û�������Ϊ�գ�").show(registerPane, idRegister, 2000);
					return;
				}
				if(!user.setEmail(e_mail.getText())) {
					new Toast(new ImageIcon(".\\res\\error.png"), "�����ַ��ʽ����").show(registerPane, e_mail, 2000);
					return;
				}
				user.setSex("����");
				user.setAge(1);
				user.setBirthday(new Date());
				user.setCompany("");
				user.setConstellation("������");
				user.setIntroduction("");
				user.setOccupation("");
				user.setSchool("");
				user.setStatus(false);
				user.setPortrait(new ImageIcon(".\\res\\default_figure.png"));
				try {
					user.setIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				for(int i=570;i>=190;i--){
					setSize(getWidth(), i);
				}
				Graphics g=getGraphics();
				paintAll(g);
				g.dispose();
				JProgressBar progress=new JProgressBar();
				progress.setBounds(117, 140, 1, 17);
				getLayeredPane().setLayer(progress, JLayeredPane.getLayer(lbl)+1);
				getLayeredPane().add(progress);
				progress.setString("�������ӷ�����");
				for(int i=1;i<=200;i++){
					progress.setSize(i, progress.getHeight());
					try {
						Thread.sleep(2);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					Graphics g1=progress.getGraphics();
					progress.paintAll(g1);
					g1.dispose();
				}
				progress.setStringPainted(true);
				progress.setIndeterminate(true);
				
				Thread thread=new Thread(){
					
					@Override
					public void run(){
						try {
							if(Client.addUser(user)){
								progress.setString("ע��ɹ���");
								progress.setIndeterminate(false);
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								progress.setStringPainted(false);
								progress.setIndeterminate(false);
								for(int i=200;i>=0;i--){
									progress.setSize(i, progress.getHeight());
									Graphics g1=progress.getGraphics();
									progress.paintAll(g1);
									g1.dispose();
								}
								progress.setVisible(false);
								registerPane.setSize(registerPane.getWidth(), 0);
								for(int i=190;i<=380;i++){
									setSize(getWidth(), i);
									Graphics g=getGraphics();
									paintAll(g);
									g.dispose();
								}
								loginPane.setVisible(true);
							}else{
								progress.setStringPainted(false);
								progress.setIndeterminate(false);
								for(int i=200;i>=0;i--){
									progress.setSize(i, progress.getHeight());
									Graphics g1=progress.getGraphics();
									progress.paintAll(g1);
									g1.dispose();
								}
								progress.setVisible(false);
								for(int i=190;i<=570;i++){
									setSize(getWidth(), i);
								}
								Graphics g=getGraphics();
								paintAll(g);
								g.dispose();
								new Toast(new ImageIcon(".\\res\\error.png"), "���˺��ѱ�ʹ�ã�").show(registerPane, idRegister, 2000);
							}
							cbtn.setDefaultAction();
						} catch (IOException e1) {
							progress.setString("�޷����ӵ���������");
							progress.setIndeterminate(false);
							e1.printStackTrace();
						}
					}
				};
				
				cbtn.setActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						progress.setStringPainted(false);
						progress.setIndeterminate(false);
						for(int i=200;i>=0;i--){
							progress.setSize(i, progress.getHeight());
							try {
								Thread.sleep(2);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							if(i%2==0){
								Graphics g1=progress.getGraphics();
								progress.paintAll(g1);
								g1.dispose();
							}
						}
						progress.setVisible(false);
						for(int i=190;i<=570;i++){
							setSize(getWidth(), i);
							if(i%2==0){
								Graphics g=getGraphics();
								paintAll(g);
								g.dispose();
							}
						}
						thread.interrupt();
						cbtn.setDefaultAction();
					}
				});

				thread.start();
			}
		});
	}
	
	/**
	 * ��¼
	 * @author ������
	 * @since v1.0
	 * @date 2015��3��18�� ����9:09:28
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(id.getText().equals("�˺�")){
			new Toast(new ImageIcon(".\\res\\error.png"), "�˺Ų���Ϊ�գ�").show(loginPane, id, 2000);
			return;
		}
		if(passwordField.getEchoChar()==0){
			new Toast(new ImageIcon(".\\res\\error.png"), "���벻��Ϊ�գ�").show(loginPane, passwordField, 2000);
			return;
		}
		for(int i=380;i>=190;i--){
			setSize(getWidth(), i);
		}
		Graphics g=getGraphics();
		paintAll(g);
		g.dispose();
		JProgressBar progress=new JProgressBar();
		progress.setBounds(117, 140, 1, 17);
		getLayeredPane().setLayer(progress, JLayeredPane.getLayer(lbl)+1);
		getLayeredPane().add(progress);
		progress.setString("�������ӷ�����");
		for(int i=1;i<=200;i++){
			progress.setSize(i, progress.getHeight());
			try {
				Thread.sleep(2);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Graphics g1=progress.getGraphics();
			progress.paintAll(g1);
			g1.dispose();
		}
		progress.setStringPainted(true);
		progress.setIndeterminate(true);

		Thread thread=new Thread(){
			
			@Override
			public void run(){
				try {
					Client.current=new Client(Integer.parseInt(id.getText()), String.valueOf(passwordField.getPassword()));
					Client.startService();
					User user=Client.getUser(Integer.parseInt(id.getText()));
					user.setIp(InetAddress.getLocalHost().getHostAddress());
					Client.editUser(user);
					FileOperator.setId(Integer.parseInt(id.getText()));
					if(cbx.isSelected()){
						char[] p=passwordField.getPassword();
						for(int i=0;i<p.length;i++){
							p[i]=(char) (p[i]^Main.PASSWORD_OPERATOR);
						}
						FileOperator.writePassword(p);
					}else{
						FileOperator.deletePassword();
					}
					ClientUI frame=new ClientUI(user);
					frame.setVisible(true);
					new Thread(){

						@Override
						public void run() {
							for(int i=190;i>=0;i--){
								setSize(getWidth(), i);
							}
							Graphics g=getGraphics();
							paintAll(g);
							g.dispose();
							for(int i=1;i<=660;i+=2){
								frame.setSize(frame.getWidth(), i);
								g=frame.getGraphics();
								frame.paintAll(g);
								g.dispose();
							}
							frame.setSize(frame.getWidth(), 660);
							g=frame.getGraphics();
							frame.paintAll(g);
							g.dispose();
						}
					}.start();
					dispose();
				} catch (IOException e1) {
					progress.setString("�޷����ӵ���������");
					progress.setIndeterminate(false);
					e1.printStackTrace();
				} catch (LoginFailedException e) {
					progress.setStringPainted(false);
					progress.setIndeterminate(false);
					for(int i=200;i>=0;i--){
						progress.setSize(i, progress.getHeight());
						Graphics g1=progress.getGraphics();
						progress.paintAll(g1);
						g1.dispose();
					}
					progress.setVisible(false);
					for(int i=190;i<=380;i++){
						setSize(getWidth(), i);
					}
					Graphics g=getGraphics();
					paintAll(g);
					g.dispose();
					passwordField.setText("");
					cbtn.setDefaultAction();
					new Toast(new ImageIcon(".\\res\\error.png"), "�������").show(loginPane, passwordField, 2000);
				}
			}
		};
		
		cbtn.setActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				progress.setStringPainted(false);
				progress.setIndeterminate(false);
				for(int i=200;i>=0;i--){
					progress.setSize(i, progress.getHeight());
					try {
						Thread.sleep(2);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					if(i%2==0){
						Graphics g1=progress.getGraphics();
						progress.paintAll(g1);
						g1.dispose();
					}
				}
				progress.setVisible(false);
				for(int i=190;i<=380;i++){
					setSize(getWidth(), i);
					if(i%2==0){
						Graphics g=getGraphics();
						paintAll(g);
						g.dispose();
					}
				}
				thread.interrupt();
				cbtn.setDefaultAction();
			}
		});

		thread.start();
	}
}
