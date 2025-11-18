/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WamMtaColMapper.java
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

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.mta.service.WamMtaCol;

@Mapper
public interface WamMtaColMapper {
    int deleteByPrimaryKey(String pdmColId);

    int insert(WamMtaCol record); 

    int insertSelective(WamMtaCol record);

    WamMtaCol selectByPrimaryKey(String pdmColId);

    List<WamMtaCol> selectList(WamMtaCol search);
    
    List<WamMtaCol> selectListGap(WamMtaCol search);

    int updateByPrimaryKeySelective(WamMtaCol record);

    int updateByPrimaryKey(WamMtaCol record);

	List<WamMtaCol> seleccoldtltList(WamMtaCol search);

	List<WamMtaCol> seleccolhisttList(WamMtaTbl search);

	/** @param search
	/** @return meta */
	//List<WamMtaCol> selectMtaColList(WamMtaCol search);
	List<HashMap<String, Object>> selectListByRqstNo(@Param("rqstNo") String rqstNo);
	List<HashMap<String, Object>> selectListByTblId(@Param("mtaTblId") String mtaTblId);
	
	/** @param search
	/** @return lsi */
	List<WamMtaCol> seleccolhisttListDtl(WamMtaCol search);
	
	List<WamMtaCol> selectMtaColChgList(WamMtaCol search);
	
	List<WamMtaCol> selectColNonStndList(WamMtaCol search); 

	List<HashMap<String, Object>> selectWamMtaColByRqstNo(String mtaRqstNo);
	
	
}