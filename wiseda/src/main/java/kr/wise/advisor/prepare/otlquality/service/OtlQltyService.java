package kr.wise.advisor.prepare.otlquality.service;

import java.util.List;

public interface OtlQltyService {

	List<OtlQltyVo> getDbmsQltyList(OtlQltyVo search);

	List<OtlQltyVo> getDbmsQltyPie(OtlQltyVo search);

	List<OtlQltyVo> getTblQltyList(OtlQltyVo search);

	List<OtlQltyVo> getTblQltyPie(OtlQltyVo search);

	List<OtlQltyVo> getColQltyList(OtlQltyVo search);

	List<OtlQltyVo> getColQltyPie(OtlQltyVo search);

	List<OtlAlgQltyVo> getOtlQltyList(OtlAlgQltyVo search);

	List<OtlAlgQltyVo> getOtlQltyPie(OtlAlgQltyVo search);

	List<OtlAlgQltyVo> getOtlQltyBar(OtlAlgQltyVo search);

}
