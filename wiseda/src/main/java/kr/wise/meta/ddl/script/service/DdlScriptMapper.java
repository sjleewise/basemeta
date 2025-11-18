/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlColumnMapper.java
 * 2. Package : kr.wise.meta.ddl.script.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오전 11:27:26
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.meta.ddl.script.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.ddl.script.model.Column;
import kr.wise.meta.ddl.script.model.EtcObject;
import kr.wise.meta.ddl.script.model.Grant;
import kr.wise.meta.ddl.script.model.Sequence;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlTbl;

import org.apache.ibatis.annotations.Param;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlColumnMapper.java
 * 3. Package  : kr.wise.meta.ddl.script.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오전 11:27:26
 * </PRE>
 */
@Mapper 
public interface DdlScriptMapper {

	/** @return insomnia */
	Column testboolean();

	/** @param ddlTblId
	/** @return insomnia */
	Table selectTableOne(String ddlTblId);

	/** @param rqstNo
	/** @return insomnia */
	List<Table> selectDdlTableListWaq(String rqstNo);
	
	/** @param rqstNo
	/** @return insomnia */
	List<Table> selectDdlIndexListWaq(String rqstNo);

	/** @param ddlTblId
	/** @return meta */
	String getScrtInfo(@Param("ddlTblId")String ddlTblId);

	List<Table> selectDdlTableListWaqRqstSno(WaqDdlTbl ddlVo);

	List<Table> selectDdlIndexListWaqRqstSno(WaqDdlIdx ddlVo);

	List<Table> selectTableIdxList(String ddlTblId);

	List<Table> selectWaqCreTableIdxList(Table table);

	List<Table> selectWaqUpdTableIdxList(Table table);

	Table selectTableIdxOne(String ddlIdxId);             

	String getScrtInfoSeq(String ddlSeqId);
	
	String getScrtInfoSeqByWaq(WaqDdlSeq ddlVo);
	
	List<Sequence> selectDdlSeqListWaq(String rqstNo);

	Table selectPartitionbyPartidByWaq(WaqDdlPart ddlVo); 

	Table selectPartitionForAlterByWaq(WaqDdlPart ddlVo); 

	Table selectTableOneByPartByWaq(WaqDdlPart ddlVo);

	List<Table> selectWahIndices(String ddlTblId);

	Table selectPartitionbyPartid(String objId);

	Table selectPartitionForAlter(String objId);

	Table selectTableOneByPart(String ddlTblId);

	Table selectDdlIndex(WaqDdlIdx ddlVo);

	Sequence selectDdlSeq(Sequence ddlVo);

	String selectDdlEtcByObjId(@Param("objId") String objId);

	Table selectTableOneForAlter(String ddlTblId);

	String getTblScrtInfo(@Param("ddlTblId") String ddlTblId, @Param("rqstNo") String rqstNo);

	String getIdxScrtInfo(@Param("ddlTblId") String ddlTblId, @Param("rqstNo") String rqstNo);

	String getSeqScrtInfo(@Param("ddlTblId") String ddlTblId, @Param("rqstNo") String rqstNo);

	List<Grant> selectDdlGrtListWaq(String rqstNo);
	
	List<Table> selectDdlPartbyRqstNo(String rqstNo);

	List<Table> selectDdlPartbyWaqDdlTblRqstNo(String rqstNo);

	Table selectTableOneByRqstNo(Map<String, Object> searchmap);

	String selectDdlGrtByObjId(@Param("ddlGrtId") String ddlGrtId);

	String selectDdlPartByObjId(@Param("ddlPartId") String ddlPartId);
	
	List<EtcObject> selectDdlEtcListWaq(String rqstNo);
	
	String getEtcScrtInfo(@Param("ddlTblId") String ddlTblId, @Param("rqstNo") String rqstNo);

	String getPartScrtInfo(@Param("ddlTblId")String ddlTblId, @Param("rqstNo")String rqstNo);

	Table selectTableForWah(@Param("ddlTblId")String ddlTblId, @Param("rqstNo")String rqstNo);

	Table selectIndexForWah(@Param("ddlIdxId")String ddlIdxId, @Param("rqstNo")String rqstNo);

	List<Sequence> selectDdlSeqListWaqRqstSno(WaqDdlSeq seqVo);

	Sequence selectSeqenceForWah(@Param("ddlSeqId")String ddlSeqId, @Param("rqstNo")String rqstNo);

	Grant selectGrantForWah(@Param("ddlGrtId")String ddlGrtId, @Param("rqstNo")String rqstNo);


}
