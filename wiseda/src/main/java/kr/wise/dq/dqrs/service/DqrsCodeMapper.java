package kr.wise.dq.dqrs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.CodeListVo;

@Mapper
public interface DqrsCodeMapper {

	List<CodeListVo> selectPubSditmConnTrgId();

	List<CodeListVo> selectPubDmnConnTrgId();

	List<CodeListVo> selectAllDbms();

	List<CodeListVo> selectAllDbmsSchId();

	List<CodeListVo> selectGovTblDbms();

	List<CodeListVo> selectGovTblSchId();
	
}
