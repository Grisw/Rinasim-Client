package org.rinasim.util;

/**
 * 信息标识符
 * @author 刘旭涛
 * @date 2015年3月17日 上午10:08:26
 * @since v1.0
 */
public interface Identifier {

	/**
	 * 服务器发送的信息
	 * 格式： SERVER->String time->String msg
	 */
	public static final int SERVER=1;
	
	/**
	 * 普通信息
	 * 格式： MSG->int to->Message msg
	 */
	public static final int MSG=2;
	
	/**
	 * 登陆信息
	 * 格式：LOGIN->int id->String password
	 */
	public static final int LOGIN=3;
	
	/**
	 * 获取用户信息
	 * 格式：GET_USER_INFO->int id
	 */
	public static final int GET_USER_INFO=4;
	
	/**
	 * 注册信息
	 * 格式：REGISTER->User user
	 */
	public static final int REGISTER=5;
	
	/**
	 * 修改用户信息
	 * 格式：EDIT_USER_INFO->User user
	 */
	public static final int EDIT_USER_INFO=6;
	
	/**
	 * 修改用户密码信息
	 * 格式：EDIT_PASSWORD->String oldPasw->String newPasw
	 */
	public static final int EDIT_PASSWORD=7;
	
	/**
	 * 获取好友列表信息
	 * 格式：GET_FRIEND_TREE
	 */
	public static final int GET_FRIEND_TREE=8;

	/**
	 * 添加联系人信息
	 * 格式：ADD_FRIEND->int frdId
	 */
	public static final int ADD_FRIEND=9;
	
	/**
	 * 删除联系人信息
	 * 格式：DELETE_FRIEND->int frdId
	 */
	public static final int DELETE_FRIEND=10;
	
	/**
	 * 推送更新
	 * 格式：PUSH_UPDATE->int length->String time->String version
	 */
	public static final int PUSH_UPDATE=11;
	
	/**
	 * 请求更新
	 * 格式：UPDATE->int length->String name->byte[] data
	 */
	public static final int UPDATE=12;

	/**
	 * 离线消息
	 * 格式：OFFLINE_MESSAGE->int frdId
	 */
	public static final int OFFLINE_MESSAGE=13;
	
	/**
	 * 联系人状态
	 * 格式：GET_FRIEND_STATUS
	 */
	public static final int GET_FRIEND_STATUS=14;
	
	/**
	 * 设置联系人备注
	 * 格式：SET_FRIEND_NOTE->int frdId->String note
	 */
	public static final int SET_FRIEND_NOTE=15;
	
	/**
	 * 已读消息
	 * 格式：READ_MESSAGE->int frdId
	 */
	public static final int READ_MESSAGE=16;

}
