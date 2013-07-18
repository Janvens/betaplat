/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-5下午4:55:13</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service;

import com.jan.betaplat.core.po.UserLoginInfo;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-5 </p>
 * @version V1.0  
 */
public interface UserLoginInfoService extends BaseService<UserLoginInfo, Long>{

	UserLoginInfo findByUsername(String username);
}
