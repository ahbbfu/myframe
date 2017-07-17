package com.zhisou.api.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**ID**/
	private Long id;

	/**登录名**/
	private String loginName;

	/**真实姓名**/
	private String realName;

	/**昵称**/
	private String nickname;

	/**密码(MD5加密后)**/
	private String password;

	/**邮箱**/
	private String email;

	/**手机号码**/
	private String phone;

	/**最后登录时间**/
	private java.util.Date lastLoginTime;

	/**启用禁用(0：启用，1：禁用)**/
	private Integer isDisabled;

	/**删除状态（0.未删除;1.已删除）**/
	private Integer deleted;

	/**创建时间**/
	private java.util.Date createTime;



	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	public String getLoginName(){
		return this.loginName;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public java.util.Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setIsDisabled(Integer isDisabled){
		this.isDisabled = isDisabled;
	}

	public Integer getIsDisabled(){
		return this.isDisabled;
	}

	public void setDeleted(Integer deleted){
		this.deleted = deleted;
	}

	public Integer getDeleted(){
		return this.deleted;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

}
