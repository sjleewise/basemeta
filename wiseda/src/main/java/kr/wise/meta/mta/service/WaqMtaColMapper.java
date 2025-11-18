/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaqMtaColMapper.java
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

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WaqMtaColMapper extends CommonRqstMapper {
	int insertByRqstSno(WaqMtaTbl saveVo);
	
	int deleteByrqstSno(WaqMtaTbl saveVo);

	int insertSelective(WaqMtaCol savevo); 

	int updateByPrimaryKeySelective(WaqMtaCol savevo);

	int deleteByPrimaryKey(WaqMtaCol savevo);

	List<WaqMtaCol> selectWaqC(@Param("rqstNo") String rqstNo);

	int updateidByKey(WaqMtaCol savevo);

	int updateWaqId(String rqstno);
	
	List<WaqMtaCol> selectMtaColRqstList(WaqMstr search);

	WaqMtaCol getMtaColRqstDetail(WaqMtaCol search);
	WaqMtaCol selectMtaColDetail(WaqMtaCol search);
	
	int updateRqstDcdbyTable(String rqstNo);
	
	int updateCheckInit(String rqstNo);
	int insertnoWaqdelCol(String rqstNo);
	int updateTblPnmbyRqstSno(String rqstNo);
	
	//컬럼순서 정비
	int checkDupCol(Map<String, Object> checkmap2);
	int updateColOrd(String rqstNo);
	int checkNotChgData(Map<String, Object> checkmap2);
	
	int checkMtaColItem(Map<String, Object> checkmap2);

	void insertByWatRqstSno(WaqMtaTbl saveVo);

	WaqMtaCol selectPopMtaColRqstDetail(WaqMtaCol search);

	void checkDupColLnm(Map<String, Object> checkmap2);

	int updatePrsnInfo(WaqMtaCol savevo);

	int checkNopenRsn(Map<String, Object> checkmap2);    
	
	int checkMtaColNm(WaqMtaCol search);
}
