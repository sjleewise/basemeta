package kr.wise.commons.damgmt.dmnginfo.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMap;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngInfotpMapMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoTypeMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("infotpService")
public class InfotpServiceImpl implements InfotpService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaInfoTypeMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Inject
	private WaaDmngInfotpMapMapper dmngInfoMapper;

	public List<WaaInfoType> getList(WaaInfoType search) {
		List<WaaInfoType> list = mapper.selectList(search);
		return list;
	}

	public WaaInfoType findInfotp(WaaInfoType search) {

		return mapper.selectByPrimaryKey(search.getInfotpId());
	}

	public int regInfotp(WaaInfoType record) throws Exception {
		logger.debug("{}", record);
		String tmpStatus ;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		//인포타입 논리명으로 기존 자료가 있는지 확인한다.
		WaaInfoType comvo = mapper.selectByName(record);
		if (comvo != null && StringUtils.hasText(comvo.getInfotpId())) {
			record.setInfotpId(comvo.getInfotpId());
			record.setIbsStatus("U");
		}
		tmpStatus = record.getIbsStatus();

		//인포타입ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getInfotpId())) {  // 신규...

			record.setInfotpId(objectIdGnrService.getNextStringId());
			record.setObjVers(1);

		} else if("U".equals(tmpStatus) && StringUtils.hasText(record.getInfotpId())) {
			//인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			/*WaaInfoType tmpVO = mapper.selectByPrimaryKey(record.getInfotpId());
			if (tmpVO == null || !tmpVO.getInfotpId().equals(record.getInfotpId())) {
				record.setInfotpId(objectIdGnrService.getNextStringId());
				record.setObjVers(1);
			} else {*/
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else {
					record.setObjVers(record.getObjVers()+1);
				}
				//기존 데이터 만료시킨다.
				mapper.updateExpDtm(record);

//			}

		}

		//사용자 ID 업데이트 후 데이터 추가한다.
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

	public int regInfotpList(ArrayList<WaaInfoType> list) throws Exception {
		int result = 0;
		for (WaaInfoType WaaInfoType : list) {
			result += regInfotp(WaaInfoType);
		}

		return result;
	}

	public int delInfotpList(ArrayList<WaaInfoType> list) {
		int result = 0;
		for (WaaInfoType WaaInfoType : list) {
			String id = WaaInfoType.getInfotpId();
			
			//==========인포타입매핑 존재 체크==========
			WaaDmngInfotpMap waaDmngInfotpMap = new WaaDmngInfotpMap(); 
			
			waaDmngInfotpMap.setInfotpId(id);
			
			List<WaaDmngInfotpMap> chkList = dmngInfoMapper.selectList(waaDmngInfotpMap);
			
			if(chkList.size() > 0) 
				return -2;
				
			//=================================
			if (id != null && !"".equals(id)) {
				//WaaInfoType.setIbsStatus("D");
				WaaInfoType.setExpDtm(null);
				result += mapper.updateExpDtm(WaaInfoType);
			}
		}
		return result;
	}

	/**인포타입 전체 조회-코드용으로 처리 insomnia */
	public List<WaaInfoType> getInfoTypeList() {

		return mapper.selectInfotpList();
	}



}