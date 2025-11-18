/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDmnRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 15. 오후 5:58:06
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 15. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.stnd.service.WaqCdVal;
import kr.wise.meta.stnd.service.WaqCdValMapper;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqDmnMapper;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqSditmMapper;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.stnd.service.WaqStwdCnfg;
import kr.wise.meta.stnd.service.WaqStwdCnfgMapper;
import kr.wise.meta.stnd.service.WaqStwdMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDmnRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 15. 오후 5:58:06
 * </PRE>
 */
@Service("stndDmnRqstService")
public class StndDmnRqstServiceImpl implements StndDmnRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDmnMapper waqmapper;

	@Inject
	private WaqStwdMapper waqStwdMapper;

	@Inject
	private WapDvCanDicMapper wapDvCanDicMapper;

	@Inject
	private WapDvCanAsmMapper wapDvCanAsmMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private WaqStwdCnfgMapper waqStwdCnfgMapper;

	@Inject
	private WaqSditmMapper waqSditmMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
    private WaqCdValMapper waqcdmapper;

	@Inject
	private StndItemRqstService stndItemRqstService;


	/** insomnia */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {

		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqDmn saveVo : (ArrayList<WaqDmn>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndDmn(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 표준도메인 요청 내용 (생성/수정/삭제) @return insomnia */
	public int saveRqstStndDmn(WaqDmn saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		
		//도메인그룹 매핑...
		logger.debug("{}", saveVo);
		if (!UtilString.isBlank(saveVo.getDmngId2())) {
			saveVo.setDmngId(saveVo.getDmngId2());
		} else if (!UtilString.isBlank(saveVo.getDmngId1())) {
			saveVo.setDmngId(saveVo.getDmngId1());
		}
		
		//암호화여부가 없을경우 "N"으로 설정
		if(UtilString.isBlank(saveVo.getEncYn())||saveVo.getEncYn()==null) {
		     saveVo.setEncYn("N");
		}
		logger.debug("암호화여부 확인" + saveVo.getEncYn());
		
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqmapper.insertSelective(saveVo);
			//waqSditmMapper.insertByDmnSno(saveVo); // 20151016 이상익 주석처리
			waqcdmapper.insertByDmnSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			//waqSditmMapper.deleteByDmnSnoNoChg(saveVo); //20151016 이상익 주석처리
			//waqSditmMapper.insertByDmnSno(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//도메인 코드 요청 리스트 삭제.....
			waqcdmapper.deleteByrqstSno(saveVo);
		//	waqSditmMapper.deleteByDmnSno(saveVo);   //이상익 주석처리

		}

		//단어구성정보 셋팅(삭제 후 추가)
		setStwdCnfg(saveVo);

		return result;
	}

	/** 단어구성정보 셋팅(삭제 후 추가) */
	public void setStwdCnfg(WaqDmn saveVo) {
		//단어구성정보를 먼저 삭제한다.
		WaqStwdCnfg data = new WaqStwdCnfg();
		data.setBizDtlCd("DMN");
		data.setRqstNo(saveVo.getRqstNo());
		data.setRqstSno(saveVo.getRqstSno());
		data.setRqstDcd(saveVo.getRqstDcd());
		
		//이베이코리아 표준분류 추가
		data.setStndAsrt(saveVo.getStndAsrt());

		//단어구성정보를 먼저 삭제한다(선 삭제 후 입력)
		waqStwdCnfgMapper.deleteBySno(data);

		//단어구성정보를 추가한다. (단 삭제요청일 경우에는 추가하지 않음...)
		if(!"D".equals(saveVo.getIbsStatus())) {

			if (StringUtils.hasText(saveVo.getLnmCriDs()) && !"DD".equals(saveVo.getRqstDcd())) {
//				String[] arrStwdLnm = saveVo.getLnmCriDs().split("_");
//				String[] arrStwdPnm = saveVo.getDmnPnm().split("_");
				String[] arrStwdLnm = saveVo.getLnmCriDs().split(";");
				String[] arrStwdPnm = saveVo.getPnmCriDs().split(";");
				
				for(int i=0;i<arrStwdLnm.length;i++) {
					data.setStwdLnm(arrStwdLnm[i]);
					if(i < arrStwdPnm.length) {
						data.setStwdPnm(arrStwdPnm[i]);
					} else {
						data.setStwdPnm("");
					}
					//단어구성정보 추가
					waqStwdCnfgMapper.insertSelective(data);
				}
			}
		}

	}

	/** insomnia
	 * @throws Exception */
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("DMN");
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("CDVAL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//유효값이  유효값이 신청목록에 없는 경우 삭제대상으로 추가한다. 
		//logger.debug("유효값삭제대상추가 시작");
		//waqcdmapper.insertnoWaqdelCdVal(rqstNo);
		//logger.debug("유효값삭제대상추가 종료");

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		waqcdmapper.updateCheckInit(rqstNo);
	
		//도메인 삭제일경우 모든 컬럼 업데이트
		waqmapper.updateDmnDelInfo(rqstNo); 
		
		//도메인 삭제일경우 유효값 삭제 요청 업데이트
		waqcdmapper.updateDmnDelCdValInfo(rqstNo); 
		
		//인포타입에 해당하는 데이터타입, 길이, 소수점 업데이트
		waqmapper.updateDataInfo(rqstNo);
		
		//용어자동생성여부 빈값이면 N으로 업데이트
		waqmapper.updateSditmAutoCrtYn(rqstNo);
		
		//ID 업데이트 :

		//검증 시작
		//도메인 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_DMN");
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "DMN");


		//등록요청 중인 도메인 검증(DM021)
		checkmap.put("vrfDtlCd", "DM021");
		waqmapper.checkRequestDmn(checkmap);

		//요청서내 중복자료 검증(DM001)
		checkmap.put("vrfDtlCd", "DM001");
		waqmapper.checkDupDmn(checkmap);

		//삭제일때 미존재도메인 체크(DM002)
		checkmap.put("vrfDtlCd", "DM002");
		waqmapper.checkNotExistDmn(checkmap);

		//유사어 존재(DM003)
		checkmap.put("vrfDtlCd", "DM003");
		waqmapper.checkLnmSymn(checkmap);

		//도메인 영문 변경시 항목에서 사용하고 있는지 체크(DM004)
		checkmap.put("vrfDtlCd", "DM004");
		waqmapper.checkEngUseItem(checkmap);

		//삭제시 사용하고 있는지 체크(DM005)
		checkmap.put("vrfDtlCd", "DM005");
		waqmapper.checkUseDmn(checkmap);

		//코드도메인인 경우 관련정보 필수입력(유효값유형, 유효값부여방식) 체크(DM006)
//		checkmap.put("vrfDtlCd", "DM006");
//		waqmapper.ckeckCdDmnInfo(checkmap);
		
		
		//코드도메인인 경우 도메인그룹(인포타입) 선택시 코드 도메인 만 입력 가능 (DM023)
//		checkmap.put("vrfDtlCd", "DM023");
//		waqmapper.ckeckCdDmng(checkmap);

		//코드도메인인 경우 코드로 끝나는지 (DM038)
//		checkmap.put("vrfDtlCd", "DM038");
//		waqmapper.ckeckNotDmnLnmCd(checkmap);

		// 목록성코드도메인인 경우 관련정보 필수입력 체크(DM007)
//		checkmap.put("vrfDtlCd", "DM007");
//		waqmapper.ckeckLstCdDmnInfo(checkmap);

		//목록엔티티 존재여부(DM008)
//		checkmap.put("vrfDtlCd", "DM008");
//		waqmapper.checkExistLstEnty(checkmap);

		//코드도메인인아닌데 코드값이 있는지 체크(DM009)
		checkmap.put("vrfDtlCd", "DM009");
//		waqmapper.checkHasCdValNotCdDmn(checkmap);

		//도메인의 인포타입 변경시 코드값 크기 체크(DM010)
		checkmap.put("vrfDtlCd", "DM010");
		waqmapper.checkCdValDmnInfoLeng(checkmap);

		//주제영역 검증(DM011)
		checkmap.put("vrfDtlCd", "DM011");
		//waqmapper.checkSubj(checkmap);

		//표준단어 존재 체크(DM012)
		checkmap.put("vrfDtlCd", "DM012");
		waqmapper.checkExistStwd(checkmap);

		//인포타입 체크(DM013)
		checkmap.put("vrfDtlCd", "DM013");
		waqmapper.checkInfoType(checkmap);

		//부모 도메인 미존재(DM014)
		checkmap.put("vrfDtlCd", "DM014");
		waqmapper.checkNotExistPrntDmn(checkmap);

		//삭제시 자식 도메인 존재여부 체크(DM015)
		checkmap.put("vrfDtlCd", "DM015");
		waqmapper.checkChldDmnExist(checkmap);

		//도메인 구성정보 오류(DM016)
		checkmap.put("vrfDtlCd", "DM016");
		waqmapper.checkDmnStwdAsm(checkmap);

		//코드성 도메인그룹에 대해서만 영문명 유니크 검사(DM017)
		checkmap.put("vrfDtlCd", "DM017");
		//waqmapper.checkDupDmnPnm(checkmap);

		//도메인 영문명 최대길이 검증을 사용 할 경우(DM018)
//		checkmap.put("vrfDtlCd", "DM018");
//		waqmapper.checkDmnPnmMaxLen(checkmap);

		//도메인 한글명 최대길이 검증을 사용 할 경우(DM019)
//		checkmap.put("vrfDtlCd", "DM019");
//		waqmapper.checkDmnLnmMaxLen(checkmap);

		//도메인 영문명 첫 글자 숫자 여부 검사(DM020)
		checkmap.put("vrfDtlCd", "DM020");
		waqmapper.checkDmnPnmStrNum(checkmap);
		
		//목록어트리뷰트 존재여부(DM022)
		checkmap.put("vrfDtlCd", "DM022");
		//waqmapper.checkExistLstAttr(checkmap);

		//도메인 물리명 존재여부(DM024)
		checkmap.put("vrfDtlCd", "DM024");
		waqmapper.checkDmnPnmExists(checkmap);
		//논리명기준구분 존재여부(DM025)
		checkmap.put("vrfDtlCd", "DM025");
		waqmapper.checkLNmCriDsExists(checkmap);
		//물리명기준구분  존재여부(DM026)
		checkmap.put("vrfDtlCd", "DM026");
		waqmapper.checkPNmCriDsExists(checkmap);
        //단순코드일 때 3레벨을 넘을 수 없음
		//checkmap.put("vrfDtlCd", "DM027");
		//waqmapper.checkSimpleCodeLevelCheck(checkmap);
		
		//코드도메인일 경우 유효값이 등록되었는지 체크
		//checkmap.put("vrfDtlCd", "DM028");
		//waqmapper.checkCdValExists(checkmap);
		
		//길이가 64,128,256 인 경우 암호화여부가 'Y'여야 함
	    //checkmap.put("vrfDtlCd", "DM029");
		//waqmapper.checkEncYn(checkmap);
		
		//단순코드(MAX 15), 복잡코드(max 25)  ibk신용정보
	    //checkmap.put("vrfDtlCd", "DM030");
		//waqmapper.checkCodeLen(checkmap);
		
		//단순,복잡일 때는 대분류코드 필수  ibk신용정보
	    //checkmap.put("vrfDtlCd", "DM032");
		//waqmapper.checkExistsDscd(checkmap);
		
		//대분류코드 기존재  ibk신용정보  (코드id로 사용가능)
//	    checkmap.put("vrfDtlCd", "DM033");
//		waqmapper.checkExistsDscdAlready(checkmap);
		
		//대분류코드 등록요청중  ibk신용정보
	    //checkmap.put("vrfDtlCd", "DM036");
		//waqmapper.checkExistsWaqDscdAlready(checkmap);
		
		//대분류코드 길이 (4자리)  ibk신용정보
		//checkmap.put("vrfDtlCd", "DM034");
		//waqmapper.checkDscdLength(checkmap);
		
		//대분류코드 주제영역 매핑  ibk신용정보
		//checkmap.put("vrfDtlCd", "DM035");
		//waqmapper.checkDscdSubjMapping(checkmap);
		
		//도메인이 신규일 경우 용어자동신청이 Y인 경우 기존재 용어 있는지 판단
		checkmap.put("vrfDtlCd", "DM037");
		waqmapper.checkExistsSditm(checkmap);
		
		//논리명기준구분과 물리명기준구분의 구성이 불일치할경우 (; 갯수로 판단)
//		checkmap.put("vrfDtlCd", "DM055");
//		waqmapper.checkDmnCriDsCnfg(checkmap);
		
		
		//코드값 검증
		//코드값 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_CD_VAL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "CDVAL");

		//요청서내 중복(CV001)
		checkmap2.put("vrfDtlCd", "CV001");
		waqcdmapper.checkDupCdval(checkmap2);

		//삭제일때 미존재코드값 체크(CV002)
		checkmap2.put("vrfDtlCd", "CV002");
		waqcdmapper.checkNotExistCdVal(checkmap2);

		//도메인존재 체크(CV003)
		checkmap2.put("vrfDtlCd", "CV003");
		waqcdmapper.checkNotExistDmn(checkmap2);

		//부모유효값 존재 체크(CV004)
		checkmap2.put("vrfDtlCd", "CV004");
		//ibk신용정보 전용 부모도메인이 없고, 코드내에서 재귀되기 때문
        //waqcdmapper.checkNotExistPrntCdValIbkC(checkmap2);
        waqcdmapper.checkNotExistPrntCdVal(checkmap2);
        
		//삭제시 자식 유효값 존재 체크(CV005)
		checkmap2.put("vrfDtlCd", "CV005");
		waqcdmapper.checkChldCdValExist(checkmap2);

		//유효값 길이 체크(CV006)
		checkmap2.put("vrfDtlCd", "CV006");
		waqcdmapper.checkCdValLen(checkmap2);
		
		
	    //대분류코드가 존재할 경우 유효값은 대분류코드와 같을 수 없다(CV007) IBK신용정보
		//checkmap2.put("vrfDtlCd", "CV007");
		//waqcdmapper.checkCdValEqualDmnDscd(checkmap2);
		
		//단순코드의 경우 대분류코드 + 유효값
		//checkmap2.put("vrfDtlCd", "CV008");
		//waqcdmapper.checkExistsDmnDscdCode(checkmap2);
		
		//단순코드인 경우 2레벨을 넘을 수 없다.
		//checkmap2.put("vrfDtlCd", "CV009");
		//waqcdmapper.checkCodeLevel(checkmap2);
		
		//자식이 Y인 경우 부모를 사용여부 N으로 변경불가
//		checkmap2.put("vrfDtlCd", "CV010");
//		waqcdmapper.checkExistsChildUseYn(checkmap2);
		//부모가 N인 경우 자식을 사용여부 Y로 변경불가
//        checkmap2.put("vrfDtlCd", "CV011");
//		waqcdmapper.checkExistsPrntUseYn(checkmap2);
		
		//부모유효값과 자식 유효값은 같을 수 없다.
//		checkmap2.put("vrfDtlCd", "CV012");
//		waqcdmapper.checkExistsPrntEqualChild(checkmap2);
		
		//유효값 명은 길이 96을 넘을 수 없다.
		checkmap2.put("vrfDtlCd", "CV013");
		//waqcdmapper.checkLenCdValNm(checkmap2);
		
		//부분유효값  체크 , 부모도메인이 등록되어있을때 부모도메인의 유효값에 없는 유효값을 입력할때(CV014)
		checkmap2.put("vrfDtlCd", "CV014");
//		오류로 인해 주석 처리 - 18.11.05 신동준
//		waqcdmapper.checkCdValUppDmn(checkmap2);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(CV000)
		checkmap2.put("vrfDtlCd", "CV000");
		waqcdmapper.checkNotChgData(checkmap2);

		//코드 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);

		// 등록불가능한 코드값 존재
	    checkmap.put("vrfDtlCd", "DM031");
		waqmapper.checkCodeErr(checkmap);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DM000)
		checkmap.put("vrfDtlCd", "DM000");
		waqmapper.checkNotChgData(checkmap);
		
		//공통표준 존재 - 확인(3)
//		checkmap.put("vrfDtlCd", "DM055");
//		waqmapper.checkExistUpStndDtl(checkmap);

		//도메인 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
				
		//공통표준 존재 - 확인(3)
//		result += waqRqstVrfDtlsMapper.updateVrfCdCheck(checkmap);

		stndItemRqstService.check(mstVo);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}

	/** insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** insomnia */
	public Map<String, String> approve(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/** insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {

		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDmn savevo : (ArrayList<WaqDmn>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("표준도메인 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamCdval(mstVo);
			
			
			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	public int regWaq2WamCdval(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqCdVal> list = waqcdmapper.selectWaqC(rqstno);
		for (WaqCdVal savevo : list) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setCdValId(id);

			waqcdmapper.updateidByKey(savevo);
		}

		result += waqcdmapper.updateWaqCUD(rqstno);

		//각종 ID를 업데이트 한다.
		result += waqcdmapper.updateWaqId(rqstno);
		result += waqcdmapper.updateUppCdValId(rqstno);

		result += waqcdmapper.deleteWAM(rqstno);
		result += waqcdmapper.insertWAM(rqstno);
		result += waqcdmapper.updateWAH(rqstno);
		result += waqcdmapper.insertWAH(rqstno);



		return result;
	}

	/** @param mstVo
	/** @return insomnia */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID 채번한다.
		List<WaqDmn> waqclist =  waqmapper.selectWaqC(rqstno);

		for (WaqDmn savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDmnId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.updateUppDmnId(rqstno);
		
		//도메인 인포타입이 변경될 경우 표준용어의 인포타입도 일괄변경 
		//result += waqmapper.updateSditmInfoTp(rqstno); 
		//result += waqmapper.updateSditmInfoTpWah(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		//테스트데이터 변환여부 업데이트
        waqmapper.updateWahTransYn(rqstno);

		//표준단어 구성은 C,U에 대해 기존꺼 삭제 후 다시 저장으로...
		waqStwdCnfgMapper.deleteWAM(rqstno);
		waqStwdCnfgMapper.insertWAM(rqstno);
		//

		//표준용어 자동신청 20151020 
		//waqSditmMapper.deleteByDmnSnoNoChg(savevo);
		waqSditmMapper.insertByDmnSno2(mstVo);
		
		//표준용어 코드도메인변경시 자동신청
		waqSditmMapper.insertByCodeDmnSno2(mstVo);
		
		//표준용어 인포타입변경시 자동신청(도메인과 표준용어 인포타입같은것만)
		waqSditmMapper.insertByCodeDmnSno3(mstVo); 
		
		return result;
	}




	/** insomnia */
	public List<WaqDmn> getInfotpList() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 표준도메인 요청 상세정보 조회 insomnia */
	public WaqDmn getStndDmnRqstDetail(WaqDmn searchVo) {

		return waqmapper.selectDomainRqstDetail(searchVo);
	}

	/** insomnia */
	public Map<String, String> getDmnStwdInfo(WaqDmn record) {
		Map<String, String> dmnDiv = new HashMap<String, String>();
		String dmnLnm = "";
		String dmnPnm = "";
		String lnmCriDs = "";
		String pnmCriDs = "";
		String sepDmnLnm = record.getDmnLnm();
		String stndAsrt = record.getStndAsrt();
		String [] arrSepDmnLnm = sepDmnLnm.split(";");
		for(int i=0;i < arrSepDmnLnm.length;i++) {
			List<WaqStwd> list = waqStwdMapper.selectListByStwdLnm(record.getRqstNo(), arrSepDmnLnm[i],stndAsrt);
			if(list.size() > 1) {
				dmnLnm += arrSepDmnLnm[i];
//				dmnPnm += "_"+list.get(0).getStwdPnm();
				dmnPnm += "_[D]";
				lnmCriDs += ";"+arrSepDmnLnm[i];
//				pnmCriDs += ";"+list.get(0).getStwdPnm();
				pnmCriDs += ";[D]";
			} else if(list.size() > 0) {
				dmnLnm += arrSepDmnLnm[i];
				dmnPnm += "_"+list.get(0).getStwdPnm();
				lnmCriDs += ";"+arrSepDmnLnm[i];
				pnmCriDs += ";"+list.get(0).getStwdPnm();
			} else {
				dmnLnm += arrSepDmnLnm[i];
				dmnPnm += "_[X]";
				lnmCriDs += ";"+arrSepDmnLnm[i];
				pnmCriDs += ";[X]";
//				pnmCriDs += ";"+list.get(0).getStwdPnm();
			}
		}

		dmnDiv.put("dmnLnm", dmnLnm);
		dmnDiv.put("dmnPnm", dmnPnm.substring(1));
		dmnDiv.put("lnmCriDs", lnmCriDs.substring(1));
		dmnDiv.put("pnmCriDs", pnmCriDs.substring(1));
		
		return dmnDiv;
	}

	/** insomnia */
	public List<WapDvCanAsm> getDmnDivisionlist(WapDvCanDic record) throws Exception {
		int execCnt = 0;
		String dvrqstno = objectIdGnrService.getNextStringId();
		record.setDvRqstNo(dvrqstno);
		
		//포함 단어정보 입력
		wapDvCanDicMapper.insertStwd(record);

		//초기데이터 입력
		execCnt = wapDvCanAsmMapper.insertFirst(record);

		while(execCnt > 0) {
			//단어정보로 분할
			wapDvCanAsmMapper.insertAsmDic(record.getDvRqstNo());
			//미존재단어정보 반영
			wapDvCanAsmMapper.insertAsmNotExistDic(record.getDvRqstNo());
			//이전데이터 삭제
			wapDvCanAsmMapper.deleteNotEndPrcAsmDic(record.getDvRqstNo());
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = wapDvCanAsmMapper.updateNotEndPrcAsmDic(record.getDvRqstNo());
		}
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo());
		//분할정보삭제
		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());

		return list;
	}
	
	
	/** 도메인 자동분할 리스트스  */
	public List<WapDvCanAsm> getDmnDvRqstList(WapDvCanDic record) {
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectDmnDvRqstList(record);
		return list;
	}
	
	
	/** insomnia */
	public Map<String, String>  regDmnAutoDiv(List<WapDvCanAsm> list)  throws Exception {
		
		Map<String, String> resultMap =  new HashMap<String, String>();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		String dvrqstno = objectIdGnrService.getNextStringId();
		
		int execCnt = 0;
		int rtnCnt = 0;
		
		WapDvCanDic record = new WapDvCanDic();
		record.setDvRqstNo(dvrqstno);
		
//		 단어정보 입력
		wapDvCanDicMapper.insertStwdAll(record);
		
		//초기데이터 입력
		for (WapDvCanAsm savevo : list) {
			savevo.setDvRqstNo(dvrqstno);
			savevo.setDvRqstUserId(userid);
			savevo.setDvSeCd("D");
			//포함 단어정보 입력
//			record.setTrgLnm(savevo.getDvOrgLnm());
//			wapDvCanDicMapper.insertStwd(record);
			
			execCnt += wapDvCanAsmMapper.insertDvListFirst(savevo);
		}
		
		rtnCnt = execCnt;
		
		while(execCnt > 0) {
			//단어정보로 분할
			wapDvCanAsmMapper.insertAsmDic(record.getDvRqstNo());
			//미존재단어정보 반영
			wapDvCanAsmMapper.insertAsmNotExistDic(record.getDvRqstNo());
			//이전데이터 삭제
			wapDvCanAsmMapper.deleteNotEndPrcAsmDic(record.getDvRqstNo());
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = wapDvCanAsmMapper.updateNotEndPrcAsmDic(record.getDvRqstNo()); 
		}
	
		//분할정보삭제
		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
//		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());
		
		//도메인 기존재
		wapDvCanDicMapper.checkDmnDup(dvrqstno);
		//분할 결과 검증
		//도메인그룹ID , INFOTYPE ID UPDATE
		wapDvCanDicMapper.updtDmngId(dvrqstno);
		wapDvCanDicMapper.updtInfotpId(dvrqstno);
		//도메인그룹 미존재
		wapDvCanDicMapper.checkDmngId(dvrqstno);
		//인포타입 미존재
		wapDvCanDicMapper.checkInfotpId(dvrqstno);
		//도메인그룹 인포타입 매핑 상이
		wapDvCanDicMapper.checkDmngInfotyp(dvrqstno);
		//구성정보 오류
		wapDvCanDicMapper.checkAsmDs(dvrqstno);
		//물리명 길이
		wapDvCanDicMapper.checkPnmMaxLen(dvrqstno);
		//물리명 끝자리 숫자체크
		wapDvCanDicMapper.checkEndNum(dvrqstno);
		
		
		resultMap.put("result", Integer.toString(rtnCnt) );
    	resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
	}
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> delDmnAutoDiv(List<WapDvCanAsm> list) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int rtnCnt = 0;
		String dvrqstno = "";
		for (WapDvCanAsm savevo : list) {
			dvrqstno = savevo.getDvRqstNo();
			rtnCnt += wapDvCanAsmMapper.delDmnAutoDiv(savevo);
		}
		
			

		
		resultMap.put("result", Integer.toString(rtnCnt) );
		resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	
	public int regStndDmnByDiv(WaqMstr mstVo, ArrayList<WapDvCanAsm> list){
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();
		String dvRqstNo = "";

		int result = 0;
		int delResult = 0;

		if(list != null) {
			for (WapDvCanAsm wapDvCanAsmVo : (ArrayList<WapDvCanAsm>)list) {
				//선택된 데이터 만
				if(wapDvCanAsmVo.getIbsCheck().equals("1") ){
					//등록가능 데이터만
					if(wapDvCanAsmVo.getRegPosYn().equals("N")){
						continue;
					}
					
					WaqDmn saveVo = new WaqDmn(); 
					//요청번호 셋팅...
					saveVo.setFrsRqstUserId(userid);
					saveVo.setRqstUserId(userid);
					saveVo.setRqstNo(rqstNo);
					saveVo.setRqstDcd("CU");
					
					saveVo.setStndAsrt(wapDvCanAsmVo.getStndAsrt());
					saveVo.setDmnLnm(wapDvCanAsmVo.getDvOrgLnm());
					saveVo.setDmnPnm(wapDvCanAsmVo.getDicAsmPnm());
//					saveVo.setLnmCriDs( wapDvCanAsmVo.getDicAsmDsLnm().replaceAll(";","_"));
					saveVo.setLnmCriDs( wapDvCanAsmVo.getDicAsmDsLnm());
					saveVo.setPnmCriDs( wapDvCanAsmVo.getDicAsmDsPnm());
					
					saveVo.setDmngLnm(wapDvCanAsmVo.getDmngLnm());
					saveVo.setDmngId(wapDvCanAsmVo.getDmngId());
					saveVo.setInfotpId(wapDvCanAsmVo.getInfotpId());
					saveVo.setInfotpLnm(wapDvCanAsmVo.getInfotpLnm());
					saveVo.setObjDescn(wapDvCanAsmVo.getDvObjDescn());
					saveVo.setDataType(wapDvCanAsmVo.getDataType());
					saveVo.setDataLen(wapDvCanAsmVo.getDataLen());
					saveVo.setDataScal(wapDvCanAsmVo.getDataScal());
					
					saveVo.setSditmAutoCrtYn(wapDvCanAsmVo.getSditmAutoCrtYn());
					saveVo.setCdValTypCd(wapDvCanAsmVo.getCdValTypCd());
					saveVo.setCdValIvwCd(wapDvCanAsmVo.getCdValIvwCd()); 
					saveVo.setFullPath(wapDvCanAsmVo.getFullPath()); 
				    
					dvRqstNo = wapDvCanAsmVo.getDvRqstNo();
					//단건 저장...
					result += waqmapper.insertSelective(saveVo);
					
					//단어구성정보 셋팅(삭제 후 추가)
					setStwdCnfg(saveVo);
					
					//항목분할 삭제
//					delResult += wapDvCanAsmMapper.delItemAutoDiv(wapDvCanAsmVo);
				}
			}
		}
		//항목분할 삭제
//		delResult += wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(dvRqstNo);
		
		logger.debug(" REQ CNT : "+result + "    DEL CNT : " + delResult);
		
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}

	/** insomnia */
	public List<WaqDmn> getDmnRqstList(WaqMstr search) {
		return waqmapper.selectDmnRqstListbyMst(search);
	}

	/** insomnia */
	public int delList(WaqMstr reqmst, ArrayList<WaqDmn> list) {

		return waqmapper.deleteDmnList(reqmst, list);

	}

	/** insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDmn> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다.
		ArrayList<WaqDmn> wamlist = waqmapper.selectwamlist(reqmst, list);

		//WAM_DMN 2 WAQ_DMN 적재(한번에 적재)
//		result = waqmapper.insertwam2waq(reqmst, list);

		//단어구성정보 적재(wam에 있는 내용을 적재하려고 했으나, 현재 wam에 단어구성정보가 없음.)
//		waqStwdCnfgMapper.insertwam2waq(reqmst, list);


		result = register(reqmst, wamlist);
		//TODO: 코드 내용 적재
//		result += waqcdmapper.insertwam2waq(reqmst, wamlist);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delStndDmnRqst(WaqMstr reqmst, ArrayList<WaqDmn> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDmn savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 도메인 코드 요청 리스트 조회.... insomnia */
	public List<WaqCdVal> getCdvalRqstList(WaqMstr search) {

		return waqcdmapper.selectCdvalRqstVal(search) ;
	}

	/** insomnia */
	public int regCdvalList(WaqMstr reqmst, List<WaqCdVal> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmst.getRqstStepCd())) {
			
			requestMstService.insertWaqMst(reqmst);
		}
				

		for (WaqCdVal savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			
			result += waqmapper.insertWaqDmnByCdVal(savevo); 

			result += saveCdvalRqst(savevo);

		}

		return result;
	}

	/** @param savevo
	/** @return insomnia */
	private int saveCdvalRqst(WaqCdVal savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();
		

		if("I".equals(tmpStatus)) {
			//도메인 요청에 논리모델이 있는지 확인한다.
			List<WaqDmn> dmnlist = waqmapper.selectListByCdval(savevo);
			if (dmnlist == null || dmnlist.isEmpty()) {
				return result; 
			} else {
				savevo.setRqstSno(dmnlist.get(0).getRqstSno());
			}

			result = waqcdmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqcdmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqcdmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/** insomnia */
	public int delCdvalList(WaqMstr reqmst, List<WaqCdVal> list) {
		int result = 0;

		//여러 도메인에 속한 리스트를 지우려면 성능이 떨어져도 건별 처리하자....
		for (WaqCdVal delvo : list) {
			result += waqcdmapper.deleteByPrimaryKey(delvo);
		}
		return result;
	}

	/** insomnia */
	public WaqDmn getDmnInfoByDmnLnm(WaqDmn data) {
		return waqmapper.selectByDmnLnm(data);
	}

	/** insomnia */
	public int regapprove(WaqMstr mstVo, List<WaqDmn> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDmn savevo : (ArrayList<WaqDmn>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
			
			//위치 옮김 이상익 20151016
			//waqSditmMapper.deleteByDmnSnoNoChg(savevo);
			//waqSditmMapper.insertByDmnSno2(savevo);
			
			
		}

		waqmapper.updatervwStsCdRejectSwtd(rqstNo);
		
		//업데이트 내용이 없으면 오류 리턴한다.
		if (result < 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}
		return result;
	}

	/** yeonho */
	@Override
	public int delAllCdvalList(WaqMstr reqmst) {
		int result = 0;

		//요청서의 모든 유효값의 RQST_DCD를 DD로 변경
		result += waqcdmapper.deleteAllCdValByRqstNo(reqmst);
		return result;
	}

	//도메인 신청내역이 있는지 체크
    public int checkExistsWaqDmn(WaqMstr reqmst){
    
    	List<WaqDmn> list = waqmapper.selectExistsDmnCheck(reqmst);
    	
    	if(list.isEmpty()){
    		return 0;
    	}else {
    		return 2;
    	}
    }
    
    	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		//코드값 INSERT
        result += waqcdmapper.insertWaqCdRejected(reqmst, oldRqstNo); 
		//마스터 등록
		register(reqmst, null);
        
		//검증
		check(reqmst);
		

		return result;

	}

	@Override
	public List<WaqDmn> getDomainCdVal(WaqDmn data) {
		return waqmapper.selectDmnCdVal(data);
	}

}
