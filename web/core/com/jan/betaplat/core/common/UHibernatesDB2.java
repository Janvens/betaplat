/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-26下午1:56:09</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.common;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-26 </p>
 * @version V1.0  
 */
public class UHibernatesDB2{
public static final String DATETIME_TYPE = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
	
	public static void initLazyProperty(Object proxyedPropertyValue){
		Hibernate.initialize(proxyedPropertyValue);
	}
	
	public static String getDialect(DataSource dataSource) {
		String jdbcUrl = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException(
						"Connection returned by DataSource [" + dataSource
								+ "] was null");
			}
			jdbcUrl = connection.getMetaData().getURL();
		} catch (SQLException e) {
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		if (StringUtils.contains(jdbcUrl, ":h2:"))
			return H2Dialect.class.getName();
		if (StringUtils.contains(jdbcUrl, ":mysql:"))
			return MySQL5InnoDBDialect.class.getName();
		if (StringUtils.contains(jdbcUrl, ":oracle:")) {
			return Oracle10gDialect.class.getName();
		}
		if(StringUtils.contains(jdbcUrl, ":db2:")){
			return DB2Dialect.class.getName();
		}
		throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
	}
}
