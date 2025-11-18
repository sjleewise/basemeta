package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WaqAppPrgmMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqAppPrgm record);

    int insertSelective(WaqAppPrgm record);

    WaqAppPrgm selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqAppPrgm record);

    int updateByPrimaryKey(WaqAppPrgm record);

	WaqAppPrgm getAppPrgmRqstDetail(WaqAppPrgm searchVo);

	int deleteByrqstSno(WaqAppPrgm saveVo);

	int updateCheckInit(String rqstNo);

	int checkDupApp(Map<String, Object> checkmap);

	int checkDupAppLnm(Map<String, Object> checkmap);

	int checkDupAppPnm(Map<String, Object> checkmap);

	int checkNotChgData(Map<String, Object> checkmap);

	List<WaqAppPrgm> selectAppPrgmListbyMst(WaqMstr search);

	boolean checkEmptyRqst(String rqstNo);

	int updatervwStsCd(WaqAppPrgm savevo);

	List<WaqAppPrgm> selectWaqC(String rqstno);

	int updateidByKey(WaqAppPrgm savevo);

	int updateWaqCUD(String rqstno);

	int deleteWAM(String rqstno);

	int insertWAM(String rqstno);

	int updateWAH(String rqstno);

	int insertWAH(String rqstno);

	List<WaqAppPrgm> selectwamlist(@Param("reqmst")WaqMstr reqmst, @Param("list") ArrayList<WaqAppPrgm> list);
}