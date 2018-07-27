package com.hcp.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import com.hcp.data.BaseData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCpDBTest {
	public static void main(String[] args) throws Exception {
		System.out.println("--------------being test hikaricp---------------");
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("JIjimaoimoma0402");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		HikariDataSource ds = new HikariDataSource(config);
		try {
			PreparedStatement sql = ds.getConnection().prepareStatement("select * from t1");
			ResultSet rs = sql.executeQuery();
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
			System.out.println(bd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("--------------end test hikaricp---------------");
	}
}
