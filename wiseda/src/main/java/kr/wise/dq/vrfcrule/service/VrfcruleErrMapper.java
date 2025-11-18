package kr.wise.dq.vrfcrule.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRel;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

@Mapper
public interface VrfcruleErrMapper {

	List<VrfcruleErrVO> selectVrfcList(VrfcruleErrVO searchVO);

	List<VrfcruleErrVO> selectVrfcErrLstAll(VrfcruleErrVO searchVO);

	VrfcruleErrVO selectVrfcDetail(String prfId);

	VrfcruleVO selectSqlGenDbmsInfoByRuleRelId(String prfId);

	VrfcSqlGeneratorVO selectCntSql(String prfId);

	VrfcruleErrVO selectMstrByPrfId(VrfcruleErrVO searchVO);

	WamPrfRelTblVO  selectPrfPT01Dtl(String prfId) ;

	List<WamPrfRelColVO>  selectPrfPT01RelColLst(String prfId) ;

	WamPrfMstrVO  selectSqlGenDbmsInfoByPrfId(String prfId) ;

	List<WaaColRuleRel> selectItmAnaExecList(WaaColRuleRel vo); 
}