package kr.wise.executor.exceute;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.UniqueKeyGenerator;
import kr.wise.commons.Utils;
import kr.wise.executor.Executor;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.ExecuteLogDAO;
import kr.wise.executor.dao.ScheduleDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.SchJobDM;
import kr.wise.executor.dm.SchMstDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.ai.PythonExecuteCallableImpl;
import kr.wise.executor.exceute.ai.PythonExecuteRunnableImpl;
import kr.wise.executor.exceute.ai.histogram.PythonHSExecuteCallableImpl;
import kr.wise.executor.exceute.ai.histogram.PythonHSExecuteRunnableImpl;
import kr.wise.executor.exceute.ai.summary.PythonSMExecuteCallableImpl;
import kr.wise.executor.exceute.ai.summary.PythonSMExecuteRunnableImpl;
import kr.wise.executor.exceute.ai.textcluster.PythonTCExecuteCallableImpl;
import kr.wise.executor.exceute.ai.textcluster.PythonTCExecuteRunnableImpl;
import kr.wise.executor.exceute.ai.textmatch.PythonTMExecuteCallableImpl;
import kr.wise.executor.exceute.ai.textmatch.PythonTMExecuteRunnableImpl;
import kr.wise.executor.exceute.ai.udoutlier.PythonUOExecuteCallableImpl;
import kr.wise.executor.exceute.ai.udoutlier.PythonUOExecuteRunnableImpl;
import kr.wise.executor.exceute.br.BusinessRuleExecuteCallableImpl;
import kr.wise.executor.exceute.br.BusinessRuleExecuteRunnableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteCallableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteRunnableImpl;
import kr.wise.executor.exceute.dp.DomainClassCallableImpl;
import kr.wise.executor.exceute.dp.DomainClassRunnableImpl;
import kr.wise.executor.exceute.dpnstnd.DomainClassNstndCallableImpl;
import kr.wise.executor.exceute.dpnstnd.DomainClassNstndRunnableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteCallableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteRunnableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteCallableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteRunnableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteCallableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteRunnableImpl;
import kr.wise.executor.exceute.vrfcrule.VrfcRuleExecuteCallableImpl;
import kr.wise.scheduler.ScheduleRegistry;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public class DAExecutor implements Job, InterruptableJob {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DAExecutor.class);
	
	private String shdId = null;
	private String shdTypCd = null;
	private String shdStaDtm = null;
	private String rqstUserId = null;
	private String aprvUserId = null;

	private ExecutorService executor;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		shdId = (String) context.getJobDetail().getJobDataMap().get("SHD_ID");
		shdTypCd = (String) context.getJobDetail().getJobDataMap().get("SHD_TYP_CD");
		shdStaDtm = Utils.getCurrDttm("yyyyMMddHHmmss");
		
		rqstUserId = (String) context.getJobDetail().getJobDataMap().get("RQST_USER_ID");
		aprvUserId = (String) context.getJobDetail().getJobDataMap().get("APRV_USER_ID");

		try {
			launch();
			
			//도메인 자동판별을 여기에 등록하면 ????????
			String testdtm = Utils.getCurrDttm("yyyyMMddHHmmss");
			logger.debug("end launch:"+testdtm);
		} catch(Exception e) {
			throw new JobExecutionException(e);
		}
		
		logger.info("Schedule finished. (" + shdId + ")");
	}

	public void interrupt() throws UnableToInterruptJobException {
		logger.info("Schedule interrupt.");
		try {
			if(executor != null) {
				executor.shutdownNow();
				while(!executor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				}
			}
		} catch(Exception ignored) {
		} finally {
			logger.info("Schedule interrupt() finished.");
			//ExecuteLogDAO executeLogDAO = new ExecuteLogDAO();
			//executeLogDAO.updateInterruptLog(shdId, shdStaDtm);
		}
	}

	public void setShdId(String shdId) {
		this.shdId = shdId;
	}
	
	public void launch() throws Exception {
	    
	    Connection con = ConnectionHelper.getDAConnection();          
	    
		try {
			SchMstDM schMstDM = null;
			List<SchJobDM> schJobList = null;
			ScheduleDAO dao = new ScheduleDAO();
			
			if(DQConstant.EXE_TYPE_01_CD.equals(shdTypCd) || DQConstant.EXE_TYPE_02_CD.equals(shdTypCd) 
					|| DQConstant.EXE_TYPE_03_CD.equals(shdTypCd) || DQConstant.EXE_TYPE_06_CD.equals(shdTypCd)) {	// 온라인 실행
				schMstDM = new SchMstDM();
				schMstDM.setShdBprYn("N");
				schMstDM.setRqstUserId(rqstUserId);
				
				schJobList = new ArrayList<SchJobDM>();
				SchJobDM dm = new SchJobDM();
				dm.setShdJobId(shdId);
				
				schJobList.add(dm);
			} else {
				
				if(shdId == null || "".equals(shdId)) {
					throw new Exception("스케줄 ID가 존재하지 않습니다.");
				}
				schMstDM = dao.selectSchMst(shdId);
				schJobList = dao.selectSchJobList(shdId);
			}
			
			if(schJobList.size() > 0) {

				if("Y".equals(schMstDM.getShdBprYn())) {	// 일괄처리
					executor = Executors.newFixedThreadPool(ExecutorConf.getThreadCount());
				} else {	// 순차처리
					executor = Executors.newSingleThreadExecutor();
				}
				
				
				ExecuteLogDAO executeLogDAO = new ExecuteLogDAO(con);
				
				for(SchJobDM schJob : schJobList) {
					
					ExecutorDM executorDM = new ExecutorDM();
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
//							TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
//							List<TargetDbmsDM> targetList = targetDbmsDAO.selectTargetDbmsList(executorDM.getShdJobId());
							
//							for(TargetDbmsDM targetDbmsDM : targetList) {
//								String logId = UniqueKeyGenerator.getKey();
//								executorDM.setShdJobId(targetDbmsDM.getDbms_id());
//								ExecutorDM executorDM1 = executorDM.clone();
//								// 미실행 로그 생성
//								executeLogDAO.insertLog(logId, executorDM1);
//								
//								try{
//								    // 스키마수집 실행
//	                                executor.execute(new DbCatalogExecuteRunnableImpl(logId, executorDM1)); 	                                
//                                }catch(Exception e){
//                                    logger.error(e);
//                                    executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
//                                }
//							}
							logger.debug("EXE_TYPE_01_CD(스키마수집 일괄처리)");
							String logId = UniqueKeyGenerator.getKey();
							executorDM.setShdJobId(executorDM.getShdJobId());
							ExecutorDM executorDM1 = executorDM.clone();
							// 미실행 로그 생성
							executeLogDAO.insertLog(logId, executorDM1);
							
							try{
							    // 스키마수집 실행
                                executor.execute(new DbCatalogExecuteRunnableImpl(logId, executorDM1)); 	                                
                            }catch(Exception e){
                                logger.error(e);
                                executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
                            }
					  } else {
							String logId = UniqueKeyGenerator.getKey();
							executeLogDAO.insertLog(logId, executorDM);	// 미실행 로그 생성
							
							if(DQConstant.EXE_TYPE_03_CD.equals(executorDM.getShdKndCd())) {			// 업무규칙
																
								try{                                
								    executor.execute(new BusinessRuleExecuteRunnableImpl(logId, executorDM));
                                }catch(Exception e){
                                    logger.error(e);
                                    executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
                                }
								
							} else if(DQConstant.EXE_TYPE_02_CD.equals(executorDM.getShdKndCd())) {		// 프로파일
							    try{							    
							        executor.execute(new ProfileExecuteRunnableImpl(logId, executorDM));
							    }catch(Exception e){
							        logger.error(e);
							        executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
							    }
								
							}  else if(DQConstant.EXE_TYPE_04_CD.equals(executorDM.getShdKndCd())) {		// 일반배치
								executor.execute(new EtcExecuteRunnableImpl(logId, executorDM));
							} else if(DQConstant.EXE_TYPE_05_CD.equals(executorDM.getShdKndCd())) {		// DB간 GAP
								executor.execute(new DbGapExecuteRunnableImpl(logId, executorDM));
							} else if("SM".equals(executorDM.getShdKndCd())) {		
								// Python Summary 호출
								executor.execute(new PythonSMExecuteRunnableImpl(logId, executorDM));  
							} else if("HS".equals(executorDM.getShdKndCd())) {		
								// Python 히스토그램 호출
								executor.execute(new PythonHSExecuteRunnableImpl(logId, executorDM)); 
							} else if(DQConstant.EXE_TYPE_06_CD.equals(executorDM.getShdKndCd())) {	
								// Python 이상값탐지 호출
								executor.execute(new PythonExecuteRunnableImpl(logId, executorDM));
							} else if(DQConstant.PY_DOMAIN_CD.equals(executorDM.getShdKndCd())) {		
								// 도메인 판별
								executor.execute(new DomainClassRunnableImpl(logId, executorDM));
							} else if("TM".equals(executorDM.getShdKndCd())) {								  
								//텍스트 매칭 
								executor.execute(new PythonTMExecuteRunnableImpl(logId, executorDM));
							} else if("TC".equals(executorDM.getShdKndCd())) {								  
								//텍스트 클러스트링
								executor.execute(new PythonTCExecuteRunnableImpl(logId, executorDM));
								
							} else if("UO".equals(executorDM.getShdKndCd())) {								  
								//사용자정의 이상값
								executor.execute(new PythonUOExecuteRunnableImpl(logId, executorDM)); 	
							} else if(DQConstant.PY_NSTND_DOMAIN_CD.equals(executorDM.getShdKndCd())) {		
								// 비표준 도메인 판별
								executor.execute(new DomainClassNstndRunnableImpl(logId, executorDM));
							} /* else if("MA".equals(executorDM.getShdKndCd())) {								  
								//마트수집
								executorDM.setEtcJobDtls("");
								executor.execute(new EtcExecuteRunnableImpl(logId, executorDM));
							} */
						}
					} 
					
					// 순차처리
					else {	
						Future<Boolean> future = null;
						// 스키마 실행
						if(DQConstant.EXE_TYPE_01_CD.equals(executorDM.getShdKndCd())) {		
							// 수집대상 DB정보 조회
//							TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
//							List<TargetDbmsDM> targetList = targetDbmsDAO.selectTargetDbmsList(executorDM.getShdJobId());
							
//							for(TargetDbmsDM targetDbmsDM : targetList) {
//								String logId = UniqueKeyGenerator.getKey();
//								executorDM.setShdJobId(targetDbmsDM.getDbms_id());
//								ExecutorDM executorDM1 = executorDM.clone();
//								// 미실행 로그 생성
//								executeLogDAO.insertLog(logId, executorDM1);
//								
//								try{
//								    // 스키마수집 실행
//								    future = executor.submit(new DbCatalogExecuteCallableImpl(logId, executorDM1));
//								}catch(Exception e){
//                                    logger.error(e);
//                                    executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
//                                }
//								
//							}
							
							String logId = UniqueKeyGenerator.getKey();
							executorDM.setShdJobId(executorDM.getShdJobId());
							ExecutorDM executorDM1 = executorDM.clone();
							logger.debug("EXE_TYPE_01_CD(스키마수집 순차처리)");
							// 미실행 로그 생성
							executeLogDAO.insertLog(logId, executorDM1);
							
							try{
							    // 스키마수집 실행
							    future = executor.submit(new DbCatalogExecuteCallableImpl(logId, executorDM1));
							}catch(Exception e){
                                logger.error(e);
                                executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
                            }
						} else {
							String logId = UniqueKeyGenerator.getKey();
							executeLogDAO.insertLog(logId, executorDM);	// 미실행 로그 생성
							if(DQConstant.EXE_TYPE_03_CD.equals(executorDM.getShdKndCd())) {			// 업무규칙
																
								try{                                
								    future = executor.submit(new BusinessRuleExecuteCallableImpl(logId, executorDM));
                                }catch(Exception e){
                                    logger.error(e);
                                    executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
                                }
																
							} else if(DQConstant.EXE_TYPE_02_CD.equals(executorDM.getShdKndCd())) {		// 프로파일
								
								try{                                
								    future = executor.submit(new ProfileExecuteCallableImpl(logId, executorDM)); 	                                
                                }catch(Exception e){
                                    logger.error(e);
                                    executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
                                }
								
							}else if(DQConstant.EXE_TYPE_CR_CD.equals(executorDM.getShdKndCd())) {  //검증룰
						    	
						    	logger.debug("\n 검증룰 ");
						    	
						    	try{	
						    		future = executor.submit(new VrfcRuleExecuteCallableImpl(logId, executorDM));   	
								 }catch(Exception e){
							        logger.error(e);
							        executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
								 } 																												
							}else if(DQConstant.EXE_TYPE_04_CD.equals(executorDM.getShdKndCd())) {		// 일반배치
								future = executor.submit(new EtcExecuteCallableImpl(logId, executorDM));
							} else if(DQConstant.EXE_TYPE_05_CD.equals(executorDM.getShdKndCd())) {		// DB간 GAP
								future = executor.submit(new DbGapExecuteCallableImpl(logId, executorDM));
							} else if("SM".equals(executorDM.getShdKndCd())) {								  
								//Summary								
								future = executor.submit(new PythonSMExecuteCallableImpl(logId, executorDM));  
							} else if("HS".equals(executorDM.getShdKndCd())) {								  
								//히스토그램								
								future = executor.submit(new PythonHSExecuteCallableImpl(logId, executorDM)); 
							} else if(DQConstant.EXE_TYPE_06_CD.equals(executorDM.getShdKndCd())) {		// Python 호출
								future = executor.submit(new PythonExecuteCallableImpl(logId, executorDM));
							} else if(DQConstant.PY_DOMAIN_CD.equals(executorDM.getShdKndCd())) {		
								// 도메인 판별 호출
								future = executor.submit(new DomainClassCallableImpl(logId, executorDM));
							} else if("TM".equals(executorDM.getShdKndCd())) {								  
								//텍스트 매칭 								
								future = executor.submit(new PythonTMExecuteCallableImpl(logId, executorDM));
							} else if("TC".equals(executorDM.getShdKndCd())) {								  
								//텍스트 클러스트링 					
								future = executor.submit(new PythonTCExecuteCallableImpl(logId, executorDM)); 
							} else if("UO".equals(executorDM.getShdKndCd())) {								  
								//사용자정의 이상값
								future = executor.submit(new PythonUOExecuteCallableImpl(logId, executorDM));
							} else if(DQConstant.PY_NSTND_DOMAIN_CD.equals(executorDM.getShdKndCd())) {		
								// 비표준 도메인 판별 호출
								future = executor.submit(new DomainClassNstndCallableImpl(logId, executorDM));
							} /* else if("MA".equals(executorDM.getShdKndCd())) {								  
								//마트수집
								future = executor.submit(new PythonUOExecuteCallableImpl(logId, executorDM));
							} */
							
						}
						future.get();
					}
				}
				
				
				//분석차수 자동증가
				if(!schMstDM.getShdTypCd().equals("O")){
					if(!(schMstDM.getAnaDgrAutoIncYn() == null) && schMstDM.getAnaDgrAutoIncYn().equals("Y")){
						dao.updateAnaDgr(schMstDM.getShdId());
					}
				}
			
				//도메인 자동판별을 여기에 등록하면 ????????
//				String testdtm = Utils.getCurrDttm("yyyyMMddHHmmss");
				if ("QP".equals(schMstDM.getShdKndCd()) && "AUTO_DMN".equals(schMstDM.getShdPnm())  ) {
					logger.debug("reg auto dmn predict:"+schMstDM.getShdId());
					
					String dbmsId = schMstDM.getRqstNo();
					String schid = dao.regSchMstDmn(dbmsId);
					
					//스케줄 id에 따라 스케줄 등록
					ScheduleRegistry regsch = new ScheduleRegistry();
					regsch.register("U", schid);
					
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
