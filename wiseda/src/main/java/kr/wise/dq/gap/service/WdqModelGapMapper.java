package kr.wise.dq.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqModelGapMapper {

	List<WdqModelGapVO> getModelGapAnalyze(WdqModelGapVO search);

	List<WdqModelGapVO> getGapChart();
	
	List<WdqModelGapVO> getModelDdlColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectPdmDdlGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDdlDbcColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectMartPdmGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectMartPdmColGapList(WdqModelGapVO search);
		
	List<WdqModelGapVO> selectDdlDbcGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDdlTsfGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDdlTsfColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDdlTsfDbcGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDdlTsfDbcColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDbcGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> selectDbcColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> selectMdlDevDbTblGapList(WdqModelGapVO search); 

	List<WdqModelGapVO> selectMdlDevDbColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> selectNonStndPdmColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> selectErwinPdmTblGapList(WdqModelGapVO search);

	List<WdqModelGapVO> selectErwinPdmColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectSrcDbSchList(WdqModelGapVO search);

    List<WdqModelGapVO> selectTgtDbSchList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDbSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDbSrcTgtColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDdlSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDdlSrcTgtColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectMdlDevDdlTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectMdlDevDdlColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDdlIdxSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> selectDdlIdxSrcTgtColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getGapChartDateJSON();           

}
