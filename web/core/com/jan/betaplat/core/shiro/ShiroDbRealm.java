/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import com.jan.betaplat.core.common.IPSeeker;
import com.jan.betaplat.core.exception.AccountLoseException;
import com.jan.betaplat.core.exception.IncorrectCaptchaException;
import com.jan.betaplat.core.po.User;
import com.jan.betaplat.core.po.UserLoginInfo;
import com.jan.betaplat.core.po.UserRole;
import com.jan.betaplat.core.service.UserLoginInfoService;
import com.jan.betaplat.core.service.UserRoleService;
import com.jan.betaplat.core.service.UserService;



/** 
 * desc:用户访问控制
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	protected UserService userService;
	
	protected UserRoleService userRoleService;
	
	protected UserLoginInfoService userLoginInfoService;
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对
	 * 描述
	 */	
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		
		String parm = token.getCaptcha();
		String c = (String)SecurityUtils.getSubject().getSession()
				.getAttribute(SimpleCaptchaServlet.CAPTCHA_KEY);
		// 忽略大小写
		if (!parm.equalsIgnoreCase(c)) {
			throw new IncorrectCaptchaException("验证码错误！"); 
		} 
		
		
		UserLoginInfo userLoginInfo = userLoginInfoService.findByUsername(token.getUsername());
		if(userLoginInfo == null){
			userLoginInfo = new UserLoginInfo(token.getUsername());
		}
		User user = userService.get(token.getUsername());
		if (user != null) {
			if (user.getStatus().intValue() == 1) {
				throw new DisabledAccountException();
			}
			if(user.getLosedate().after(new Date())&&user.getEffdate().before(new Date())){
				//用户正常
			}else{
				// 用户有效期不正常
				throw new AccountLoseException();
			}
			
			byte[] salt = Encodes.decodeHex(user.getSalt());
			
			ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(),user,userLoginInfo);
			updateUserLoginInfo(token.getHost(), userLoginInfo);
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
		
	}

	
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		List<UserRole> userRoles = userRoleService.find(shiroUser.getId());
		shiroUser.getUser().setUserRoles(userRoles);
		
		if (!userRoles.isEmpty()) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (UserRole userRole : userRoles) {
				//基于Permission的权限信息
				info.addStringPermissions(userRole.getRole().getPermissionList());
			}
			return info;
		} else {
			return null;
		}
	}
	
	private void updateUserLoginInfo(String host, UserLoginInfo userLoginInfo){
		userLoginInfo.setLastip(userLoginInfo.getCurip());
		userLoginInfo.setCurip(host);
		userLoginInfo.setLastloginarea(userLoginInfo.getCurloginarea());
		userLoginInfo.setCurloginarea(IPSeeker.getInstance().getAddress(host));
		userLoginInfo.setErrorcount(0);
		userLoginInfo.setLastloginstate(userLoginInfo.getCurloginstate());
		userLoginInfo.setCurloginstate(UserLoginInfo.LOGIN_SUCCESS);
		userLoginInfo.setLastlogintime(userLoginInfo.getCurlogintime());
		userLoginInfo.setCurlogintime(new Date());
		
		userLoginInfoService.save(userLoginInfo);

	}
	
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	public HashPassword encrypt(String plainText) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainText.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * 设置 userRoleService 的值  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**
	 * @param userLoginInfoService the userLoginInfoService to set
	 */
	public void setUserLoginInfoService(UserLoginInfoService userLoginInfoService) {
		this.userLoginInfoService = userLoginInfoService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String loginName;
		private User user;
		private UserLoginInfo userLoginInfo;
		
		public ShiroUser() {
			
		}
		
		/**  
		 * 构造函数
		 * @param id
		 * @param loginName
		 * @param email
		 * @param createTime
		 * @param status  
		 */ 
		public ShiroUser(Long id, String loginName, User user,UserLoginInfo userLoginInfo) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
			this.userLoginInfo = userLoginInfo;
		}

		/**  
		 * 返回 id 的值   
		 * @return id  
		 */
		public Long getId() {
			return id;
		}

		/**  
		 * 返回 loginName 的值   
		 * @return loginName  
		 */
		public String getLoginName() {
			return loginName;
		}

		/**  
		 * 返回 user 的值   
		 * @return user  
		 */
		public User getUser() {
			return user;
		}

		/**
		 * @return the userLoginInfo
		 */
		public UserLoginInfo getUserLoginInfo() {
			return userLoginInfo;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
	}
}
