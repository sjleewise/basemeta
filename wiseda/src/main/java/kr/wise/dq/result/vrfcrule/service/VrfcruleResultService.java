package kr.wise.dq.result.vrfcrule.service;

import java.util.List;

import kr.wise.dq.result.service.ResultVO;

public interface VrfcruleResultService {
	
	List<ResultVO> getVrfcruleResultList(ResultVO search);
	
	/**
	 * 값진단결과 - 진단대상 테이블 현황
	 * @param data
	 * @return
	 */
	VrfcruleResultVO getTargetTableStatus(VrfcruleResultVO data);
	
	/**
	 * 값진단결과 - 진단실행상태
	 * @param data
	 * @return
	 */
	VrfcruleResultVO getAnaExecuteStatus(VrfcruleResultVO data);
	
	/**
	 * 값진단결과 - 값진단결과
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getValAnaResultList(VrfcruleResultVO data);
	
	/**
	 * 진단대상테이블
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getTargetTableList(VrfcruleResultVO data);
	
	/**
	 * 도메인
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getDmnList(VrfcruleResultVO data);
	
	/**
	 * 참조무결성
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getRefIgList(VrfcruleResultVO data);
	
	/**
	 * 필수값완전성
	 * @param data
	 * @return
	 */
//	List<VrfcruleResultVO> getReqValList(VrfcruleResultVO data);
	
	/**
	 * 데이터중복
	 * @param data
	 * @return
	 */
//	List<VrfcruleResultVO> getDataRdndList(VrfcruleResultVO data);
	
	/**
	 * 업무규칙
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getBrList(VrfcruleResultVO data);
	
	/**
	 * 진단항목실행정보
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getExcInfoList(VrfcruleResultVO data);
	
	/**
	 * 진단항목오류정보
	 * @param data
	 * @return
	 */
	List<VrfcruleResultVO> getExcErrInfoList(VrfcruleResultVO data);
	List<VrfcruleResultVO> getExcErrInfoList(VrfcruleResultVO data, boolean isDetail);
}
