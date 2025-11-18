package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqDdlTblMapper extends CommonRqstMapper {
//    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlTbl record);

    int insertSelective(WaqDdlTbl record);

    WaqDdlTbl selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlTbl record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlTbl record);

    int updateByPrimaryKey(WaqDdlTbl record);

	/** @param search
	/** @return insomnia */
	List<WaqDdlTbl> selectDdlListbyMst(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	List<WaqDdlTbl> selectpdmtbllist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqPdmTbl> list);
	
	List<WaqDdlTbl> selectdelddltbllist(@Param("reqmst") WaqMstr reqmst,  @Param("list") ArrayList<WaqPdmTbl> list);         


	/** @param saveVo
	/** @return insomnia */
	int deleteByrqstSno(WaqDdlTbl saveVo);

	/** @param searchVo
	/** @return insomnia */
	WaqDdlTbl selectDdlTblDetail(WaqDdlTbl searchVo);

	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo insomnia */
	int updatePdmTblId(String rqstNo);

	/** @param rqstNo insomnia */
	int updateDbmsId(String rqstNo);

	/** @param checkmap insomnia */
	int checkDupTbl(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNotExistTbl(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNonDbmsID(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkRequestTbl(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int updateTblSpaceId(String rqstNo);

	/** @param checkmap insomnia */
	int checkNonPdmTbl(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int updateDropTableCd(String rqstNo);

	/** @param rqstNo insomnia */
	int updateDropTableCd2(String rqstNo);

	/** @param checkmap insomnia */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkColErr(Map<String, Object> checkmap);

	/** @param savevo
	/** @return insomnia */
	int updatervwStsCd(WaqDdlTbl savevo);

	/** @param rqstno
	/** @return insomnia */
	List<WaqDdlTbl> selectWaqC(String rqstno);

	/** @param savevo insomnia */
	int updateidByKey(WaqDdlTbl savevo);

	/** @param savevo insomnia */
	int updateDdlScriptWaq(WaqDdlTbl savevo);

	/** @param mstVo
	/** @return insomnia */
	List<WaqDdlTbl> selectDdlScriptCre(WaqMstr mstVo);

	/** @param rqstNo insomnia */
	int updateAlterTableCd(String rqstNo);

	int updateDbschema(String rqstNo);

	int updateDbmsPnm(String rqstNo);

	ArrayList<WaqDdlTbl> selectPdmToDdlTbl(WaqMstr mstVo);

	List<WaqDdlTbl> selectTsfDdlTblList(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlTbl> list);

	List<WaqDdlTbl> selectDdlTsfTblRqstList(WaqMstr search);

	void updateInitTblChgTypCd(String rqstNo);

	void updateDropTableCd3(String rqstNo);

	void updateDropTableCd4(String rqstNo);

	int updatervwStsCdIdx(WaqMstr mstVo);

	List<WaqDdlTbl> selectDdlTblVrfCdList(WaqMstr reqmst);

	int updateDropTablePkChg(String rqstNo);

	int updateTblCmmtChgYn(String rqstNo);


	int updateDevDdlTstReflYn(WaqMstr mstVo);

	int updateDevDdlOprReflYn(WaqMstr mstVo);

	int updateTstDdlOprReflYn(WaqMstr mstVo);

    int updateIdxTblSpaceId(String rqstNo);

	int updateEncYn(String rqstNo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
}