package kr.wise.meta.gap.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.gap.service.GapMainService;
import kr.wise.meta.gap.service.ModelGapMapper;
import kr.wise.meta.gap.service.ModelGapVO;

import org.springframework.stereotype.Service;

@Service("GapMainService")
public class GapMainServiceImpl implements GapMainService {

	@Inject
	ModelGapMapper modelGapMapper;
	
	@Override
	public List<ModelGapVO> getModelGapAnalyze(ModelGapVO search) {
		return modelGapMapper.getModelGapAnalyze(search);
	}

	/** yeonho */
	@Override
	public List<ModelGapVO> getGapChart() {
		return modelGapMapper.getGapChart();
	}
	
	@Override
	public List<ModelGapVO> getModelDdlColGapList(ModelGapVO search) {
		return modelGapMapper.getModelDdlColGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getPdmDdlGapList(ModelGapVO search) {
		return modelGapMapper.selectPdmDdlGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlDbcGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlDbcGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlDbcColGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlDbcColGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getMartPdmGapList(ModelGapVO search) {
		return modelGapMapper.selectMartPdmGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getMartPdmColGapList(ModelGapVO search) {
		return modelGapMapper.selectMartPdmColGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlTsfGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlTsfGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlTsfColGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlTsfColGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlTsfDbcGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlTsfDbcGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDdlTsfDbcColGapList(ModelGapVO search) {
		return modelGapMapper.selectDdlTsfDbcColGapList(search);
	}
	
		@Override
	public List<ModelGapVO> getDbcGapList(ModelGapVO search) {
		return modelGapMapper.selectDbcGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getDbcColGapList(ModelGapVO search) {
		return modelGapMapper.selectDbcColGapList(search);
	}

	//===============모델개발DB GAP분석===================
	@Override
	public List<ModelGapVO> getMdlDevDbTblGapList(ModelGapVO search) {
		
	
		return modelGapMapper.selectMdlDevDbTblGapList(search); 
	}

	//모델개발DB GAP분석
	@Override
	public List<ModelGapVO> getMdlDevDbColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectMdlDevDbColGapList(search);
	}
	//===============모델개발DB GAP분석 END=================
	
	//===============모델개발DDL GAP분석===================
	@Override
    public List<ModelGapVO> getMdlDevDdlTblGapList(ModelGapVO search) {
        
        return modelGapMapper.selectMdlDevDdlTblGapList(search);  
    }

    @Override
    public List<ModelGapVO> getMdlDevDdlColGapList(ModelGapVO search) {
        
        return modelGapMapper.selectMdlDevDdlColGapList(search); 
    }
    //===============모델개발DDL GAP분석 END===================

    //===============비표준컬럼 GAP분석===================
	@Override
	public List<ModelGapVO> getNonStndPdmColGapList(ModelGapVO search) {
		List<ModelGapVO> result = modelGapMapper.selectNonStndPdmColGapList(search);
		return result;   
	}
	
	//================ER-WIN메타모델GAP 분석====================
	@Override
	public List<ModelGapVO> getErwinPdmTblGapList(ModelGapVO search) {
		
		return modelGapMapper.selectErwinPdmTblGapList(search);  
	}

	@Override
	public List<ModelGapVO> getErwinPdmColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectErwinPdmColGapList(search);  
	}
	//===============ER-WIN메타모델GAP 분석 END===================

    @Override
    public List<ModelGapVO> getSrcDbSchList(ModelGapVO search) {
        
        return modelGapMapper.selectSrcDbSchList(search); 
    }

    @Override
    public List<ModelGapVO> getTgtDbSchList(ModelGapVO search) {
        
        return modelGapMapper.selectTgtDbSchList(search); 
    }

    @Override 
    public List<ModelGapVO> getDbSrcTgtTblGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDbSrcTgtTblGapList(search); 
    }

    @Override
    public List<ModelGapVO> getDbSrcTgtColGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDbSrcTgtColGapList(search); 
    }

    
    @Override
    public List<ModelGapVO> getDdlSrcTgtTblGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDdlSrcTgtTblGapList(search); 
    }

    @Override
    public List<ModelGapVO> getDdlSrcTgtColGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDdlSrcTgtColGapList(search);  
    }

    @Override
    public List<ModelGapVO> getDdlIdxSrcTgtTblGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDdlIdxSrcTgtTblGapList(search); 
    }

    @Override
    public List<ModelGapVO> getDdlIdxSrcTgtColGapList(ModelGapVO search) {
        
        return modelGapMapper.selectDdlIdxSrcTgtColGapList(search);  
    }

	@Override
	public List<ModelGapVO> getGapChartDateJSON() {
		return modelGapMapper.getGapChartDateJSON();
	}
	
	@Override
	public List<ModelGapVO> getSeqDdlDbcGapList(ModelGapVO search) {
		return modelGapMapper.selectSeqDdlDbcGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getMtNonStndPdmColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectMtNonStndPdmColGapList(search);   
	}
	
	@Override
	public List<ModelGapVO> getMtMdlDevDbTblGapList(ModelGapVO search) {
		
	
		return modelGapMapper.getMtMdlDevDbTblGapList(search); 
	}
	
	//===============모델개발DB GAP분석===================
	@Override
	public List<ModelGapVO> getMdlDbTblGapList(ModelGapVO search) {
		
	
		return modelGapMapper.selectMdlDbTblGapList(search); 
	}

	//모델개발DB GAP분석
	@Override
	public List<ModelGapVO> getMdlDbColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectMdlDbColGapList(search);
	}
	
	@Override
	public List<ModelGapVO> getMtNonStndGovColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectMtNonStndGovColGapList(search);   
	}
	
	@Override
    public List<ModelGapVO> getGovDevDbTblGapList(ModelGapVO search) {
        
        return modelGapMapper.selectGovDevDbTblGapList(search);  
    }
	
	@Override
	public List<ModelGapVO> getGovDevDbColGapList(ModelGapVO search) {
		
		return modelGapMapper.selectGovDevDbColGapList(search);
	}
	
	@Override
    public List<ModelGapVO> getDbcSchList(ModelGapVO search) {
    	
    	return modelGapMapper.selectDbcSchList(search); 
    }
	
	@Override
    public List<ModelGapVO> getTgtMtaDbSchList(ModelGapVO search) {
        
        return modelGapMapper.selectTgtMtaDbSchList(search); 
    }
	
	@Override
    public List<ModelGapVO> getDbcMtaColGapList(ModelGapVO search) {
    	
    	return modelGapMapper.selectDbcMtaColGapList(search); 
    }
	
	@Override
    public List<ModelGapVO> getDbcMtaTblGapList(ModelGapVO search) {
    	
    	return modelGapMapper.selectDbcMtaTblGapList(search); 
    }
}
