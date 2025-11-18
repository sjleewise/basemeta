package kr.wise.dq.vrfcrule.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface VrfcruleMapper {
    int deleteByPrimaryKey(int menuNo);

    VrfcruleVO selectByPrimaryKey(int menuNo);

    int updateByPrimaryKeySelective(VrfcruleVO record);

	List<VrfcruleVO> selectVrfcList(VrfcruleVO searchVO);

	VrfcruleVO selectVrfcDetail(String menuId);

	int insertVrfc(VrfcruleVO saveVO);

	int updateVrfc(VrfcruleVO saveVO);

	int deleteVrfc(String menuId);
	
	int updateExpDtm(VrfcruleVO record);

}