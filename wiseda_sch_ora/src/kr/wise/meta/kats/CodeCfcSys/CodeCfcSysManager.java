package kr.wise.meta.kats.CodeCfcSys;

import java.sql.Connection;
import java.util.List;

import kr.wise.commons.ConnectionHelper;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.meta.kats.CodeCfcSys.dao.CodeCfcSysDAO;

/**
 * 기술표준원 코드분류체계 interface class
 */
public class CodeCfcSysManager {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Parameter is not vaild.");
			System.exit(1);
		} else {
			
			try{
				/* 
				 * 개발 테스트 운영 구분 
				 * 공통코드 DDL대상구분코드 와 동일
				 * 개발 : D
				 * 테스트 : T
				 * 운영 : R
				 */
				String TrgDbmsDcd   = args [0];
				
				//접속정보 조회
				TargetDbmsDAO trgDbmsDAO = new TargetDbmsDAO();
				List<TargetDbmsDM> trgDbmsDM = trgDbmsDAO.selectTrgDbmsByTrgDcd( TrgDbmsDcd );
				
				CodeCfcSysDAO dao = new CodeCfcSysDAO();
				
				//접속대상 DB DATA -> META REP DB 적재
				for(TargetDbmsDM trgDM : trgDbmsDM ){
					 dao.doProcess(trgDM);
				}
				
				
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
