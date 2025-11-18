package kr.wise.commons.damgmt.db.service;

/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwDbService.java
 * 2. Package : kr.wise.cmvw.db.service
 * 3. Comment :
 * 4. 작성자  : jwoolee(이정우)
 * 5. 작성일  : 2013. 5. 28.
 * 6. 변경이력 :
 *    이름		: 일자			: 변경내용
 *    ------------------------------------------------------
 *    jwoolee 	: 2013. 5. 28.	: 신규 개발.
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;

/**
 * <PRE>
 * 1. ClassName : CmvwDbService
 * 2. Package  : kr.wise.cmvw.db.service
 * 3. Comment  :
 * 4. 작성자   : jwoolee(이정우)
 * 5. 작성일   : 2013. 5. 28.
 * </PRE>
 */

public interface CmvwDbService {

	public List<WaaDbConnTrgVO> getDbConnTrgList(WaaDbConnTrgVO search);

	public WaaDbConnTrgVO findDb(WaaDbConnTrgVO search);

	public int regDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception;

	public int delDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception;

	public void chkDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception;

	List<WaaDbSch> getDbSchList(String dbConnTrgId);

	int regDbSchList(ArrayList<WaaDbSch> list) throws Exception;

	int regDbSch(WaaDbSch record) throws Exception;

	int delDbSchList(ArrayList<WaaDbSch> list) throws Exception;

	/** @param search
	/** @return insomnia */
	public List<WaaDbSch> getDbSchemaList(WaaDbSch search);

	/** @param list
	/** @return yeonho 
	 * @throws Exception 
	 * @throws SQLException */
	public int dbConnTrgConnTest(ArrayList<WaaDbConnTrgVO> list) throws SQLException, Exception;

	public List<WaaDbSch> getDevSubjDbSchemaList(WaaDbSch search);
	
	
	public void getSchema(ArrayList<WaaDbConnTrgVO> list) throws SQLException, Exception;

	public List<WaaDbRole> getDbRoleList(WaaDbRole search); 


}
