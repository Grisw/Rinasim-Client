package org.rinasim.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.text.StyledDocument;

/**
 * ������Ϣ
 * @author ������
 * @date 2015��3��17�� ����10:09:17
 * @since v1.0
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -8689340103713911133L;

	/**
	 * ���յ���Ϣ����
	 */
	public static final int FROM=1;
	
	/**
	 * ���͵���Ϣ����
	 */
	public static final int TO=2;

	private StyledDocument doc;
	private String time;
	private int fileLength;
	private String fileName;
	private int type;
	private boolean isFile;

	/**
	 * ������Ϣ
	 * @param msg ��Ϣ����
	 * @param time ��Ϣ����ʱ��
	 * @param type ��Ϣ���ͣ�PopMessage.FROM �� PopMessage.TO
	 */
	public Message(StyledDocument doc, String time,	int type){
		StyledDocument document=null;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(baos);
			oos.writeObject(doc);
			oos.flush();
			oos.close();
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			document=(StyledDocument) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.doc=document;
		this.time=time;
		this.type=type;
		this.isFile=false;
	}
	
	/**
	 * �����ļ���Ϣ
	 * @date 2015��4��20�� ����9:08:55
	 * @since v1.0
	 * @param fileName
	 * @param fileLength
	 * @param time
	 * @param type
	 */
	public Message(String fileName, int fileLength, String time, int type){
		this.fileName=fileName;
		this.fileLength=fileLength;
		this.time=time;
		this.type=type;
		this.isFile=true;
	}
	
	/**
	 * ��ȡ��Ϣ����
	 * @return ��Ϣ����
	 */
	public StyledDocument getMessage(){
		return doc;
	}
	
	/**
	 * ��ȡʱ��
	 * @return ��Ϣ���͵�ʱ��
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * ��ȡ��Ϣ����
	 * @return ��Ϣ���ͣ�PopMessage.FROM �� PopMessage.TO
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * �Ƿ����ļ�
	 * @author ������
	 * @date 2015��4��20�� ����9:04:17
	 * @since v1.0
	 * @return
	 */
	public boolean isFile(){
		return isFile;
	}
	
	/**
	 * ��ȡ�ļ�����
	 * @author ������
	 * @date 2015��4��20�� ����9:04:47
	 * @since v1.0
	 * @return
	 */
	public int getFileLength(){
		return fileLength;
	}
	
	/**
	 * ��ȡ�ļ���
	 * @author ������
	 * @date 2015��4��20�� ����9:07:58
	 * @since v1.0
	 * @return
	 */
	public String getFileName(){
		return fileName;
	}
}
