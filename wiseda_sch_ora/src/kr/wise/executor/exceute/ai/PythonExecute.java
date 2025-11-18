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
package kr.wise.executor.exceute.ai;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.dao.OutlierDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.TargetDbmsDM;

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
public class PythonExecute extends Executor {
	
	private static final Logger logger = Logger.getLogger(PythonExecute.class);

	/** insomnia */
	public PythonExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	protected Boolean execute() throws SQLException, Exception {
		logger.debug("start python call");
		
		//지금은 이상값 탐지만 처리한다...
		OutlierDAO otdao = new OutlierDAO();
		TargetDbmsDAO tgtdao = new TargetDbmsDAO();
		
		//이상값 탐지할 내용 가져오기, 알고리즘(위치포함), 알고리즘 변수, 탐지대상 스키마, 테이블, 컬럼목록
		WadOtlDtcVo dtcvo = otdao.getOutlierVo(executorDM.getShdJobId());
		TargetDbmsDM tgtdb = tgtdao.getTargetDbmsDMbySchID(dtcvo.getDbSchId());
//		logger.debug("*****error0*****");
		// set up command ana parameter
//		String pythonScript = "C:/ide/workspace_wad/wad_python/kr/wise/test/dbtest.py";
		String pythonExecute = PythonConf.getPYTHON_EXECUTE_PATH();
		String pythonScript = "";
//		String pythonScript = "D:/Project/bdq/getTextclustering.py";
		
		String tblnm = dtcvo.getDbTblNm().toLowerCase();
		if (!UtilString.isBlank(tgtdb.getDbc_owner_nm())) {
			tblnm = tgtdb.getDbc_owner_nm().toLowerCase()+"."+ tblnm;
		}
		
		String dbconnStr = PythonConf.getTgtdbStr(tgtdb); 
//		logger.debug("*****error1*****");
		
		//============추가 조건================		 
		 String addCnd = otdao.getOtlVarAddCndStr(executorDM.getShdJobId());
		 		
		 logger.debug("\n addCnd:" + addCnd);
		 //=================================
				
		//이상값 탐지일 경우
		if ("OD".equals(dtcvo.getAlgTypCd()) ) {
			String algNm = dtcvo.getAlgPnm();
			
			//단변량일 경우...
			if ("Box Plot".equals(algNm) || "MAD".equals(algNm)) {
				for (WadOtlVal valvo : dtcvo.getVarlist()) {
//					logger.debug("*****error2*****");
					List<String> cmdlist = new ArrayList<String>();
					cmdlist.add(pythonExecute);
					if("Box Plot".equals(algNm)) {
						pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getBoxplot.py";
					} else if("MAD".equals(algNm)) {
						pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getMAD.py";
					}
					cmdlist.add(pythonScript);
					
					cmdlist.add("-cn"); 
					cmdlist.add(dbconnStr);
					cmdlist.add("-db"); 
					switch(tgtdb.getDbms_type_cd()) {
					case "ORA":
						cmdlist.add("1");
						break;
					case "MSQ":
						cmdlist.add("2");
						break;
					case "HIV":
						cmdlist.add("3");
						break;
					}
					cmdlist.add("-t"); 
					cmdlist.add(tblnm);
					cmdlist.add("-cl");										
					cmdlist.add(valvo.getDbColNm().toLowerCase());
									
					if(!addCnd.equals("")) {
					
						cmdlist.add("-c");  //condition(추가조건)
						cmdlist.add(addCnd);
					}

					cmdlist.add("-v");		
					cmdlist.add(valvo.getAnlVarId());
					cmdlist.add("-f");
					cmdlist.add(PythonConf.getPYTHON_DATA_PATH()+valvo.getAnlVarId()+".json");
					if("Box Plot".equals(algNm)) {
						cmdlist.add("-img");
						cmdlist.add(PythonConf.getPYTHON_IMG_PATH()+valvo.getAnlVarId()+".png");
					}
					
					int exitVal = ProcessPython(cmdlist);
					
				}
							
			} else { 
				//다변량: ISFR, LOF, EE, OCSVM, RRGR 
				
				List<String> cmdlist = new ArrayList<String>();
				cmdlist.add(pythonExecute);
				
				switch(algNm) {
				case "RRGR":
					pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getRobustRgr.py";
					break;
				case "EE":
				case "ISOFOR":
				case "OCSVM":
				case "LOF":
					pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getOutlier.py";
					break;
				case "CBLOF":
				case "KNN":
				case "AKNN":
				case "FBOD":
				case "HBOD":
				case "ABOD":
					pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getOutlierPyod.py";
					break;
				}
				
//				if("RRGR".equals(algNm)){
//					pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getRobustRgr.py";
//				}else{
//					pythonScript = PythonConf.getPYTHON_SCRIPT_PATH()+"getOutlier.py";
//				}
												
				cmdlist.add(pythonScript);
				cmdlist.add("-cn"); //Connection Information
				cmdlist.add(dbconnStr);
				cmdlist.add("-t");  //Table name
				cmdlist.add(tblnm);
				cmdlist.add("-cl"); //Column name
				for (WadOtlVal valvo : dtcvo.getVarlist()) {
					cmdlist.add(valvo.getDbColNm().toLowerCase());
				}
				
				if(!addCnd.equals("")) {
					
					cmdlist.add("-c");  //condition(추가조건)
					cmdlist.add(addCnd);
				}
								
				if ("LOF".equals(algNm)) {
					cmdlist.add("-o"); //option
					cmdlist.add("3");
				} else if ("ISOFOR".equals(algNm)) {
					cmdlist.add("-o"); //option
					cmdlist.add("2");
				} else if ("OCSVM".equals(algNm)) {
					cmdlist.add("-o"); //option
					cmdlist.add("4");
				} else if ("EE".equals(algNm)) {
					cmdlist.add("-o"); //option
					cmdlist.add("1");
				} else if ("KNN".equals(algNm)) {
					cmdlist.add("-o");
					cmdlist.add("10");
				} else if ("AKNN".equals(algNm)) {
					cmdlist.add("-o");
					cmdlist.add("5");
				} else if ("HBOS".equals(algNm) || "HBOD".equals(algNm)) {
					cmdlist.add("-o");
					cmdlist.add("6");
				} else if ("CBLOF".equals(algNm)) {
					cmdlist.add("-o");
					cmdlist.add("7");
				} else if ("FB".equals(algNm) || "FBOD".equals(algNm)) {
					cmdlist.add("-o");
					cmdlist.add("8");
				}
				
				for (WadOtlArg argvo : dtcvo.getArglist()) {
					if ("OP".equals(argvo.getArgPnm())) {
						//Outlier 비율 Contamination
						cmdlist.add("-cnt");
						cmdlist.add(argvo.getArgVal());
					} else if ("NON".equals(argvo.getArgPnm())) { 
						//이웃수 : Number of Neighbor 
						cmdlist.add("-n");
						cmdlist.add(argvo.getArgVal());
					}
				}
				cmdlist.add("-v"); //ValID
				/*for (WadOtlVal valvo : dtcvo.getVarlist()) {
					cmdlist.add(valvo.getAnlVarId());
				}*/
				cmdlist.add(dtcvo.getOtlDtcId());
				cmdlist.add("-f"); //File.json
				cmdlist.add(PythonConf.getPYTHON_DATA_PATH()+dtcvo.getOtlDtcId()+".json");
				cmdlist.add("-img"); //File.json
				cmdlist.add(PythonConf.getPYTHON_IMG_PATH()+dtcvo.getOtlDtcId()+".png");
				
				//분할 분석용 파라미터 추가
				cmdlist.add("-ls");
				cmdlist.add("" + dtcvo.getAlgCnt());
				
				int exitVal = ProcessPython(cmdlist);
				
			}
			
			otdao.saveResult(logId, exeStaDttm, executorDM, dtcvo);
		}
		
		
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
				
		errGobbler.start();
		outGobbler.start();
		
		while (true) {
			if (!outGobbler.isAlive() && !errGobbler.isAlive()) {  //두개의 스레드가 정지하면 프로세스 종료때까지 기다린다.
				pr.waitFor();
				break;
			}
		}
		
		int exitValue = pr.exitValue();
		
		if(exitValue != 0) {
			throw new Exception(errGobbler.getResult().getOutmsg());
//			throw new Exception("Runtime error.("+exitValue+")");
		}
		
		logger.debug("*****python excution output*****\n" + outGobbler.getResult().getOutmsg());
		
		return exitValue;
	}

	

}
