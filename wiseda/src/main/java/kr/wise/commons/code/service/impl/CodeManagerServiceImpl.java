/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeManagerServiceImpl.java
 * 2. Package : kr.wise.commons.code.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 17. 오전 9:16:26
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 17. :            : 신규 개발.
 */
package kr.wise.commons.code.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CodeManagerService;
import kr.wise.commons.code.service.CommonCodeMapper;
import kr.wise.commons.code.service.WaaCommDcd;
import kr.wise.commons.code.service.WaaCommDtlCd;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CodeManagerServiceImpl.java
 * 3. Package  : kr.wise.commons.code.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 17. 오전 9:16:26
 * </PRE>
 */
@Service("codeManagerService")
public class CodeManagerServiceImpl implements CodeManagerService {

	final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private CommonCodeMapper codemapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;



	/** 리스트 조회 insomnia */
	public List<WaaCommDcd> getcodelist(WaaCommDcd searchvo) {
		return codemapper.selectList(searchvo);
	}

	/** 단건 조회 insomnia */
	public WaaCommDcd selectCodeDetail(WaaCommDcd searchVO) {
		return codemapper.selectCodebyID(searchVO);
	}

	/** 단건 저장 insomnia
	 * @throws Exception */
	public int saveCode(WaaCommDcd saveVO) throws Exception {

		String tmpstatus = saveVO.getIbsStatus();

		int result = 0;

		if("I".equals(tmpstatus)) {

			String objid = objectIdGnrService.getNextStringId();

			saveVO.setRegTypCd("C");
			saveVO.setCommDcdId(objid);
			saveVO.setObjVers(1);

			result = codemapper.insertCode(saveVO);
		} else if ("U".equals(tmpstatus)) {
			saveVO.setRegTypCd("U");
			result = codemapper.updateCode(saveVO);
		} else if ("D".equals(tmpstatus)) {
			saveVO.setRegTypCd("D");
			result = codemapper.deleteCode(saveVO);
		}

		return result;
	}

	/** 상세코드 리스트 조회 insomnia */
	public List<WaaCommDtlCd> getcodeDtllist(WaaCommDcd searchvo) {
		return codemapper.selectCodeDtlList(searchvo);
	}

	/** 공통코드와 상세코드 리스트 저장 insomnia
	 * @throws Exception */
	public int regCodeDtlList(ArrayList<WaaCommDtlCd> list, WaaCommDcd saveVO) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		saveVO.setWritUserId(userid);

		int result = 0;
		//공통코드 상위 정보 저장
		result = saveCode(saveVO);
		if(result <= 0) {
			return result;
		}

		String codeid = saveVO.getCommDcdId();

//		result = 0;
		for (WaaCommDtlCd subVO : list) {

			//상위 공통코드 셋팅
			subVO.setCommDcdId(codeid);
			subVO.setWritUserId(userid);

			result += saveDtlCode(subVO);
		}

		return result ;
	}

	/** 상세코드 저장 @return insomnia
	 * @throws Exception */
	public int saveDtlCode(WaaCommDtlCd saveVO) throws Exception {
		String tmpStatus = saveVO.getIbsStatus();
		int result = 0;
		if("I".equals(tmpStatus)) {

			//상세코드 신규 ID 채번
			String objid = objectIdGnrService.getNextStringId();

			saveVO.setRegTypCd("C");
			saveVO.setCommDtlCdId(objid);
			saveVO.setObjVers(1);

			result = codemapper.insertDtlCode(saveVO);

		} else if ("U".equals(tmpStatus)) {
			saveVO.setRegTypCd("U");
			saveVO.setObjVers(saveVO.getObjVers()+1);
			result = codemapper.updateDtlCode(saveVO);
		} else if ("D".equals(tmpStatus)) {
			saveVO.setRegTypCd("D");
			saveVO.setObjVers(saveVO.getObjVers()+1);
			result = codemapper.deleteDtlCode(saveVO);
		}
		return result;
	}

	/** 공통코드 리스트 삭제 insomnia
	 * @throws Exception */
	public int delCodeList(List<WaaCommDcd> dellist) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaaCommDcd delvo : dellist) {
			delvo.setIbsStatus("D");
			delvo.setWritUserId(userid);

			result += saveCode(delvo);
			deldtlCodeByDcdid(delvo);
		}

		return result;
	}

	/** 공통코드에 포함되는 상세코드 리스트 삭제 업데이트 @return insomnia */
	private int deldtlCodeByDcdid(WaaCommDcd delvo) {
		return codemapper.deleteDtlCodeByDcdid(delvo);
	}

	/** 공통상세코드 리스트를 삭제 업데이트 insomnia
	 * @throws Exception */
	public int delDtlCodeList(List<WaaCommDtlCd> list) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaaCommDtlCd delvo : list) {
			delvo.setIbsStatus("D");
			delvo.setWritUserId(userid);

			result += saveDtlCode(delvo);
		}

		return result;
	}

	/** 공통코드 엑셀 업로드 처리 insomnia
	 * @throws Exception */
	public int regCodeList(List<WaaCommDcd> list) throws Exception {

		int result = 0;

		//사용자 정보 획득
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaaCommDcd savevo : list) {

			//regtype에 따라 ibsstatus 셋
			String tmpregtype = savevo.getRegTypCd();
			if ("C".equals(tmpregtype)) {
				savevo.setIbsStatus("I");
			} else {
				savevo.setIbsStatus(tmpregtype);
			}

			//작성자 id 셋팅
			savevo.setWritUserId(userid);

			result += saveCode(savevo);
		}

		return result;
	}

	/** 공통 상세코드 리스트 엑셀저장 insomnia
	 * @throws Exception */
	public int regDtlCodeList(ArrayList<WaaCommDtlCd> list) throws Exception {
		int result = 0;

		//사용자 정보 획득
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaaCommDtlCd savevo : list) {
			//regtype에 따라 ibsstatus 셋
			String tmpregtype = savevo.getRegTypCd();
			if ("C".equals(tmpregtype)) {
				savevo.setIbsStatus("I");
			} else {
				savevo.setIbsStatus(tmpregtype);
			}

			//작성자 id 셋팅
			savevo.setWritUserId(userid);

			result += saveDtlCode(savevo);
		}

		return result;
	}

	/** 공통상세코드 리스트 저장 insomnia
	 * @throws Exception */
	public int regCodeDtlListWithout(ArrayList<WaaCommDtlCd> list) throws Exception {

		int result = 0;

		//사용자 정보 획득
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaaCommDtlCd savevo : list) {

			//작성자 id 셋팅
			savevo.setWritUserId(userid);

			result += saveDtlCode(savevo);
		}

		return result;
	}

}
