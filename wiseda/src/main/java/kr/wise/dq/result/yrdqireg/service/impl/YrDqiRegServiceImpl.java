package kr.wise.dq.result.yrdqireg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.dq.result.yrdqireg.service.YrDqiRegMapper;
import kr.wise.dq.result.yrdqireg.service.YrDqiRegService;
import kr.wise.dq.result.yrdqireg.service.YrDqiRegVO;

import org.springframework.stereotype.Service;

@Service("YrDqiRegService")
public class YrDqiRegServiceImpl implements YrDqiRegService{
	
	@Inject
	YrDqiRegMapper yrDqiRegMapper;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;	
	
	@Override
	public List<YrDqiRegVO> getRegTbl(YrDqiRegVO search) {
		return yrDqiRegMapper.selectList(search);
	}
	
	@Override
	public int regYrDqiTbl(ArrayList<YrDqiRegVO> list) throws Exception {
		
		int result = 0;
		
		for(YrDqiRegVO saveVo : list){
				result += register(saveVo);
		}
		return result;
	}
	
	private int register(YrDqiRegVO saveVo) throws Exception{
		int result =0;
		String tmpstatus = saveVo.getIbsStatus();

//		 result += govDmnMapper.checkDmnLnm(saveVo);
		
		if("I".equals(tmpstatus)) {
			String ruleRelId = objectIdGnrService.getNextStringId();  
			saveVo.setYrDqiId(ruleRelId); 
			
			result = yrDqiRegMapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)) {
			saveVo.setRegTyp("U");
			result = yrDqiRegMapper.updateByPrimaryKeySelective(saveVo);

		} else if ("D".equals(tmpstatus)) {
			//String id =  objectIdGnrService.getNextStringId();
			//saveVo.setDmnId(id);
			//result = govDmnMapper.delDiagDmnList(saveVo);
			saveVo.setRegTyp("D");
			result = yrDqiRegMapper.deleteByPrimaryKeySelective(saveVo);

		}

		return result;
	}
	
	
	
	@Override
	public int delYrDqi(ArrayList<YrDqiRegVO> list) throws Exception {
		
		int result = 0;
		
		for(YrDqiRegVO delVo : list){
			delVo.setIbsStatus("D");
			result = register(delVo);
		}
		
		return result;
	}

	@Override
	public int checkDup(YrDqiRegVO search) throws Exception{
		String pk = search.getYrDqiId();
		int cnt = yrDqiRegMapper.cntDup(search);
		if(!pk.equals("")){
			cnt=cnt-1;
		}
		return cnt;
	}
}
