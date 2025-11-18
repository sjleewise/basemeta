package kr.wise.dq.dqrs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.dq.dqrs.service.DqrsExpCol;
import kr.wise.dq.dqrs.service.DqrsExpMapper;
import kr.wise.dq.dqrs.service.DqrsExpService;
import kr.wise.dq.dqrs.service.DqrsExpTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("dqrsExpService")
public class DqrsExpServiceImpl implements DqrsExpService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsExpMapper dqrsExpMapper;
	
    @Inject
    private EgovIdGnrService objectIdGnrService;

    ////////////////////테이블
    @Override
	public List<DqrsExpTbl> getExpTbl(DqrsExpTbl vo) {
		// TODO Auto-generated method stub
		return dqrsExpMapper.selectExpTbl(vo);
	}

	@Override
	public int regExpTblLst(ArrayList<DqrsExpTbl> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(DqrsExpTbl saveVo : list){
		
			result = dqrsExpMapper.updateExpTbl(saveVo); 
			
			if(result == 0){
								
				result += dqrsExpMapper.insertExpTbl(saveVo);
			}
		}
		
		return result;
	}
	
	//////////////컬럼
	@Override
	public List<DqrsExpCol> getExpCol(DqrsExpCol search) {
		// TODO Auto-generated method stub
		return dqrsExpMapper.selectExpCol(search);
	}

	@Override
	public int regExpColLst(ArrayList<DqrsExpCol> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(DqrsExpCol saveVo : list){
			if(saveVo.getExpYn().equals("Y")) {
		
				result = dqrsExpMapper.updateExpCol(saveVo); 
				
				if(result == 0){
									
					result += dqrsExpMapper.insertExpCol(saveVo);
				}
			}else if(saveVo.getExpYn().equals("N")){
				result += dqrsExpMapper.deleteExpCol(saveVo);
			}
		}
		
		return result;
	}
}
