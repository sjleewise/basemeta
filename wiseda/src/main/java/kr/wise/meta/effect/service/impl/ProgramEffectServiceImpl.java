/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ProgramEffectServiceImpl.java
 * 2. Package : kr.wise.meta.effect.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 2. 오후 6:54:17
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 2. :            : 신규 개발.
 */
package kr.wise.meta.effect.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.effect.service.ProgramEffectService;
import kr.wise.meta.effect.service.WatPgmAsta;
import kr.wise.meta.effect.service.WatPgmAstaFuc;
import kr.wise.meta.effect.service.WatPgmAstaMapper;
import kr.wise.meta.effect.service.WatPgmAstaTbl;
import kr.wise.meta.effect.service.WatPgmAstaTblMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ProgramEffectServiceImpl.java
 * 3. Package  : kr.wise.meta.effect.service.impl
 * 4. Comment  : 프로그램 영향도 조회...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 2. 오후 6:54:17
 * </PRE>
 */
@Service("programEffectService")
public class ProgramEffectServiceImpl implements ProgramEffectService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WatPgmAstaMapper mapper;

	@Inject
	private WatPgmAstaTblMapper tblcrudMapper;

	/** insomnia */
	public List<WatPgmAsta> getProgramEffectList(WatPgmAsta search) {

		if(StringUtils.hasText(search.getSubsystemNm())) {
			search.setSubsystemNm(search.getSubsystemNm().toUpperCase());
		}
		if(StringUtils.hasText(search.getPgmNm())) {
			search.setPgmNm(search.getPgmNm().toUpperCase());
		}
		if(StringUtils.hasText(search.getTblEnm())) {
			search.setTblEnm(search.getTblEnm().toUpperCase());
		}

		return mapper.selectProgramEffectList(search);
	}

	/** insomnia */
	public WatPgmAsta getProgramEffectDetail(WatPgmAsta search) {
		return mapper.selectProgramEffectDetail(search);
	}

	/** insomnia */
	public List<WatPgmAstaTbl> getProgramEffectTblCRUD(WatPgmAsta search) {
		return tblcrudMapper.selectTblCRUD(search);
	}

	/** insomnia */
	public List<WatPgmAsta> getRelRrogramList(WatPgmAsta search) {
		return mapper.selectRelProgramList(search);
	}

	/** insomnia */
	public List<WatPgmAstaFuc> getRelFuncList(WatPgmAsta search) {
		List<WatPgmAstaFuc> result = mapper.selectRelFuncList(search);

		for (WatPgmAstaFuc vo : result) {
			List<String> tbls = vo.getTblEnms();
//			if(!tbls.isEmpty()) {
			vo.setTblEnm(StringUtils.collectionToCommaDelimitedString(tbls));
//			}
		}

		return result;
	}

}
