package kr.wise.commons.sysmgmt.program.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageMapper;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageService;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("progrmManageService")
public class ProgrmManageServiceImpl implements ProgrmManageService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private ProgrmManageMapper progrmMapper;

	@Override
	public List selectProgrmList(ProgrmManageVO searchVO) {
		return progrmMapper.selectProgrmList(searchVO);
	}

	@Override
	public int selectProgrmListTotCnt(SearchVO searchVO) {
		return progrmMapper.selectProgrmListTotCnt(searchVO);
	}

	@Override
	public ProgrmManageVO selectProgrmDetail(ProgrmManageVO searchVO) {
		return progrmMapper.selectProgrmDetail(searchVO);
	}

	/** 프로그램 폼 내용을 저장 - saction (I|U)에 따라 저장 또는 업데이트... */
	@Override
	public int saveProgram(ProgrmManageVO saveVO, String saction) {
		if("I".equals(saction)) {
			return progrmMapper.insertProgram(saveVO);
		} else if ("U".equals(saction)) {
			return progrmMapper.updateProgram(saveVO);
		} else {
			return 0;
		}
	}

	/** 일괄삭제 - 프로그램명을 '|'으로 조인하여 한번에 삭제함... */
	@Override
	public int delProgram(ProgrmManageVO saveVO) {
		String id = saveVO.getProgrmFileNm();
		String[] ids = id.split("[|]");

		return progrmMapper.deleteProgram(ids);
	}

	@Override
	public List<ProgrmManageVO> selectProgrmRow(ProgrmManageVO searchVO) {

		List<ProgrmManageVO> reslist = new ArrayList<ProgrmManageVO>();

		reslist.add(progrmMapper.selectProgrmDetail(searchVO));

		return reslist;
	}


	/** 프로그램 리스트 일괄 저장 */
	@Override
	public int regProgramList(ArrayList<ProgrmManageVO> list) {

		int res = 0;

		for (ProgrmManageVO vo : list) {
			res += regProgram(vo);
		}

		return res;
	}

	/** 프로그램 요청구분(신규|변경|삭제)에 따라 처리
	 *  이력 테이블이 아니며 삭제시 실제 삭제 처리함....
	 *  */
	private int regProgram(ProgrmManageVO vo) {

		String tmpStatus = vo.getRegTypCd();

		if (!StringUtils.hasText(tmpStatus)) tmpStatus = "C";

		int result = 0;

		if("C".equals(tmpStatus)) {

			result = progrmMapper.insertProgram(vo);

		} else if ("U".equals(tmpStatus)) {

			result = progrmMapper.updateProgram(vo);

		} else if ("D".equals(tmpStatus)) {

			result = progrmMapper.deleteProgramVo(vo);

		}

		return result;
	}

}
