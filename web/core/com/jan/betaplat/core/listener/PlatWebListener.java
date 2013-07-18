/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-14下午5:29:56</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.jan.betaplat.core.common.CommonConfig;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-14 </p>
 * @version V1.0  
 */
public class PlatWebListener extends ContextLoaderListener implements ServletContextListener{
	
	/* 
	 * desc:
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event){
		super.contextDestroyed(event);
		ServletContext context = event.getServletContext();
		WebApplicationContext webApplicationContext =WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		((AbstractRefreshableWebApplicationContext)webApplicationContext).close();
	}
	
	/* 
	 * desc:
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event){
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		String appPath = context.getRealPath("");
		String cfgFilePath=context.getInitParameter("cfgFilePath");
		if(null==cfgFilePath){
			cfgFilePath =appPath+"/WEB-INF/classes/config/platConfig.properties";
		}
		CommonConfig.loadConfig(cfgFilePath);
	}
	
}
