package kr.wise.commons.code.service;

import java.util.List;

import kr.wise.commons.cmm.SearchVO;

public interface ComCodeService {

	List<Comtccmmncode> selectComCodeList(SearchVO searchVO);

	Comtccmmncode selectComCodeDetail(Comtccmmncode searchVO);

	int saveComCode(Comtccmmncode saveVO, String saction);

	int deleteComCode(Comtccmmncode deleteVO);

}
