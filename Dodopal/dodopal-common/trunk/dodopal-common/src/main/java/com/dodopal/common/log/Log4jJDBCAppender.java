package com.dodopal.common.log;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.jdbc.JDBCAppender;

public class Log4jJDBCAppender extends JDBCAppender {

	protected String datasourceJNDIName;

	public String getDatasourceJNDIName() {
		return datasourceJNDIName;
	}

	public void setDatasourceJNDIName(String datasourceJNDIName) {
		this.datasourceJNDIName = datasourceJNDIName;
	}

	@Override
	public Connection getConnection() {
		Connection conn = null;
		DataSource ds;
		javax.naming.Context context;
		try {
			context = new javax.naming.InitialContext();
			ds = (DataSource) context.lookup(datasourceJNDIName);
			conn = ds.getConnection();
		}	catch (Exception e) {
			throw new RuntimeException("Error from JDBC Logger creating datasource connection " + e.getMessage());
		}
		return conn;
	}

}
