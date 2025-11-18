package kr.wise.commons.damgmt.dmnginfo.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.dmnginfo.service.DmngInfotpMapService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMap;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMapMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoTypeMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.WamDmn;
import kr.wise.meta.stnd.service.WamDmnMapper;
import kr.wise.meta.stnd.service.WamSditm;
import kr.wise.meta.stnd.service.WamSditmMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("DmngInfotpMapService")
public class DmngInfotpMapServiceImpl implements DmngInfotpMapService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaDmngInfotpMapMapper mapper;
	
	@Inject
	private WaaDmngMapper dmngMapper;
	
	@Inject
	private WaaInfoTypeMapper infoMapper;
	
	@Inject
	private WamDmnMapper wamDmnMapper;
	
	@Inject
	private WamSditmMapper wamSditmMapper;
	
	public List<WaaDmngInfotpMap> getList(WaaDmngInfotpMap search) {
		List<WaaDmngInfotpMap> list = mapper.selectList(search);
		return list;
	}
	
	public WaaDmngInfotpMap findDmngInfotpMap(WaaDmngInfotpMap search) {
		
		return mapper.selectByPrimaryKey(search.getDmngId(), search.getInfotpId());
	}
	
	public int regdmngInfotpMap(WaaDmngInfotpMap record) {
		logger.debug("{}", record);
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
		WaaDmng dmngVO = dmngMapper.selectByPrimaryKey(record.getDmngId());
		WaaInfoType infoVO = infoMapper.selectByPrimaryKey(record.getInfotpId());
		
		//도메인그룹ID, 인포타입ID가 있는지 확인하여 없을경우 리턴처리..(데이터 미입력)
		if(dmngVO == null || !dmngVO.getDmngId().equals(record.getDmngId())) {
			return 0;
		}
		if(infoVO == null || !infoVO.getInfotpId().equals(record.getInfotpId())) {
			return 0;
		}
		
			//도메인그룹ID, 인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
			WaaDmngInfotpMap tmpVO = mapper.selectByPrimaryKey(record.getDmngId(), record.getInfotpId());
			if (tmpVO == null || !tmpVO.getInfotpId().equals(record.getInfotpId()) || !tmpVO.getDmngId().equals(record.getDmngId())) { 
				
				
				record.setObjVers(1);
			} else {
				if (UtilObject.isNull(tmpVO.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(tmpVO.getObjVers()+1);
				}
				mapper.updateExpDtm(record);
				
				
			}
			
		
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

	@Override
	public int regDmngInfotpMapList(ArrayList<WaaDmngInfotpMap> list) {
		int result = 0;
		for (WaaDmngInfotpMap WaaDmngInfotpMap : list) {
			result += regdmngInfotpMap(WaaDmngInfotpMap);
		}
		return result;
		
	}


	public int deldmngInfotpMapList(ArrayList<WaaDmngInfotpMap> list) {
		int result = 0;
		for (WaaDmngInfotpMap waaDmngInfotpMap : list) {
			String dmngId = waaDmngInfotpMap.getDmngId();
			String infotpId = waaDmngInfotpMap.getInfotpId();
			String stndAsrt = waaDmngInfotpMap.getStndAsrt();
			
			//=========도메인 사용여부 체크=========
			WamDmn wamDmn = new WamDmn();	
			
			wamDmn.setDmngId(dmngId);
			wamDmn.setInfotpId(infotpId);
			wamDmn.setStndAsrt(stndAsrt);
			
			List<WamDmn> chkUsedDmn = wamDmnMapper.selectList(wamDmn);
			
			if(chkUsedDmn.size() > 0) {
				return -2;
			}
			//==============================
			
			//=========표준용어 사용여부 체크=========
			WamSditm wamSditm = new WamSditm();
			
			wamSditm.setDmngId(dmngId);
			wamSditm.setInfotpId(infotpId);
			wamSditm.setStndAsrt(stndAsrt);
			
			List<WamSditm> chkUsedSditm = wamSditmMapper.selectSditmList(wamSditm);
			
			if(chkUsedSditm.size() > 0) {
				return -3;
			}
			//==============================
			
			if (dmngId != null && !"".equals(dmngId) && !"".equals(infotpId)) {
				//WaaDmng.setIbsStatus("D");
				waaDmngInfotpMap.setExpDtm(null);
				result += mapper.updateExpDtm(waaDmngInfotpMap);
			}
		}	
		return result;
	}

	
	
	
}