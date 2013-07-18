/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-5下午4:44:32</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
 * desc:用户登录信息表
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-5 </p>
 * @version V1.0  
 */
@Entity
@Table(schema="umpay", name="t_smp_userlogininfo")
public class UserLoginInfo implements Serializable{

	public static final Integer LOGIN_FAIL = Integer.valueOf("1");
	public static final Integer LOGIN_SUCCESS = Integer.valueOf("0");
	
	/**
	 * long:serialVersionUID 
	 */
	private static final long serialVersionUID = 2302223937379303782L;
	
	@Id
	private String username;
	private String curip = "";
	private String lastip = "";
	private String curloginarea = "";
	private String lastloginarea = "";
	private Integer errorcount = Integer.valueOf("0");
	private Integer curloginstate = Integer.valueOf("0");
	private Integer lastloginstate = Integer.valueOf("0");
	@Temporal(TemporalType.TIMESTAMP)
	private Date curlogintime = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastlogintime = new Date();
	
	public UserLoginInfo(){}
	public UserLoginInfo(String username){
		this.username = username;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the curip
	 */
	public String getCurip() {
		return curip;
	}
	/**
	 * @param curip the curip to set
	 */
	public void setCurip(String curip) {
		this.curip = curip;
	}
	/**
	 * @return the lastip
	 */
	public String getLastip() {
		return lastip;
	}
	/**
	 * @param lastip the lastip to set
	 */
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	/**
	 * @return the curloginarea
	 */
	public String getCurloginarea() {
		return curloginarea;
	}
	/**
	 * @param curloginarea the curloginarea to set
	 */
	public void setCurloginarea(String curloginarea) {
		this.curloginarea = curloginarea;
	}
	/**
	 * @return the lastloginarea
	 */
	public String getLastloginarea() {
		return lastloginarea;
	}
	/**
	 * @param lastloginarea the lastloginarea to set
	 */
	public void setLastloginarea(String lastloginarea) {
		this.lastloginarea = lastloginarea;
	}
	/**
	 * @return the errorcount
	 */
	public Integer getErrorcount() {
		return errorcount;
	}
	/**
	 * @param errorcount the errorcount to set
	 */
	public void setErrorcount(Integer errorcount) {
		this.errorcount = errorcount;
	}
	/**
	 * @return the curloginstate
	 */
	public Integer getCurloginstate() {
		return curloginstate;
	}
	/**
	 * @param curloginstate the curloginstate to set
	 */
	public void setCurloginstate(Integer curloginstate) {
		this.curloginstate = curloginstate;
	}
	/**
	 * @return the lastloginstate
	 */
	public Integer getLastloginstate() {
		return lastloginstate;
	}
	/**
	 * @param lastloginstate the lastloginstate to set
	 */
	public void setLastloginstate(Integer lastloginstate) {
		this.lastloginstate = lastloginstate;
	}
	/**
	 * @return the curlogintime
	 */
	public Date getCurlogintime() {
		return curlogintime;
	}
	/**
	 * @param curlogintime the curlogintime to set
	 */
	public void setCurlogintime(Date curlogintime) {
		this.curlogintime = curlogintime;
	}
	/**
	 * @return the lastlogintime
	 */
	public Date getLastlogintime() {
		return lastlogintime;
	}
	/**
	 * @param lastlogintime the lastlogintime to set
	 */
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	
	
}
