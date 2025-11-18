package kr.wise.dq.vrfcrule.service;

import java.util.List;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

public interface VrfcruleErrService {

	List<VrfcruleErrVO> selectVrfcList(VrfcruleErrVO searchVO);

	VrfcruleErrVO selectVrfcDetail(String prfId);

	VrfcruleVO getSqlGenDbmsInfoByRuleRelId(String prfId);

	VrfcSqlGeneratorVO getCntSql(String prfId);

	VrfcruleErrVO  selectMstrByPrfId(VrfcruleErrVO search) ;

	WamPrfRelTblVO  getPrfPT01Dtl(String prfId) ;

	List<WamPrfRelColVO>  getPrfPT01RelColLst(String prfId) ;

	WamPrfMstrVO  getSqlGenDbmsInfoByPrfId(String prfId) ;
}
