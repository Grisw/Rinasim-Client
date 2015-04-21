package org.rinasim.network;

import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.text.StyledDocument;

import org.rinasim.frame.ClientUI;
import org.rinasim.frame.Dialog;
import org.rinasim.frame.Login;
import org.rinasim.object.LoginFailedException;
import org.rinasim.object.Message;
import org.rinasim.object.User;
import org.rinasim.util.FileOperator;
import org.rinasim.util.Identifier;
import org.rinasim.util.Time;
import org.rinasim.widget.FriendPanel;

/**
 * �ͻ���
 * @author ������
 * @date 2015��3��19�� ����10:07:12
 * @since v1.0
 */
public class Client extends Thread{
	
	/**
	 * ��ǰ�ͻ������߳�
	 */
	public static Client current;
	
	private static Socket socket;
	private static ObjectInputStream reader;
	private static ObjectOutputStream writer;
	
	private static int id;
	private static String password;
	
	private static boolean isReturned=false;
	
	private static boolean booleanReturned;
	private static User userReturned;
	private static List<FriendPanel> panelReturned=new ArrayList<FriendPanel>();
	private static List<Message> msgReturned=null;
	private static String timeReturned=null;
	private static String broadReturned=null;
	
	private static Map<Integer, Boolean> booleanMapReturned;


	/**
	 * ��ʼ���ͻ���
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws LoginFailedException 
	 */
	public Client(int id,String password) throws UnknownHostException, IOException, LoginFailedException{
		socket=new Socket(FileOperator.getServerIp(), FileOperator.getServerPort());
		socket.setTcpNoDelay(true);
		writer=new ObjectOutputStream(socket.getOutputStream());
		writer.writeInt(Identifier.LOGIN);
		writer.writeInt(id);
		writer.writeUTF(password);
		writer.flush();
		reader=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		if(reader.readBoolean()==false){
			throw new LoginFailedException();
		}else{
			Client.id=id;
			Client.password=password;
		}
	}
	
	/**
	 *  д����Ϣ
	 * @param doc д�����Ϣ
	 * @param id �Է�ID
	 * @throws IOException 
	 */
	public static synchronized void writeMsg(StyledDocument doc,int id) throws IOException{
		writer.writeInt(Identifier.MSG);
		writer.writeInt(id);
		writer.writeObject(new Message(doc, Time.getDate(), Message.FROM));
		writer.flush();
	}
	
	/**
	 * д����Ϣ
	 * @param msg ��Ϣ
	 * @param id �Է�ID
	 * @throws IOException 
	 */
	public static synchronized void writeMsg(Message msg,int id) throws IOException{
		writer.writeInt(Identifier.MSG);
		writer.writeInt(id);
		if(msg.isFile())
			writer.writeObject(new Message(msg.getFileName(), msg.getFileLength(), Time.getDate(), Message.FROM));
		else
			writer.writeObject(new Message(msg.getMessage(), Time.getDate(), Message.FROM));
		writer.flush();
	}

	/**
	 * ������Ϣ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			while(true){
				int identifier=reader.readInt();
				if(identifier==Identifier.MSG){
					int id=reader.readInt();
					try {
						Message msg = (Message) reader.readObject();
						if(getFrdName(id)!=null){
							ClientUI.current.setHasMsg(true, id);
							ClientUI.current.addMessage(msg, id);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else if(identifier==Identifier.SERVER){
					timeReturned=reader.readUTF();
					broadReturned=reader.readUTF();
					isReturned=true;
				}else if(identifier==Identifier.DELETE_FRIEND){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}else if(identifier==Identifier.EDIT_PASSWORD){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}else if(identifier==Identifier.EDIT_USER_INFO){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}else if(identifier==Identifier.GET_USER_INFO){
					try {
						userReturned=(User) reader.readObject();
					} catch (ClassNotFoundException e) {
						userReturned=null;
						e.printStackTrace();
					}
					isReturned=true;
				}else if(identifier==Identifier.GET_FRIEND_TREE){
					try {
						panelReturned=(List<FriendPanel>) reader.readObject();
					} catch (ClassNotFoundException e) {
						panelReturned=new ArrayList<FriendPanel>();
						e.printStackTrace();
					}
					isReturned=true;
				}else if(identifier==Identifier.ADD_FRIEND){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}else if(identifier==Identifier.OFFLINE_MESSAGE){
					try {
						msgReturned=(List<Message>) reader.readObject();
					} catch (ClassNotFoundException e) {
						msgReturned=null;
						e.printStackTrace();
					}
					isReturned=true;
				}else if(identifier==Identifier.GET_FRIEND_STATUS){
					try {
						booleanMapReturned=(Map<Integer, Boolean>) reader.readObject();
					} catch (ClassNotFoundException e) {
						booleanMapReturned=new HashMap<Integer, Boolean>();
						e.printStackTrace();
					}
					isReturned=true;
				}else if(identifier==Identifier.SET_FRIEND_NOTE){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}else if(identifier==Identifier.GET_RECORD){
					try {
						msgReturned=(List<Message>) reader.readObject();
					} catch (ClassNotFoundException e) {
						msgReturned=new ArrayList<Message>();
						e.printStackTrace();
					}
					isReturned=true;
				}else if(identifier==Identifier.CLEAR_RECORD){
					booleanReturned=reader.readBoolean();
					isReturned=true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			new Dialog("�޷����ӵ����������������磡", Dialog.TYPE_MESSAGE, null).setVisible(true);
			while(true){
				try {
					new Client(id, password).start();
					break;
				} catch (IOException e1) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				} catch (LoginFailedException e1) {
					new Dialog("��¼ʧ�ܣ������µ�¼��", Dialog.TYPE_MESSAGE, null).setVisible(true);
					try{
						if(reader!=null)
							reader.close();
						if(socket!=null)
							socket.close();				
					} catch(IOException e2){
						e2.printStackTrace();
					}
					Login frame=new Login();
					for(int i=1;i<=380;i++){
						if(i%2==0){
							frame.setSize(frame.getWidth(), i);
							try {
								Thread.sleep(1);
							} catch (InterruptedException e2) {
								e2.printStackTrace();
							}
						}
					}
					Graphics g=frame.getGraphics();
					frame.paintAll(g);
					g.dispose();
					break;
				}
			}
		}
	}
	
	/**
	 * ��������
	 */
	public static void startService(){
		if(current!=null){
			current.start();
		}
	}
	
	/**
	 * ��ȡ�û�		
	 * @param id �û�ID
	 * @return �û�
	 * @throws IOException 
	 */
	public static synchronized User getUser(int id) throws IOException{
		try {
			writer.writeInt(Identifier.GET_USER_INFO);
			writer.writeInt(id);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return userReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return null;
	}
	
	/**
	 * �޸��û�		
	 * @param user �û�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 * @throws IOException 
	 */
	public static synchronized boolean editUser(User user) throws IOException{
		try {
			writer.writeInt(Identifier.EDIT_USER_INFO);
			writer.writeObject(user);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
	
	/**
	 * �޸��û�����		
	 * @param old �û�������
	 * @param newp �û�������
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 * @throws IOException 
	 */
	public static synchronized boolean editPasw(String old,String newp) throws IOException{
		try {
			writer.writeInt(Identifier.EDIT_PASSWORD);
			writer.writeUTF(old);
			writer.writeUTF(newp);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
	
	/**
	 * ����û�		
	 * @param user �û�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized static boolean addUser(User user) throws UnknownHostException, IOException{
		Socket tmp=null;
		ObjectOutputStream twriter=null;
		try {
			tmp=new Socket(FileOperator.getServerIp(), FileOperator.getServerPort());
			twriter=new ObjectOutputStream(tmp.getOutputStream());
			twriter.writeInt(Identifier.REGISTER);
			twriter.writeObject(user);
			twriter.flush();
			return new ObjectInputStream(tmp.getInputStream()).readBoolean();
		} finally {
			if(tmp!=null)
				try {
					tmp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if(twriter!=null)
				try {
					twriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * ��ȡ��ϵ�˱�		
	 * @return ��ϵ����
	 * @throws IOException 
	 */
	public static synchronized List<FriendPanel> getFrdTable() throws IOException{
		try {
			writer.writeInt(Identifier.GET_FRIEND_TREE);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return panelReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return null;
	}
	
	/**
	 * �����ϵ��		
	 * @param frdid ��ϵ��ID
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 * @throws IOException 
	 */
	public static synchronized boolean appendFrd(int frdid) throws IOException{
		try {
			writer.writeInt(Identifier.ADD_FRIEND);
			writer.writeInt(frdid);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
	
	/**
	 * ɾ����ϵ��		
	 * @param frdid ��ϵ��ID
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 * @throws IOException 
	 */
	public static synchronized boolean deleteFrd(int frdid) throws IOException{
		try {
			writer.writeInt(Identifier.DELETE_FRIEND);
			writer.writeInt(frdid);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
	
	/**
	 * ��ȡ��ϵ���ǳ�		
	 * @param frdId ��ϵ��ID
	 * @return ������ڷ����ǳƣ����򷵻�null
	 */
	private static String getFrdName(int frdId){
		for(int i=0;i<panelReturned.size();i++){
			if(panelReturned.get(i).getId()==frdId){
				return panelReturned.get(i).getName();
			}
		}
		return null;
	}

	/**
	 * ��ȡ������Ϣ
	 * @param frdId
	 * @return
	 * @throws IOException 
	 */
	public static List<Message> getOfflineMessage(int frdId) throws IOException{
		try {
			writer.writeInt(Identifier.OFFLINE_MESSAGE);
			writer.writeInt(frdId);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return msgReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return null;
	}
	
	/**
	 * ��ȡ��ϵ������״̬
	 * @return
	 * @throws IOException 
	 */
	public static Map<Integer, Boolean> getFrdStatus() throws IOException{
		try {
			writer.writeInt(Identifier.GET_FRIEND_STATUS);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanMapReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return null;
	}
	
	/**
	 * ������Ϣ�Ѷ�
	 * @param frdId
	 */
	public static void readMessage(int frdId){
		try {
			writer.writeInt(Identifier.READ_MESSAGE);
			writer.writeInt(frdId);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ñ�ע
	 * @param frdId
	 * @param note
	 * @return
	 * @throws IOException
	 */
	public static boolean setFrdNote(int frdId, String note) throws IOException{
		try {
			writer.writeInt(Identifier.SET_FRIEND_NOTE);
			writer.writeInt(frdId);
			writer.writeUTF(note);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
	
	/**
	 * ��ȡ�㲥
	 * @author ������
	 * @date 2015��4��17�� ����7:25:42
	 * @since v1.0
	 * @return
	 * @throws IOException
	 */
	public static String getBroadCast() throws IOException{
		try {
			writer.writeInt(Identifier.SERVER);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return broadReturned+"<br />"+timeReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return "Unknown";
	}
	
	/**
	 * �һ�����	
	 * @author ������
	 * @date 2015��4��18�� ����12:43:02
	 * @since v1.0
	 * @param id
	 * @param email
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public synchronized static boolean forgotPassword(int id, String email) throws UnknownHostException, IOException{
		Socket tmp=null;
		ObjectOutputStream twriter=null;
		try {
			tmp=new Socket(FileOperator.getServerIp(), FileOperator.getServerPort());
			twriter=new ObjectOutputStream(tmp.getOutputStream());
			twriter.writeInt(Identifier.FORGOT_PASSWORD);
			twriter.writeInt(id);
			twriter.writeUTF(email);
			twriter.flush();
			return new ObjectInputStream(tmp.getInputStream()).readBoolean();
		} finally {
			if(tmp!=null)
				try {
					tmp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if(twriter!=null)
				try {
					twriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * �ϴ��ļ�
	 * @author ������
	 * @date 2015��4��21�� ����10:42:40
	 * @since v1.0
	 * @param file
	 * @param pro
	 * @return
	 * @throws IOException
	 */
	public synchronized static boolean uploadFile(File file, JProgressBar pro) throws IOException{
		if(!file.isFile())
			return false;
		Socket tmp=new Socket(FileOperator.getServerIp(), FileOperator.getServerPort()+100);
		
		ObjectOutputStream cmdWriter=new ObjectOutputStream(tmp.getOutputStream());
		cmdWriter.writeInt(Identifier.UPLOAD_FILE);
		cmdWriter.writeUTF(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1));
		cmdWriter.writeInt((int) file.length());
		cmdWriter.flush();

		DataOutputStream fileWriter=new DataOutputStream(cmdWriter);
		FileInputStream fis=new FileInputStream(file);
		try {
			byte[] buffer=new byte[1024];
			while (true) {
                int len = 0;
                if (fis != null) {
                    len = fis.read(buffer);
                }
                if (len == -1) {
                    break;
                }
                fileWriter.write(buffer);
                fileWriter.flush();
                if(pro!=null&&pro.isVisible())
                	pro.setValue(pro.getValue()+len);
            }
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(fis!=null)
					fis.close();
				if(fileWriter!=null)
					fileWriter.close();
				if(tmp!=null)
					tmp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �����ļ�
	 * @author ������
	 * @date 2015��4��21�� ����11:16:43
	 * @since v1.0
	 * @param dir
	 * @param name
	 * @param length
	 * @param pro
	 * @return
	 * @throws IOException
	 */
	public synchronized static boolean downloadFile(File dir, String name, int length, JProgressBar pro) throws IOException{
		if(!dir.isDirectory()){
			dir.mkdirs();
		}
		File file=new File(dir, name);
		if(!file.isFile()){
			file.createNewFile();
		}else{
			file.delete();
			file.createNewFile();
		}
		
		Socket tmp=new Socket(FileOperator.getServerIp(), FileOperator.getServerPort()+100);
		
		ObjectOutputStream cmdWriter=new ObjectOutputStream(tmp.getOutputStream());
		cmdWriter.writeInt(Identifier.DOWNLOAD_FILE);
		cmdWriter.writeUTF(name);
		cmdWriter.writeInt(length);
		cmdWriter.flush();

		DataInputStream fileReader=new DataInputStream(tmp.getInputStream());
		FileOutputStream fos=new FileOutputStream(file);
		try {
			byte[] buffer=new byte[1024];
			while (true) {
				int len = 0;
                if (fileReader != null) {
                    len = fileReader.read(buffer);
                }
                if (len == -1) {
                    break;
                }
                fos.write(buffer);
                if(pro!=null&&pro.isVisible())
                	pro.setValue(pro.getValue()+len);
            }
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(fos!=null)
					fos.close();
				if(fileReader!=null)
					fileReader.close();
				if(tmp!=null)
					tmp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡ�����¼
	 * @author ������
	 * @date 2015��4��21�� ����6:41:53
	 * @since v1.0
	 * @param frdId
	 * @return
	 * @throws IOException
	 */
	public synchronized static List<Message> getRecord(int frdId) throws IOException{
		try {
			writer.writeInt(Identifier.GET_RECORD);
			writer.writeInt(frdId);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return msgReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return null;
	}
	
	/**
	 * ��������¼
	 * @author ������
	 * @date 2015��4��21�� ����6:58:37
	 * @since v1.0
	 * @param frdId
	 * @return
	 * @throws IOException
	 */
	public synchronized static boolean clearRecord(int frdId) throws IOException{
		try {
			writer.writeInt(Identifier.CLEAR_RECORD);
			writer.writeInt(frdId);
			writer.flush();
			while(isReturned==false){
				Thread.sleep(10);
			}
			return booleanReturned;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isReturned=false;
		}
		return false;
	}
}
