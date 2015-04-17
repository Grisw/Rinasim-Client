package org.rinasim.object;

import java.io.Serializable;
import java.util.Date;

import javax.swing.ImageIcon;

/**
 * 用户对象
 * @author 刘旭涛
 * @date 2015年3月18日 下午12:56:07
 * @since v1.0
 */
public class User implements Serializable{

	private static final long serialVersionUID = -7474127554963548186L;
	
	private int id;
	private ImageIcon portrait;
	private String name;
	private String password;
	private String sex;
	private Date birthday;
	private String email;
	private int age;
	private String constellation;
	private String occupation;
	private String company;
	private String school;
	private String address;
	private String introduction;
	private String ip;
	private boolean status;
	
	/**
	 * 获取ID
	 * @return 用户ID
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 设定ID
	 * @param id 用户ID,1~4294967296
	 */
	public void setId(int id){	
		this.id=id;
	}
	
	/**
	 * 获取头像
	 * @return 头像
	 */
	public ImageIcon getPortrait() {
		return portrait;
	}

	/**
	 * 设置头像
	 * @param portrait 要设置的头像Image
	 */
	public void setPortrait(ImageIcon portrait) {
		this.portrait = portrait;
	}

	/**
	 * 获取用户名
	 * @return 用户名
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 设定用户名
	 * @param nickname 用户名,不大于18个字符
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setName(String name){
		if(name.length()<1||name.length()>18){
			return false;
		}else{
			this.name=name;
			return true;
		}
	}
	
	/**
	 * 获取密码
	 * @return 用户密码
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 设定密码
	 * @param password 密码,不大于50个字符
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setPassword(String password){
		if(password.length()<6||password.length()>50){
			return false;
		}else{
			this.password=password;
			return true;
		}
	}
	
	/**
	 * 获取性别
	 * @return 用户性别
	 */
	public String getSex(){
		return sex;
	}
	
	/**
	 * 设定性别
	 * @param sex 性别，男或女或保密
	 */
	public void setSex(String sex){
		this.sex=sex;
	}	
	
	/**
	 * 获取生日
	 * @return 生日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置生日
	 * @param birthday 要设置的生日
	 */
	public void setBirthday(Date birthday) {
		this.birthday=birthday;
	}

	/**
	 * 获取email地址
	 * @return email地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置email地址
	 * @param email 要设置的 email地址
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setEmail(String email) {
		if(email.length()<=320&&email.matches("^\\w{1,64}@\\w{1,}\\.\\w{1,}$")){
			this.email=email;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取地址
	 * @return 用户地址
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * 设定地址
	 * @param address 地址,不大于50个字符
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setAddress(String address){
		if(address.length()>50){
			return false;
		}else{
			this.address=address;
			return true;
		}
	}
	
	/**
	 * 获取年龄
	 * @return 用户年龄
	 */
	public int getAge(){
		return age;
	}
	
	/**
	 * 设定年龄
	 * @param age 年龄,1~100
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setAge(int age){
		if(age<1||age>100){
			return false;
		}else{
			this.age=age;
			return true;
		}
	}
	
	/**
	 * 获取星座
	 * @return 星座
	 */
	public String getConstellation() {
		return constellation;
	}

	/**
	 * 设置星座
	 * @param constellation 要设置的星座
	 */
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	/**
	 * 获取职业
	 * @return 职业
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * 设置职业
	 * @param occupation 要设置的职业
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * 获取公司
	 * @return 公司名称
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * 设置公司
	 * @param company 要设置的公司名称，长度不超过50个字符
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setCompany(String company) {
		if(company.length()>50){
			return false;
		}else{
			this.company = company;
			return true;
		}
	}

	/**
	 * 获取学校名称
	 * @return 学校名称
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * 设置学校名称
	 * @param school 要设置的学校名称，长度不超过50个字符
	 * @return 
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setSchool(String school) {
		if(school.length()>50){
			return false;
		}else{
			this.school = school;
			return true;
		}
	}

	/**
	 * 获取自我说明
	 * @return 自我说明
	 */
	public String getIntroduction(){
		return introduction;
	}
	
	/**
	 * 设定自我说明
	 * @param remark 自我说明,不大于127个字符
	 * @return true 修改成功，false 修改失败
	 */
	public boolean setIntroduction(String introduction){
		if(introduction.length()>127){
			return false;
		}else{
			this.introduction=introduction;
			return true;
		}
	}
	
	/**
	 * 获取IP
	 * @return 用户IP
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * 设定IP
	 * @param ip 符合格式的IP地址
	 */
	public void setIp(String ip){	
		this.ip=ip;
	}

	/**
	 * 是否在线
	 * @return true 在线，false 不在线
	 */
	public boolean isOnline() {
		return status;
	}

	/**
	 * 设置状态
	 * @param status true 在线，false 不在线
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}
