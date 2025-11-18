package kr.wise.executor.exceute.schema;

import java.sql.SQLException;

import kr.wise.commons.util.UtilString;
import kr.wise.executor.Executor;
import kr.wise.executor.dao.ProfileDAO;
import kr.wise.executor.dao.ScheduleDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.scheduler.ScheduleRegistry;

import org.apache.log4j.Logger;

public class DbCatalogExecute extends Executor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbCatalogExecute.class);

	//LOG UPDATE
	public DbCatalogExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	protected Boolean execute() throws SQLException, Exception {
		try {
			// 수집대상 DB정보 조회
			TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
			TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms(executorDM.getShdJobId());

			if(targetDbmsDM.getDbLinkNm() == null || targetDbmsDM.getDbLinkNm().equals("") || targetDbmsDM.getDbLinkNm().lastIndexOf("외부") == -1) {
				// 수집 시작 및 수집정보 저장
	//			SchemaManager schemaRun = new SchemaManager(targetDbmsDM);
				DbCatalogManager schemaRun = new DbCatalogManager(targetDbmsDM);
				
				//스키마 수집
				schemaRun.launch();
				
				String dmnPdtYn = UtilString.null2Blank(targetDbmsDM.getDmnPdtYn());
				String colPrfYn = UtilString.null2Blank(targetDbmsDM.getColPrfYn());
				
				logger.debug("\n dmnPdtYn:" + dmnPdtYn);
				logger.debug("\n colPrfYn:" + colPrfYn);
				
				//도메인 판별 여부 "Y" 컬럼프로파일 등록 		
				if(dmnPdtYn.equals("Y") || colPrfYn.equals("Y")) {
					
					logger.debug("col profile auto reg start:"+targetDbmsDM.getDbms_id());
					//컬럼 프로파일 등록
					ProfileDAO profileDao = new ProfileDAO();
					profileDao.regColProfile(targetDbmsDM);
					
					logger.debug("col profile reg schedule start:"+targetDbmsDM.getDbms_id());
					//컬럼프로파일 스케줄 등록
					ScheduleDAO schdao = new ScheduleDAO();
					String schid = schdao.regSchMst(targetDbmsDM);
					
					logger.debug("col profile reg quartz start:"+schid);
					//스케줄 id에 따라 스케줄 등록
					ScheduleRegistry regsch = new ScheduleRegistry();
					regsch.register("U", schid);
				}
			} else {
				logger.debug("ssh id@ip port DbCatalogRun.sh OBJ_00000173675");
				
				DbCatalogRunSSH dcrssh = new DbCatalogRunSSH();
				dcrssh.runSSH(targetDbmsDM.getDbms_id());
			}
						
		} catch(SQLException e) {
			logger.error(e);
			throw e;
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return new Boolean(true);
	}

}
