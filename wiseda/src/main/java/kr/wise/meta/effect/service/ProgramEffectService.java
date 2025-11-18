/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ProgramEffectService.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 2. 오후 6:49:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 2. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ProgramEffectService.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 2. 오후 6:49:13
 * </PRE>
 */
public interface ProgramEffectService {

	/** @param search
	/** @return insomnia */
	List<WatPgmAsta> getProgramEffectList(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	WatPgmAsta getProgramEffectDetail(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	List<WatPgmAstaTbl> getProgramEffectTblCRUD(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	List<WatPgmAsta> getRelRrogramList(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	List<WatPgmAstaFuc> getRelFuncList(WatPgmAsta search);

}
