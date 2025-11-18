package kr.wise.dq.gap.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.dq.gap.service.WdqGapMainService;
import kr.wise.dq.gap.service.WdqModelGapMapper;
import kr.wise.dq.gap.service.WdqModelGapVO;

@Service("wdqGapMainService")
public class WdqGapMainServiceImpl implements WdqGapMainService {

	@Inject
	WdqModelGapMapper modelGapMapper;
	
	@Override
	public List<WdqModelGapVO> getModelGapAnalyze(WdqModelGapVO search) {
		return modelGapMapper.getModelGapAnalyze(search);
	}

	/** yeonho */
	@Override
	public List<WdqModelGapVO> getGapChart() {
		return modelGapMapper.getGapChart();
	}
	
	@Override
	public List<WdqModelGapVO> getModelDdlColGapList(WdqModelGapVO search) {
		return modelGapMapper.getModelDdlColGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getPdmDdlGapList(WdqModelGapVO search) {
		return modelGapMapper.selectPdmDdlGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlDbcGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlDbcGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlDbcColGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlDbcColGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getMartPdmGapList(WdqModelGapVO search) {
		return modelGapMapper.selectMartPdmGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getMartPdmColGapList(WdqModelGapVO search) {
		return modelGapMapper.selectMartPdmColGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlTsfGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlTsfGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlTsfColGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlTsfColGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlTsfDbcGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlTsfDbcGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDdlTsfDbcColGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDdlTsfDbcColGapList(search);
	}
	
		@Override
	public List<WdqModelGapVO> getDbcGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDbcGapList(search);
	}
	
	@Override
	public List<WdqModelGapVO> getDbcColGapList(WdqModelGapVO search) {
		return modelGapMapper.selectDbcColGapList(search);
	}

	//===============모델개발DB GAP분석===================
	@Override
	public List<WdqModelGapVO> getMdlDevDbTblGapList(WdqModelGapVO search) {
		
	
		return modelGapMapper.selectMdlDevDbTblGapList(search); 
	}

	//모델개발DB GAP분석
	@Override
	public List<WdqModelGapVO> getMdlDevDbColGapList(WdqModelGapVO search) {
		
		return modelGapMapper.selectMdlDevDbColGapList(search);
	}
	//===============모델개발DB GAP분석 END=================
	
	//===============모델개발DDL GAP분석===================
	@Override
    public List<WdqModelGapVO> getMdlDevDdlTblGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectMdlDevDdlTblGapList(search);  
    }

    @Override
    public List<WdqModelGapVO> getMdlDevDdlColGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectMdlDevDdlColGapList(search); 
    }
    //===============모델개발DDL GAP분석 END===================

    //===============비표준컬럼 GAP분석===================
	@Override
	public List<WdqModelGapVO> getNonStndPdmColGapList(WdqModelGapVO search) {
		
		return modelGapMapper.selectNonStndPdmColGapList(search);   
	}
	
	//================ER-WIN메타모델GAP 분석====================
	@Override
	public List<WdqModelGapVO> getErwinPdmTblGapList(WdqModelGapVO search) {
		
		return modelGapMapper.selectErwinPdmTblGapList(search);  
	}

	@Override
	public List<WdqModelGapVO> getErwinPdmColGapList(WdqModelGapVO search) {
		
		return modelGapMapper.selectErwinPdmColGapList(search);  
	}
	//===============ER-WIN메타모델GAP 분석 END===================

    @Override
    public List<WdqModelGapVO> getSrcDbSchList(WdqModelGapVO search) {
        
        return modelGapMapper.selectSrcDbSchList(search); 
    }

    @Override
    public List<WdqModelGapVO> getTgtDbSchList(WdqModelGapVO search) {
        
        return modelGapMapper.selectTgtDbSchList(search); 
    }

    @Override 
    public List<WdqModelGapVO> getDbSrcTgtTblGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDbSrcTgtTblGapList(search); 
    }

    @Override
    public List<WdqModelGapVO> getDbSrcTgtColGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDbSrcTgtColGapList(search); 
    }

    
    @Override
    public List<WdqModelGapVO> getDdlSrcTgtTblGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDdlSrcTgtTblGapList(search); 
    }

    @Override
    public List<WdqModelGapVO> getDdlSrcTgtColGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDdlSrcTgtColGapList(search);  
    }

    @Override
    public List<WdqModelGapVO> getDdlIdxSrcTgtTblGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDdlIdxSrcTgtTblGapList(search); 
    }

    @Override
    public List<WdqModelGapVO> getDdlIdxSrcTgtColGapList(WdqModelGapVO search) {
        
        return modelGapMapper.selectDdlIdxSrcTgtColGapList(search);  
    }

	@Override
	public List<WdqModelGapVO> getGapChartDateJSON() {
		return modelGapMapper.getGapChartDateJSON();
	}
	
}
