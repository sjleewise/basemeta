package kr.wise.commons.code.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface CommonCodeMapper {

	List<WaaCommDcd> selectCodeList(WaaCommDcd object);

	List<WaaCommDcd> selectList(WaaCommDcd vo);

	/** @param searchVO
	/** @return insomnia */
	WaaCommDcd selectCodebyID(WaaCommDcd searchVO);

	/** @param saveVO
	/** @return insomnia */
	int regCode(WaaCommDcd saveVO);

	/** @param saveVO
	/** @return insomnia */
	int insertCode(WaaCommDcd saveVO);

	/** @param saveVO
	/** @return insomnia */
	int updateCode(WaaCommDcd saveVO);

	/** @param saveVO
	/** @return insomnia */
	int deleteCode(WaaCommDcd saveVO);

	/** @param searchvo
	/** @return insomnia */
	List<WaaCommDtlCd> selectCodeDtlList(WaaCommDcd searchvo);

	/** @param saveVO
	/** @return insomnia */
	int insertDtlCode(WaaCommDtlCd saveVO);

	/** @param saveVO
	/** @return insomnia */
	int updateDtlCode(WaaCommDtlCd saveVO);

	/** @param saveVO
	/** @return insomnia */
	int deleteDtlCode(WaaCommDtlCd saveVO);

	/** @param delvo
	/** @return insomnia */
	int deleteDtlCodeByDcdid(WaaCommDcd delvo);

}
