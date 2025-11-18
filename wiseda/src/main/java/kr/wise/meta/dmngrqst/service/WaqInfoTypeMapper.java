package kr.wise.meta.dmngrqst.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.dmngrqst.service.WaqInfoType;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaqInfoTypeMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Short rqstSno, @Param("rqstDtlSno") Short rqstDtlSno);

    int insert(WaqInfoType record);

    int insertSelective(WaqInfoType record);

    WaqInfoType selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Short rqstSno, @Param("rqstDtlSno") Short rqstDtlSno);

    int updateByPrimaryKeySelective(WaqInfoType record);

    int updateByPrimaryKey(WaqInfoType record);

	void updateRqstDcdbyDmng(String rqstNo);

	void updateCheckInit(String rqstNo);

	void updateDmngNmbyRqstSno(String rqstNo);

	void checkNotChgData(Map<String, Object> checkmap2);

	void checkRequestInfoType(Map<String, Object> checkmap2);

	void checkNotExistInfoType(Map<String, Object> checkmap2);

	void checkDupInfoType(Map<String, Object> checkmap2);

	void checkDupInfoTypeLnm(Map<String, Object> checkmap2);

	void updateidByKey(WaqInfoType savevo);

	List<WaqInfoType> selectWaqC(String rqstno);

	int updateWaqId(String rqstno);

	WaqInfoType selectInfoTypeDetail(WaqInfoType search);

	int deleteByPrimaryKey(WaqInfoType savevo);

	List<WaqInfoType> selectInfoTypeRqstList(WaqMstr search);

	void deleteByrqstSno(WaqDmng saveVo);
	
	int updateWaaDmngInfotpMap(String rqstNo);
	
	int insertWaaDmngInfotpMap(String rqstNo);
}