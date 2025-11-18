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
package kr.wise.executor.exceute.ai.udoutlier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dao.UdefOutlierDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.exceute.ai.PythonConf;

import org.apache.log4j.Logger;

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
public class PythonUOExecute extends Executor {
	
	private static final Logger logger = Logger.getLogger(PythonUOExecute.class);

	/** insomnia */
	public PythonUOExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	protected Boolean execute() throws SQLException, Exception {
		logger.debug("start python call");
		
		//지금은 이상값 탐지만 처리한다...
		UdefOutlierDAO uodao = new UdefOutlierDAO(); 
		TargetDbmsDAO tgtdao = new TargetDbmsDAO();
		
		//사용자정의 이상값탐지 ID
		String pyFile = UtilString.null2Blank(executorDM.getShdJobId()) + ".py";
	
		// set up command ana parameter
		String pythonExecute = PythonConf.getPYTHON_EXECUTE_PATH();
		String pythonScript = "";

		
		List<String> cmdlist = new ArrayList<String>();
		cmdlist.add(pythonExecute);
		pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+ pyFile;
		cmdlist.add(pythonScript);
		
		/*
		cmdlist.add("-cn"); //Connection Information
		cmdlist.add(dbconnStr);
		cmdlist.add("-t");  //Table name
		cmdlist.add(tblnm);
		cmdlist.add("-cl"); //Column name
				
		cmdlist.add("-f"); //File.json
		cmdlist.add(PythonConf.getPYTHON_DATA_PATH()+dtcvo.getOtlDtcId()+".json");
		cmdlist.add("-img"); //File.json
		cmdlist.add(PythonConf.getPYTHON_IMG_PATH()+dtcvo.getOtlDtcId()+".png");
		*/
		
		int exitVal = ProcessPython(cmdlist);
		
		uodao.saveResult(logId, exeStaDttm, executorDM);
		
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

	

}
