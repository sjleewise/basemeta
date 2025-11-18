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
package kr.wise.meta.ldm.service;

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
public interface ModelLdmService {
	public List<WamLdmEnty> getLdmEntyList(WamLdmEnty search) ; 

	public List<WamLdmEnty> getPdmTblHist(WamLdmEnty search);

	public List<WamLdmEnty> getPdmTblTop30(WamLdmEnty search) ;

	public List<WamLdmAttr> getLdmAttrList(WamLdmAttr search) ; 
	
	public List<WamLdmAttr> getGapPdmColList(WamLdmAttr search) ;

	public List<WamLdmEnty> getPdmStatHisTblList(WamLdmEnty search);

	public List<WamLdmAttr> getPdmColDtlList(WamLdmAttr search);

	public List<WamLdmEnty> getLdmEntyHistList(WamLdmEnty search);

	public List<WamLdmAttr> getLdmAttrHistList(WamLdmEnty search);

	public List<WamLdmEnty> getPdmIdxColList(WamLdmEnty search);

	/** @param search
	/** @return meta */
	public List<WamLdmAttr> selectPdmColList(WamLdmAttr search);

	/** @param search
	/** @return meta */
	public List<WamLdmEnty> getPdmTblListForDdl(WamLdmEnty search);

	
	/** @param search
	/** @return lsi */
	public List<WamLdmAttr> getPdmColHistListDtl(WamLdmAttr search);
	

	public List<WamLdmAttr> getPdmColChgList(WamLdmAttr search);
	
	public List<WamLdmAttr> getColNonStndlist(WamLdmAttr search) ;
}
