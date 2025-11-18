package kr.wise.dq.dqrs.service;

import java.util.List;

import kr.wise.commons.code.service.CodeListVo;

public interface DqrsCodeService {
	//코드 리스트 조회 쿼리(기존 CodeList에 추가 하지 않고 신규 모듈에 추가(기존 소스 영향 없게 하기 위함.)) 
	List<CodeListVo> getPubSditmConnTrgId();
	List<CodeListVo> getPubDmnConnTrgId();
	List<CodeListVo> getAllDbms();
	List<CodeListVo> getAllDbmsSchId();
	
	List<CodeListVo> getGovTblDbms();
	List<CodeListVo> getGovTblSchId();
}
