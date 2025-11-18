/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 표준단어 조회 서비스 구현체
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 24. 오후 11:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.meta.stnd.service.StndWordAbrService;
import kr.wise.meta.stnd.service.StndWordService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WamStwdAbrMapper;
import kr.wise.meta.stnd.service.WamStwdMapper;
import kr.wise.meta.stnd.service.WamWhereUsed;
import kr.wise.meta.stnd.service.WamWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 24. 오후 11:55:20
 * </PRE>
 */
@Service("stndWordService")
public class StndWordServiceImpl implements StndWordService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamStwdMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Inject
	private WamWhereUsedMapper whereUsedMapper;
	
	@Inject
	private WamStwdAbrMapper abrMapper;
	
	@Inject
	private StndWordAbrService stndWordAbrService;

	

	/** insomnia */
	public List<WamStwd> getStndWordList(WamStwd data) {

		logger.debug("searchvo:{}", data);

		return mapper.selectList(data);
	}

	public WamStwd selectStndWordDetail(String stwdId) {
		logger.debug("searchId:{}", stwdId);

		return mapper.selectWordDetail(stwdId);

	}

	public List<WamStwd> selectStndWordChangeList(String stwdId) {
		logger.debug("search Id:{}", stwdId);

		return mapper.selectWordChangeList(stwdId);
	}

	@Override
	//public List<WamWhereUsed> selectStwdWhereUsedList(String stwdId) {
	public List<WamWhereUsed> selectStwdWhereUsedList(WamStwd vo) {
		//return whereUsedMapper.selectStwdWhereUsedList(stwdId);
		return whereUsedMapper.selectStwdWhereUsedList(vo);
	}

	@Override
	public int regAbr(WamStwdAbr record) throws Exception {
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
	public String generateAbrList(ArrayList<WamStwdAbr> list) throws Exception {
		int result = 0;
		String abrId = objectIdGnrService.getNextStringId();
		int dtlSeq = 1;

		for (WamStwdAbr record : list) {
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
	public List<WamStwd> selectStndWordComparisonList(String stwdId) {
		return mapper.selectStndWordComparisonList(stwdId);
	}

	@Override
	public WamStwd selectGovStndWordDetail(String stwdId) {
		logger.debug("searchId:{}", stwdId);

		return mapper.selectGovWordDetail(stwdId);

	}
	
	@Override
	public String selectGovYn(String stwdId) {
		// TODO Auto-generated method stub
		return mapper.selectGovYn(stwdId);
	}

	

}
