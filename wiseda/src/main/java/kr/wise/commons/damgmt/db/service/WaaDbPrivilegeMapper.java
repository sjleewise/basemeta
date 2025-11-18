package kr.wise.commons.damgmt.db.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDbPrivilegeMapper {
    int deleteByPrimaryKey(@Param("privilegeId") String privilegeId, @Param("expDtm") Date expDtm);

    int insert(WaaDbPrivilege record);

    int insertSelective(WaaDbPrivilege record);

    WaaDbPrivilege selectByPrimaryKey(@Param("privilegeId") String privilegeId);

    int updateByPrimaryKeySelective(WaaDbPrivilege record);

    int updateByPrimaryKey(WaaDbPrivilege record);

	/** @param search
	/** @return yeonho */
	List<WaaDbPrivilege> getDbRoleList(WaaDbPrivilege search);

	/** @param record yeonho */
	int updateExpDtm(WaaDbPrivilege record);

	/** @param record
	/** @return yeonho */
	int deleteDbRoleAuth(WaaDbPrivilege record);
}