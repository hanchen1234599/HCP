package com.hcp.thread;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hcp.data.BaseData;
import com.hcp.data.Commond;
import com.mysql.cj.conf.ModifiableMemorySizeProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.netty.channel.Channel;

public class MysqlThread implements ThreadDealInterface {
	private String mName = null;
	private boolean mState = false;
	private ExecutorService mExec = null;
	private MysqlLogic mLogic = null;

	public MysqlThread(AppThreadLogicManager app, String name) {
		this.mName = name;
		this.mExec = Executors.newSingleThreadExecutor();
		this.mLogic = new MysqlLogic(app);
		this.mState = mLogic.initHikariCp();
	}

	@Override
	public void exec(Commond commond) {
		this.mLogic.addCommond(commond);
		this.mExec.submit(this.mLogic);
	}

	@Override
	public void addCommond(Commond commond) {
		this.mLogic.addCommond(commond);
	}
}

class MysqlLogic implements Callable<Integer> {
	private AppThreadLogicManager mApp;
	private HikariDataSource mDs = null;
	private ConcurrentLinkedQueue<Commond> mCommonds = new ConcurrentLinkedQueue<Commond>();// 这里存放命令列表

	public MysqlLogic(AppThreadLogicManager app) {
		this.mApp = app;
	}

	public void addCommond(Commond commond) {
		mCommonds.add(commond);
	}

	public boolean initHikariCp() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(
				"jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("JIjimaoimoma0402");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		mDs = new HikariDataSource(config);
		return true;
	}

	@Override
	public Integer call() throws Exception {
		while (mCommonds.isEmpty() != true) {
			Commond commond = mCommonds.poll();
			System.out.println("mysql thread  deal:" + commond.getmComKey());
			if (commond.getmComKey().equals("select")) {
				execQuarySql(commond.getmComValue(), commond.getmResponse(), commond.getmData());
			} else if (commond.getmComKey().equals("select")) {
				execUpdateSql(commond.getmComValue());
			}
		}
		return null;
	}

	public void execQuarySql(BaseData arg, Object response, Object[] objs) {
		try {
			String sql = (String) arg.getObject("sql").getBaseValue();
			PreparedStatement ps = this.mDs.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> types = new ArrayList<String>();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String name = metaData.getColumnName(i + 1);
				String type = metaData.getColumnTypeName(i+ 1);
				names.add(name);
				types.add(type);
			}
			BaseData bd = new BaseData(null);
			bd.createObject();
			while(rs.next()) {
				Iterator<String> nameit =  names.iterator();
				Iterator<String> typeit =  types.iterator();
				int index = 1;
				while(nameit.hasNext()) {
					String name = nameit.next();
					String type = typeit.next();
					if(type.equals("BIGINT")) {
						bd.putObject(name, rs.getLong(index ++));
					}else if(type.equals("VARCHAR")) {
						bd.putObject(name, rs.getString(index++));
					}
				}
			}
			if(response != null) {
				if(response instanceof ThreadDealInterface) {
					ThreadDealInterface exec = (ThreadDealInterface) response;
					
					exec.exec(null);
				}else if(response instanceof Channel) {
					Channel ctx = (Channel) response;
					ctx.write(bd);
					ctx.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execUpdateSql(BaseData arg) {

	}

}

// commond 格式
