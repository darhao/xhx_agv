package com.jimi.xhx_agv.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jimi.xhx_agv.constant.SystemProperties;
import com.jimi.xhx_agv.entity.model.MappingKit;
import com.jimi.xhx_agv.util.PropUtil;

public class DataSourceConfig {
	
	private static final Logger logger = LogManager.getLogger();

	private static DruidPlugin dp;
	private static ActiveRecordPlugin arp;
	
	
	public void shutdown() {
		arp.stop();
		dp.stop();
	}
	
	
	public void start() {
		String mysqlUrl = null;
		String mysqlUser = null;
		String mysqlPassword = null;
		
		String env = null;
		
		if(isProductionEnvironment()) {
			mysqlUrl = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.P_MYSQL_URL);
			mysqlUser = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.P_MYSQL_USER);
			mysqlPassword = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.P_MYSQL_PASSWORD);
			env = "生产";
		} else if(isTestEnvironment()) {
			mysqlUrl = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.T_MYSQL_URL);
			mysqlUser = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.T_MYSQL_USER);
			mysqlPassword = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.T_MYSQL_PASSWORD);
			env = "测试";
		} else {
			mysqlUrl = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_URL);
			mysqlUser = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_USER);
			mysqlPassword = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_PASSWORD);
			env = "开发";
		}
		
		dp = new DruidPlugin(mysqlUrl, mysqlUser, mysqlPassword);
		
		arp = new ActiveRecordPlugin(dp);
	    arp.setDialect(new MysqlDialect());
	    arp.setShowSql(false);
	    MappingKit.mapping(arp);
	    
	    dp.start();
	    arp.start();
	    
	    logger.info("数据库连接成功，当前是" + env + "环境");
	}

	
	private boolean isProductionEnvironment() {
		File[] roots = File.listRoots();
        for (int i=0; i < roots.length; i++) {
            if(new File(roots[i].toString() + "PRODUCTION_ENVIRONMENT_FLAG").exists()) {
            	return true;
            }
        }
        return false;
	}

	private boolean isTestEnvironment() {
		File[] roots = File.listRoots();
        for (int i=0; i < roots.length; i++) {
            if(new File(roots[i].toString() + "TEST_ENVIRONMENT_FLAG").exists()) {
            	return true;
            }
        }
        return false;
	}
	
	
	public static Connection getLog4j2JDBCAppenderConnection() throws SQLException {
		return dp.getDataSource().getConnection();
	}
	
}
