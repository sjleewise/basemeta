/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DataSetService.java
 * 2. Package : kr.wise.advisor.prepare.dataset.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:55:43
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DataSetService.java
 * 3. Package  : kr.wise.advisor.prepare.dataset.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:55:43
 * </PRE>
 */
public interface DataSetNstndService {

	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	List<WadDataSetNstnd> regDataSetNstnd(List<AnaTrgTblVO> list) throws Exception;

	/** @param varlist
	/** @return insomnia 
	 * @throws Exception */
	int regAnaVarNstnd(List<WadAnaVarNstnd> varlist) throws Exception;

	/** @param dslist
	/** @return insomnia */
	List<WadAnaVarNstnd> getAnaVarNstnd(List<WadDataSetNstnd> dslist);

	/** @param search
	/** @return insomnia */
	List<WadDataSetNstnd> getDataSetNstndList(WadDataSetNstnd search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVarNstnd> getAnaVarNstndbyDs(WadDataSetNstnd search);

	/** @param search
	/** @return insomnia */
	WadAnaVarNstnd getAnlVarNstndDtl(WadAnaVarNstnd search);

	/** @param rqstNo
	/** @return insomnia */
	List<WaaDbConnTrgVO> getDataSetNstndListbyrqstNo(String rqstNo);

	/** @param search
	/** @return insomnia */
	WaaDbConnTrgVO getTgtDbmsbyds(WadDataSetNstnd search);

	/** @param dataset
	/** @return insomnia */
	List<WadAnaVarNstnd> getAnaVarNstndListbyDsId(WadDataSetNstnd dataset);

}
