package kr.wise.commons.cmm.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.wise.commons.cmm.service.MessengerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("MessengerService")
public class MessengerServiceImpl implements MessengerService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Connection getConnection(String driver, String jdbcUrl, String user, String password) throws SQLException, Exception {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
//			con.setAutoCommit(false);
			return con;
		}catch(SQLException e){
			logger.error(e.toString());
			throw e;
		}catch(Exception e){
			logger.error(e.toString());
			throw e;
		}
	}
	
	@Override
	public int sendMsg(String sendUser, String targetUser, String content, String url, String kindName) throws Exception {
		//실제 반영시
//		String driver = "com.tmax.tibero.jdbc.TbDriver";
//		String jdbcUrl = "jdbc:tibero:thin:@10.130.6.219:8629:KOSPOUC";
//		String user = "MessageMng";
//		String pw = "aajngab23f!Wcf";
//		
//		Connection con = getConnection(driver, jdbcUrl, user, pw);
//		
//		StringBuffer sb = new StringBuffer();
//
//		sb.setLength(0);
//		sb.append("\n INSERT INTO M_ALARM (    ");
//		sb.append("\n    NUM                   ");
//		sb.append("\n   ,SENDUSER              ");
//		sb.append("\n   ,TARGETUSER            ");
//		sb.append("\n   ,REGDATE               ");
//		sb.append("\n   ,CONTENT               ");
//		sb.append("\n   ,URL                   ");
//		sb.append("\n   ,TF                    ");
//		sb.append("\n   ,KIND                  ");
//		sb.append("\n   ,KINDNAME              ");
//		sb.append("\n   ,ON_FLAG               ");
//		sb.append("\n ) VALUES (               ");
//		sb.append("\n   AUTOSEQ.NEXTVAL        ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,SYSDATE               ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,'0'                   ");
//		sb.append("\n   ,'1'                   ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,'0'                   ");
//		sb.append("\n )                        ");
//
//		int cnt = 0;
//		
//		PreparedStatement pstmt = con.prepareStatement(sb.toString());
//		pstmt.clearParameters();
//		
//		pstmt.setString(1, sendUser);
//		pstmt.setString(2, targetUser);
//		pstmt.setString(3, content);
//		pstmt.setString(4, url);
//		pstmt.setString(5, kindName);
//		
//		cnt = pstmt.executeUpdate();
		
		//test용
//		String driver = "oracle.jdbc.driver.OracleDriver";
//		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/orcl";
//		String user = "WISEDA_BASE";
//		String pw = "wise1012";
//		
//		Connection con = getConnection(driver, jdbcUrl, user, pw);
//		
//		StringBuffer sb = new StringBuffer();
//
//		sb.setLength(0);
//		sb.append("\n INSERT INTO T_TEMP (     ");
//		sb.append("\n    COL1                  ");
//		sb.append("\n   ,COL2                  ");
//		sb.append("\n   ,COL3                  ");
//		sb.append("\n   ,COL4                  ");
//		sb.append("\n   ,COL5                  ");
//		sb.append("\n   ,COL6                  ");
//		sb.append("\n   ,COL7                  ");
//		sb.append("\n   ,COL8                  ");
//		sb.append("\n   ,COL9                  ");
//		sb.append("\n   ,COL10                 ");
//		sb.append("\n ) VALUES (               ");
//		sb.append("\n   '1'        ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,SYSDATE               ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,'0'                   ");
//		sb.append("\n   ,'1'                   ");
//		sb.append("\n   ,?                     ");
//		sb.append("\n   ,'0'                   ");
//		sb.append("\n )                        ");
//
		int cnt = 0;
//		
//		PreparedStatement pstmt = con.prepareStatement(sb.toString());
//		pstmt.clearParameters();
//		
//		pstmt.setString(1, sendUser);
//		pstmt.setString(2, targetUser);
//		pstmt.setString(3, content);
//		pstmt.setString(4, url);
//		pstmt.setString(5, kindName);
//		
//		cnt = pstmt.executeUpdate();

		return cnt;
	}
}
