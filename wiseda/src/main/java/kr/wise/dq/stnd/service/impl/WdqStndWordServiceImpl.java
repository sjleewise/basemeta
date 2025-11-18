/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordServiceImpl.java
 * 2. Package : kr.wise.dq.stnd.service.impl
 * 3. Comment : 표준단어 조회 서비스 구현체
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 24. 오후 11:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.stnd.service.WdqStndWordAbrService;
import kr.wise.dq.stnd.service.WdqStndWordService;
import kr.wise.dq.stnd.service.WdqmStwd;
import kr.wise.dq.stnd.service.WdqmStwdAbr;
import kr.wise.dq.stnd.service.WdqmStwdAbrMapper;
import kr.wise.dq.stnd.service.WdqmStwdMapper;
import kr.wise.dq.stnd.service.WdqmWhereUsed;
import kr.wise.dq.stnd.service.WdqmWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 24. 오후 11:55:20
 * </PRE>
 */
@Service("wdqStndWordService")
public class WdqStndWordServiceImpl implements WdqStndWordService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqmStwdMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Inject
	private WdqmWhereUsedMapper whereUsedMapper;
	
	@Inject
	private WdqmStwdAbrMapper abrMapper;
	
	@Inject
	private WdqStndWordAbrService stndWordAbrService;

	

	/** insomnia */
	public List<WdqmStwd> getStndWordList(WdqmStwd data) {

		logger.debug("searchvo:{}", data);

		return mapper.selectList(data);
	}

	public WdqmStwd selectStndWordDetail(String stwdId) {
		logger.debug("searchId:{}", stwdId);

		return mapper.selectWordDetail(stwdId);

	}

	public List<WdqmStwd> selectStndWordChangeList(String stwdId) {
		logger.debug("search Id:{}", stwdId);

		return mapper.selectWordChangeList(stwdId);
	}

	@Override
	//public List<WdqmWhereUsed> selectStwdWhereUsedList(String stwdId) {
	public List<WdqmWhereUsed> selectStwdWhereUsedList(WdqmStwd vo) {
		//return whereUsedMapper.selectStwdWhereUsedList(stwdId);
		return whereUsedMapper.selectStwdWhereUsedList(vo);
	}

	@Override
	public int regAbr(WdqmStwdAbr record) throws Exception {
		logger.debug("약어생성 Impl 시작");
		// String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		ArrayList listValue = new ArrayList();
		String objEnm = "";
		String conEnm = "";
		String[] initEnmList = null;
		String[] errTxt = null;

		// 저장하기
		
			objEnm = stndWordAbrService.delSpec(record.getEngFullNm());
			conEnm = stndWordAbrService.getConsonant(objEnm);
			listValue = stndWordAbrService.generateInitEnm(objEnm, conEnm, record.getAbrLength(), record.getGenRule(),record.getStndAsrt());

			initEnmList = (String[]) listValue.get(0);
			errTxt = (String[]) listValue.get(1);
			record.setInitEnm06(null);
			record.setOrglUser(user.getUniqId());
			record.setInitEnm01(initEnmList[0]);
			record.setInitEnm02(initEnmList[1]);
			record.setInitEnm03(initEnmList[2]);
			record.setInitEnm04(initEnmList[3]);
			record.setInitEnm05(initEnmList[4]);
			for(int i=0; i<5; i++){
				if(!("").equals(initEnmList[i]) && null != initEnmList[i] && ("").equals(errTxt[i])) {
					record.setInitEnm06(initEnmList[i]);
					break;
				}
			}
			
			record.setErrTxt01(errTxt[0]);
			record.setErrTxt02(errTxt[1]);
			record.setErrTxt03(errTxt[2]);
			record.setErrTxt04(errTxt[3]);
			record.setErrTxt05(errTxt[4]);
						
			result = abrMapper.insertSelective(record);
			

		// record.setWritUserId(user.getUniqId());
		// result = mapper.insertSelective(record);
		return result;
	}


	@Override
	public String generateAbrList(ArrayList<WdqmStwdAbr> list) throws Exception {
		int result = 0;
		String abrId = objectIdGnrService.getNextStringId();
		int dtlSeq = 1;

		for (WdqmStwdAbr record : list) {
//			record.setGenRule(tmpGenRule);
//			record.setAbrLength(tmpAbrLength);
			record.setAbrId(abrId);
			record.setDtlSeq(dtlSeq++);
			result += regAbr(record);

		}
		// mapper.updateAllDeptNm();

		if(result != 0) {
			return abrId;
		} else {
			return null;
		}
	}

	@Override
	public List<WdqmStwd> selectStndWordComparisonList(String stwdId) {
		return mapper.selectStndWordComparisonList(stwdId);
	}

	

}
