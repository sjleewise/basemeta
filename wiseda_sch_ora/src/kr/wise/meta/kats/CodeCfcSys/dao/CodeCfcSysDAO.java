package kr.wise.meta.kats.CodeCfcSys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class CodeCfcSysDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CodeCfcSysDAO.class);

	private Connection con = null;
	private Connection tgtCon = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int pstmtIdx = 1;
	
	public void doProcess(TargetDbmsDM trgDM) throws SQLException, Exception {

		try {
			//meta rep db connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			//대상 DB connection
			tgtCon = ConnectionHelper.getConnection(trgDM.getJdbc_driver(), trgDM.getConnect_string(), trgDM.getDb_user(), trgDM.getDb_pwd());
			tgtCon.setAutoCommit(false);
			
			//코드분류체계 meta rep insert
			insertMetaCodeCfcSys(con, tgtCon, trgDM);
			//코드분류체계항목 meta rep insert
			insertMetaCodeCfcSysItem(con, tgtCon, trgDM);
			
			//코드분류체계 신규 이관
			insertCodeCfcSys(con, tgtCon, trgDM);
			//코드분류체계 변경 이관
			updateCodeCfcSys(con, tgtCon, trgDM);
			//코드분류체계 삭제 이관
			deleteCodeCfcSys(con, tgtCon, trgDM);
			
			//코드분류체계항목 신규 이관
			insertCodeCfcSysItem(con, tgtCon, trgDM);
			//코드분류체계항목 변경 이관
			updateCodeCfcSysItem(con, tgtCon, trgDM);
			//코드분류체계항목 삭제 이관
			deleteCodeCfcSysItem(con, tgtCon, trgDM);
			
			con.commit();
			tgtCon.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
			tgtCon.rollback();
		}finally {
			if(rs    != null) {
				try { rs.close(); } catch(Exception igonred) {}
			}
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
				try { tgtCon.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	
	public void insertMetaCodeCfcSys(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE TCM_STD_NO_CL_SYSTM_C ");
		deleteSQL.append("\n WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		deleteSQL.append("\n    AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		
		
		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("\n SELECT ");
		selectSQL.append("\n 		STD_NO_CL_SYSTM_ID	--VARCHAR(15)	PK0	표준번호분류체계ID ");
		selectSQL.append("\n 		,CL_SYSTM_NM	--VARCHAR(200)	Not Null	분류체계명 ");
		selectSQL.append("\n 		,CL_SYSTM_ENG_NM	--VARCHAR(200)	분류체계영문명 ");
		selectSQL.append("\n 		,CL_SYSTM_TY_CODE	--VARCHAR(4)	Not Null	분류체계유형코드 ");
		selectSQL.append("\n 		,CL_SYSTM_FOM_CN	--VARCHAR(100)	분류체계형식내용 ");
		selectSQL.append("\n 		,OBJECT_DC	--VARCHAR(4000)	객체설명 ");
		selectSQL.append("\n 		,TABLE_NM	--VARCHAR(40)	테이블명 ");
		selectSQL.append("\n 		,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ");
		selectSQL.append("\n 		,UPDT_DT	--DATE Not Null	수정일시 ");
		selectSQL.append("\n 		,REGISTER_ID	--VARCHAR(20)	Not Null	등록자ID ");
		selectSQL.append("\n 		,REGIST_DT	--DATE	Not Null	등록일시 ");
		selectSQL.append("\n 		,USE_AT  ");
		selectSQL.append("\n  FROM TCM_STD_NO_CL_SYSTM_C ");
		selectSQL.append("\n WHERE NVL(USE_AT, 'N') = 'Y' ");
		
		
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("\n INSERT INTO TCM_STD_NO_CL_SYSTM_C ( ") ;
		insertSQL.append("\n    DB_CONN_TRG_ID ") ;
		insertSQL.append("\n    ,DB_SCH_ID  ") ;
		insertSQL.append("\n    ,STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,CL_SYSTM_NM	--VARCHAR(200)	Not Null	분류체계명 ") ;
		insertSQL.append("\n    ,CL_SYSTM_ENG_NM	--VARCHAR(200)	분류체계영문명 ") ;
		insertSQL.append("\n    ,CL_SYSTM_TY_CODE --VARCHAR(4)	Not Null	분류체계유형코드 ") ;
		insertSQL.append("\n    ,CL_SYSTM_FOM_CN	--VARCHAR(100)	분류체계형식내용 ") ;
		insertSQL.append("\n    ,OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,TABLE_NM	--VARCHAR(40)	테이블명 ") ;
		insertSQL.append("\n    ,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,USE_AT ") ;
		insertSQL.append("\n )VALUES( ") ;
		insertSQL.append("\n     ? ") ;
		insertSQL.append("\n    ,? ") ;
		insertSQL.append("\n    ,? -- STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_NM	--VARCHAR(200)	Not Null	분류체계명 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_ENG_NM	--VARCHAR(200)	분류체계영문명 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_TY_CODE --VARCHAR(4)	Not Null	분류체계유형코드 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_FOM_CN	--VARCHAR(100)	분류체계형식내용 ") ;
		insertSQL.append("\n    ,? -- OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,? -- TABLE_NM	--VARCHAR(40)	테이블명 ") ;
		insertSQL.append("\n    ,? -- UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,? -- UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,? -- REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,? -- REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,? -- USE_AT ") ;
		insertSQL.append("\n  ) ") ;
		
		//코드분류체계 삭제
		pstmt = null;
		pstmt = con.prepareStatement(deleteSQL.toString());
		pstmt.executeUpdate();
		
		//대상DB 코드분류체계 조회
		pstmt = null;
		rs = null;
		pstmt = tgtCon.prepareStatement(selectSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = con.prepareStatement(insertSQL.toString());
			pstmt.setString(pstmtIdx++, trgDM.getDbms_id());
			pstmt.setString(pstmtIdx++, trgDM.getDbSchId());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_ENG_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_FOM_CN"));
			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++ ;
		}
		logger.debug (" insertMetaCodeCfcSys : "+ cnt );
	}
	
	public void insertMetaCodeCfcSysItem(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE TCM_STD_NO_CL_SYSTEM_IEM_C ") ;
		deleteSQL.append("\n   WHERE STD_NO_CL_SYSTM_ID IN (SELECT STD_NO_CL_SYSTM_ID ") ;
		deleteSQL.append("\n                                  FROM TCM_STD_NO_CL_SYSTM_C ") ;
		deleteSQL.append("\n                                 WHERE DB_CONN_TRG_ID = '").append(trgDM.getDbms_id()).append("' ");
		deleteSQL.append("\n                                   AND DB_SCH_ID = '").append(trgDM.getDbSchId()).append("' ) ");
		
		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("\n SELECT ");
		selectSQL.append("\n		STD_NO_CL_SYSTM_ID	--VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		selectSQL.append("\n		,CL_SYSTM_IEM_SN	--NUMERIC(5)	PK1	분류체계항목순번 ") ;
		selectSQL.append("\n		,CL_SYSTM_GROUP_ID	--VARCHAR(4)	분류체계그룹ID ") ;
		selectSQL.append("\n		,CL_SYSTM_TY_IEM_CODE	--VARCHAR(4)	분류체계유형항목코드 ") ;
		selectSQL.append("\n		,CL_SYSTM_IEM_FOM_CN	--VARCHAR(100)	Not Null	분류체계항목형식내용 ") ;
		selectSQL.append("\n		,CL_SYSTM_IEM_LT	--NUMERIC(4)	분류체계항목길이 ") ;
		selectSQL.append("\n		,IEM_SPRTR	--VARCHAR(4)	항목구분자 ") ;
		selectSQL.append("\n		,IEM_REFRN_TABLE_NM	--VARCHAR(40)	항목참조테이블명 ") ;
		selectSQL.append("\n		,IEM_REFRN_COLUMN_NM	--VARCHAR(50)	항목참조컬럼명 ") ;
		selectSQL.append("\n		,IEM_REFRN_CODE_ID	--VARCHAR(6)	항목참조코드ID ") ;
		selectSQL.append("\n		,OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		selectSQL.append("\n		,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		selectSQL.append("\n		,UPDT_DT	--DATE	Not Null	수정일시 ") ;
		selectSQL.append("\n		,REGISTER_ID	--VARCHAR(20)	Not Null	등록자ID ") ;
		selectSQL.append("\n		,REGIST_DT	--DATE	Not Null	등록일시 ") ;
		selectSQL.append("\n		,USE_AT ") ;
		selectSQL.append("\n  FROM TCM_STD_NO_CL_SYSTEM_IEM_C ");
		selectSQL.append("\n WHERE NVL(USE_AT, 'N') = 'Y' ");
		
		
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("\n INSERT INTO TCM_STD_NO_CL_SYSTEM_IEM_C ( ") ;
		insertSql.append("\n		STD_NO_CL_SYSTM_ID	--VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSql.append("\n		,CL_SYSTM_IEM_SN	--NUMERIC(5)	PK1	분류체계항목순번 ") ;
		insertSql.append("\n		,CL_SYSTM_GROUP_ID	--VARCHAR(4)	분류체계그룹ID ") ;
		insertSql.append("\n		,CL_SYSTM_TY_IEM_CODE	--VARCHAR(4)	분류체계유형항목코드 ") ;
		insertSql.append("\n		,CL_SYSTM_IEM_FOM_CN	--VARCHAR(100)	Not Null	분류체계항목형식내용 ") ;
		insertSql.append("\n		,CL_SYSTM_IEM_LT	--NUMERIC(4)	분류체계항목길이 ") ;
		insertSql.append("\n		,IEM_SPRTR	--VARCHAR(4)	항목구분자 ") ;
		insertSql.append("\n		,IEM_REFRN_TABLE_NM	--VARCHAR(40)	항목참조테이블명 ") ;
		insertSql.append("\n		,IEM_REFRN_COLUMN_NM	--VARCHAR(50)	항목참조컬럼명 ") ;
		insertSql.append("\n		,IEM_REFRN_CODE_ID	--VARCHAR(6)	항목참조코드ID ") ;
		insertSql.append("\n		,OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSql.append("\n		,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSql.append("\n		,UPDT_DT	--DATE	Not Null	수정일시 ") ;
		insertSql.append("\n		,REGISTER_ID	--VARCHAR(20)	Not Null	등록자ID ") ;
		insertSql.append("\n		,REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSql.append("\n		,USE_AT ") ;
		insertSql.append("\n )VALUES( ") ;
		insertSql.append("\n		 ? --STD_NO_CL_SYSTM_ID	--VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSql.append("\n		,? --CL_SYSTM_IEM_SN	--NUMERIC(5)	PK1	분류체계항목순번 ") ;
		insertSql.append("\n		,? --CL_SYSTM_GROUP_ID	--VARCHAR(4)	분류체계그룹ID ") ;
		insertSql.append("\n		,? --CL_SYSTM_TY_IEM_CODE	--VARCHAR(4)	분류체계유형항목코드 ") ;
		insertSql.append("\n		,? --CL_SYSTM_IEM_FOM_CN	--VARCHAR(100)	Not Null	분류체계항목형식내용 ") ;
		insertSql.append("\n		,? --CL_SYSTM_IEM_LT	--NUMERIC(4)	분류체계항목길이 ") ;
		insertSql.append("\n		,? --IEM_SPRTR	--VARCHAR(4)	항목구분자 ") ;
		insertSql.append("\n		,? --IEM_REFRN_TABLE_NM	--VARCHAR(40)	항목참조테이블명 ") ;
		insertSql.append("\n		,? --IEM_REFRN_COLUMN_NM	--VARCHAR(50)	항목참조컬럼명 ") ;
		insertSql.append("\n		,? --IEM_REFRN_CODE_ID	--VARCHAR(6)	항목참조코드ID ") ;
		insertSql.append("\n		,? --OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSql.append("\n		,? --UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSql.append("\n		,? --UPDT_DT	--DATE	Not Null	수정일시 ") ;
		insertSql.append("\n		,? --REGISTER_ID	--VARCHAR(20)	Not Null	등록자ID ") ;
		insertSql.append("\n		,? --REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSql.append("\n		,? --USE_AT  ") ;
		insertSql.append("\n  ) ") ;
		
		//코드분류체계항목 삭제
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(deleteSQL.toString());
		pstmt.executeUpdate();
		
		
		//대상DB 코드분류체계항목 조회
		pstmt = null;
		rs = null;
		pstmt = tgtCon.prepareStatement(selectSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = con.prepareStatement(insertSql.toString());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_IEM_SN"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_GROUP_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_IEM_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_IEM_FOM_CN"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_LT"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_SPRTR"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_COLUMN_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_CODE_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" insertMetaCodeCfcSysItem : "+ cnt );
	}
	
	
	public void insertCodeCfcSys(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.CODE_CFC_SYS_ID AS STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_LNM AS CL_SYSTM_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_PNM AS CL_SYSTM_ENG_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_CD AS CL_SYSTM_TY_CODE ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_FRM AS CL_SYSTM_FOM_CN ") ;
		strSQL.append("\n        ,NULL AS TABLE_NM ") ;
		strSQL.append("\n        ,A.OBJ_DESCN AS OBJECT_DC ") ;
		strSQL.append("\n        ,A.FRS_RQST_USER_ID AS REGISTER_ID ") ;
		strSQL.append("\n        ,A.FRS_RQST_DTM AS REGIST_DT ") ;
		strSQL.append("\n        ,A.RQST_USER_ID AS UPDUSR_ID ") ;
		strSQL.append("\n        ,A.RQST_DTM AS UPDT_DT ") ;
		strSQL.append("\n        ,'Y' AS USE_AT ") ;
		strSQL.append("\n   FROM WAM_CODE_CFC_SYS A ") ;
		strSQL.append("\n  WHERE NOT EXISTS (SELECT 1 ") ;
		strSQL.append("\n                      FROM TCM_STD_NO_CL_SYSTM_C B ") ;
		strSQL.append("\n                     WHERE B.STD_NO_CL_SYSTM_ID = A.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                        AND B.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n                        AND B.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n                ) ") ;
		

		
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("\n INSERT INTO TCM_STD_NO_CL_SYSTM_C ( ") ;
		insertSQL.append("\n     STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,CL_SYSTM_NM	--VARCHAR(200)	Not Null	분류체계명 ") ;
		insertSQL.append("\n    ,CL_SYSTM_ENG_NM	--VARCHAR(200)	분류체계영문명 ") ;
		insertSQL.append("\n    ,CL_SYSTM_TY_CODE --VARCHAR(4)	Not Null	분류체계유형코드 ") ;
		insertSQL.append("\n    ,CL_SYSTM_FOM_CN	--VARCHAR(100)	분류체계형식내용 ") ;
		insertSQL.append("\n    ,OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,TABLE_NM	--VARCHAR(40)	테이블명 ") ;
		insertSQL.append("\n    ,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,USE_AT ") ;
		insertSQL.append("\n )VALUES( ") ;
		insertSQL.append("\n     ? -- STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_NM	--VARCHAR(200)	Not Null	분류체계명 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_ENG_NM	--VARCHAR(200)	분류체계영문명 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_TY_CODE --VARCHAR(4)	Not Null	분류체계유형코드 ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_FOM_CN	--VARCHAR(100)	분류체계형식내용 ") ;
		insertSQL.append("\n    ,? -- OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,? -- TABLE_NM	--VARCHAR(40)	테이블명 ") ;
		insertSQL.append("\n    ,? -- UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,? -- UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,? -- REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,? -- REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,? -- USE_AT ") ;
		insertSQL.append("\n  ) ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(insertSQL.toString());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_ENG_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_FOM_CN"));
			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" insertCodeCfcSys : "+ cnt );
	}
	
	public void updateCodeCfcSys(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.CODE_CFC_SYS_ID AS STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_LNM AS CL_SYSTM_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_PNM AS CL_SYSTM_ENG_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_CD AS CL_SYSTM_TY_CODE ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_FRM AS CL_SYSTM_FOM_CN ") ;
		strSQL.append("\n        ,NULL AS TABLE_NM ") ;
		strSQL.append("\n        ,A.OBJ_DESCN AS OBJECT_DC ") ;
		strSQL.append("\n        ,A.FRS_RQST_USER_ID AS REGISTER_ID ") ;
		strSQL.append("\n        ,A.FRS_RQST_DTM AS REGIST_DT ") ;
		strSQL.append("\n        ,A.RQST_USER_ID AS UPDUSR_ID ") ;
		strSQL.append("\n        ,A.RQST_DTM AS UPDT_DT ") ;
		strSQL.append("\n        ,'Y' AS USE_AT ") ;
		strSQL.append("\n   FROM WAM_CODE_CFC_SYS A ") ;
		strSQL.append("\n  WHERE  EXISTS (SELECT 1 ") ;
		strSQL.append("\n                  FROM TCM_STD_NO_CL_SYSTM_C B ") ;
		strSQL.append("\n                 WHERE B.STD_NO_CL_SYSTM_ID = A.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                    AND B.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n                    AND B.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n                    AND (NVL(B.CL_SYSTM_NM, '▦') != NVL(A.CODE_CFC_SYS_LNM, '▦') ") ;
		strSQL.append("\n                      OR NVL(B.CL_SYSTM_ENG_NM, '▦') != NVL(A.CODE_CFC_SYS_PNM, '▦') ") ;
		strSQL.append("\n                      OR NVL(B.CL_SYSTM_TY_CODE, '▦') != NVL(A.CODE_CFC_SYS_CD, '▦') ") ;
		strSQL.append("\n                      OR NVL(B.CL_SYSTM_FOM_CN, '▦') != NVL(A.CODE_CFC_SYS_FRM, '▦') ") ;
		strSQL.append("\n                      OR NVL(B.OBJECT_DC, '▦') != NVL(A.OBJ_DESCN, '▦') ) ") ;
		strSQL.append("\n                ) ") ;
		
		
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("\n UPDATE TCM_STD_NO_CL_SYSTM_C  ") ;
		updateSql.append("\n   SET ") ;
		updateSql.append("\n     CL_SYSTM_NM = ?	--VARCHAR(200)	Not Null	분류체계명 ") ;
		updateSql.append("\n    ,CL_SYSTM_ENG_NM = ? 	--VARCHAR(200)	분류체계영문명 ") ;
		updateSql.append("\n    ,CL_SYSTM_TY_CODE = ? --VARCHAR(4)	Not Null	분류체계유형코드 ") ;
		updateSql.append("\n    ,CL_SYSTM_FOM_CN = ?	--VARCHAR(100)	분류체계형식내용 ") ;
		updateSql.append("\n    ,TABLE_NM = ?	--VARCHAR(40)	테이블명 ") ;
		updateSql.append("\n    ,OBJECT_DC = ?	--VARCHAR(4000)	객체설명 ") ;
		updateSql.append("\n    ,UPDUSR_ID = ?	--VARCHAR(20)	Not Null	수정자ID ") ;
		updateSql.append("\n    ,UPDT_DT = ?	-- DATE Not Null	수정일시 ") ;
		updateSql.append("\n    ,REGISTER_ID = ? --VARCHAR(20)	Not Null	등록자ID ") ;
		updateSql.append("\n    ,REGIST_DT = ?	--DATE	Not Null	등록일시 ") ;
		updateSql.append("\n    ,USE_AT = ?	 ") ;
		updateSql.append("\n WHERE STD_NO_CL_SYSTM_ID = ? --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db update
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(updateSql.toString());
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_ENG_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_FOM_CN"));
			pstmt.setString(pstmtIdx++, rs.getString("TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" updateCodeCfcSys : "+ cnt );
		
	}
	
	public void deleteCodeCfcSys(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n   FROM TCM_STD_NO_CL_SYSTM_C A ") ;
		strSQL.append("\n  WHERE A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n     AND NOT EXISTS (SELECT 1 ") ;
		strSQL.append("\n                      FROM WAM_CODE_CFC_SYS B ") ;
		strSQL.append("\n                     WHERE A.STD_NO_CL_SYSTM_ID = B.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                       AND B.REG_TYP_CD IN ('C','U') ") ;
		strSQL.append("\n                   ) ") ;

		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("\n UPDATE TCM_STD_NO_CL_SYSTM_C  ") ;
		deleteSql.append("\n     SET USE_AT = 'N'  ") ;
		deleteSql.append("\n WHERE STD_NO_CL_SYSTM_ID = ? --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db delete
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(deleteSql.toString());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" deleteCodeCfcSys : "+ cnt );
	}
	
	public void insertCodeCfcSysItem(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.CODE_CFC_SYS_ID AS STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_SEQ AS CL_SYSTM_IEM_SN ") ;
		strSQL.append("\n        ,NULL AS CL_SYSTM_GROUP_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_CD AS CL_SYSTM_TY_IEM_CODE ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_FRM AS CL_SYSTM_IEM_FOM_CN ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_LEN AS CL_SYSTM_IEM_LT ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_SPT AS IEM_SPRTR ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_TBL AS IEM_REFRN_TABLE_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_COL AS IEM_REFRN_COLUMN_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_ID AS IEM_REFRN_CODE_ID ") ;
		strSQL.append("\n        ,A.OBJ_DESCN AS OBJECT_DC ") ;
		strSQL.append("\n        ,A.FRS_RQST_USER_ID AS REGISTER_ID ") ;
		strSQL.append("\n        ,A.FRS_RQST_DTM AS REGIST_DT ") ;
		strSQL.append("\n        ,A.RQST_USER_ID AS UPDUSR_ID ") ;
		strSQL.append("\n        ,A.RQST_DTM AS UPDT_DT ") ;
		strSQL.append("\n        ,'Y' AS USE_AT ") ;
		strSQL.append("\n   FROM WAM_CODE_CFC_SYS_ITEM A ") ;
		strSQL.append("\n  WHERE NOT EXISTS (SELECT 1 ") ;
		strSQL.append("\n                  FROM TCM_STD_NO_CL_SYSTEM_IEM_C B ") ;
		strSQL.append("\n                 WHERE B.STD_NO_CL_SYSTM_ID IN (SELECT STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n                                                              FROM TCM_STD_NO_CL_SYSTM_C  ") ;
		strSQL.append("\n                                                             WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n                                                                AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n                                                             ) ") ;
		strSQL.append("\n                   AND B.STD_NO_CL_SYSTM_ID = A.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                   AND B.CL_SYSTM_IEM_SN = A.CODE_CFC_SYS_ITEM_SEQ ") ;
		strSQL.append("\n                ) ") ;
		
		
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("\n INSERT INTO TCM_STD_NO_CL_SYSTEM_IEM_C ( ") ;
		insertSQL.append("\n     STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,CL_SYSTM_IEM_SN ") ;
//		insertSQL.append("\n    ,CL_SYSTM_GROUP_ID ") ;
		insertSQL.append("\n    ,CL_SYSTM_TY_IEM_CODE ") ;
		insertSQL.append("\n    ,CL_SYSTM_IEM_FOM_CN ") ;
		insertSQL.append("\n    ,CL_SYSTM_IEM_LT ") ;
		insertSQL.append("\n    ,IEM_SPRTR ") ;
		insertSQL.append("\n    ,IEM_REFRN_TABLE_NM ") ;
		insertSQL.append("\n    ,IEM_REFRN_COLUMN_NM ") ;
		insertSQL.append("\n    ,IEM_REFRN_CODE_ID ") ;
		insertSQL.append("\n    ,OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,USE_AT ") ;
		insertSQL.append("\n )VALUES( ") ;
		insertSQL.append("\n     ? -- STD_NO_CL_SYSTM_ID --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_IEM_SN ") ;
//		insertSQL.append("\n    ,? -- CL_SYSTM_GROUP_ID ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_TY_IEM_CODE ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_IEM_FOM_CN ") ;
		insertSQL.append("\n    ,? -- CL_SYSTM_IEM_LT ") ;
		insertSQL.append("\n    ,? -- IEM_SPRTR ") ;
		insertSQL.append("\n    ,? -- IEM_REFRN_TABLE_NM ") ;
		insertSQL.append("\n    ,? -- IEM_REFRN_COLUMN_NM ") ;
		insertSQL.append("\n    ,? -- IEM_REFRN_CODE_ID ") ;
		insertSQL.append("\n    ,? -- OBJECT_DC	--VARCHAR(4000)	객체설명 ") ;
		insertSQL.append("\n    ,? -- UPDUSR_ID	--VARCHAR(20)	Not Null	수정자ID ") ;
		insertSQL.append("\n    ,? -- UPDT_DT	-- DATE Not Null	수정일시 ") ;
		insertSQL.append("\n    ,? -- REGISTER_ID --VARCHAR(20)	Not Null	등록자ID ") ;
		insertSQL.append("\n    ,? -- REGIST_DT	--DATE	Not Null	등록일시 ") ;
		insertSQL.append("\n    ,? -- USE_AT ") ;
		insertSQL.append("\n  ) ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		int cnt = 0 ;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(insertSQL.toString());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_SN"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_IEM_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_IEM_FOM_CN"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_LT"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_SPRTR"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_COLUMN_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_CODE_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" insertCodeCfcSysItem : "+ cnt );
		
	}
	
	public void updateCodeCfcSysItem(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.CODE_CFC_SYS_ID AS STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_SEQ AS CL_SYSTM_IEM_SN ") ;
		strSQL.append("\n        ,NULL AS CL_SYSTM_GROUP_ID ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_CD AS CL_SYSTM_TY_IEM_CODE ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_FRM AS CL_SYSTM_IEM_FOM_CN ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_LEN AS CL_SYSTM_IEM_LT ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_SPT AS IEM_SPRTR ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_TBL AS IEM_REFRN_TABLE_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_COL AS IEM_REFRN_COLUMN_NM ") ;
		strSQL.append("\n        ,A.CODE_CFC_SYS_ITEM_REF_ID AS IEM_REFRN_CODE_ID ") ;
		strSQL.append("\n        ,A.OBJ_DESCN AS OBJECT_DC ") ;
		strSQL.append("\n        ,A.FRS_RQST_USER_ID AS REGISTER_ID ") ;
		strSQL.append("\n        ,A.FRS_RQST_DTM AS REGIST_DT ") ;
		strSQL.append("\n        ,A.RQST_USER_ID AS UPDUSR_ID ") ;
		strSQL.append("\n        ,A.RQST_DTM AS UPDT_DT ") ;
		strSQL.append("\n        ,'Y' AS USE_AT ") ;
		strSQL.append("\n   FROM WAM_CODE_CFC_SYS_ITEM A ") ;
		strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
		strSQL.append("\n                  FROM TCM_STD_NO_CL_SYSTEM_IEM_C B ") ;
		strSQL.append("\n                 WHERE B.STD_NO_CL_SYSTM_ID IN (SELECT STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n                                                              FROM TCM_STD_NO_CL_SYSTM_C  ") ;
		strSQL.append("\n                                                             WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n                                                                AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n                                                             ) ") ;
		strSQL.append("\n                   AND B.STD_NO_CL_SYSTM_ID = A.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                   AND B.CL_SYSTM_IEM_SN = A.CODE_CFC_SYS_ITEM_SEQ ") ;
		strSQL.append("\n                   AND (NVL(A.CODE_CFC_SYS_ITEM_CD, '▦') != NVL(B.CL_SYSTM_TY_IEM_CODE, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_FRM, '▦') != NVL(B.CL_SYSTM_IEM_FOM_CN, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_LEN, '0') != NVL(B.CL_SYSTM_IEM_LT, '0') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_SPT, '▦') != NVL(B.IEM_SPRTR, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_REF_TBL, '▦') != NVL(B.IEM_REFRN_TABLE_NM, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_REF_COL, '▦') != NVL(B.IEM_REFRN_COLUMN_NM, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.CODE_CFC_SYS_ITEM_REF_ID, '▦') != NVL(B.IEM_REFRN_CODE_ID, '▦') ") ;
		strSQL.append("\n                   OR NVL(A.OBJ_DESCN, '▦') != NVL(B.OBJECT_DC, '▦') ) ") ;
		strSQL.append("\n                ) ") ;
		
		
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("\n UPDATE TCM_STD_NO_CL_SYSTEM_IEM_C   ") ;
		updateSql.append("\n   SET CL_SYSTM_TY_IEM_CODE  = ? ") ;
		updateSql.append("\n        ,CL_SYSTM_IEM_FOM_CN  = ? ") ;
//		updateSql.append("\n        ,CL_SYSTM_GROUP_ID  = ? ") ;
		updateSql.append("\n        ,CL_SYSTM_IEM_LT  = ? ") ;
		updateSql.append("\n        ,IEM_SPRTR  = ? ") ;
		updateSql.append("\n        ,IEM_REFRN_TABLE_NM  = ? ") ;
		updateSql.append("\n        ,IEM_REFRN_COLUMN_NM  = ? ") ;
		updateSql.append("\n        ,IEM_REFRN_CODE_ID  = ? ") ;
		updateSql.append("\n        ,OBJECT_DC  = ? ") ;
		updateSql.append("\n        ,REGISTER_ID  = ? ") ;
		updateSql.append("\n        ,REGIST_DT = ?  ") ;
		updateSql.append("\n        ,UPDUSR_ID  = ? ") ;
		updateSql.append("\n        ,UPDT_DT = ?  ") ;
		updateSql.append("\n        ,USE_AT = ?  ") ;
		updateSql.append("\n WHERE STD_NO_CL_SYSTM_ID = ? --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		updateSql.append("\n   AND CL_SYSTM_IEM_SN = ? ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db update
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(updateSql.toString());
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_TY_IEM_CODE"));
			pstmt.setString(pstmtIdx++, rs.getString("CL_SYSTM_IEM_FOM_CN"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_LT"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_SPRTR"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_TABLE_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_COLUMN_NM"));
			pstmt.setString(pstmtIdx++, rs.getString("IEM_REFRN_CODE_ID"));

			pstmt.setString(pstmtIdx++, rs.getString("OBJECT_DC"));
			pstmt.setString(pstmtIdx++, rs.getString("UPDUSR_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPDT_DT"));
			pstmt.setString(pstmtIdx++, rs.getString("REGISTER_ID"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REGIST_DT"));
			
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_SN"));
			pstmt.setString(pstmtIdx++, rs.getString("USE_AT"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" updateCodeCfcSysItem : "+ cnt );
	}
	
	public void deleteCodeCfcSysItem(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  SELECT A.STD_NO_CL_SYSTM_ID, A.CL_SYSTM_IEM_SN ") ;
		strSQL.append("\n   FROM TCM_STD_NO_CL_SYSTEM_IEM_C A ") ;
		strSQL.append("\n  WHERE A.STD_NO_CL_SYSTM_ID IN (SELECT STD_NO_CL_SYSTM_ID ") ;
		strSQL.append("\n                                               FROM TCM_STD_NO_CL_SYSTM_C  ") ;
		strSQL.append("\n                                              WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		strSQL.append("\n                                                 AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		strSQL.append("\n                                              ) ") ;
		strSQL.append("\n    AND NOT EXISTS (SELECT 1 ") ;
		strSQL.append("\n                      FROM WAM_CODE_CFC_SYS_ITEM B ") ;
		strSQL.append("\n                     WHERE A.STD_NO_CL_SYSTM_ID = B.CODE_CFC_SYS_ID ") ;
		strSQL.append("\n                       AND A.CL_SYSTM_IEM_SN = B.CODE_CFC_SYS_ITEM_SEQ ") ;
		strSQL.append("\n                       AND B.REG_TYP_CD IN ('C','U') ") ;
		strSQL.append("\n                   ) ") ;

		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("\n UPDATE TCM_STD_NO_CL_SYSTEM_IEM_C  ") ;
		deleteSql.append("\n      SET USE_AT = 'N'") ;
		deleteSql.append("\n WHERE STD_NO_CL_SYSTM_ID = ? --VARCHAR(15)	PK0	표준번호분류체계ID ") ;
		deleteSql.append("\n   AND CL_SYSTM_IEM_SN = ? ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db delete
		int cnt = 0;
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt = tgtCon.prepareStatement(deleteSql.toString());
			pstmt.setString(pstmtIdx++, rs.getString("STD_NO_CL_SYSTM_ID"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CL_SYSTM_IEM_SN"));
			pstmt.executeUpdate();
			cnt ++;
		}
		logger.debug (" deleteCodeCfcSysItem : "+ cnt );
	}
	
	
}
