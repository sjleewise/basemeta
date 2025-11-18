/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ScheduleManagerServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service.impl
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 4. 11. 오후 3:14:19
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 11. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.schedule.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WahShdJobMapper;
import kr.wise.commons.damgmt.schedule.service.WahShdMstrMapper;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.damgmt.schedule.service.WamShdJobMapper;
import kr.wise.commons.damgmt.schedule.service.WamShdMapper;
import kr.wise.commons.helper.CStreamGobbler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ScheduleManagerServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service.impl
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 4. 11. 오후 3:14:19
 * </PRE>
 */
@Service("scheduleManagerService")
public class ScheduleManagerServiceImpl implements  ScheduleManagerService{
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamShdMapper mapper;
	@Inject
	private WamShdJobMapper jobMapper;

	@Inject
	private WahShdMstrMapper wahMapper;

	@Inject
	private WahShdJobMapper wahJobMapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;
	
	@Inject
	private WaaDbConnTrgMapper waaDbConnTrgMapper;

	@Override
	public List<WamShd> getScheduleList(WamShd search) {
		return mapper.selectList(search);
	}

	@Override
	public WamShd getScheduleDtlList(WamShd search) {
		return mapper.selectByPrimaryKey(search);
	}

	@Override
	public List<WamShd> getScheduleJobList(WamShd search) {
		List<WamShd> list = null;
		String shdKndCd = search.getShdKndCd();

		if("SC".equals(shdKndCd)){		//스키마 수집
			list = jobMapper.selectScJobList(search);
		}else if("QP".equals(shdKndCd)){		//프로파일
			list = jobMapper.selectQpJobList(search);
		}else if("GN".equals(shdKndCd)){		//일반배치
			list = jobMapper.selectGnJobList(search);
		}else if("QB".equals(shdKndCd)){
			list = jobMapper.selectQbJobList(search);
		}else if("DG".equals(shdKndCd)){
			list = jobMapper.selectDgJobList(search);
		}else if("CS".equals(shdKndCd)){
			list = jobMapper.selectCsJobList(search);
		}else if("PY".equals(shdKndCd)){ //파이썬 실행
			list = jobMapper.selectJobPyList(search);
		}else if("DC".equals(shdKndCd)){ //도메인 판별
			list = jobMapper.selectJobDcList(search);
		}else if("UO".equals(shdKndCd)){ //사용자정의이상값탐지
			list = jobMapper.selectJobUoList(search);
		}else if("sm".equals(shdKndCd)){ //사용자정의이상값탐지
			list = jobMapper.selectJobSmList(search);
		}else if("CR".equals(shdKndCd)){		//검증룰	
			list = jobMapper.selectVrfcJobList(search);	 
		}
		return list;
	}

	@Override
	public List<WamShdJob> getJobPopList(WamShdJob search) {
		List<WamShdJob> list = null;
		String shdKndCd = search.getShdKndCd();
		
		logger.debug("search >>> " + search);

		if("SC".equals(shdKndCd)){			//스키마 수집
			list = jobMapper.selectJobScPopList(search);
		}else if("QP".equals(shdKndCd)){		//프로파일
			list = jobMapper.selectJobQpPobList(search);
		}else if("QB".equals(shdKndCd)){		//업무규칙
			list = jobMapper.selectJobQbPopList(search);
		}else if("DG".equals(shdKndCd)){		//DB간GAP분석
			list = jobMapper.selectJobDgPopList(search);
		}else if("CS".equals(shdKndCd)){
			list = jobMapper.selectJobCsPopList(search);
		}else if("PY".equals(shdKndCd)){ //파이썬 실행
			list = jobMapper.selectJobPyPopList(search);
		}else if("DC".equals(shdKndCd)){ //도메인 판별
			list = jobMapper.selectJobDcPopList(search);
		}else if("UO".equals(shdKndCd)){ //사용자정의이상값탐지
			list = jobMapper.selectJobUoPopList(search);
		}else if("SM".equals(shdKndCd)) {
			list = jobMapper.selectJobSmPopList(search);
		}else if("TM".equals(shdKndCd)) {
			list = jobMapper.selectJobTmPopList(search);
		}else if("TC".equals(shdKndCd)) {
			list = jobMapper.selectJobTcPopList(search);
		}else if("CR".equals(shdKndCd)) { //검증룰
			list = jobMapper.selectJobCrPopList(search);
		}
		return list;
	}
	

	//스케줄 정보, 작업 정보 동시 저장
	@Override
	public int saveSchedule(ArrayList<WamShdJob> list, WamShd saveVO) throws Exception{

    	int result = 0;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//요청자 세팅..
		saveVO.setRqstUserId(userid);

		//요청ID 미존재시
		if(UtilString.null2Blank(saveVO.getShdId()).equals("")){
			saveVO.setShdId(objectIdGnrService.getNextStringId());
			saveVO.setRegTypCd("C");
		}
		
		//스케줄마스터 저장
    	result = saveShd(saveVO);

    	
    	//작업정보 저장
    	String orgShdId = UtilString.null2Blank(saveVO.getOrgShdId()); 
        
        if(orgShdId.equals("")) {
            //작업정보 저장
            for ( WamShdJob subVO : list ){

                subVO.setShdId(saveVO.getShdId());
				subVO.setShdKndCd(saveVO.getShdKndCd());
                
                //일반배치 작업ID 채번
                if("GN".equals(subVO.getShdKndCd())||"MA".equals(subVO.getShdKndCd())){
                    if(UtilString.null2Blank(subVO.getShdJobId()).equals("")){
                        subVO.setShdJobId(objectIdGnrService.getNextStringId());
                        subVO.setRegTypCd("C");
                    }
                }
                //작업저장
                result += saveShdJob(subVO);
            }

        }else{
            
            WamShdJob jobVO = new WamShdJob(); 
            
            jobVO.setShdId(saveVO.getShdId());
            jobVO.setOrgShdId(orgShdId);
            
            result +=  mapper.insertOrgShdJob(jobVO); 
        }

    	return result;

	}

	//스케줄 정보 저장
	public int saveShd(WamShd saveVO) throws Exception{

		int result = 0;

		String tmpStatus = saveVO.getRegTypCd();

		//이력테이블 만료
		result = wahMapper.updateWahShd("WAH_SHD_MSTR", saveVO.getShdId());

		//이력테이블 등록
		result = wahMapper.insertSelective(saveVO);

		if("C".equals(tmpStatus) || "U".equals(tmpStatus) ) {
			//WAM 테이블 삭제
			result = mapper.deleteByPrimaryKey(saveVO.getShdId());
			//WAM 테이블 등록
			result = mapper.insertShd(saveVO.getShdId());
		}else if("D".equals(tmpStatus)){
			result = mapper.deleteByPrimaryKey(saveVO.getShdId());
		}

		return result;

	}

 	public int saveShdJob(WamShdJob saveVO) throws Exception{
	 	int result = 0;
	 	String tmpStatus = saveVO.getIbsStatus();
	
		//이력테이블 만료
		result += wahMapper.updateWahShd("WAH_SHD_JOB", saveVO.getShdId());
		
		if ("D".equals(tmpStatus)) {
			saveVO.setRegTypCd("D");
			//이력테이블 등록
			//result += wahJobMapper.insertSelective(saveVO);
			//WAM 테이블 만료
			result += mapper.deleteJobByKey(saveVO);
		}else {
			saveVO.setRegTypCd("C");
			//이력테이블 등록
			result += wahJobMapper.insertSelective(saveVO);
			
			//WAM 테이블 만료
			result += mapper.deleteJobByKey(saveVO);
			if ("DC".equals(saveVO.getShdKndCd())) {
				mapper.deleteJobByschId(saveVO);
			}
			
			//WAM 테이블 등록
			result += mapper.insertShdJob(saveVO);
		} 

		return result;
 	}


	@Override
	public int delSchedule(List<WamShdJob> dellist) throws Exception {
		int result = 0;

		for (WamShdJob delvo : dellist) {
			delvo.setRegTypCd("D");

			result += saveShd(delvo);
			delScheduleJob(delvo);
		}

		return result;
	}

	//스케줄 정보에 해당되는 스케줄 작업 정보도 삭제 업데이트..
	private int delScheduleJob(WamShd delvo) {
		return mapper.deleteJobByPrimaryKey(delvo.getShdId());
	}

	@Override
	public int delScheduleJob(List<WamShdJob> list, String shdId) throws Exception {
		int result = 0;

		for (WamShdJob delvo : list) {
			if(UtilString.null2Blank(delvo.getObjVers()).equals("")){
				delvo.setObjVers(1);
			}else{
				delvo.setObjVers(delvo.getObjVers()+1);
			}
			delvo.setIbsStatus("D");
			delvo.setRegTypCd("D");
			//System.out.println("===objvers====>"+delvo.getObjVers());
			result += saveShdJob(delvo);
		}

		return result;
	}

	@Override
	public int schStart(String schedulerpath) throws Exception{
		// TODO Auto-generated method stub
		Process process = null;
		ProcessBuilder pb = null;
		String cmd = "";
		String cmd2 = schedulerpath + "/start.sh";
		
		try {
			// 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
	        if (System.getProperty("os.name").indexOf("Windows") > -1) {
	        	cmd = "cd " + schedulerpath + " && start start.cmd";
	        	process = new ProcessBuilder("cmd", "/c", cmd).start();
	        } else {
	        	pb = new ProcessBuilder(cmd2);
	        	pb.directory(new File(schedulerpath));
	        	process = pb.start();
	        }
			
			logger.debug(cmd);
			logger.debug(cmd2);
			
			// 서버가 윈도우인 경우 버퍼 해소
			CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
			CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

			out.start();
			error.start();
			
			while (true) {
				if (!out.isAlive() && !error.isAlive()) {  //두개의 스레드가 정지하면 프로세스 종료때까지 기다린다.
					process.waitFor();
					break;
				}
			}

			int exitValue = process.exitValue();
			logger.debug("ExitValue : " + exitValue);
			
			if(exitValue != 0) {
				throw new Exception("Runtime error.("+exitValue+")");
			}
			
			return exitValue;
		} catch (InterruptedException e) {
			logger.error("" + e);
			throw new Exception(e);
		} catch(IOException e) {
			logger.error("" + e);
			throw new Exception(e);
		} catch(Exception e) {
			logger.error("" + e);
			throw e;
		} finally {
			if(process != null) try { process.destroy(); } catch(Exception ignored) {}
		}
	}
	
	@Override
	public int schStop(String schedulerpath) throws Exception{
		// TODO Auto-generated method stub
		Process process = null;
		ProcessBuilder pb = null;
		String cmd = "";
		String cmd2 = schedulerpath + "/stop.sh";
		
		try {
			// 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
	        if (System.getProperty("os.name").indexOf("Windows") > -1) {
	        	cmd = "start taskkill /f /im cmd.exe /t";
	        	process = new ProcessBuilder("cmd", "/c", cmd).start();
	        } else {
	        	pb = new ProcessBuilder(cmd2);
	        	pb.directory(new File(schedulerpath));
	        	process = pb.start();
	        }
			
			logger.debug(cmd);
			logger.debug(cmd2);
			
			// 서버가 윈도우인 경우 버퍼 해소
			CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
			CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

			out.start();
			error.start();
			
			while (true) {
				if (!out.isAlive() && !error.isAlive()) {  //두개의 스레드가 정지하면 프로세스 종료때까지 기다린다.
					process.waitFor();
					break;
				}
			}

			int exitValue = process.exitValue();
			logger.debug("ExitValue : " + exitValue);
			
			if(exitValue != 0) {
				throw new Exception("Runtime error.("+exitValue+")");
			}
			
			return exitValue;
		} catch (InterruptedException e) {
			logger.error("" + e);
			throw new Exception(e);
		} catch(IOException e) {
			logger.error("" + e);
			throw new Exception(e);
		} catch(Exception e) {
			logger.error("" + e);
			throw e;
		} finally {
			if(process != null) try { process.destroy(); } catch(Exception ignored) {}
		}
	}

	@Override
	public List<WamShdJob> getImdScheduleJobList(WamShd search) {
		return jobMapper.selectImdScheduleJobList(search);
	}

	@Override
	public String getDbmsTypCd(String dbConnTrgId) throws Exception {
		return waaDbConnTrgMapper.selectDbmsTypCdById(dbConnTrgId);
	}
}
