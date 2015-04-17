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
	private int type;

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
	
}
