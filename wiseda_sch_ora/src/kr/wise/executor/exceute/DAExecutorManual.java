package kr.wise.executor.exceute;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.UniqueKeyGenerator;
import kr.wise.commons.Utils;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.ExecuteLogDAO;
import kr.wise.executor.dao.ScheduleDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.SchJobDM;
import kr.wise.executor.dm.SchMstDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.br.BusinessRuleExecuteCallableImpl;
import kr.wise.executor.exceute.br.BusinessRuleExecuteRunnableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteCallableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteRunnableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteCallableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteRunnableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteCallableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteRunnableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteCallableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteRunnableImpl;
import kr.wise.meta.mmart.dao.Mmart9WaeErwinDAO;
import kr.wise.scheduler.ScheduleRegistry;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class DAExecutorManual  {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DAExecutorManual.class);
	
	
	private static ExecutorService executor; 
	
	private static String shdId    = "";
	private static String shdJobId    = "";
	private static String shdTypCd = "QP"; //GN, QB: 업무규칙, QP :프로파일
                
	private static String rqstUserId = "meta";
	private static String aprvUserId = "meta";
	
	private static String shdStaDtm = "";
	
	/**
     * @param args
	 * @throws Exception 
	 * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException, Exception {   
        
        Connection con = ConnectionHelper.getDAConnection();
        
        try{
            
            shdStaDtm = Utils.getCurrDttm("yyyyMMddHHmmss"); 
            
            ScheduleDAO dao = new ScheduleDAO();
                      
            con.setAutoCommit(false);            
            
            //스케줄 마스트
            shdId = dao.insertSchMst(con, shdTypCd);
            
            //========스케줄 작업등록 ========
            WamPrfMstrVO mstVo = new WamPrfMstrVO();
            
            shdJobId = "OBJ_00000034562";
            
            mstVo.setPrfId(shdJobId); //OBJ_00000047284 
                        
            dao.insertSchJob(con, shdId, mstVo);
            //=================================
            
            con.commit();
            
            launch();
            
        } catch(Exception e) {
            con.rollback();
            
            e.printStackTrace();
            System.exit(1);
        }finally{
            
            if(con   != null) try { con.close(); } catch(Exception igonred) {}
        }
    }
    
    
    public static void launch() throws Exception {
        
        Connection con = ConnectionHelper.getDAConnection();          
        
        try {
        
            SchMstDM schMstDM = null;
            List<SchJobDM> schJobList = null;
            ScheduleDAO dao = new ScheduleDAO();
            
            schMstDM = dao.selectSchMst(shdId);
            schJobList = dao.selectSchJobList(shdId);
            
            
            if(schJobList.size() > 0) {

                if("Y".equals(schMstDM.getShdBprYn())) {    // 일괄처리
                    executor = Executors.newFixedThreadPool(ExecutorConf.getThreadCount());
                } else {    // 순차처리
                    executor = Executors.newSingleThreadExecutor();
                }
                
                ExecuteLogDAO executeLogDAO = new ExecuteLogDAO(con);
                
                for(SchJobDM schJob : schJobList) {
                    
                    ExecutorDM executorDM = new ExecutorDM();
                    
                    logger.debug("\n shdId:" + schMstDM.getShdId());
                    
                    //스케줄ID
                    executorDM.setShdId(schMstDM.getShdId());
                    //스케줄종류
                    executorDM.setShdKndCd(schMstDM.getShdKndCd());
                    //스케줄유형 : 한번만, 매일, 매주, 매달 등등
                    executorDM.setShdTypCd(schMstDM.getShdTypCd());
                    //스케줄 전체시작시간
                    executorDM.setShdStaDtm(shdStaDtm);
                    //요청자ID
                    executorDM.setRqstUserId(schMstDM.getRqstUserId());
                    //실행차수              
                    executorDM.setAnaDgr(schMstDM.getAnaDgr());
                    //스케줄작업ID
                    executorDM.setShdJobId(schJob.getShdJobId());
                    
                    //일반배치
                    executorDM.setEtcJobKndCd(schJob.getEtcJobKndCd());
                    executorDM.setEtcJobDtls(schJob.getEtcJobDtls());
                    
                    //분석차수 자동증가
                    executorDM.setAnaDgrAutoIncYn(schMstDM.getAnaDgrAutoIncYn());
                    
                    //PK데이터적재
                    executorDM.setPkDataLdYn(schMstDM.getPkDataLdYn());
                    executorDM.setPkDataLdCnt(schMstDM.getPkDataLdCnt());
                    
                    //에러데이터적재
                    executorDM.setErDataLdYn(schMstDM.getErDataLdYn());
                    executorDM.setErDataLdCnt(schMstDM.getErDataLdCnt());
                    
                    // 일괄처리
                    if("Y".equals(schMstDM.getShdBprYn())) {
                        // 스키마 수집
                        if(DQConstant.EXE_TYPE_01_CD.equals(executorDM.getShdKndCd())) {        
                            // 수집대상 DB정보 조회
                            TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
                            List<TargetDbmsDM> targetList = targetDbmsDAO.selectTargetDbmsList(executorDM.getShdJobId());
                            
                            for(TargetDbmsDM targetDbmsDM : targetList) {
                                String logId = UniqueKeyGenerator.getKey();
                                executorDM.setShdJobId(targetDbmsDM.getDbms_id());
                                ExecutorDM executorDM1 = executorDM.clone();
                                // 미실행 로그 생성
                                executeLogDAO.insertLog(logId, executorDM1);
                                // 스키마수집 실행
                                executor.execute(new DbCatalogExecuteRunnableImpl(logId, executorDM1));
                            }
                      } else {
                            String logId = UniqueKeyGenerator.getKey();
                            executeLogDAO.insertLog(logId, executorDM); // 미실행 로그 생성
                            
                            if(DQConstant.EXE_TYPE_03_CD.equals(executorDM.getShdKndCd())) {            // 업무규칙
                                executor.execute(new BusinessRuleExecuteRunnableImpl(logId, executorDM));
                            } else if(DQConstant.EXE_TYPE_02_CD.equals(executorDM.getShdKndCd())) {     // 프로파일
                                executor.execute(new ProfileExecuteRunnableImpl(logId, executorDM));
                            }  else if(DQConstant.EXE_TYPE_04_CD.equals(executorDM.getShdKndCd())) {        // 일반배치
                                executor.execute(new EtcExecuteRunnableImpl(logId, executorDM));
                            } else if(DQConstant.EXE_TYPE_05_CD.equals(executorDM.getShdKndCd())) {     // DB간 GAP
                                executor.execute(new DbGapExecuteRunnableImpl(logId, executorDM));
                            }
                        }
                    } 
                    
                    // 순차처리
                    else {  
                        Future<Boolean> future = null;
                        // 스키마 실행
                        if(DQConstant.EXE_TYPE_01_CD.equals(executorDM.getShdKndCd())) {        
                            // 수집대상 DB정보 조회
                            TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
                            List<TargetDbmsDM> targetList = targetDbmsDAO.selectTargetDbmsList(executorDM.getShdJobId());
                            
                            for(TargetDbmsDM targetDbmsDM : targetList) {
                                String logId = UniqueKeyGenerator.getKey();
                                executorDM.setShdJobId(targetDbmsDM.getDbms_id());
                                ExecutorDM executorDM1 = executorDM.clone();
                                // 미실행 로그 생성
                                executeLogDAO.insertLog(logId, executorDM1);
                                // 스키마수집 실행
                                future = executor.submit(new DbCatalogExecuteCallableImpl(logId, executorDM1));
                            }
                        } else {
                            String logId = UniqueKeyGenerator.getKey();
                            executeLogDAO.insertLog(logId, executorDM); // 미실행 로그 생성
                            if(DQConstant.EXE_TYPE_03_CD.equals(executorDM.getShdKndCd())) {            // 업무규칙
                                future = executor.submit(new BusinessRuleExecuteCallableImpl(logId, executorDM));
                            } else if(DQConstant.EXE_TYPE_02_CD.equals(executorDM.getShdKndCd())) {     // 프로파일
                                future = executor.submit(new ProfileExecuteCallableImpl(logId, executorDM));
                            } else if(DQConstant.EXE_TYPE_04_CD.equals(executorDM.getShdKndCd())) {     // 일반배치
                                future = executor.submit(new EtcExecuteCallableImpl(logId, executorDM));
                            } else if(DQConstant.EXE_TYPE_05_CD.equals(executorDM.getShdKndCd())) {     // DB간 GAP
                                future = executor.submit(new DbGapExecuteCallableImpl(logId, executorDM));
                            }
                        }
                        future.get();
                    }
                }
                
                
                //분석차수 자동증가
                if(!schMstDM.getShdTypCd().equals("O")){
                    if(!schMstDM.getAnaDgrAutoIncYn().equals(null) || schMstDM.getAnaDgrAutoIncYn().equals("Y")){
                        dao.updateAnaDgr(schMstDM.getShdId());
                    }
                }
                
                executor.shutdown();
            } else {
                throw new Exception("스케줄정보가 존재하지 않습니다.");
            }
            
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }finally{
            
            if(con != null) con.close();
        }
    }
}
