/**
 * 
 */
package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * @author woori
 *
 */
public interface DdlPartRqstService extends CommonRqstService {

	WaqDdlPart getDdlPartRqstDetail(WaqDdlPart searchVo);

	List<WaqDdlPart> getDdlPartRqstList(WaqMstr search);

	int regDdlMainPartList(WaqMstr reqmst, WaqDdlPart waqpart,
			List<WaqDdlPartMain> list) throws Exception;

	List<WaqDdlPartMain> getDdlPartMainRqstList(WaqMstr search);

	int regDdlSubPartList(WaqMstr reqmst, WaqDdlPart waqpart,
			List<WaqDdlPartSub> list) throws Exception;

	List<WaqDdlPartSub> getDdlPartSubRqstList(WaqMstr search);

	int delDdlPartRqst(WaqMstr reqmst, ArrayList<WaqDdlPart> list) throws Exception;

	int delDdlPartMainRqst(WaqMstr reqmst, ArrayList<WaqDdlPartMain> list) throws Exception;

	int delDdlPartSubRqst(WaqMstr reqmst, ArrayList<WaqDdlPartSub> list) throws Exception;

	List<WaqDdlPart> getWamPartList(WaqDdlPart search);

	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlPart> list) throws Exception;
	
	void updateDdlPartScriptWaq(WaqMstr mstVo) throws Exception;
	
	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	int regWaq2WamMain(WaqMstr mstVo) throws Exception;
	int regWaq2WamSub(WaqMstr mstVo) throws Exception;
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;

	int delDdlPartbyDdlTbl(WaqMstr mstVo);
	
	//임시
	int ddlTrgCdcUpdateTemp(WaqMstr mstVo);
	
	int ddlTrgCdcUpdateTemp2(WaqMstr mstVo);
	
	int ddlTrgCdcUpdateTemp3(WaqMstr mstVo);
	
	//운영이관시 WAM영역 개발, 테스트 SR번호/서브프로젝트 번호 UPDATE
	int updateSrMngNoPrjMngNo(WaqMstr mstVo) throws Exception;
	
	String selectPartColumnLength(WaqDdlPart searchvo);

}
