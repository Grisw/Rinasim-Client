package org.rinasim.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.text.StyledDocument;

/**
 * 聊天信息
 * @author 刘旭涛
 * @date 2015年3月17日 上午10:09:17
 * @since v1.0
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -8689340103713911133L;

	/**
	 * 接收的消息类型
	 */
	public static final int FROM=1;
	
	/**
	 * 发送的消息类型
	 */
	public static final int TO=2;

	private StyledDocument doc;
	private String time;
	private int fileLength;
	private String fileName;
	private int type;
	private boolean isFile;

	/**
	 * 创建消息
	 * @param msg 消息内容
	 * @param time 消息发送时间
	 * @param type 消息类型，PopMessage.FROM 或 PopMessage.TO
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
	 * 创建文件信息
	 * @date 2015年4月20日 下午9:08:55
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
	 * 获取消息内容
	 * @return 消息内容
	 */
	public StyledDocument getMessage(){
		return doc;
	}
	
	/**
	 * 获取时间
	 * @return 消息发送的时间
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * 获取消息类型
	 * @return 消息类型，PopMessage.FROM 或 PopMessage.TO
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * 是否是文件
	 * @author 刘旭涛
	 * @date 2015年4月20日 下午9:04:17
	 * @since v1.0
	 * @return
	 */
	public boolean isFile(){
		return isFile;
	}
	
	/**
	 * 获取文件数据
	 * @author 刘旭涛
	 * @date 2015年4月20日 下午9:04:47
	 * @since v1.0
	 * @return
	 */
	public int getFileLength(){
		return fileLength;
	}
	
	/**
	 * 获取文件名
	 * @author 刘旭涛
	 * @date 2015年4月20日 下午9:07:58
	 * @since v1.0
	 * @return
	 */
	public String getFileName(){
		return fileName;
	}
}
