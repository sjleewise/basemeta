/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaGapServiceImpl.java
 * 2. Package : kr.wise.meta.mta.service.impl
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.10.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.10. :            : 신규 개발.
 */
package kr.wise.meta.mta.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.mta.service.MtaGapMapper;
import kr.wise.meta.mta.service.MtaGapService;
import kr.wise.meta.mta.service.MtaGapVO;

@Service("mtaGapService")
public class MtaGapServiceImpl implements MtaGapService {

	@Inject
	private MtaGapMapper mapper;
	
	/* 
	 * @see kr.wise.meta.mta.service.MtaGapService#getMtaGapAnalyze(kr.wise.meta.mta.service.MtaGapVO)
	 */
	@Override
	public List<MtaGapVO> getMtaGapAnalyze(MtaGapVO search) {

		return mapper.selectMtaGapAnalyze(search);
	}

	@Override
	public List<MtaGapVO> getMtaColGapList(MtaGapVO search) {
		return mapper.selectMtaColGapList(search);
	}
	
	

}
