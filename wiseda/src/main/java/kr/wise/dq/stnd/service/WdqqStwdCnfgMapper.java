package kr.wise.dq.stnd.service;

import java.util.ArrayList;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqqStwdCnfgMapper {
    int deleteByPrimaryKey(@Param("bizDtlCd") String bizDtlCd, @Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("wdSno") Integer wdSno);

    int insert(WdqqStwdCnfg record);

    int insertSelective(WdqqStwdCnfg record);

    WdqqStwdCnfg selectByPrimaryKey(@Param("bizDtlCd") String bizDtlCd, @Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("wdSno") Integer wdSno);

    int updateByPrimaryKeySelective(WdqqStwdCnfg record);

    int updateByPrimaryKey(WdqqStwdCnfg record);

	int deleteBySno(WdqqStwdCnfg data);

	/** @param rqstno insomnia */
	int deleteWAM(String rqstno);

	int deleteWAMItem(String rqstno);

	/** @param rqstno insomnia */
	int insertWAM(String rqstno);

	int insertWAMItem(String rqstno);

	/** @param reqmst
	/** @param list insomnia */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WdqqDmn> list);
}