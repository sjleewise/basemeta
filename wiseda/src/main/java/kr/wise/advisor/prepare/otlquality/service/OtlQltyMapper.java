package kr.wise.advisor.prepare.otlquality.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface OtlQltyMapper {

	List<OtlQltyVo> selectDbmsQltyList(OtlQltyVo search);

	List<OtlQltyVo> selectDbmsQltyPie(OtlQltyVo search);

	List<OtlQltyVo> selectTblQltyPie(OtlQltyVo search);

	List<OtlQltyVo> selectColQltyPie(OtlQltyVo search);

	List<OtlQltyVo> selectTblQltyList(OtlQltyVo search);

	List<OtlQltyVo> selectColQltyList(OtlQltyVo search);

	List<OtlAlgQltyVo> selectOtlQltyList(OtlAlgQltyVo search);

	List<OtlAlgQltyVo> selectOtlQltyBar(OtlAlgQltyVo search);

	List<OtlAlgQltyVo> selectOtlQltyPie(OtlAlgQltyVo search);

}
