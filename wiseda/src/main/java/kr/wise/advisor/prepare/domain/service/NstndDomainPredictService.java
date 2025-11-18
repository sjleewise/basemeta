/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DomainPredictService.java
 * 2. Package : kr.wise.advisor.prepare.domain.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:34:56
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.domain.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstnd;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DomainPredictService.java
 * 3. Package  : kr.wise.advisor.prepare.domain.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:34:56
 * </PRE>
 */
public interface NstndDomainPredictService {

	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	int requestNstndDmnPredict(List<AnaTrgTblVO> list) throws Exception;

	/** @param list
	/** @return insomnia */
	int regNstndDmnResult(List<WadAnaVarNstnd> list);

	/** @param search
	/** @return insomnia */
	List<WadNstndDmnPdt> getNstndDmnPredictResultbyId(WadNstndDmnPdt search);

	/** @param rqstNo
	/** @return insomnia */
	int callNstndDmnPredictGrpc(String rqstNo);

}
