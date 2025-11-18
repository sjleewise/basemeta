/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlScriptService.java
 * 2. Package : kr.wise.meta.ddl.script.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오후 5:28:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.meta.ddl.script.service;

import java.io.IOException;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.script.model.Sequence;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.service.WaqDdlGrt;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddletc.service.WaqDdlEtc;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlScriptService.java
 * 3. Package  : kr.wise.meta.ddl.script.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오후 5:28:41
 * </PRE>
 */
public interface DdlScriptService {

	/** @param ddlTblId
	/** @return insomnia
	 * @throws IOException */
	String getDdlScriptTable(String ddlTblId) throws IOException;

	String getDDlScriptIndex(Table table) throws IOException;
	
	/** @param mstVo insomnia
	 * @throws IOException */
	List<WaqDdlTbl> updateDdlScirptWaq(WaqMstr mstVo) throws IOException;
	
	/** @param mstVo insomnia
	 * @throws IOException */
	List<WaqDdlIdx> updateDdlIdxScirptWaq(WaqMstr mstVo) throws IOException;

	/** @param ddlTblId
	/** @return meta */
	String getScrtInfo(String ddlTblId);

	String getDdlScirptWaqRqstSno(WaqDdlTbl ddlVo) throws IOException;

	String getDdlIdxScirptWaqRqstSno(WaqDdlIdx idxVo) throws IOException;

	Table getTableIndexOne(String ddlIdxId);  
	
	List<WaqDdlSeq> updateDdlSeqScirptWaq(WaqMstr mstVo) throws IOException;
	
	String getScrtInfoSeq(String ddlSeqId);
	
	String getScrtInfoSeqByWaq(WaqDdlSeq ddlVo, String chgTypCd) throws IOException;
	
	String getDDlScriptIndexByChgTypCd(String ddlIdxId, String tblChgTypCd) throws IOException;
	
	String getDDlScriptTable(String ddlTblId, String tblChgTypCd) throws IOException;
	
	String getDDlScriptPartition(Table tablepart) throws IOException;
	String getDDlScriptPartition(String objId, String tblChgTypCd) throws IOException;

	String getDDlScriptPartByWaq(WaqDdlPart ddlvo, String chgTypCd) throws IOException;

	String getDdlIdxScirpt(WaqDdlIdx idx) throws IOException;

	String getDdlSeqScirpt(Sequence ddlVo) throws IOException;

	String getDdlEtcScript(String string) throws Exception;

	String getDdlScriptTable(String string, String creDrpDcd) throws Exception;

	String getWahDdlTblScript(String ddlTblId, String rqstNo, String creDrpDcd) throws Exception;

	String getWahDdlIdxScirpt(String ddlTblId, String rqstNo) throws Exception;

	String getWahDdlSeqScirpt(String ddlTblId, String rqstNo) throws Exception;

	List<WaqDdlGrt> updateDdlGrtScirptWaq(WaqMstr mstVo) throws Exception;
	
	List<WaqDdlPart> updateDdlPartScirptWaq(WaqMstr mstVo) throws Exception;

	String getDdlGrtScript(String ddlGrtId) throws Exception;
	
	String getWahDdlEtcScript(String ddlTblId, String rqstNo) throws Exception;
	
	String getWahDdlPartScript(String ddlPartId, String rqstNo) throws Exception;

	List<WaqDdlEtc> updateDdlEtcScriptWaq(WaqMstr mstVo) throws Exception;

	String getDdlSeqScriptWaqRqstSno(WaqDdlSeq seqVo) throws IOException;

	String getWahDdlGrtScript(String ddlGrtId, String rqstNo) throws Exception;


}
