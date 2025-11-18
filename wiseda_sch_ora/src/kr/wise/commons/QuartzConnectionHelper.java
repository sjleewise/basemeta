package kr.wise.commons;

import java.sql.Connection;
import java.sql.SQLException;

public class QuartzConnectionHelper implements org.quartz.utils.ConnectionProvider {

	@Override
	public Connection getConnection() throws SQLException {
		
		Connection con = null;
		
		try {
			con = ConnectionHelper.getDAConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	} 

	@Override
	public void shutdown() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
