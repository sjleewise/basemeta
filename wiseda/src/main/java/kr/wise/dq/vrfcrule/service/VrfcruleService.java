package kr.wise.dq.vrfcrule.service;

import java.util.ArrayList;
import java.util.List;

public interface VrfcruleService {

	List<VrfcruleVO> selectVrfcList(VrfcruleVO searchVO);

	VrfcruleVO selectVrfcDetail(String vrfcId);

	public int saveVrfc(VrfcruleVO record, String saction) throws Exception ;
	
	public int regVrfc(ArrayList<VrfcruleVO> saveVO) throws Exception;

	public int deleteVrfc(ArrayList<VrfcruleVO> record) throws Exception;

}
