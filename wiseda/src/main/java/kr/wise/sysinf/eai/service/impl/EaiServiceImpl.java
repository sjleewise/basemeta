package kr.wise.sysinf.eai.service.impl;
import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.sysinf.eai.service.EaiMapper;
import kr.wise.sysinf.eai.service.EaiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service ("eaiService")
public class EaiServiceImpl implements EaiService {

	Logger logger = LoggerFactory.getLogger(getClass());

	/** 메타 DB MAPPER*/
	@Inject
	private EaiMapper eaiMapper;
	
	@Override
    public int insertWae(WaqMstr mstVo){
		int retVal = 0;
		try{
			if(mstVo.getBizDcd().equals("DIC")){
//				retVal = eaiMapper.insertWAEDMNCDVAL(mstVo.getRqstNo());
				retVal = eaiMapper.insertWAEDMN(mstVo.getRqstNo());
				retVal += eaiMapper.insertWAECDVAL(mstVo.getRqstNo());
			}
			if(mstVo.getBizDcd().equals("SEM")){
				retVal = eaiMapper.insertWAESTDERRMSG(mstVo.getRqstNo());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retVal;
	}
}
