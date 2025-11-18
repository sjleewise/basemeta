/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaqMtaTblMapper.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.esb.send.service.EsbFilesendVO;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.WaqMtaTbl;

@Mapper
public interface WaqMtaTblMapper extends CommonRqstMapper {
	
	WaqMtaTbl selectMtaTblDetail(WaqMtaTbl searchVo);
	
	List<WaqMtaTbl> selectMtaTblListbyMst(WaqMstr search);
	
	int insertSelective(WaqMtaTbl record);
	
	int updateByPrimaryKeySelective(WaqMtaTbl record);
	
	int deleteByrqstSno(WaqMtaTbl saveVo);

	int updatervwStsCd(WaqMtaTbl savevo);

	List<WaqMtaTbl> selectWaqC(@Param("rqstNo") String rqstNo);

	int updateidByKey(WaqMtaTbl savevo);

	int updateWaqId(String rqstno); 

	List<WaqMtaTbl> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqMtaTbl> list);
	
	int updateCheckInit(String rqstNo);
	
	/** 요청서내 중복 확인 */
	int checkDupTbl(Map<String, Object> checkmap);
	
	/** 삭제일때 미존재 테이블 체크 */
	int checkNotExistTbl(Map<String, Object> checkmap);
	
	/** 요청중인 테이블 (PT004) */
	int checkRequestTbl(Map<String, Object> checkmap);
	
	int checkColCnt(Map<String, Object> checkmap);
	int checkNotChgData(Map<String, Object> checkmap);
	int checkMtaTblItem(Map<String, Object> checkmap);
	
	/** 메타데이터 변경요청을 위한 목록 조회(DBC) */
	List<WaqMtaTbl> selectDbcTblList(WaqMtaTbl search);

	List<WaqMtaTbl> selectwatlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqMtaTbl> list);

	int updateWamMtaGapStsCd(WatDbcTbl data);

	int updateInitGapStsCd(WatDbcTbl data);

	int checkColErr(Map<String, Object> checkmap);

	List<HashMap> selectOrgInfoSys(HashMap data);

	List<HashMap> selectInfoSysDbConnTrg(HashMap<String, String> data);

	List<HashMap> selectInfoSysDbSch(HashMap<String, String> data);

	HashMap<String, String>  selectTempRqstCnt(WaqMstr reqmst);

	int deleteNotExistsWaqMstr(String rqstNo);

	ArrayList<WaqMtaTbl> selectMtaTblByDbSchId(WaqMtaTbl data);


	int deleteNotMyTempSave(WaqMtaTbl data);

	int deleteErrWaqMstr(WaqMtaTbl data);

	ArrayList<WaqMtaTbl> selectUpdWatMtaCheck(WaqMtaTbl search);

	int updateTblAutoItem(String rqstNo);       
	
	int updateMtaGapStsCd(EsbFilesendVO paramEsbFilesendVO);
	
	int updateMtaRegTypCd(EsbFilesendVO paramEsbFilesendVO);

	int updateMtaColRegTypCd(EsbFilesendVO paramEsbFilesendVO);
}
