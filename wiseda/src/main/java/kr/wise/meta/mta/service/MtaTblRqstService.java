/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblRqstService.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment : 메타데이터 등록
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12. 오후 4:31:42
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.esb.send.service.EsbFilesendVO;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.WaqMtaCol;

public interface MtaTblRqstService extends CommonRqstService {
	
	/** 메타데이터 요청 테이블정보 상세 조회 */
	WaqMtaTbl getMtaTblRqstDetail(WaqMtaTbl searchVo);
	
	/** 메타데이터 요청 테이블정보 조회 */
	List<WaqMtaTbl> getMtaTblrqstList(WaqMstr search);
	
	int delMtaTblRqst(WaqMstr reqmst, ArrayList<WaqMtaTbl> list) throws Exception;

	/** 메타데이터 컬럼 요청 */
	int regMtaColList(WaqMstr reqmst, List<WaqMtaCol> list);

	/** 메타데이터 컬럼 요청 리스트 조회 */
	List<WaqMtaCol> getMtaColRqstList(WaqMstr search);

	int delMtaColRqst(WaqMstr reqmst, ArrayList<WaqMtaCol> list);

	WaqMtaCol getMtaColRqstDetail(WaqMtaCol search);
	
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqMtaTbl> list) throws Exception;
	
	/** 팝업) 메타데이터 테이블 리스트 조회(DBC) */
	List<WaqMtaTbl> getDbcTblList(WaqMtaTbl search); 
	/** 팝업) 메타데이터 테이블 리스트 조회(MTA) */
	List<WaqMtaTbl> getMtaTblList(WaqMtaTbl search);
	
	/** ESB전송 파일 전송(신규) */
	boolean sendEsb(EsbFilesendVO fileVO) throws IOException;
	/** ESB전송 파일 전송(재전송) */
	/*boolean resendEsb(List<EsbFilesendVO> list) throws IOException;*/
	
	/** ESB전송 파일 생성) 메타데이터 테이블 정보  */
	boolean sendMtaTbl(EsbFilesendVO fileVO) throws IOException;
	/** ESB전송 파일 생성) 메타데이터 컬럼 정보  */
	boolean sendMtaCol(EsbFilesendVO fileVO) throws IOException;

	int regWat2Waq(WaqMstr reqmst, ArrayList<WaqMtaTbl> list) throws Exception;

	int updateWamMtaGapStsCd(WatDbcTbl data);

	List<HashMap> getOrgInfoSys(HashMap data);

	List<HashMap> getInfoSysDbConnTrg(HashMap<String, String> data);

	List<HashMap> getInfoSysDbSch(HashMap<String, String> data);

	HashMap<String, String> getTempRqstCnt(WaqMstr reqmst);

	WaqMtaCol getPopMtaColRqstDetail(WaqMtaCol search);

	/*boolean sendWamMtaTbl(EsbFilesendVO fileVO) throws IOException;

	boolean sendWamMtaCol(EsbFilesendVO fileVO) throws IOException;*/

	ArrayList<WaqMtaTbl> getMtaTblByDbSchId(WaqMtaTbl data);       
	
	int registerWat(WaqMstr reqmstVo, List<WaqMtaTbl> reglist);

	int updatePrsnColList(WaqMstr reqmst, List<WaqMtaCol> list);
	
	String checkMtaColNm(WaqMtaCol search);

	ArrayList<WaqMtaTbl> getUpdWatMtaCheck(WaqMtaTbl search);
	
	/*boolean sendEsbAuto(EsbFilesendVO fileVO) throws IOException;

	boolean sendMtaTblId(EsbFilesendVO fileVO) throws IOException;

	boolean sendMtaColId(EsbFilesendVO fileVO) throws IOException;*/

	List<WaqMtaTbl> getMatTblAuto(WaqMtaTbl search); 
	
	List<BrmInfoVo> getBrmInfoVoList();

	boolean sendMtaTotal(EsbFilesendVO fileVO) throws IOException;

	boolean sendMtaInfoSys(EsbFilesendVO fileVO) throws IOException;
	
	boolean sendEsbAuto(EsbFilesendVO paramEsbFilesendVO) throws IOException;
	
	int preAutoEnd(EsbFilesendVO paramEsbFilesendVO) throws Exception;
	
	int rollBackRegTbl(EsbFilesendVO paramEsbFilesendVO) throws Exception;
}
