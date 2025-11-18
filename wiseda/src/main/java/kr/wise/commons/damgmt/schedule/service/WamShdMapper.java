package kr.wise.commons.damgmt.schedule.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamShdMapper {

	 List<WamShd> selectList(WamShd record);

	 WamShd selectByPrimaryKey(WamShd search);

	 int insertSelectiveShd(WamShd saveVO);

	 int insertShd(String shdId);

//	 int insertShdJob(String shdId);
	 int insertShdJob(WamShdJob saveVO);

	 int updateSchedule(WamShd saveVO);

	 int updateScheduleJob(WamShd saveVO);

	 int deleteSchedule(WamShd saveVO);

	 int deleteScheduleJob(WamShd saveVO);

	 int deleteScheduleJobDtl(WamShd saveVO);



    int deleteByPrimaryKey(String shdId);

    int deleteJobByPrimaryKey(String shdId);

    int deleteJobByKey(WamShdJob record);



    int insert(WamShd record);

    int insertSelective(WamShd record);

    int updateByPrimaryKeySelective(WamShd record);

    int updateByPrimaryKey(WamShd record);

    int insertOrgShdJob(WamShdJob jobVO); 
	/** @param saveVO insomnia */
	int deleteJobByschId(WamShdJob saveVO);

	void deleteClstSimByJobId(WamShdJob saveVO);

	int insertClstSim(WamShdJob saveVO);

}