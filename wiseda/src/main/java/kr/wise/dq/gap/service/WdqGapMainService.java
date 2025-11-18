package kr.wise.dq.gap.service;

import java.util.List;

public interface WdqGapMainService {

	List<WdqModelGapVO> getModelGapAnalyze(WdqModelGapVO search);

	/** @return yeonho */
	List<WdqModelGapVO> getGapChart();

	List<WdqModelGapVO> getGapChartDateJSON();
	
	List<WdqModelGapVO> getModelDdlColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getPdmDdlGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getDdlDbcGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDdlDbcColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getMartPdmGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getMartPdmColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDdlTsfGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDdlTsfColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDdlTsfDbcGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDdlTsfDbcColGapList(WdqModelGapVO search);
	
	List<WdqModelGapVO> getDbcGapList(WdqModelGapVO search);
	
    List<WdqModelGapVO> getDbcColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getMdlDevDbTblGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getMdlDevDbColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getNonStndPdmColGapList(WdqModelGapVO search);

	List<WdqModelGapVO> getErwinPdmTblGapList(WdqModelGapVO search); 

	List<WdqModelGapVO> getErwinPdmColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getSrcDbSchList(WdqModelGapVO search);

    List<WdqModelGapVO> getTgtDbSchList(WdqModelGapVO search);

    List<WdqModelGapVO> getDbSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getDbSrcTgtColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getDdlSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getDdlSrcTgtColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getMdlDevDdlTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getMdlDevDdlColGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getDdlIdxSrcTgtTblGapList(WdqModelGapVO search);

    List<WdqModelGapVO> getDdlIdxSrcTgtColGapList(WdqModelGapVO search);
}
