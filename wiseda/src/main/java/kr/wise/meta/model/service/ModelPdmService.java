/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 31. 오후 3:36:30
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 31. :            : 신규 개발.
 */
package kr.wise.meta.model.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ModelPdmService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 31. 오후 3:36:30
 * </PRE>
 */
public interface ModelPdmService {
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) ;

	public List<WamPdmTbl> getPdmTblHist(WamPdmTbl search);

	public List<WamPdmTbl> getPdmTblTop30(WamPdmTbl search) ;

	public List<WamPdmCol> getPdmColList(WamPdmCol search) ;
	
	public List<WamPdmCol> getGapPdmColList(WamPdmCol search) ;

	public List<WamPdmTbl> getPdmStatHisTblList(WamPdmTbl search);

	public List<WamPdmCol> getPdmColDtlList(WamPdmCol search);

	public List<WamPdmTbl> getPdmTblHistList(WamPdmTbl search);

	public List<WamPdmCol> getPdmColHistList(WamPdmTbl search);

	public List<WamPdmTbl> getPdmIdxColList(WamPdmTbl search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmCol> selectPdmColList(WamPdmCol search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmTbl> getPdmTblListForDdl(WamPdmTbl search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmRel> getPdmRelList(WamPdmRel search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmRel> getPdmRelHistList(WamPdmRel search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmRel> getPdmRelListHistPage(WamPdmRel search);

	/** @param search
	/** @return yeonho */
	public List<WamPdmRel> getPdmRelHistListHistPage(WamPdmRel search);
	
	/** @param search
	/** @return lsi */
	public List<WamPdmCol> getPdmColHistListDtl(WamPdmCol search);
	

	public List<WamPdmCol> getPdmColChgList(WamPdmCol search);
	
	public List<WamPdmCol> getColNonStndlist(WamPdmCol search) ;

	List<WamPdmTbl> getAsisPdmTblList(WamPdmTbl search);

	public List<GovVo> getGovList(GovVo search);

	public GovVo getOrgNm();

	public int regGovServerInfo(ArrayList<WaaGovServerInfo> list) throws Exception;

	public WaaGovServerInfo getGovServerInfo();
	
	List<GovVo> getGovListForSendFile(GovVo search);

	public int connTestGovServer(String resMsg, String resErrMsg);

	public List<PdmTblColDownVo> getPdmTblForDownBaiExl(PdmTblColDownVo search);

	public List<PdmTblColDownVo> getPdmColForDownBaiExl(PdmTblColDownVo search);
}
