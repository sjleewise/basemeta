package kr.wise.commons.damgmt.gov.service.impl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.damgmt.gov.service.GovInftMapper;
import kr.wise.commons.damgmt.gov.service.GovInftService;
import kr.wise.commons.damgmt.gov.service.WaaGovInft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("GovInftService")
public class GovInftServiceImpl implements GovInftService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private GovInftMapper mapper;

	@Override
	public List<WaaGovInft> getTotList(WaaGovInft search) {
		// TODO Auto-generated method stub
		List<WaaGovInft> list = mapper.selectTotList(search);
		
		return list;
	}

	@Override
	public List<WaaGovInft> getDbList(WaaGovInft search) {
		// TODO Auto-generated method stub
		List<WaaGovInft> list = mapper.selectDbList(search);
		
		return list;
	}

	@Override
	public List<WaaGovInft> getTblList(WaaGovInft search) {
		// TODO Auto-generated method stub
		List<WaaGovInft> list = mapper.selectTblList(search);
		
		return list;
	}

	@Override
	public List<WaaGovInft> getColList(WaaGovInft search) {
		// TODO Auto-generated method stub
		List<WaaGovInft> list = mapper.selectColList(search);
		
		return list;
	}

	@Override
	public void govInftCsvDown(WaaGovInft search, String filepath, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<WaaGovInft> dbList = mapper.selectDbList(search);
		List<WaaGovInft> tblList = mapper.selectTblList(search);
		List<WaaGovInft> colList = mapper.selectColList(search);
		
		govInftFileWrite(filepath, dbList, tblList, colList, response);
	}

	@Override
	public void govInftMapCsvDown(WaaGovInft search, String filepath, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<WaaGovInft> dbList = mapper.selectDbList(search);
		List<WaaGovInft> tblList = mapper.selectTblList(search);
		List<WaaGovInft> colList = mapper.selectMapColList(search);
		
		govInftFileWrite(filepath, dbList, tblList, colList, response);
	}
	
	private void closeWindow(HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
	    PrintWriter printwriter = response.getWriter();
	    printwriter.println("<html>");
	    printwriter.println("<head>");
	    printwriter.println("<script>");
	    printwriter.println("self.close();");
	    printwriter.println("</script>");
	    printwriter.println("</head>");
	    printwriter.println("</html>");

	    printwriter.flush();

	    printwriter.close();
	}
	
	private void sendREST(String sendUrl, String jsonValue) {
		try {
			URL url = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonValue.getBytes("UTF-8"));
			os.flush();
			
			conn.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void govInftFileWrite(String filepath, List<WaaGovInft> dbList, List<WaaGovInft> tblList, List<WaaGovInft> colList, HttpServletResponse response) throws Exception {
		LocalDateTime now = LocalDateTime.now();
		
		try {
			File folder = new File(filepath + "/dbcon");
			if(folder.exists() == false) folder.mkdirs();
			folder = new File(filepath + "/tbl");
			if(folder.exists() == false) folder.mkdirs();
			folder = new File(filepath + "/col");
			if(folder.exists() == false) folder.mkdirs();
			
			folder = null;
			
			BufferedWriter fw = null;
			String filename = "MB552640Z186N000" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + "0";
			
			//db csv 파일 생성
			//파일명명 규칙 : ‘M’+송신기관코드+송신시스템코드+’N000’+YYYYMMDDHHMMSS+밀리초(3)+’0’+’.csv
			fw = new BufferedWriter(new FileWriter(filepath + "/dbcon/" + filename + ".csv", true));
			
			fw.write("org_cd,info_sys_cd,db_conn_trg_id,obj_vers,reg_typ_cd,writ_user_id,writ_dtm,db_conn_trg_pnm,db_conn_trg_lnm,dbms_typ_cd,dbms_vers_cd,obj_descn,tbl_cnt,const_dt,data_cpct,aply_biz_nm,os_ver_nm,pdata_expt_rsn");
			fw.newLine();
			
			for(WaaGovInft dbInfo : dbList) {
				fw.write("\"" + dbInfo.getOrgCd() + "\"");				   //org_cd				기관코드
				fw.write(",\"" + dbInfo.getInfoSysCd() + "\"");            //info_sys_cd		정보시스템코드
				fw.write(",\"" + dbInfo.getDbConnTrgId() + "\"");          //db_conn_trg_id		접속대상DB ID
				fw.write(",\"" + dbInfo.getDbObjVers() + "\"");            //obj_vers			객체버전
				fw.write(",\"" + dbInfo.getDbRegTypCd() + "\"");           //reg_typ_cd			등록유형코드
				fw.write(",\"" + dbInfo.getDbWritUserId() + "\"");         //writ_user_id		사용자ID
				fw.write(",\"" + dbInfo.getDbWritDtm() + "\"");            //writ_dtm			작성일시
				fw.write(",\"" + dbInfo.getDbConnTrgPnm() + "\"");         //db_conn_trg_pnm	물리DB명
				fw.write(",\"" + dbInfo.getDbConnTrgLnm() + "\"");         //db_conn_trg_lnm	논리DB명
				fw.write(",\"" + dbInfo.getDbmsTypCd() + "\"");            //dbms_typ_cd		DBMS정보(유형)
				fw.write(",\"" + dbInfo.getDbmsVersCd() + "\"");           //dbms_vers_cd		DBMS정보(버전)
				fw.write(",\"" + dbInfo.getDbObjDescn() + "\"");           //obj_descn			DB설명
				fw.write(",\"" + dbInfo.getTblCnt() + "\"");               //tbl_cnt			테이블수
				fw.write(",\"" + dbInfo.getConstDt() + "\"");              //const_dt			구축일자
				fw.write(",\"" + dbInfo.getDataCpct() + "\"");             //data_cpct			데이터용량
				fw.write(",\"" + dbInfo.getAplyBizNm() + "\"");            //aply_biz_nm		적용업무
				fw.write(",\"" + dbInfo.getOsVerNm() + "\"");              //os_ver_nm			운영체제정보
				fw.write(",\"" + dbInfo.getPdataExptRsn() + "\"");         //pdata_expt_rsn		수집제외사유
				
				fw.newLine();
			}
			
			fw.flush();
			fw.close();
			
			//tbl csv 파일 생성
			//파일명명규칙 : ‘M’+송신기관코드+송신시스템코드+’N000’+YYYYMMDDHHMMSS+밀리초(3)+’0’+’.csv
			fw = new BufferedWriter(new FileWriter(filepath + "/tbl/" + filename + ".csv", true));
			
			fw.write("mta_tbl_id,org_cd,info_sys_cd,db_conn_trg_id,db_conn_trg_pnm,db_sch_id,obj_vers,reg_typ_cd,rqst_no,rqst_sno,frs_rqst_dtm,aprv_dtm,rqst_user_id,mta_tbl_pnm,mta_tbl_lnm,db_sch_pnm,obj_descn,tbl_typ_nm,subj_id,subj_nm,prsv_term,tbl_vol,open_rsn_cd,nopen_rsn,nopen_dtl_rel_bss,open_data_lst,occr_cyl,dq_dgns_yn");
			fw.newLine();
			
			for(WaaGovInft tblInfo : tblList) {
				fw.write("\"" + tblInfo.getMtaTblId() + "\"");		       //mta_tbl_id			메타테이블ID
				fw.write(",\"" + tblInfo.getOrgCd() + "\"");               //org_cd				기관코드
				fw.write(",\"" + tblInfo.getInfoSysCd() + "\"");           //info_sys_cd		정보시스템코드
				fw.write(",\"" + tblInfo.getDbConnTrgId() + "\"");         //db_conn_trg_id		DB접속대상ID
				fw.write(",\"" + tblInfo.getDbConnTrgPnm() + "\"");        //db_conn_trg_pnm	물리DB명
				fw.write(",\"" + tblInfo.getDbSchId() + "\"");             //db_sch_id			테이블소유자ID
				fw.write(",\"" + tblInfo.getTblObjVers() + "\"");          //obj_vers			객체버전
				fw.write(",\"" + tblInfo.getTblRegTypCd() + "\"");         //reg_typ_cd			등록유형코드
				fw.write(",\"" + tblInfo.getTblRqstNo() + "\"");           //rqst_no			등록번호
				fw.write(",\"" + tblInfo.getTblRqstSno() + "\"");          //rqst_sno			등록일련번호
				fw.write(",\"" + tblInfo.getTblFrsRqstDtm() + "\"");       //frs_rqst_dtm		등록일시
				fw.write(",\"" + tblInfo.getTblAprvDtm() + "\"");          //aprv_dtm			승인(처리)일시
				fw.write(",\"" + tblInfo.getTblRqstUserId() + "\"");       //rqst_user_id		사용자ID
				fw.write(",\"" + tblInfo.getMtaTblPnm() + "\"");           //mta_tbl_pnm		테이블영문명
				fw.write(",\"" + tblInfo.getMtaTblLnm() + "\"");           //mta_tbl_lnm		테이블한글명
				fw.write(",\"" + tblInfo.getDbSchPnm() + "\"");            //db_sch_pnm			테이블소유자
				fw.write(",\"" + tblInfo.getTblObjDescn() + "\"");         //obj_descn			테이블설명
				fw.write(",\"" + tblInfo.getTblTypNm() + "\"");            //tbl_typ_nm			테이블유형
				fw.write(",\"" + tblInfo.getSubjId() + "\"");              //subj_id			업무분류체계ID
				fw.write(",\"" + tblInfo.getSubjNm() + "\"");              //subj_nm			업무분류체계명
				fw.write(",\"" + tblInfo.getPrsvTerm() + "\"");            //prsv_term			보존기간
				fw.write(",\"" + tblInfo.getTblVol() + "\"");              //tbl_vol			테이블볼륨
				fw.write(",\"" + tblInfo.getOpenRsnCd() + "\"");           //open_rsn_cd		공개/비공개여부
				fw.write(",\"" + tblInfo.getNopenRsn() + "\"");            //nopen_rsn			비공개사유
				fw.write(",\"" + tblInfo.getNopenDtlRelBss() + "\"");      //nopen_dtl_rel_bss	상세관련근거
				fw.write(",\"" + tblInfo.getOpenDataLst() + "\"");         //open_data_lst		개방데이터목록
				fw.write(",\"" + tblInfo.getOccrCyl() + "\"");             //occr_cyl			발생주기
				fw.write(",\"" + tblInfo.getDqDgnsYn() + "\"");            //dq_dgns_yn			품질진단여부
				
				fw.newLine();
			}
			
			fw.flush();
			fw.close();
			
			//col csv 파일 생성
			//파일명명 규칙 : ‘M’+송신기관코드+송신시스템코드+’N000’+YYYYMMDDHHMMSS+밀리초(3)+’0’+’.csv
			fw = new BufferedWriter(new FileWriter(filepath + "/col/" + filename + ".csv", true));
			
			fw.write("mta_col_id,mta_tbl_id,col_ord,pri_rsn,rqst_no,rqst_sno,rqst_dtl_sno,obj_vers,reg_typ_cd,rqst_dtm,aprv_dtm,rqst_user_id,mta_col_pnm,mta_col_lnm,pk_yn,fk_yn,data_type,data_len,data_fmt,nonul_yn,const_cnd,open_yn,prsn_info_yn,enc_trg_yn,obj_descn");
			fw.newLine();
			
			for(WaaGovInft colInfo : colList) {
				fw.write("\"" + colInfo.getMtaColId() + "\"");		      //mta_col_id		메타컬럼ID
				fw.write(",\"" + colInfo.getMtaTblId() + "\"");           //mta_tbl_id		메타테이블ID
				fw.write(",\"" + colInfo.getColOrd() + "\"");             //col_ord			컬럼순서
				fw.write(",\"" + colInfo.getPriRsn() + "\"");             //pri_rsn			비공개사유
				fw.write(",\"" + colInfo.getColRqstNo() + "\"");          //rqst_no			등록번호
				fw.write(",\"" + colInfo.getColRqstSno() + "\"");         //rqst_sno		등록일련번호
				fw.write(",\"" + colInfo.getColRqstDtlSno() + "\"");      //rqst_dtl_sno	등록상세일련번호
				fw.write(",\"" + colInfo.getColObjVers() + "\"");         //obj_vers		객체버전
				fw.write(",\"" + colInfo.getColRegTypCd() + "\"");        //reg_typ_cd		등록유형코드
				fw.write(",\"" + colInfo.getColRqstDtm() + "\"");         //rqst_dtm		등록일시
				fw.write(",\"" + colInfo.getColAprvDtm() + "\"");         //aprv_dtm		승인(처리)일시
				fw.write(",\"" + colInfo.getColRqstUserId() + "\"");      //rqst_user_id	사용자ID
				fw.write(",\"" + colInfo.getMtaColPnm() + "\"");          //mta_col_pnm		컬럼영문명
				fw.write(",\"" + colInfo.getMtaColLnm() + "\"");          //mta_col_lnm		컬럼한글명
				fw.write(",\"" + colInfo.getPkYn() + "\"");               //pk_yn			PK정보
				fw.write(",\"" + colInfo.getFkYn() + "\"");               //fk_yn			FK정보
				fw.write(",\"" + colInfo.getDataType() + "\"");           //data_type		데이터타입
				fw.write(",\"" + colInfo.getDataLen() + "\"");            //data_len		데이터길이
				fw.write(",\"" + colInfo.getDataFmt() + "\"");            //data_fmt		데이터포맷
				fw.write(",\"" + colInfo.getNonulYn() + "\"");            //nonul_yn		NOTNULL여부
				fw.write(",\"" + colInfo.getConstCnd() + "\"");           //const_cnd		제약조건
				fw.write(",\"" + colInfo.getOpenYn() + "\"");             //open_yn			공개/비공개여부
				fw.write(",\"" + colInfo.getPrsnInfoYn() + "\"");         //prsn_info_yn	개인정보여부
				fw.write(",\"" + colInfo.getEncTrgYn() + "\"");           //enc_trg_yn		암호화여부
				fw.write(",\"" + colInfo.getColObjDescn() + "\"");        //obj_descn		컬럼설명
				
				fw.newLine();
			}
			
			fw.flush();
			fw.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String jsonDt = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));	//요청일시
		String transcId = UUID.randomUUID().toString();								//트랜잭션id
		String[] fileNm = {"", "", ""};												//송신파일명
		String filePath = filepath;													//송신파일 디렉토리
		String linkId = "";															//연계id(인터페이스id) - 연계팀 //인터페이스 정의서 작성 후 전달.
		String instncId = "gp_vmet_was01_A";										//was 인스턴스 id
		
		//필요한 정보
		//보내야 되는 파일명(3개)이 같고 폴더로 분리가 되는데 폴더 경로만 보내도 되는지? 무조건 파일로 보내야 하면 3번 요청해야 되는지? -- 요청 3번
		//link 아이디 값이 어떻게 되는지?
		String sendUrl = "http://172.21.210.60:27720/mediate/geps/batch/" + linkId;
		String jsonValue  = "{																		";
			   jsonValue += "  \"BODY\" : {															";
		       jsonValue += "    \"FILE_PATH\" : \"" + filePath + "\",								"; // /DATA3/batch/eai_files/Send/{현재날짜}/{수신기관코드} 예제의 현재날짜, 수신기관코드 필수인지?
		       jsonValue += "    \"FILE_NAME\" : \"testfile.txt\"									"; // file이 3개인 경우, 3번 요청해야 되는지?
		       jsonValue += "  },																	";
		       jsonValue += "  \"COMMON_HEADER\" : {												";
		       jsonValue += "    \"INT_EXT_CL_CD\" : \"E\",											";
		       jsonValue += "    \"TRNSC_ID\" : \"" + transcId + "\",								";
		       jsonValue += "    \"INSTNC_ID\" : \"" + instncId + "\",								";
		       jsonValue += "    \"LINK_ID\" : \"" + linkId + "\",									";
		       jsonValue += "    \"DMND_RSPNS_CL_CD\" : \"D\",										";
		       jsonValue += "    \"DMND_OCRN_DT\" : \"" + jsonDt + "\"								";
		       jsonValue += "  }																	";
		       jsonValue += "}																		";


		sendREST(sendUrl, jsonValue);
		
		closeWindow(response);
	}

	@Override
	public List<WaaGovInft> getSubjBisMapList(WaaGovInft search) {
		List<WaaGovInft> list = mapper.selectSubjBisMapList(search);
		
		return list;
	}

	@Override
	public int regSubjBisMap(ArrayList<WaaGovInft> list) throws Exception {
		int result = 0;
		
		for(WaaGovInft govInft : list) {
			if(govInft.getBisId() == null) govInft.setBisId("");
			if(govInft.getBisNm() == null) govInft.setBisNm("");
			
			result += mapper.deleteSubjBisMap(govInft);
			result += mapper.insertSubjBisMap(govInft);
		}
		
		return result;
	}
		
}