package kr.wise.commons.damgmt.dmnginfo.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.dmnginfo.service.DmngService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMap;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMapMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoTypeMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:33:34
 * </PRE>
 */ 
@Service("DmngService")
public class DmngServiceImpl implements DmngService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaDmngMapper mapper;
	
	@Inject
	private WaaDmngInfotpMapMapper dmngInfoMapper; 
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	public List<WaaDmng> getList(WaaDmng search) {
		
		List<WaaDmng> list = mapper.selectList(search);
		return list;
	}
	
	public WaaDmng findDmng(WaaDmng search) {
		
		return mapper.selectByPrimaryKey(search.getDmngId());
	}
	
	@Override
	public int regDmng(WaaDmng record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
		WaaDmng comVO = mapper.selectDetail(record);
		
		if (comVO != null) {
			tmpStatus = "U";
			record.setDmngId(comVO.getDmngId());
			record.setUppDmngId(comVO.getUppDmngId());
		}
		
		//도메인그룹ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDmngId())) {  // 신규...
			record.setDmngId(objectIdGnrService.getNextStringId());
			record.setObjVers(1);
			
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getDmngId())) {
			//도메인그룹ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			/*WaaDmng tmpVO = mapper.selectByPrimaryKey(record.getDmngId());
			if (tmpVO == null || !tmpVO.getDmngId().equals(record.getDmngId())) { 
				record.setDmngId(objectIdGnrService.getNextStringId());
				record.setObjVers(1);
			} else {*/
//				record.setStrDtm(tmpVO.getStrDtm());
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
				}
				mapper.updateExpDtm(record);
//			}
		} 
		
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}
	
	public int regDmngList(ArrayList<WaaDmng> list, WaaBscLvl bscLvl) throws Exception {
		int result = 0;
		String rqstNo = objectIdGnrService.getNextStringId();
		
		for (WaaDmng vo : list) {
			
			//===========기본레벨과 틀릴 경우============ 			
			if((int)vo.getDmngLvl() > (int)bscLvl.getBscLvl()){
				
				return -5;
			}
			//======================================
			//===========상위 도메인 그룹의 레벨이 기본레벨과 같거나 클  경우============
			if(vo.getUppDmngId() != null && vo.getUppDmngId() != "") {
				int uppLvl = (mapper.selectByPrimaryKey(vo.getUppDmngId())).getDmngLvl();
				if(uppLvl >= (int)bscLvl.getBscLvl()) {
					return -5;
				}
			}
			//========================================================
			
			
			logger.debug("waadmng:{}", vo);
			vo.setRqstNo(rqstNo);
			
			if(UtilString.null2Blank(vo.getUppDmngLnm()).equals("")){
				vo.setDmngLvl(1);
			}
			
			result += regDmng(vo);
		}
		
		//상위도메인그룹명 존재 시 레벨 NULL UPDATE
//		result += mapper.updtDmngLvl(rqstNo);
		//상위도메인그룹ID, LEVEL UPDATE
		result += mapper.updtUppDmngInfo(rqstNo);
		//도메인그룹 경로 update
		result += mapper.updateFullPath(rqstNo);
		
		return result;
	}


	public int delDmngList(ArrayList<WaaDmng> list) {
		int result = 0;
		
		for (WaaDmng WaaDmng : list) {
			
			String id = WaaDmng.getDmngId();
			
			//=========인포타입매핑 존재 체크========
			WaaDmngInfotpMap waaDmngInfo = new WaaDmngInfotpMap();
			
			waaDmngInfo.setDmngId(id);
			
			List<WaaDmngInfotpMap> chkList = dmngInfoMapper.selectList(waaDmngInfo);
			
			if(chkList.size() > 0){
				
				return -2;
			}
			//=================================
			
			//=========하위도메인 존재 체크========
			WaaDmng waaDmng = new WaaDmng();
			
			waaDmng.setUppDmngId(id);
			
			List<WaaDmng> chkChd = mapper.selectChkChdDmngList(waaDmng); 
			
			if(chkChd.size() > 0){
				
				return -3;
			}
			//=================================
						
			if (id != null && !"".equals(id)) {
				//WaaDmng.setIbsStatus("D");
				WaaDmng.setExpDtm(null);
				result +=mapper.updateExpDtm(WaaDmng);
			}
		}	
		return result;
	}

	/** yeonho */
	@Override
	public WaaDmng getDmngId(String dmngLnm) {
		return mapper.getDmngId(dmngLnm);
	}
	
	
}
