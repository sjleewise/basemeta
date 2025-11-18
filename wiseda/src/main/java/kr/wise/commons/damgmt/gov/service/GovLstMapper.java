package kr.wise.commons.damgmt.gov.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface GovLstMapper {
//    int deleteByPrimaryKey(@Param("dmngId") String dmngId, @Param("infotpId") String infotpId);

//    int insert(WaaDmngInfotpMap record);

    int insertSelective(WaaGovStwd record);
    
    int deleteSelective(WaaGovStwd record);

//    WaaGovStwd selectByPrimaryKey(@Param("stwdLnm") String stwdLnm, @Param("stwdPnm") String stwdPnm);
    WaaGovStwd selectByPrimaryKey(WaaGovStwd record);

    int updateByPrimaryKeySelective(WaaGovStwd record);

    int updateByPrimaryKey(WaaGovStwd record);
    
    List<WaaGovStwd> selectList(WaaGovStwd record); 
    
    int updateExpDtm(WaaGovStwd record);

//	int updateByPrimaryKeySelective(WaaGovStwd record);







///    int deleteByPrimaryKey(@Param("dmngId") String dmngId, @Param("infotpId") String infotpId);
//
////int insert(WaaDmngInfotpMap record);
//
int insertSelectivebySditm(WaaGovSditm record);
//
    int deleteSelectivebysditm(WaaGovSditm record);
//
////WaaGovStwd selectByPrimaryKey(@Param("stwdLnm") String stwdLnm, @Param("stwdPnm") String stwdPnm);
    WaaGovSditm selectCheckLstsditm(WaaGovSditm record);

	List<WaaGovSditm> selectByPrimaryKeySditm(WaaGovSditm record); 

	int updateByPrimaryKeySelectiveSditm(WaaGovSditm record);
	//
	int updateByPrimaryKeySditm(WaaGovSditm record);
	//
	List<WaaGovSditm> selectListBySditm(WaaGovSditm record); 
	//
	int updateExpDtmSditm(WaaGovSditm record);
	//
	////int updateByPrimaryKeySelective(WaaGovStwd record);
		


// ===========================================================도메인 관리=======================================
	List<WaaGovDmn> selectByPrimaryKey_dmn(WaaGovDmn record);  //특정 도메인명을 가진 기존 데이터 있는지 확인(리스트형식)

    List<WaaGovDmn> selectList_DMN(WaaGovDmn record);  //조건절 없이 전체 정보 확인
    
	WaaGovDmn selectByPrimaryKey_dmn2(WaaGovDmn record);  //특정 도메인명을 가진 기존 데이터 있는지 확인(객체형식)
	
    int updateByPrimaryKeySelective_dmn(WaaGovDmn record);  //기존 데이터 존재시 해당 내용 업데이트

    int updateExpDtm_dmn(WaaGovDmn record); //등록요청시 날짜 업데이트

    int insertSelective_dmn(WaaGovDmn record);

    int deleteSelective_dmn(WaaGovDmn record);
    
    
    
    // ===========================================================유효값 관리=============================================
    		List<WaaGovCdVal> selectByPrimaryKey_cdVal(WaaGovCdVal record);  //특정 유효값의 이름을 가진 기존 데이터 있는지 확인(리스트형식)

    	    List<WaaGovCdVal> selectList_cdVal(WaaGovCdVal record);  //조건절 없이 전체 정보 확인
//    	    
    	    WaaGovCdVal selectByPrimaryKey_cdVal2(WaaGovCdVal record);  //특정 유효값을 가진 기존 데이터 있는지 확인(객체형식)
//    		
    	    int updateByPrimaryKeySelective_cdVal(WaaGovCdVal record);  //기존 데이터 존재시 해당 내용 업데이트(신규 아님)
//
//    	    int updateExpDtm_cdVal(WaaGovCdVal record); //등록요청시 날짜 업데이트
//
    	    int insertSelective_cdVal(WaaGovCdVal record);
//
    	    int deleteSelective_cdVal(WaaGovCdVal record);


}