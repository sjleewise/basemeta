package kr.wise.commons.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import kr.wise.commons.ConnectionHelper;

public class CommOrgDAO {

	/**
	 * Logger for this class
	 */
	
	private static final Logger logger = Logger.getLogger(CommOrgDAO.class);
	
	private Connection con = null;
	private Connection con_tgt = null;
	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int pstmtmtIdx = 1;
	
	public void doCollectionCommOrg() throws Exception {
		
		try {
			// DB Connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			// 대상 DB Connection
			con_tgt = ConnectionHelper.getConnection("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@10.175.144.211:1521:hamodb1","SUNGGWA_USER","sunggwa");
			con_tgt.setAutoCommit(false);
			
			// 데이터 insert
			insertCommOrg(con,con_tgt);
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con_tgt.rollback();
		} finally {
			if(rs    != null) {
				try { rs.close(); } catch(Exception igonred) {}
			}
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
				try { con_tgt.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	public void insertCommOrg(Connection con, Connection con_tgt) throws SQLException {
		
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE FROM DATAMGMT.SUNGGWA_USER ");
		
		StringBuilder selectSQL = new StringBuilder();
		StringBuilder insertSQL = new StringBuilder();
		
		// 가져올 데이터 쿼리
		selectSQL.append("\n SELECT                        ");
		selectSQL.append("\n        ORGNZT_ID 			   ");
		selectSQL.append("\n      , ORGNZT_NM              ");
		selectSQL.append("\n      , ORGNZT_ALTRTV_NM       ");
		selectSQL.append("\n      , ORGNZT_ABBREV_NM       ");
		selectSQL.append("\n      , ORGNZT_CD              ");
		selectSQL.append("\n      , ORGNZT_UPPER_ID        ");
		selectSQL.append("\n      , ORGNZT_ORDR            ");
		selectSQL.append("\n      , ORGNZT_SERVER          ");
		selectSQL.append("\n      , ORGNZT_TY              ");
		selectSQL.append("\n      , ORGNZT_DC              ");
		selectSQL.append("\n      , ORGNZT_CHF_OFCPS       ");
		selectSQL.append("\n      , ORGNZT_EXTRL_NM        ");
		selectSQL.append("\n      , INSTT_ID               ");
		selectSQL.append("\n      , ORGNZT_HOME            ");
		selectSQL.append("\n      , ORGNZT_EMAIL           ");
		selectSQL.append("\n      , ORGNZT_ADRES           ");
		selectSQL.append("\n      , ORGNZT_DETAIL_ADRES    ");
		selectSQL.append("\n      , ORGNZT_ZIP             ");
		selectSQL.append("\n      , ORGNZT_TELNO           ");
		selectSQL.append("\n      , ORGNZT_FAX             ");
		selectSQL.append("\n      , ORGNZT_GRAD_NO         ");
		selectSQL.append("\n      , ORGNZT_TOP_ID          ");
		selectSQL.append("\n      , ORGNZT_CHF_ID          ");
		selectSQL.append("\n      , PSITNINSTT_AT          ");
		selectSQL.append("\n      , DELETE_AT              ");
		selectSQL.append("\n      , RESERVED1              ");
		selectSQL.append("\n      , RESERVED2              ");
		selectSQL.append("\n      , RESERVED3              ");
		selectSQL.append("\n      , REGIST_DT              ");
		selectSQL.append("\n      , DELETE_DT              ");
		selectSQL.append("\n   FROM v_orgnzt   ");
		
		// 데이터 입력 쿼리
		insertSQL.append("\nINSERT INTO DATAMGMT.SUNGGWA_USER (");
	    insertSQL.append("\n  ORGNZT_ID		");
	    insertSQL.append("\n ,ORGNZT_NM		");                               
	    insertSQL.append("\n ,ORGNZT_ALTRTV_NM ");                                 
	    insertSQL.append("\n ,ORGNZT_ABBREV_NM ");                             
	    insertSQL.append("\n ,ORGNZT_CD        ");                  
	    insertSQL.append("\n ,ORGNZT_UPPER_ID  ");                    
	    insertSQL.append("\n ,ORGNZT_ORDR      ");            
	    insertSQL.append("\n ,ORGNZT_SERVER    ");          
	    insertSQL.append("\n ,ORGNZT_TY       	");
	    insertSQL.append("\n ,ORGNZT_DC        ");                            
	    insertSQL.append("\n ,ORGNZT_CHF_OFCPS ");                                   
	    insertSQL.append("\n ,ORGNZT_EXTRL_NM  ");                                  
	    insertSQL.append("\n ,INSTT_ID         ");                           
	    insertSQL.append("\n ,ORGNZT_HOME      ");                              
	    insertSQL.append("\n ,ORGNZT_EMAIL     ");                           
	    insertSQL.append("\n ,ORGNZT_ADRES     ");                          
	    insertSQL.append("\n ,ORGNZT_DETAIL_ADRES  ");                             
	    insertSQL.append("\n ,ORGNZT_ZIP       ");                 
	    insertSQL.append("\n ,ORGNZT_TELNO     ");                            
	    insertSQL.append("\n ,ORGNZT_FAX       ");                        
	    insertSQL.append("\n ,ORGNZT_GRAD_NO   ");                              
	    insertSQL.append("\n ,ORGNZT_TOP_ID    ");                         
	    insertSQL.append("\n ,ORGNZT_CHF_ID    ");                          
	    insertSQL.append("\n ,PSITNINSTT_AT    ");                          
	    insertSQL.append("\n ,DELETE_AT        ");                      
	    insertSQL.append("\n ,RESERVED1        ");                      
	    insertSQL.append("\n ,RESERVED2        ");                      
	    insertSQL.append("\n ,RESERVED3        ");                      
	    insertSQL.append("\n ,REGIST_DT        ");                      
	    insertSQL.append("\n ,DELETE_DT        ");  
		insertSQL.append("\n) VALUES (                      ");
		insertSQL.append("\n  ?,?,?,?,?,                    ");
		insertSQL.append("\n  ?,?,?,?,?,                    ");
		insertSQL.append("\n  ?,?,?,?,?,                    ");
		insertSQL.append("\n  ?,?,?,?,?,                    ");
		insertSQL.append("\n  ?,?,?,?,?,                    ");
		insertSQL.append("\n  ?,?,?,?,?                    ");
		insertSQL.append("\n)                               ");
		
		try {
			
			//코드분류체계 삭제
			pstmt = null;
			pstmt = con.prepareStatement(deleteSQL.toString());
			pstmt.executeUpdate();
	//
	//		con.commit();
	//		
			// 대상DB 코드분류체계 조회
			pstmt = null;
			rs = null;
			pstmt = con_tgt.prepareStatement(selectSQL.toString());
			rs = pstmt.executeQuery();
	
			logger.debug("확인용");
			logger.debug(rs.toString());
			
			// meta rep db insert
			pstmt = null;
			pstmt = con.prepareStatement(insertSQL.toString());
			
			logger.debug("확인용2");
			
			while (rs.next()) {
				pstmtmtIdx = 1;
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_ID"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_ALTRTV_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_ABBREV_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_CD"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_UPPER_ID"));
				pstmt.setInt(pstmtmtIdx++, rs.getInt("ORGNZT_ORDR"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_SERVER"));
				pstmt.setInt(pstmtmtIdx++, rs.getInt("ORGNZT_TY"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_DC"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_CHF_OFCPS"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_EXTRL_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("INSTT_ID"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_HOME"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_EMAIL"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_ADRES"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_DETAIL_ADRES"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_ZIP"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_TELNO"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_FAX"));
				pstmt.setInt(pstmtmtIdx++, rs.getInt("ORGNZT_GRAD_NO"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_TOP_ID"));
				pstmt.setString(pstmtmtIdx++, rs.getString("ORGNZT_CHF_ID"));
				pstmt.setString(pstmtmtIdx++, rs.getString("PSITNINSTT_AT"));
				pstmt.setInt(pstmtmtIdx++, rs.getInt("DELETE_AT"));
				pstmt.setString(pstmtmtIdx++, rs.getString("RESERVED1"));
				pstmt.setString(pstmtmtIdx++, rs.getString("RESERVED2"));
				pstmt.setString(pstmtmtIdx++, rs.getString("RESERVED3"));
				pstmt.setDate(pstmtmtIdx++, rs.getDate("REGIST_DT"));
				pstmt.setDate(pstmtmtIdx++, rs.getDate("DELETE_DT"));
				pstmt.addBatch();
			}
			
			logger.debug("확인용3");
			
			pstmt.executeBatch();
			
			con.commit();
			
			//Batch 초기화
	        pstmt.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
            
            //실패시 rollback 해줘야함
            try {
                con_tgt.rollback();                
            }catch(Exception e1) {
                e1.printStackTrace();
            }
		} finally {
            if(pstmt != null){pstmt.close(); pstmt = null;}
            if(con_tgt != null){con_tgt.close(); con_tgt = null;}
		}
	}
}
