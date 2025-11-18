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
package kr.wise.executor.exceute.ai.summary;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.advisor.prepare.summary.service.WadDataSet;
import kr.wise.advisor.prepare.textcluster.service.WadAnaVar;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.dao.HistogramDAO;
import kr.wise.executor.dao.OutlierDAO;
import kr.wise.executor.dao.SummaryDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dao.TextClusterDAO;
import kr.wise.executor.dao.TextMatchDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.ai.PythonConf;

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
public class PythonSMExecute extends Executor { 
	
	private static final Logger logger = Logger.getLogger(PythonSMExecute.class);

	/** insomnia */
	public PythonSMExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	protected Boolean execute() throws SQLException, Exception {
		logger.debug("start summary python call"); 
		
		
		SummaryDAO dao = new SummaryDAO();     
		TargetDbmsDAO dbdao = new TargetDbmsDAO();
		ResultSet rs = null;
		
		//Summary 
		WadDataSet mtcvo = dao.getWadDataSetVo(executorDM.getShdJobId());     
		
		TargetDbmsDM db = dbdao.getTargetDbmsDMbySchID(mtcvo.getDbSchId());
		
		String dbType = db.getDbms_type_cd();
		
		String pythonExecute = PythonConf.getPYTHON_EXECUTE_PATH(); 
		String pythonScript = ""; 
		
		String dbconnStr = PythonConf.getTgtdbStr(db); 
		
		String tablename = mtcvo.getDbTblNm().toLowerCase();
		
		if (!UtilString.isBlank(db.getDbc_owner_nm())) { 
			tablename = db.getDbc_owner_nm().toLowerCase()+"."+ tablename;
		}
		
		List<String> cmdlist = new ArrayList<String>();
		
		pythonScript = PythonConf.getPYTHON_SCRIPT_PATH() + "getSummary.py";	
		
		logger.debug("\n pythonExecute:" + pythonExecute); 
		
		cmdlist.add(pythonExecute);		
		cmdlist.add(pythonScript);
		
		cmdlist.add("-cn");
		cmdlist.add(dbconnStr); 
		
		if ("ORA".equals(dbType)) {
			cmdlist.add("-db");
			cmdlist.add("1");
		}else if ("MSQ".equals(dbType)) {
			cmdlist.add("-db");
			cmdlist.add("2");
		}else if ("HIV".equals(dbType)) {
			cmdlist.add("-db");
			cmdlist.add("3");
		}
		
		
		cmdlist.add("-t");
		cmdlist.add(tablename);
		
		cmdlist.add("-v");
		cmdlist.add(mtcvo.getDaseId());
		
		cmdlist.add("-f");
		cmdlist.add(PythonConf.getPYTHON_DATA_PATH() + mtcvo.getDaseId() +".json"); 
				
		int exitVal = ProcessPython(cmdlist);
			
		dao.saveResult(logId, exeStaDttm, executorDM, mtcvo);
		
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
	/** @return insomnia  */ 
	private String getTgtdbStr(TargetDbmsDM tgtdb) {
		String pydburl = "";
		String dbType = tgtdb.getDbms_type_cd();
		int idx = tgtdb.getConnect_string().indexOf("@");
		int lidx = tgtdb.getConnect_string().lastIndexOf(":");
		String ipAddr = tgtdb.getConnect_string().substring(idx+1, lidx);
		
		if ("ORA".equals(dbType)) {
			//오라클일 경우...  "oracle+cx_oracle://wiseda:wise1012@fds"
			//윈도우일 때, 아래 형식으로 connect 할 수 있음
			//pydburl = "oracle+cx_Oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+tgtdb.getDbLinkNm();
			
			//CentOS7일 때, 아래 형식으로 connect 할 수 있음
			pydburl = "oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+ipAddr+"/"+tgtdb.getDbLinkNm();
			
		}else if ("MSQ".equals(dbType)) {
			//MS-SQLSERVER
			pydburl = "mssql+pyodbc://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+ tgtdb.getDbLinkNm() +"/UPLOAD?driver=ODBC+Driver+11+for+SQL+Server";
		}else if ("HIV".equals(dbType)) {
			//Hive
			//hive://jdbc:hive2://169.56.81.7/default
			int i = ipAddr.lastIndexOf("/");
			pydburl = "hive://" + ipAddr.substring(i+1) + "/"+ tgtdb.getDbLinkNm();
		}
		
		return pydburl; 
	}

}
