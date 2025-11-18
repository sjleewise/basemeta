package kr.wise.commons.damgmt.schedule.service;

import java.util.List;

import kr.wise.dq.impv.service.WaqImActMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamShdJobMapper {
    int deleteByPrimaryKey(@Param("shdId") String shdId, @Param("shdJobId") String shdJobId, @Param("shdJobSno") Integer shdJobSno);

    int insert(WamShdJob record);

    int insertSelective(WamShdJob record);

    WamShdJob selectByPrimaryKey(@Param("shdId") String shdId, @Param("shdJobId") String shdJobId, @Param("shdJobSno") Integer shdJobSno);

    int updateByPrimaryKeySelective(WamShdJob record);

    int updateByPrimaryKey(WamShdJob record);

    List<WamShdJob> selectJobList(WamShdJob record);
    
    
    List<WamShd> selectScJobList(WamShd record);

	 List<WamShd> selectQpJobList(WamShd record);

	 List<WamShd> selectGnJobList(WamShd record);

	 List<WamShd> selectQbJobList(WamShd record);
	 
	 List<WamShd> selectDgJobList(WamShd record);
	 
	 List<WamShd> selectCsJobList(WamShd record);
	 
	 List<WamShd> selectMaJobList(WamShd record);
	 
	 List<WamShdJob> selectJobScPopList(WamShdJob record);

	 List<WamShdJob> selectJobQpPobList(WamShdJob record);

	 List<WamShdJob> selectJobQbPopList(WamShdJob record);
	 
	 List<WamShdJob> selectJobDgPopList(WamShdJob record);
	 
	 List<WamShdJob> selectJobCsPopList(WamShdJob record);
    
    /** 개선결과중 개선결과코드가 개선불가이면서 스케줄작업 삭제요청여부가 Y인 내용 삭제...
     *  yeonho
     */
    int deleteShdJob (@Param("shdJobId") String shdJobId);

	/** @param search
	/** @return insomnia */
	List<WamShdJob> selectJobPyPopList(WamShdJob search);

	/** @param search
	/** @return insomnia */
	List<WamShd> selectJobPyList(WamShd search);

	/** @param search
	/** @return insomnia */
	List<WamShdJob> selectJobDcPopList(WamShdJob search);

	/** @param search
	/** @return insomnia */
	List<WamShd> selectJobDcList(WamShd search);

	List<WamShdJob> selectJobUoPopList(WamShdJob search);

	List<WamShd> selectJobUoList(WamShd search);

	List<WamShdJob> selectJobSmPopList(WamShdJob search);

	List<WamShd> selectJobSmList(WamShd search);

	List<WamShdJob> selectJobTmPopList(WamShdJob search);

	List<WamShdJob> selectJobTcPopList(WamShdJob search);

	List<WamShdJob> selectImdScheduleJobList(WamShd search);

	List<WamShd> selectVrfcJobList(WamShd search);

	List<WamShdJob> selectJobCrPopList(WamShdJob search);
}