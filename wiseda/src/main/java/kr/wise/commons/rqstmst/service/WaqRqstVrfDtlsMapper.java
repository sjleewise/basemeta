package kr.wise.commons.rqstmst.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqRqstVrfDtlsMapper {
    int deleteByPrimaryKey(WaqRqstVrfDtls record);

    int deleteSelective(WaqRqstVrfDtls record);

    int deleteByRqstNo(WaqRqstVrfDtls record);

    int insert(WaqRqstVrfDtls record);

    int insertSelective(WaqRqstVrfDtls record);

    WaqRqstVrfDtls selectByPrimaryKey(WaqRqstVrfDtls record);

    List<WaqRqstVrfDtls> selectList(WaqRqstVrfDtls record);

    int updateByPrimaryKeySelective(WaqRqstVrfDtls record);

    int updateByPrimaryKey(WaqRqstVrfDtls record);

	int updateVrfCd(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd);

	int updateVrfCd(Map<String, Object> param);

	/** @param checkmap2 insomnia */
	int updateVrfCdsNo(Map<String, Object> checkmap2);

	int updateNotChgSno(Map<String, Object> checkmap2);

	int updateVrfCdDtlsNo(Map<String, Object> checkidxcolmap); 
	
	//2018.10.31
	int delByBrfCd(Map<String, Object> checkmap); 
	//요청사유 미입력 또는 한글기준 10자 이상....(COM01)
	int checkRqstResn(Map<String, Object> checkmap);
	//오라클만 사용 가능...COM02
	int checkDbmsType(Map<String, Object> checkmap);
	
	int updateNotChgData(Map<String, Object> checkmap);

	int updateNotChgDatabySno(Map<String, Object> checkmap2);

	int updateVrfCdCheck(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd);
	int updateVrfCdCheck(Map<String, Object> param);
}