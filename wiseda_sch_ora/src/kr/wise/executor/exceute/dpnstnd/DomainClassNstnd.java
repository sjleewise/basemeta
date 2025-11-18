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
package kr.wise.executor.exceute.dpnstnd;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.DomainDAO;
import kr.wise.executor.dao.DomainNstndDAO;
import kr.wise.executor.dao.OutlierDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
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
public class DomainClassNstnd extends Executor {
	
	private static final Logger logger = Logger.getLogger(DomainClassNstnd.class);

	/** insomnia */
	public DomainClassNstnd(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	protected Boolean execute() throws SQLException, Exception {
		logger.debug("start 비표준 domain class call");
		
		//비표준 도메인 판별 dao
		DomainNstndDAO dmnNstndDao = new DomainNstndDAO();

		String rqstNo = executorDM.getShdJobId();
				
		String pythonExecute = PythonConf.getPYTHON_EXECUTE_PATH();
		String pythonScript = "";
		
		pythonScript = PythonConf.getPYTHON_SCRIPT_PATH() + "getDmnPredDt.py";
		
		String dbconnStr = getTgtdbStr();
		
		List<String> cmdlist = new ArrayList<String>();
		cmdlist.add(pythonExecute);
		cmdlist.add(pythonScript);
		cmdlist.add("-cn"); //Connection Information
		cmdlist.add(dbconnStr);
		cmdlist.add("-rn");  //rqstNo 
		cmdlist.add(rqstNo);
		cmdlist.add("-m"); //도메이 판별 모델 path
		cmdlist.add(PythonConf.getPYTHON_SCRIPT_PATH() + "mdl_dmn_nstnd_clf.pkl");
		cmdlist.add("-f"); //json file path
		cmdlist.add(PythonConf.getPYTHON_DATA_PATH() + rqstNo +".json");
		
		int exitVal = ProcessPython(cmdlist);
		
		dmnNstndDao.saveResult(logId, exeStaDttm, executorDM, rqstNo); 
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
		
		logger.debug("*****domain excution output*****\n" + outGobbler.getResult().getOutmsg());
		
		return exitValue;
	}

	/** @param tgtdb
	/** @return insomnia 
	 * @throws Exception */
	private String getTgtdbStr() throws Exception {
		String pydburl = "";
		int idx = ExecutorConf.getDa_jdbcUrl().indexOf("@");
		int lidx_c = ExecutorConf.getDa_jdbcUrl().lastIndexOf(":");
		int lidx_s = ExecutorConf.getDa_jdbcUrl().lastIndexOf("/");
		
		int lidx = lidx_c > lidx_s ? lidx_c : lidx_s;
		String ipAddr = ExecutorConf.getDa_jdbcUrl().substring(idx+1, lidx);
//		String dbType = tgtdb.getDbms_type_cd();
		//오라클일 경우...  "oracle+cx_oracle://wiseda:wise1012@fds"
		
		//ExecutorConf.getDa_user()+ ":"+ExecutorConf.getDa_password()+"@"+
		//윈도우인 경우
		//pydburl = "oracle+cx_oracle://" +ExecutorConf.getDa_user()+":"+ExecutorConf.getDa_password()+"@"+ExecutorConf.getDa_tns();
		
		//centos인 경우
		pydburl = "oracle://" + ExecutorConf.getDa_user() + ":"+ExecutorConf.getDa_password()+"@"+ipAddr+"/"+ExecutorConf.getDa_tns();
		
//		if ("ORA".equals(dbType)) {
//			pydburl = "oracle+cx_oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+tgtdb.getDbLinkNm();
//			
//		}
		
		return pydburl;
	}

}
