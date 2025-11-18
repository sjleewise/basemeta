package kr.wise.meta.mmart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.util.UtilString;

import org.apache.log4j.Logger;

import com.genesis.mmart.action.ERwin;

public class Mmart9WaeErwinDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Mmart9WaeErwinDAO.class);

	private Connection con = null;
	private Connection tgtCon = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int pstmtIdx = 1;
	
	private int execCnt= 1000;
	private int iCnt= 0;
	
	public ResultSet selectSubj (Connection con) throws SQLException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
				
		/*
		sb.append("\n SELECT DISTINCT                                              ");
		sb.append("\n        B.SYS_AREA_LNM AS LIB_NM                              ");
		sb.append("\n      , C.SUBJ_LNM     AS MDL_NM                              ");
		sb.append("\n      , A.SUBJ_LNM                                            ");
		sb.append("\n   FROM WAA_SUBJ A                                            ");
		sb.append("\n        INNER JOIN WAA_SYS_AREA B                             ");
		sb.append("\n           ON B.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')  ");
		sb.append("\n          AND B.SYS_AREA_ID = A.SYS_AREA_ID                   "); 
		sb.append("\n        INNER JOIN WAA_SUBJ C                                 ");
		sb.append("\n           ON C.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')  ");
		sb.append("\n          AND C.SUBJ_ID = A.UPP_SUBJ_ID                       ");
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')        ");  
		sb.append("\n    AND B.LECY_DCD = 'TO'                                     ");  
		*/
		
		
		sb.append("\n SELECT DISTINCT                                              ");
		sb.append("\n        B.SYS_AREA_LNM  AS LIB_NM                             ");
		sb.append("\n      , A.SUBJ_LNM      AS MDL_NM                             ");
		sb.append("\n      , ''              AS SUBJ_LNM                           ");
		sb.append("\n   FROM WAA_SUBJ A                                            ");
		sb.append("\n        INNER JOIN WAA_SYS_AREA B                             ");
		sb.append("\n           ON B.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')  "); 
		sb.append("\n          AND B.SYS_AREA_ID = A.SYS_AREA_ID                   ");		
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')        ");  
		sb.append("\n    AND B.LECY_DCD = 'TO'                                     ");
		sb.append("\n    AND A.SUBJ_LVL = 0                                        ");
		
		    		
		pstmt = con.prepareStatement(sb.toString());
		
		rs = pstmt.executeQuery();
		
		return rs;
	}
	

	public void doCollectErwinCol() throws SQLException {
		
		try {
			//meta rep db connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			logger.debug("\n ============START WAE_ERWIN_COL===============");
			
			ResultSet rs = null;
			Mmart9TblDm mdlVo = new Mmart9TblDm();
					
			//WAE_ERWIN_COL 삭제 
			deleteWaeErwinCol(con);
			
			rs = selectSubj(con);
			
			while(rs.next()){
				
				mdlVo.setLibNm(rs.getString("LIB_NM"));
				mdlVo.setMdlNm(rs.getString("MDL_NM"));
				mdlVo.setSubjNm(rs.getString("SUBJ_LNM"));
				
				logger.debug("\n LIB_NM:" + mdlVo.getLibNm());
				logger.debug("\n MDL_NM:" + mdlVo.getMdlNm());
											
				//WAE_ERWIN_COL meta rep insert
				insertWaeErwinColEn(con, mdlVo); 
				
			}
												
			con.commit();
			
			logger.debug("\n ============END WAE_ERWIN_COL===============");
			
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

	private void insertWaeErwinCol(Connection con, Mmart9TblDm mdlVo) throws SQLException {
				
		PreparedStatement pstmtInsCol = null;
		
		int iRtn = 0;
		
		StringBuffer sb = new StringBuffer();
	
		
		HashMap param = new HashMap(); 
		
		param.put("category_name", mdlVo.getLibNm());
    	param.put("model_name", mdlVo.getMdlNm());
    	param.put("subjectArea_name", mdlVo.getSubjNm());
				
    	logger.debug("param : > "+param);
    	
    	List list = null;
    	
    	list = ERwin.getComboEntityList (param); 
			
    	logger.debug("tbllist : > "+ list.size()); 
    	
				
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_ERWIN_COL ");
		sb.append("\n (                         ");
		sb.append("\n     LIB_NM                ");
		sb.append("\n   , MDL_NM                ");
		sb.append("\n   , SUBJ_NM               ");
		sb.append("\n   , PDM_TBL_PNM           ");
		sb.append("\n   , PDM_TBL_LNM           "); //5
		sb.append("\n   , PDM_COL_PNM           "); 
		sb.append("\n   , PDM_COL_LNM           ");
		sb.append("\n   , COL_ORD               ");		
		sb.append("\n   , PK_YN                 "); //10
		sb.append("\n   , PK_ORD                ");
		sb.append("\n   , DATA_TYPE             ");
		sb.append("\n   , NONUL_YN              ");
		sb.append("\n   , DEFLT_VAL             ");
		sb.append("\n   , OBJ_DESCN             ");		
		sb.append("\n )                         ");
		sb.append("\n SELECT ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  "); //5
		sb.append("\n      , ?                  ");  
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  "); // 10
		sb.append("\n      , ?                  ");  
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n   FROM DUAL               ");
		
		logger.debug("\n" + sb.toString());
		 	
		pstmtInsCol = con.prepareStatement(sb.toString());
		
		if(list != null){
    		
			for(int i = 0; i< list.size(); i++){ 
				
				HashMap map = (HashMap) list.get(i);
												
				String categoryId    = UtilString.null2Blank(map.get("category_id"));
				String modelId       = UtilString.null2Blank(map.get("model_id"));
				String subjectAreaId = UtilString.null2Blank(map.get("subjectArea_id"));
				String entityId      = UtilString.null2Blank(map.get("entity_id"));
				
				String libNm      = UtilString.null2Blank(map.get("category_name"));
				String modelNm    = UtilString.null2Blank(map.get("model_name"));
				String subjNm     = UtilString.null2Blank(map.get("subjectArea_name"));
												
				String entityName = UtilString.null2Blank(map.get("entity_name"));
				String pdmTblPnm  = UtilString.null2Blank(map.get("entity_name_physical"));
				String pdmTblLnm  = UtilString.null2Blank(map.get("entity_name_logical"));
				
				
				logger.debug("\n entityId:" + entityId);   
				logger.debug("\n pdmTblPnm:" + pdmTblPnm); 
				
				
				String fullPath = libNm+ ">" + modelNm + ">" + subjNm;
				
				List rslist = null;
				
				Map maplist = new HashMap();
				
				maplist.put("category_id", categoryId);
				maplist.put("model_id", modelId)  ;
				maplist.put("subjectArea_id", subjectAreaId);
				maplist.put("entity_id", entityId);
				
				maplist.put("category_name", libNm)  ;
				maplist.put("model_name", modelNm)  ;
				maplist.put("subjectarea_name", subjNm) ;
				
				maplist.put("entity_name", entityName)  ;
				maplist.put("entity_name_physical", pdmTblPnm)  ;
				maplist.put("entity_name_logical", pdmTblLnm)  ;
				//maplist.put("entity_logical_yn", saveVo.getEntity_logical_yn())  ;
				
				rslist	= ERwin.getComboList2(maplist);  
				
				logger.debug("colist : > "+ rslist.size()); 
				
				for(int j= 0; j < rslist.size(); j++){ 
					
					HashMap mapCol = (HashMap) rslist.get(j);
										
					String  pdmColPnm = UtilString.null2Blank(mapCol.get("attribute_name_physical"));
	    			String  pdmColLnm = UtilString.null2Blank(mapCol.get("attribute_name"));
	    			Integer colOrd    = Integer.parseInt(UtilString.null2Blank(mapCol.get("attribute_order_logical"))); 
	    			String  dataType  = UtilString.null2Blank(mapCol.get("attribute_data_type_physical"));
	    			String  pkYn      = UtilString.null2Blank(mapCol.get("attribute_key_YN"));
	    			Integer pkOrd     = null;
					
	    			if(!UtilString.null2Blank(mapCol.get("attribute_key_order")).equals("")) {
	    				pkOrd = colOrd;
	    			}	    	
	    			
	    			String  defltVal  = UtilString.null2Blank(mapCol.get("attribute_default_value"));
	    			String  nullYn    = UtilString.null2Blank(mapCol.get("attribute_null_YN"));	    			
	    			String  objDescn  = UtilString.null2Blank(mapCol.get("attribute_comment"));
	    			
	    			String notnullYn = "";
	    			
	    			if(nullYn.equals("Y")){ 
	    				notnullYn = "N";
	    			}else{
	    				notnullYn = "Y";
	    			}
	    			
					//logger.debug("\n fullPath:" + fullPath);
					//logger.debug("\n PDM_TBL_PNM:" + rs.getString("PDM_TBL_PNM"));  
					//logger.debug("\n COL_ORD:" + rs.getInt("COL_ORD"));  
					//logger.debug("\n PK_ORD:" + rs.getInt("PK_ORD"));
					
	    			pstmtIdx = 1;
	    			
					pstmtInsCol.setString(pstmtIdx++, libNm);
					pstmtInsCol.setString(pstmtIdx++, modelNm);
					pstmtInsCol.setString(pstmtIdx++, subjNm);			
					pstmtInsCol.setString(pstmtIdx++, pdmTblPnm);
					pstmtInsCol.setString(pstmtIdx++, pdmTblLnm); //5
					pstmtInsCol.setString(pstmtIdx++, pdmColPnm);
					pstmtInsCol.setString(pstmtIdx++, pdmColLnm);
					pstmtInsCol.setInt(pstmtIdx++, colOrd); 
					pstmtInsCol.setString(pstmtIdx++, pkYn);
					pstmtInsCol.setInt(pstmtIdx++, pkOrd);     //10
					pstmtInsCol.setString(pstmtIdx++, dataType);
					pstmtInsCol.setString(pstmtIdx++, notnullYn);  
					pstmtInsCol.setString(pstmtIdx++, defltVal);
					pstmtInsCol.setString(pstmtIdx++, objDescn);
					
					iRtn = pstmtInsCol.executeUpdate();
					
					logger.debug("\n iRnt:" + iRtn);					
					
					//pstmtInsCol.addBatch();
					
					iCnt++;
					
					if(iCnt == execCnt) {
						
						//pstmtInsCol.executeBatch();								
						
						iCnt = 0;
					}	    
				}
			
			
    		}
		}	
		
	
		//pstmtInsCol.executeBatch();
		
		con.commit();  	
	}
	
	public void insertWaeErwinColEn(Connection con, Mmart9TblDm mdlVo) throws SQLException {
		
		PreparedStatement pstmtInsCol = null;
		
		int iRtn = 0;
		
		StringBuffer sb = new StringBuffer();
	
		
		HashMap param = new HashMap(); 
		
		param.put("category_name", mdlVo.getLibNm());
    	param.put("model_name", mdlVo.getMdlNm());
    	
    	if(!UtilString.null2Blank(mdlVo.getSubjNm()).equals("")) {
    	
    		param.put("subjectarea_name", mdlVo.getSubjNm());
    	}
    	 				
    	logger.debug("param : > "+param);
    	
    	List list = null;
    	
    	list = ERwin.getComboList2(param);   
			    	
    					
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_ERWIN_COL ");
		sb.append("\n (                         ");
		sb.append("\n     LIB_NM                ");
		sb.append("\n   , MDL_NM                ");
		sb.append("\n   , SUBJ_NM               ");
		sb.append("\n   , PDM_TBL_PNM           ");
		sb.append("\n   , PDM_TBL_LNM           "); //5
		sb.append("\n   , PDM_COL_PNM           "); 
		sb.append("\n   , PDM_COL_LNM           ");
		sb.append("\n   , COL_ORD               ");		
		sb.append("\n   , PK_YN                 "); //10
		sb.append("\n   , PK_ORD                ");
		sb.append("\n   , DATA_TYPE             ");
		sb.append("\n   , NONUL_YN              ");
		sb.append("\n   , DEFLT_VAL             ");
		sb.append("\n   , OBJ_DESCN             ");		
		sb.append("\n )                         ");
		sb.append("\n SELECT ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  "); //5
		sb.append("\n      , ?                  ");  
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  "); // 10
		sb.append("\n      , ?                  ");  
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n      , ?                  ");
		sb.append("\n   FROM DUAL               ");
		
		logger.debug("\n" + sb.toString());
		 	
		pstmtInsCol = con.prepareStatement(sb.toString());
		
		if(list != null){
			
			logger.debug("collist : > "+ list.size());
    		
			for(int i = 0; i< list.size(); i++){ 
				
				HashMap map = (HashMap) list.get(i);
												
				String categoryId    = UtilString.null2Blank(map.get("category_id"));
				String modelId       = UtilString.null2Blank(map.get("model_id"));
				String subjectAreaId = UtilString.null2Blank(map.get("subjectArea_id"));
				String entityId      = UtilString.null2Blank(map.get("entity_id"));
				
				String libNm      = UtilString.null2Blank(map.get("category_name"));
				String modelNm    = UtilString.null2Blank(map.get("model_name"));
				String subjNm     = UtilString.null2Blank(map.get("subjectArea_name")); 
												
				String entityName = UtilString.null2Blank(map.get("entity_name"));
				String pdmTblPnm  = UtilString.null2Blank(map.get("entity_name_physical"));
				String pdmTblLnm  = UtilString.null2Blank(map.get("entity_name_logical"));
				
				String  pdmColPnm = UtilString.null2Blank(map.get("attribute_name_physical"));
    			String  pdmColLnm = UtilString.null2Blank(map.get("attribute_name"));
    			String  colOrd    = UtilString.null2Blank(map.get("attribute_order_logical")); 
    			String  dataType  = UtilString.null2Blank(map.get("attribute_data_type_physical"));
    			String  pkYn      = UtilString.null2Blank(map.get("attribute_key_YN"));
    			String  pkOrd     = "";
				
    			if(!UtilString.null2Blank(map.get("attribute_key_order")).equals("")) {
    				pkOrd = colOrd;
    			}	    	
    			
    			String  defltVal  = UtilString.null2Blank(map.get("attribute_default_value"));
    			String  nullYn    = UtilString.null2Blank(map.get("attribute_null_YN"));	    			
    			String  objDescn  = UtilString.null2Blank(map.get("attribute_comment"));
    			
    			String notnullYn = "";
    			
    			if(nullYn.equals("Y")){ 
    				notnullYn = "N";
    			}else{
    				notnullYn = "Y";
    			}
    			
				//logger.debug("\n fullPath:" + fullPath);
				//logger.debug("\n PDM_TBL_PNM:" + rs.getString("PDM_TBL_PNM"));  
				//logger.debug("\n COL_ORD:" + rs.getInt("COL_ORD"));  
				//logger.debug("\n PK_ORD:" + rs.getInt("PK_ORD"));
    			
    			logger.debug("\n entityId:" + entityId);   
				logger.debug("\n pdmTblPnm:" + pdmTblPnm); 
				
				
    			pstmtIdx = 1;
    			
				pstmtInsCol.setString(pstmtIdx++, libNm);
				pstmtInsCol.setString(pstmtIdx++, modelNm);
				pstmtInsCol.setString(pstmtIdx++, subjNm);			
				pstmtInsCol.setString(pstmtIdx++, pdmTblPnm);
				pstmtInsCol.setString(pstmtIdx++, pdmTblLnm); //5
				pstmtInsCol.setString(pstmtIdx++, pdmColPnm);
				pstmtInsCol.setString(pstmtIdx++, pdmColLnm);
				pstmtInsCol.setString(pstmtIdx++, colOrd); 
				pstmtInsCol.setString(pstmtIdx++, pkYn);
				pstmtInsCol.setString(pstmtIdx++, pkOrd);     //10
				pstmtInsCol.setString(pstmtIdx++, dataType);
				pstmtInsCol.setString(pstmtIdx++, notnullYn);  
				pstmtInsCol.setString(pstmtIdx++, defltVal);
				pstmtInsCol.setString(pstmtIdx++, objDescn);
				
				//iRtn = pstmtInsCol.executeUpdate();
				
				con.commit();  	
				
				logger.debug("\n iRnt:" + iRtn);					
				
				pstmtInsCol.addBatch();
				
				iCnt++;
				
				if(iCnt == execCnt) {
					
					pstmtInsCol.executeBatch();								
					con.commit();
					
					iCnt = 0;
				}	    																
				
    		}
		}	
		
	
		pstmtInsCol.executeBatch();
		
		con.commit();  	
	}
	
	public void deleteWaeErwinCol(Connection con) throws SQLException {
		
		PreparedStatement pstmtMmartCol = null;
		
		int iRtn = 0;
		
		StringBuffer sb = new StringBuffer();
										
		
		sb.setLength(0);
		
		sb.append("\n DELETE FROM WAE_ERWIN_COL ");
		
		logger.debug("\n" + sb.toString()); 
		
		pstmtMmartCol = con.prepareStatement(sb.toString());
				
		iRtn = pstmtMmartCol.executeUpdate();
				
		con.commit();  
		
	}
	
	
}

