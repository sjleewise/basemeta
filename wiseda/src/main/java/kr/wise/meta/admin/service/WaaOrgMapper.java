package kr.wise.meta.admin.service;

import java.util.List;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaaOrgMapper extends CommonRqstMapper {
	
	List<WaaOrg> selectListOrderSys(WaaOrg record);

	WaaOrg selectByPrimaryKey(String OrgId);
	
	int updateExpDtm(WaaOrg record);
	
	int insertSelective(WaaOrg record);
	
	WaaOrg selectUppOrgByOrgNm(WaaOrg record);
	
	int updateAllOrgNm(@Param("OrgId") String OrgId);
	
}