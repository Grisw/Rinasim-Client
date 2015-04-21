package org.rinasim.util;

/**
 * 信息标识符
 * @author 刘旭涛
 * @date 2015年3月15日 下午8:33:14
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
	 * 获取聊天记录
	 * 格式：GET_RECORD->int frdId
	 */
	public static final int GET_RECORD=11;

	/**
	 * 清空聊天记录
	 * 格式：CLEAR_RECORD->int frdId
	 */
	public static final int CLEAR_RECORD=12;
	
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

	/**
	 * 忘记密码
	 * 格式：FORGOT_PASSWORD->int id->String email
	 */
	public static final int FORGOT_PASSWORD=17;
	
	/**
	 * 上传文件
	 * 格式：UPLOAD_FILE->String name->int length->byte[] data
	 */
	public static final int UPLOAD_FILE=18;
	
	/**
	 * 下载文件
	 * 格式：DOWNLOAD_FILE->String name->int length
	 */
	public static final int DOWNLOAD_FILE=19;
}
