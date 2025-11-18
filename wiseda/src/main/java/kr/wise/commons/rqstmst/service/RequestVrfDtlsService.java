package kr.wise.commons.rqstmst.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service("requestVrfDtlsService")
public class RequestVrfDtlsService {
	
	@ Inject
	private WaqRqstVrfDtlsMapper mapper;
	
	//======= 검증결과 조회 =====
	public List<WaqRqstVrfDtls> getRqstVrfLst(WaqRqstVrfDtls search ){
		List<WaqRqstVrfDtls> list = mapper.selectList( search);
		return list;
	}
}
