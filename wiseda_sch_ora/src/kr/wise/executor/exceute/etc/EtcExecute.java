package kr.wise.executor.exceute.etc;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import kr.wise.commons.CStreamGobbler;
import kr.wise.commons.DQConstant;
import kr.wise.commons.user.CommOrgMng;
import kr.wise.commons.user.dao.CommOrgDAO;
import kr.wise.executor.Executor;
import kr.wise.executor.dm.ExecutorDM;

public class EtcExecute extends Executor {
	private static final Logger logger = Logger.getLogger(EtcExecute.class);
	
	public EtcExecute(String logId, ExecutorDM executorDM){
		super(logId, executorDM);
	}
	
//	public static final String ETC_PROPERTIES_FILENAME = "etc.properties";
	
	protected Boolean execute() throws SQLException, Exception {
		
		if(executorDM.getEtcJobKndCd().equals(DQConstant.EXE_JOB_01_CD)){
			
		}
		//EXEC 호출
		else if(executorDM.getEtcJobKndCd().equals(DQConstant.EXE_JOB_02_CD)){
			
			Process process = null;
			
			try {
				//코드분류체계이관 shell 경로
				String cmd = executorDM.getEtcJobDtls();
				logger.debug(cmd);
				process = Runtime.getRuntime().exec(cmd);
				
				// 서버가 윈도우인 경우 버퍼 해소
				CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
				CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

				out.start();
				error.start();

				int exitValue = process.waitFor();
				logger.debug("ExitValue : " + exitValue);
				
				if(exitValue != 0) {
					throw new Exception("Runtime error.("+exitValue+")");
				}
				
			} catch (InterruptedException e) {
				logger.error(e);
				throw new Exception(e);
			} catch(IOException e) {
				logger.error(e);
				throw new Exception(e);
			} catch(Exception e) {
				logger.error(e);
				throw e;
			} finally {
				if(process != null) try { process.destroy(); } catch(Exception ignored) {}
			}
		}
		
		//JAVA 호출
		else if(executorDM.getEtcJobKndCd().equals(DQConstant.EXE_JOB_03_CD)){
			System.err.println("EtcExecute");
			
			CommOrgMng commOrgMng = new CommOrgMng();
			CommOrgDAO dao = new CommOrgDAO();
			
			dao.doCollectionCommOrg();
			
		}
		
		return new Boolean(false);
	}
	
}
