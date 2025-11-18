package kr.wise.advisor.prepare.stat.service;

import java.util.List;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface StatMapper {

	List<StatVo> selectProfStatList(StatVo search);

	List<StatVo> selectBrStatList(StatVo search);

	List<StatVo> selectOtlStatList(StatVo search);

	List<StatVo> selectProfStatDtl(StatVo search);

	List<StatVo> selectBrStatDtl(StatVo search);

	List<StatVo> selectOtlStatDtl(StatVo search);

	List<StatVo> selectClstStatList(StatVo search);

	List<StatVo> selectMtchStatList(StatVo search);

	List<StatVo> selectClstStatDtl(StatVo search);

	List<StatVo> selectMtchStatDtl(StatVo search);

}
