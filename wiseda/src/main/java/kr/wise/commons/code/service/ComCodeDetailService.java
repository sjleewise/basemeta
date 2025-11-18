package kr.wise.commons.code.service;

import java.util.List;

public interface ComCodeDetailService {

	List<ComCodeDetailVO> selectComCodeDetailList(ComCodeDetailVO searchVO);

	ComCodeDetailVO selectComCodeDetailInfo(ComCodeDetailVO searchVO);

	int saveComCodeDetail(ComCodeDetailVO saveVO, String saction);

	int deleteComCodeDetail(ComCodeDetailVO deleteVO);

}
