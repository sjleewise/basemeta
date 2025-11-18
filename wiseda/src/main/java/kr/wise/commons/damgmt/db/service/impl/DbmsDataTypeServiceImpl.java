package kr.wise.commons.damgmt.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.DbmsDataTypeService;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataType;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataTypeMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


	/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbmsDataTypeServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 22. 오후 4:16:56
 * </PRE>
 */ 
@Service("DbmsDataTypeService")
public class DbmsDataTypeServiceImpl implements DbmsDataTypeService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WaaDbmsDataTypeMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Override
	public List<WaaDbmsDataType> getList(WaaDbmsDataType record) {
		return mapper.selectList(record);
	}

	@Override
	public int regDataTypeList(ArrayList<WaaDbmsDataType> list) throws Exception {
		int result = 0;
		for (WaaDbmsDataType WaaDbmsDataType : list) {
			
			result += regDataType(WaaDbmsDataType);
		}
		return result;
	}

	@Override
	public int delDataTypeList(ArrayList<WaaDbmsDataType> list) {
		int result = 0;
		for (WaaDbmsDataType WaaDbmsDataType : list) {
			String id = WaaDbmsDataType.getDbmsDataTypeId();
			if (id != null && !"".equals(id)) {
				//WaaDmng.setIbsStatus("D");
				WaaDbmsDataType.setExpDtm(null);
				result +=mapper.updateExpDtm(WaaDbmsDataType);
			}
		}	
		return result;
	}

	@Override
	public int regDataType(WaaDbmsDataType record) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
			
		//데이터타입ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(!StringUtils.hasText(record.getDbmsDataTypeId())) {  // 신규...
			
			
			record.setDbmsDataTypeId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if(StringUtils.hasText(record.getDbmsDataTypeId())) {
			//데이터타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WaaDbmsDataType tmpVO = mapper.selectByPrimaryKey(record.getDbmsDataTypeId());
			if (tmpVO == null || !tmpVO.getDbmsDataTypeId().equals(record.getDbmsDataTypeId())) { 
				record.setDbmsDataTypeId(objectIdGnrService.getNextStringId());
				record.setRegTypCd("C");
				record.setObjVers(1);
			} else {
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
				}
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
			}
		} 
		
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

}
