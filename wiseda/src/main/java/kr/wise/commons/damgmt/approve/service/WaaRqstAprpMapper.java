package kr.wise.commons.damgmt.approve.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaRqstAprpMapper {
    int insert(WaaRqstAprp record);

    int insertSelective(WaaRqstAprp record);

	/** @param search
	/** @return insomnia */
	List<WaaRqstAprp> selectRqstApproveList(WaaRqstAprp search);

	/** @param savevo
	/** @return insomnia */
	int insertRqstApprove(WaaRqstAprp savevo);


	int insertRqstApprovebyLine(WaaRqstAprp savevo);



	/** @param savevo
	/** @return insomnia */
	int updateRqstApprove(WaaRqstAprp savevo);


	int updateRqstApprovebyLine(WaaRqstAprp savevo);

	/** @param savevo
	/** @return insomnia */
	int deleteRqstApprove(WaaRqstAprp savevo);

	/** @param reqmst
	/** @return insomnia */
	ArrayList<WaaRqstAprp> selectapprovebyline(WaqMstr reqmst);

	int checkRequst(MstrAprPrcVO mapvo);

	int updateRequestCancel(String rqst_no); 

	void deleteRqstmapper(String rqst_no);

}