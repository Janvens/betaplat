/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-5-29上午10:27:51</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-5-29 </p>
 * @version V1.0  
 */
@Entity
@Table(schema="umpay", name="t_smp_user_group")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="umpay_platCache")
public class UserGroup extends IdEntity{

	/**
	 * long:serialVersionUID 
	 */
	private static final long serialVersionUID = 7110486736151027493L;
	
	/**优先级，值越小，优先级越高 */
	@Column(nullable=false, length=2)
	private int priority;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="groupId")
	private Group group;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
