/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbmsDataTypeMapServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.db.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 5. 21. 오후 1:24:45
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.DbmsDataTypeMapService;
import kr.wise.commons.damgmt.db.service.WaaDataTypeMapru;
import kr.wise.commons.damgmt.db.service.WaaDataTypeMapruMapper;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataType;
import kr.wise.commons.damgmt.db.service.WaaDbmsDataTypeMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbmsDataTypeMapServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 21. 오후 1:24:45
 * </PRE>
 */
@Service("DbmsDataTypeMapService")
public class DbmsDataTypeMapServiceImpl implements DbmsDataTypeMapService {

	@Inject
	WaaDataTypeMapruMapper waaDataTypeMapruMapper;
	
	@Inject
	WaaDbmsDataTypeMapper waaDbmsDataTypeMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	/** yeonho */
	@Override
	public List<WaaDataTypeMapru> getList(WaaDataTypeMapru search) {
		return waaDataTypeMapruMapper.selectList(search);
	}

	/** yeonho 
	 * @throws Exception */
	@Override
	public int regDataTypeMapList(ArrayList<WaaDataTypeMapru> list) throws Exception {
		int result = 0;
		for (WaaDataTypeMapru waaDataTypeMapru : list) {
			
			result += regDataTypeMap(waaDataTypeMapru);
		}
		return result;
	}

	/** @param waaDataTypeMapru
	/** @return yeonho 
	 * @throws Exception */
	private int regDataTypeMap(WaaDataTypeMapru record) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
			
		//데이터타입 매핑룰ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(!StringUtils.hasText(record.getDataTypeMapruId())) {  // 신규...
			
			
			record.setDataTypeMapruId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if(StringUtils.hasText(record.getDataTypeMapruId())) {
			//데이터타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WaaDataTypeMapru tmpVO = waaDataTypeMapruMapper.selectByPrimaryKey(record.getDataTypeMapruId());
			if (tmpVO == null || !tmpVO.getDataTypeMapruId().equals(record.getDataTypeMapruId())) { 
				record.setDataTypeMapruId(objectIdGnrService.getNextStringId());
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
				waaDataTypeMapruMapper.updateExpDtm(record);
			}
		} 
		
		//매핑룰 Nm 설정...
		WaaDbmsDataType tmpDbmsDataType = waaDbmsDataTypeMapper.selectByPrimaryKeyForMapping(record.getDbmsDataTypeId());
		if (tmpDbmsDataType != null && tmpDbmsDataType.getDbmsDataTypeId().equals(record.getDbmsDataTypeId())) {
			record.setDbmsTypCdNm(tmpDbmsDataType.getDbmsTypCdNm());
		}
			
		
		record.setMapruNm(record.getDbmsTypCdNm() + "_" + record.getLgcDataType());
		record.setWritUserId(user.getUniqId());
		result = waaDataTypeMapruMapper.insertSelective(record);
		return result;
	}

	/** yeonho */
	@Override
	public int delDataTypeMapList(ArrayList<WaaDataTypeMapru> list) {
		int result = 0;
		for (WaaDataTypeMapru waaDataTypeMapru : list) {
			String id = waaDataTypeMapru.getDataTypeMapruId();
			if (id != null && !"".equals(id)) {
				//WaaDmng.setIbsStatus("D");
				waaDataTypeMapru.setExpDtm(null);
				result += waaDataTypeMapruMapper.updateExpDtm(waaDataTypeMapru);
			}
		}	
		return result;
	}

}
