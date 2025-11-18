package kr.wise.meta.dmngrqst.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;


public interface DmngRqstService extends CommonRqstService {

	/** @param searchVo
	/** @return insomnia */
	WaqDmng getDmngRqstDetail(WaqDmng searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqDmng> getDmngRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int delDmngRqst(WaqMstr reqmst, ArrayList<WaqDmng> list) throws Exception;

	/** @param reqmst
	/** @param list
	/** @return insomnia
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDmng> list) throws Exception;

	/** @param search
	/** @return insomnia */
	WaqInfoType getInfoTypeRqstDetail(WaqInfoType search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int regInfoTypeList(WaqMstr reqmst, List<WaqInfoType> list);

	/** @param search
	/** @return insomnia */
	List<WaqInfoType> getInfoTypeRqstList(WaqMstr search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int delInfoTypeRqst(WaqMstr reqmst, ArrayList<WaqInfoType> list);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	HashMap<String, String> regInfoTypexlsList(WaqMstr reqmst, List<WaqInfoType> list);


	//모델 재상신 
	int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception;

//	int regPdmXlsTblColList(WaqMstr reqmst, List<WaeInfoType> list);

	boolean checkEmptyRqst(WaqMstr reqmst);



 
}
