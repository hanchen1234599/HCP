package com.hcp.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCpDB {
	HikariDataSource mDs = null;
	boolean mState = false;
	public HikariCpDB initHikariCp() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("JIjimaoimoma0402");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		mDs = new HikariDataSource(config);
		return this;
	}
	
	public void execQuarySql(Object arg) {
		
	}
	
	public void execUpdateSql(Object arg) {
		
	}
}
