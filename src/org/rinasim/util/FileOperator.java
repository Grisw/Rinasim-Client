package org.rinasim.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;

/**
 * �ļ�������
 * @author ������
 * @date 2015��3��18�� ����7:38:18
 * @since v1.0
 */
public class FileOperator {

	private static final String PORTRAIT_PATH=".\\data\\portrait.dat";
	private static final String ID_PATH=".\\data\\id.dat";
	private static final String PASSWORD_PATH=".\\data\\password.dat";
	private static final String SERVER_IP_PATH=".\\data\\server_ip.dat";
	private static final String SERVER_PORT_PATH=".\\data\\server_port.dat";
	private static final String BROADCAST_PATH=".\\data\\broadcast.dat";
	
	/**
	 * ��ȡ�����ͷ��
	 * @date 2015��3��18�� ����7:44:06
	 * @since v1.0
	 * @return �����ͷ����������ڣ�����Ĭ��ͷ��
	 */
	public static ImageIcon getPortrait(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(PORTRAIT_PATH));
			return (ImageIcon) reader.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return new ImageIcon(".\\res\\default_figure.png");
	}
	
	/**
	 * ��ȡ�����ID����
	 * @date 2015��3��18�� ����7:52:23
	 * @since v1.0
	 * @return �����ID
	 */
	public static String getID(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(ID_PATH));
			return reader.readInt()+"";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return "";
	}

	/**
	 * ��ȡ������IP��ַ
	 * @author ������
	 * @date 2015��3��19�� ����9:35:52
	 * @since v1.0
	 * @return �趨�ķ�����IP���粻���ڷ���Ĭ��IP:192.168.1.102
	 */
	public static String getServerIp() {
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(SERVER_IP_PATH));
			return reader.readUTF();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return "192.168.1.102";
	}

	/**
	 * �������˿�
	 * @author ������
	 * @date 2015��3��19�� ����9:50:10
	 * @since v1.0
	 * @return ���ر���Ķ˿ںţ�Ĭ��1024
	 */
	public static int getServerPort() {
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(SERVER_PORT_PATH));
			return reader.readInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return 1024;
	}

	/**
	 * ���÷�����IP��ַ
	 * @author ������
	 * @date 2015��3��19�� ����10:01:22
	 * @since v1.0
	 * @param ip
	 */
	public static void setServerIp(String ip) {
		ObjectOutputStream writer=null;
		File file=new File(SERVER_IP_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeUTF(ip);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * �趨�˿ں�
	 * @author ������
	 * @date 2015��3��19�� ����10:04:12
	 * @since v1.0
	 * @param port
	 */
	public static void setServerPort(int port) {
		ObjectOutputStream writer=null;
		File file=new File(SERVER_PORT_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeInt(port);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * ����ID
	 * @author ������
	 * @date 2015��3��20�� ����1:10:08
	 * @since v1.0
	 * @param id
	 */
	public static void setId(int id) {
		ObjectOutputStream writer=null;
		File file=new File(ID_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeInt(id);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * ��������
	 * @author ������
	 * @date 2015��3��20�� ����1:11:33
	 * @since v1.0
	 * @param password
	 */
	public static void writePassword(char[] password) {
		ObjectOutputStream writer=null;
		File file=new File(PASSWORD_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(password);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * ɾ������
	 * @author ������
	 * @date 2015��3��20�� ����1:14:52
	 * @since v1.0
	 */
	public static void deletePassword() {
		File file=new File(PASSWORD_PATH);
		if(file.isFile())
			file.delete();
	}

	/**
	 * ��ȡ���������
	 * @author ������
	 * @date 2015��3��20�� ����1:17:26
	 * @since v1.0
	 * @return
	 */
	public static char[] getPassword() {
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(PASSWORD_PATH));
			return (char[]) reader.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/**
	 * ��ȡ����
	 * @return ʱ��+����
	 * @throws IOException 
	 */
	public static String getBroadCast(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(BROADCAST_PATH));
			return reader.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return "Unknown";
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * ���������Ϣ
	 * @param msg ����
	 */
	public static void writeBroadCast(String msg){
		ObjectOutputStream writer=null;
		File file=new File(BROADCAST_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeUTF(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
