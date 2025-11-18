/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysItemRqstServiceImpl.java
 * 2. Package : kr.wise.meta.codecfcsys.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 8. 오후 1:32:53
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 8. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.codecfcsys.service.CodeCfcSysItemRqstService;
import kr.wise.meta.codecfcsys.service.CodeCfcSysRqstService;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItem;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItemMapper;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysMapper;
import kr.wise.meta.stnd.service.WaqStwd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysItemRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.codecfcsys.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 8. 오후 1:32:53
 * </PRE>
 */
@Service("CodeCfcSysItemRqstService")
public class CodeCfcSysItemRqstServiceImpl implements CodeCfcSysItemRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqCodeCfcSysMapper waqCodeCfcSysMapper;
	
	@Inject
	private WaqCodeCfcSysItemMapper waqCodeCfcSysItemMapper;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
    private CodeCfcSysRqstService codeCfcSysRqstService;
	
	/** meta */
	@Override
	/** 코드분류체계 항목 요청서 저장.. meta */
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
			for (WaqCodeCfcSysItem saveVo : (ArrayList<WaqCodeCfcSysItem>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveCodeCfcSysItemRqst(saveVo);
			}
		}
		
		//INSERT 완료된 ITEM항목을 불러온다.
		List<WaqCodeCfcSysItem> itemList = waqCodeCfcSysItemMapper.selectCodeCfcSysRqstItemList(mstVo);
		int chkRqstSno = 0; // rqstSno 크기만큼 Insert해야 하므로 변수 선언...
		String chkRqstSnoPipe = "";
		logger.debug("select Item List : {}", itemList);
		
		//RQST_SNO의 갯수를 구한다. 뒤의 for문을 돌릴 횟수를 정하기 위함...
		for(WaqCodeCfcSysItem item : itemList) {
			if(item.getRqstSno() != chkRqstSno) {
				if(chkRqstSno != 0) {
					chkRqstSnoPipe += "|";
				}
				chkRqstSno = item.getRqstSno();
				chkRqstSnoPipe += item.getRqstSno(); 
			}
		}
		String[] chkRqstSnoPipeLength = chkRqstSnoPipe.split("[|]");
		chkRqstSno = chkRqstSnoPipeLength.length;
		
		List<WaqCodeCfcSys> list = new ArrayList<WaqCodeCfcSys>();
		
		for(int i=1; i<=chkRqstSno; i++) {
			//list에 add시킬 VO생성...
			WaqCodeCfcSys record = new WaqCodeCfcSys();
			String tmpCodeFrm = "";
			for (WaqCodeCfcSysItem item : itemList) {
				if(item.getRqstSno() == i) {
					tmpCodeFrm += item.getCodeCfcSysItemFrm();
					
					//맨 마지막의 구분자 제거는 나중에 다시 구현...
					if(item.getCodeCfcSysItemSpt() != null){
						
						tmpCodeFrm += item.getCodeCfcSysItemSpt();
					}
					record.setRqstNo(item.getRqstNo());
					record.setRqstSno(item.getRqstSno());
					record.setRqstDcd("CU");
					record.setCodeCfcSysCd(item.getCodeCfcSysCd());
					record.setCodeCfcSysLnm(item.getCodeCfcSysLnm());
				}
			}
			record.setCodeCfcSysFrm(tmpCodeFrm);
			list.add(record);
		}
		
		
		//WAQ테이블에 코드분류체계 등록요청...
//				LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
//				String userid = user.getUniqId();
		if(list != null) {
			for (WaqCodeCfcSys saveVo : list) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setIbsStatus("I");  // 기존데이터 삭제후 신규로 생성
//						saveVo.setRegTypCd("C");
//						saveVo.setRqstNo(rqstNo);
				
				//기존 waq테이블의 내용 삭제
				result += waqCodeCfcSysMapper.deleteByPrimaryKey(saveVo.getRqstNo(), saveVo.getRqstSno());
				//단건 저장...
				result += codeCfcSysRqstService.saveCodeCfcSysRqst(saveVo);
			}
		}
		

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 코드분류체계 항목 요청서 단건 저장... @return meta */
	private int saveCodeCfcSysItemRqst(WaqCodeCfcSysItem saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if (!"D".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqCodeCfcSysItemMapper.insertSelective(saveVo);

//			waqCodeCfcSysItemMapper.insertSelective(saveVo);

			//무조건 전부 삭제후 신규 등록되도록 수정(rqstSno문제로)
//		} else if ("U".equals(tmpstatus)){
//			//업데이트
//			result = waqCodeCfcSysItemMapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqCodeCfcSysItemMapper.deleteByrqstSno(saveVo);
		}

		return result;
	}
	
	/** meta */
	@Override
	public int check(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** meta */
	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** meta */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** 코드분류체계 항목 삭제 요청 */
	/** meta 
	 * @throws Exception */
	@Override
	public int delCodeCfcSysItemRqstList(WaqMstr reqmst, ArrayList<WaqCodeCfcSysItem> list) throws Exception {
		int result = 0;

		for (WaqCodeCfcSysItem savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** meta */
	@Override
	public int deleteOldCodeCfcSysItemInfo(WaqMstr reqmst) {
		return waqCodeCfcSysItemMapper.deleteOldCodeCfcSysItemInfo(reqmst);
	}

}
