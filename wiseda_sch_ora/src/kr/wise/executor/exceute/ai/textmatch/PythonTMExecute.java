/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : PythonExecute.java
 * 2. Package : kr.wise.executor.exceute.ai
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 1. 11. 오후 3:57:15
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 1. 11. :            : 신규 개발.
 */
package kr.wise.executor.exceute.ai.textmatch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.dao.OutlierDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dao.TextMatchDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.ai.PythonConf;
import kr.wise.executor.exceute.ai.PythonExecute;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : PythonExecute.java
 * 3. Package  : kr.wise.executor.exceute.ai
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 1. 11. 오후 3:57:15
 * </PRE>
 */
public class PythonTMExecute extends Executor { 
	
	private static final Logger logger = Logger.getLogger(PythonExecute.class);

	/** insomnia */
	public PythonTMExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	protected Boolean execute() throws SQLException, Exception {
		logger.debug("start text match python call");
		
		
		TextMatchDAO tmdao = new TextMatchDAO();  
		TargetDbmsDAO tgtdao = new TargetDbmsDAO();
		
		//텍스트 매칭 
		WadDataMtcTbl mtcvo = tmdao.getTextMatchVo(executorDM.getShdJobId()); 
		
		TargetDbmsDM srcdb = tgtdao.getTargetDbmsDMbySchID(mtcvo.getSrcDbcSchId());
		TargetDbmsDM tgtdb = tgtdao.getTargetDbmsDMbySchID(mtcvo.getTgtDbcSchId());
				

		String pythonExecute = PythonConf.getPYTHON_EXECUTE_PATH();
		String pythonScript = ""; 
		
		String dbconnStr = PythonConf.getTgtdbStr(tgtdb);
		
		String tablename1 = mtcvo.getSrcDbcTblNm().toLowerCase();
		if (!UtilString.isBlank(srcdb.getDbc_owner_nm())) { 
			tablename1 = srcdb.getDbc_owner_nm().toLowerCase()+"."+ tablename1;
		}
		
		String tablename2 = mtcvo.getTgtDbcTblNm().toLowerCase();
		if (!UtilString.isBlank(tgtdb.getDbc_owner_nm())) {
			tablename2 = tgtdb.getDbc_owner_nm().toLowerCase()+"."+ tablename2;
		}
		
		
		List<WadDataMtcCol> colList = mtcvo.getColList(); 
								
		List<String> cmdlist = new ArrayList<String>();
		
		pythonScript = PythonConf.getPYTHON_SCRIPT_PATH() + "getTextMatch.py";		
		
		cmdlist.add(pythonExecute);		
		cmdlist.add(pythonScript);
		
		cmdlist.add("-cn");
		cmdlist.add(dbconnStr); 
		cmdlist.add("-t1");
		cmdlist.add(tablename1);
		cmdlist.add("-cl1");
		
		for(WadDataMtcCol colvo : colList){ 
			
			cmdlist.add(colvo.getSrcDbcColNm().toLowerCase()); 			
		}
				
		cmdlist.add("-t2");
		cmdlist.add(tablename2);
		cmdlist.add("-cl2");
		
		for(WadDataMtcCol colvo : colList){ 
			
			cmdlist.add(colvo.getTgtDbcColNm().toLowerCase()); 			
		}

		cmdlist.add("-v");
		cmdlist.add(mtcvo.getMtcId());
		
		cmdlist.add("-f");
		cmdlist.add(PythonConf.getPYTHON_DATA_PATH() + mtcvo.getMtcId() +".json"); 
				
		int exitVal = ProcessPython(cmdlist);
			
		tmdao.saveResult(logId, exeStaDttm, executorDM, mtcvo);
		
		return false;
	}

	/** @param cmdlist
	/** @return insomnia 
	 * @throws Exception */
	private int ProcessPython(List<String> cmdlist) throws Exception {
		logger.debug(cmdlist.toString());

		String[] cmd = cmdlist.toArray(new String[cmdlist.size()]);
		
		// 외부 프로세스 만들기
		Process pr = Runtime.getRuntime().exec(cmd);
//		Process pr = Runtime.getRuntime().exec("javac");
		
		//파이썬 스크립트 출력내용 가져오기...
		CStreamGobbler errGobbler  = new CStreamGobbler(pr.getErrorStream(), "ERROR");
		CStreamGobbler outGobbler  = new CStreamGobbler(pr.getInputStream(), "OUT");
		
//		BufferedReader outbfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//		BufferedReader errorbfr = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
		
		errGobbler.start();
		outGobbler.start();
		
		int exitValue = pr.waitFor();
		
		if(exitValue != 0) {
			throw new Exception(errGobbler.getResult().getOutmsg());
//			throw new Exception("Runtime error.("+exitValue+")");
		}
		
		logger.debug("*****python excution output*****\n" + outGobbler.getResult().getOutmsg());
		
		return exitValue;
	}

	/** @param tgtdb
	/** @return insomnia */
	private String getTgtdbStr(TargetDbmsDM tgtdb) {
		String pydburl = "";
		String dbType = tgtdb.getDbms_type_cd();
		//오라클일 경우...  "oracle+cx_oracle://wiseda:wise1012@fds"
		if ("ORA".equals(dbType)) {
			pydburl = "oracle+cx_oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+tgtdb.getDbLinkNm();
			
		}
		
		return pydburl;
	}

}
