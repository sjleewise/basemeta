/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblServiceImpl.java
 * 2. Package : kr.wise.meta.mta.service.impl
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.02.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.02. :            : 신규 개발.
 */
package kr.wise.meta.mta.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.UtilEncryption;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.user.service.impl.KISA_SHA256;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.admin.service.WaaInfoSysMapper;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.dbc.service.WatDbcTblMapper;
import kr.wise.meta.mta.service.MtaTblService;
/*import kr.wise.meta.srcdata.service.SrcDataRqstService;
import kr.wise.meta.srcdata.service.WaqSrcDataRqst;*/
import kr.wise.meta.mta.service.WamMtaCol;
import kr.wise.meta.mta.service.WamMtaColMapper;
import kr.wise.meta.mta.service.WamMtaExl;
import kr.wise.meta.mta.service.WamMtaTbl;
import kr.wise.meta.mta.service.WamMtaTblMapper;
import kr.wise.meta.mta.service.WaqMtaTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("mtaTblService")
public class MtaTblServiceImpl implements MtaTblService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamMtaTblMapper wamMtaTblMapper;
	
	@Inject
	private WamMtaColMapper wamMtaColMapper;
	
	@Inject
	private WaaInfoSysMapper waaInfoSysMapper;
	
/*	@Inject
	private SrcDataRqstService srcDataRqstService;*/

	@Inject
	private WatDbcTblMapper watDbcTblMapper;
	
	@Inject
	private WaaDbConnTrgMapper dbConnTrgMapper;
	
	@Inject
	private WaaDbSchMapper dbSchMapper;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private ScheduleManagerService scheduleManagerService;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;
	

	/** 테이블정의서 정보 상세 조회  */
	@Override
	public WamMtaTbl selectMtaTblInfoDetail(String mtaTblId) {
		
		logger.debug("mtaTblId: ",mtaTblId);
		
		return wamMtaTblMapper.selectMtaTblInfoDetail(mtaTblId);
	}

	/** 테이블정의서  목록 조회 */
	@Override
	public List<WamMtaTbl> getMtaTblList(WamMtaTbl data) {
		
		logger.debug("searchvo:{}", data);

		return wamMtaTblMapper.selectList(data); 
	}

	/** 테이블정의서에 대한 컬럼정의서 목록 조회 */
	@Override
	public List<WamMtaCol> getMtaColList(WamMtaCol data) {
		
		logger.debug("searchvo:{}", data);
		
		return wamMtaColMapper.selectList(data);
	}

	/** 정보시스템 상세 조회 */
	@Override
	public WaaInfoSys getInfoSysInfoDetail(String orgCd, String infoSysCd) {
		
		logger.debug("infoSysCd:{}", infoSysCd);
		
		return waaInfoSysMapper.selectInfoSysInfoDetail(orgCd, infoSysCd);
	}
	
	/** 보유DB변경(DDL) */
	/*@Override
	public int regDbChgList(ArrayList<WamMtaTbl> list, WamMtaTbl record) {

		int result = 0;

		for (WamMtaTbl wamMtaTbl : list) {
			wamMtaTbl.setOrgNm(record.getOrgNm());
			wamMtaTbl.setInfoSysNm(record.getInfoSysNm());
			wamMtaTbl.setDbConnTrgPnm(record.getDbConnTrgPnm());
			wamMtaTbl.setDbSchPnm(record.getDbSchPnm());
			
			try {
				result += regDbChg(wamMtaTbl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}*/
	
	/** 보유DB변경(DDL) : 원천데이터요청정보(waq_src_data_rqst)에 저장  */
	/*@Override
	public int regDbChg(WamMtaTbl record) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			WaqSrcDataRqst saveVo = new WaqSrcDataRqst();
			String[] conts = record.getRvwConts().split("\\n");
			
			for(int i=0; i<conts.length; i++){
				saveVo.setRgstType("D");	//요청유형 : DDL
				saveVo.setRgstNo(System.currentTimeMillis()+String.format("%02d",(int)(Math.random()*100)));
				saveVo.setSendOrgCd(record.getOrgCd());
				saveVo.setSendOrgNm(record.getOrgNm());
				saveVo.setRecvOrgCd(record.getOrgCd());
				saveVo.setRecvOrgNm(record.getOrgNm());
				saveVo.setInfoSysCd(record.getInfoSysCd());
				saveVo.setInfoSysNm(record.getInfoSysNm());
				saveVo.setDbConnTrgPnm(record.getDbConnTrgPnm());
				saveVo.setDbSchNm(record.getDbSchPnm());
				saveVo.setTblNm(record.getMtaTblPnm());
				saveVo.setCrgUserId(user.getUniqId());	//요청자 = 담당자
				saveVo.setReqUserNm(user.getName());
				saveVo.setClctSql(conts[i]);
//				saveVo.setCmmPrcuse("N");	//활용안함
				saveVo.setProcStatus("02");	//배부완료
//				saveVo.setShdStrDtm(sdFormat.format(new Date()));
//				saveVo.setShdStrHr("00");
//				saveVo.setShdStrMnt("00");
				saveVo.setFrsRqstUserId(user.getUniqId());
				saveVo.setFrsRqstDtm(transFormat.parse(FileManagerUtil.getTimeStamp24()));
				saveVo.setRqstUserId(user.getUniqId());
				saveVo.setRqstDtm(transFormat.parse(FileManagerUtil.getTimeStamp24()));
				saveVo.setAprvUserId(user.getUniqId());
				
				result = srcDataRqstService.register(saveVo);	//요청정보 저장
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}*/

	@Override
	public List<WatDbcTbl> getWatTblList(WatDbcTbl data) {
		
		logger.debug("searchvo:{}", data);

		return watDbcTblMapper.selectWatGapList(data); 
	}
	
	@Override
	public List<WamMtaExl> getMtaExlLst(WamMtaExl data) {
		
		logger.debug("searchvo:{}", data);

		return watDbcTblMapper.selectMtaExlLst(data); 
	}
	
	@Override
	public List<WamMtaExl> getMtaExlRqst(WamMtaExl data) {
		
		logger.debug("searchvo:{}", data);

		return watDbcTblMapper.selectMtaExlRqst(data); 
	}
	
	
	@Override
	public List<WaaDbConnTrgVO> getDbConnTrgList(WaaDbConnTrgVO search) {
		
		//List<WaaDbConnTrgVO> list = dbConnTrgMapper.selectDbDefnList(search);
		
		List<WaaDbConnTrgVO> list = dbConnTrgMapper.selectMtaDbDefnList(search); 
		
		return list;
	}

	@Override
	public List<WaaDbSch> getDbSchList(String dbConnTrgId) {
		return dbSchMapper.selectListByFK(dbConnTrgId);
	}

	@Override
	public WaaDbConnTrgVO getDbDefnInfoDetail(String dbConnTrgId) {
		return dbConnTrgMapper.selectDbDefnInfo(dbConnTrgId);
	}

	@Override
	public int regList(ArrayList<WamMtaExl> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		int mtaDgr = 0;
		
		if(list.get(0).getMtaDgr() != null)
			watDbcTblMapper.deleteMtaExl(list.get(0).getMtaDgr());
		else
			mtaDgr = watDbcTblMapper.selectMtaDgr();

		String id = objectIdGnrService.getNextStringId();
		
		for (WamMtaExl wamMtaExl : list) {
			wamMtaExl.setMtaExlId(id);
			if(wamMtaExl.getMtaDgr() == null)
				wamMtaExl.setMtaDgr(mtaDgr+"");
			result += regMtaExl(wamMtaExl);
		}

		return result;
	}
	
	public int regMtaExl(WamMtaExl record) {
		int result = 0;
		
		result = watDbcTblMapper.insertMtaExl(record);
		
		return result;
	}
	
	
}
