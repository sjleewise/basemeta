package kr.wise.advisor.prepare.stat.service;

import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public interface StatService {

	List<StatVo> getProfStatList(StatVo search);

	List<StatVo> getBrStatList(StatVo search);

	List<StatVo> getOtlStatList(StatVo search);

	List<StatVo> getProfStatDtl(StatVo search);

	List<StatVo> getBrStatDtl(StatVo search);

	List<StatVo> getOtlStatDtl(StatVo search);

	List<StatVo> getClstStatList(StatVo search);

	List<StatVo> getMtchStatList(StatVo search);

	List<StatVo> getClstStatDtl(StatVo search);

	List<StatVo> getMtchStatDtl(StatVo search);

}
