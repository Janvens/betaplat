
package com.jan.betaplat.core.po;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.google.common.collect.Lists;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Entity
@Table(schema="umpay", name="t_smp_user")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="umpay_platCache")
public class User extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -4277639149589431277L;
	
	@Column(nullable=false, length=32, unique=true, updatable=false)
	private String username;
	@Column(nullable=false, length=64)
	private String password;
	@Column(nullable=false, length=64, updatable=false)
	private String realname;
	@Column(nullable=false, length=128)
	private String email;
	@Column(nullable=false, length=32)
	private String phone;
	@Column(nullable=false, length=32)
	private String salt;
	@Column(nullable=false, length=1)
	private Short sex;
	private Date effdate;
	private Date losedate;
	@Column(nullable=false, length=1)
	private Short status;
	@Column(nullable=false, length=1)
	private Short cardtype;
	@Column(nullable=false, length=32)
	private String cardno;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date intime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date modtime;
	
	@Transient
	private String plainPassword;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.DETACH)
	@OrderBy("priority ASC")
	private List<UserRole> userRoles = Lists.newArrayList();
	
	@OneToMany(mappedBy="user", cascade=CascadeType.DETACH)
	@OrderBy("priority ASC")
	private List<UserGroup> userGroups = Lists.newArrayList();

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the sex
	 */
	public Short getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Short sex) {
		this.sex = sex;
	}

	/**
	 * @return the effdate
	 */
	public Date getEffdate() {
		return effdate;
	}

	/**
	 * @param effdate the effdate to set
	 */
	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}

	/**
	 * @return the losedate
	 */
	public Date getLosedate() {
		return losedate;
	}

	/**
	 * @param losedate the losedate to set
	 */
	public void setLosedate(Date losedate) {
		this.losedate = losedate;
	}

	/**
	 * @return the status
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * @return the cardtype
	 */
	public Short getCardtype() {
		return cardtype;
	}

	/**
	 * @param cardtype the cardtype to set
	 */
	public void setCardtype(Short cardtype) {
		this.cardtype = cardtype;
	}

	/**
	 * @return the cardno
	 */
	public String getCardno() {
		return cardno;
	}

	/**
	 * @param cardno the cardno to set
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * @return the intime
	 */
	public Date getIntime() {
		return intime;
	}

	/**
	 * @param intime the intime to set
	 */
	public void setIntime(Date intime) {
		this.intime = intime;
	}

	/**
	 * @return the modtime
	 */
	public Date getModtime() {
		return modtime;
	}

	/**
	 * @param modtime the modtime to set
	 */
	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}

	/**
	 * @return the plainPassword
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**
	 * @param plainPassword the plainPassword to set
	 */
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/**
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * @return the userGroups
	 */
	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	/**
	 * @param userGroups the userGroups to set
	 */
	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	
	
}
