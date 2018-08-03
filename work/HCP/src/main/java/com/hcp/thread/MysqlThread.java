package com.hcp.thread;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.hcp.util.BaseData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MysqlThread {
	private String mName = null;
	private ExecutorService mExec = null;
	private HikariDataSource mDs = null;

	public MysqlThread(String name) {
		this.mName = name;
		this.mExec = Executors.newSingleThreadExecutor();
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(
				"jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("JIjimaoimoma0402");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		mDs = new HikariDataSource(config);
	}

	public String getDbName() {
		return this.mName;
	}

	public void quarySqlAsync(String sql, Consumer<? super BaseData> action, ExecutorService exec) {
		CompletableFuture<BaseData> f = CompletableFuture.supplyAsync(() -> {
			return execQuarySql(sql);
		}, this.mExec);
		f.thenAcceptAsync(action, exec);
	}

	public BaseData execQuarySql(String sql) {
		try {
			PreparedStatement ps = this.mDs.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> types = new ArrayList<String>();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String name = metaData.getColumnName(i + 1);
				String type = metaData.getColumnTypeName(i + 1);
				names.add(name);
				types.add(type);
			}
			BaseData bd = new BaseData(null);
			bd.createObject();
			while (rs.next()) {
				Iterator<String> nameit = names.iterator();
				Iterator<String> typeit = types.iterator();
				int index = 1;
				while (nameit.hasNext()) {
					String name = nameit.next();
					String type = typeit.next();
					if (type.equals("BIGINT")) {
						bd.putObject(name, rs.getLong(index++));
					} else if (type.equals("VARCHAR")) {
						bd.putObject(name, rs.getString(index++));
					}else if (type.equals("INT")) {
						bd.putObject(name, rs.getString(index++));
					}
				}
			}
			return bd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
