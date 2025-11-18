package kr.wise.commons.damgmt.schedule.service;

import java.util.Date;
import kr.wise.commons.damgmt.schedule.service.WahShdJob;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahShdJobMapper {

    int insertSelective(WamShdJob record);

}