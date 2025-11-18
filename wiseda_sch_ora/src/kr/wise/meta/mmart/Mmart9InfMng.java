package kr.wise.meta.mmart;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.meta.mmart.dao.Mmart9TblDm;
import kr.wise.meta.mmart.dao.Mmart9WaeErwinDAO;

import org.apache.log4j.Logger;

import com.genesis.mmart.action.ERwin;




/**
 * 기술표준원 코드분류체계 interface class
 */
public class Mmart9InfMng {
	
	private static final Logger logger = Logger.getLogger(Mmart9InfMng.class);
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		Connection con = null;
		
		try{
			
			WaqPdmTbl search = new WaqPdmTbl();
			
			//getMart9TblList(search);
			
			Mmart9WaeErwinDAO dao = new Mmart9WaeErwinDAO();
			
			con = ConnectionHelper.getMetaConnection();
			
			con.setAutoCommit(false);
			
			Mmart9TblDm mdlVo = new Mmart9TblDm(); 
			
			mdlVo.setLibNm("해운항만물류통합시스템");
			//mdlVo.setMdlNm("선박");
			//mdlVo.setSubjNm("선박운항");
			
			//삭제
			dao.deleteWaeErwinCol(con);
			
			//주제영역별 insert
			dao.doCollectErwinCol();
			
			//dao.insertWaeErwinColEn(con, mdlVo);
			
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally{
			
			if(con != null) {
				con.rollback();
				con.close();
			}
		}
	}
	
	public static List<WaqPdmTbl> getMart9TblList(WaqPdmTbl search) {
		List listSubjectArea	= new ArrayList();
		
		Map param	 = new HashMap();
    	
//    	String category_id				= search.getCategory_id();	
//    	String model_id					= search.getModel_id();
//    	String entity_id				= search.getEntity_id();
//    	String attribute_id				= search.getAttribute_id();
//    	String subjectArea_id			= search.getSubjectArea_id();
//    	String subjectArea_name			= search.getFullPath();
//    	String entity_name				= search.getPdmTblLnm();
//    	String entity_name_physical		= search.getEntity_name_physical();
    	
//    	if(category_id				!= null && !category_id		.equals("0"))  param.put("category_id"		, category_id		);
//    	if(model_id					!= null && !model_id		.equals("0"))  param.put("model_id"			, model_id			);
//    	if(entity_id				!= null && !entity_id		.equals("0"))  param.put("entity_id"		, entity_id			);
//    	if(attribute_id				!= null && !attribute_id	.equals("0"))  param.put("attribute_id"		, attribute_id		);
//    	if(subjectArea_id	!= null && !subjectArea_id	.equals("0"))  param.put("subjectArea_id"	, subjectArea_id	);
//    	if(subjectArea_name	!= null && !subjectArea_name	.equals("0"))  param.put("subjectArea_name"	, subjectArea_name	);
//
//    	if(entity_name			!= null && !entity_name	.equals("0"))  param.put("entity_name"			, entity_name				);
//    	if(entity_name_physical	!= null && !entity_name_physical	.equals("0"))  param.put("entity_name_physical"	, entity_name_physical		);
    	
     	
    	
		param.put("category_name", "해운항만물류통합시스템");
    	param.put("model_name", "선박");
    	param.put("subjectArea_name", "선박운항"); 
		
		//param.put("category_name", "New Library");
    	//param.put("model_name", "EWS_1");
    	//param.put("subjectArea_name", "Subject_Area_34");
    	
    	logger.debug("param : > "+param);
    	List list    = null;
    	List listmap = null;
    	
    	
    	list = ERwin.getComboList2(param);
    	
    	
    	for(int i = 0; i < list.size(); i++){
    		
    		Map maplist = new HashMap();
			Map map = (Map)list.get(i);
    		
    		System.out.println("model:" + map.get("subjectArea_name") );
    		
    		
    		
    	}
    	
		return listmap;
	}
}
