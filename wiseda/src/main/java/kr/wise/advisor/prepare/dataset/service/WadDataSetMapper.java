package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

@Mapper
public interface WadDataSetMapper {
    int deleteByPrimaryKey(String daseId);

    int insert(WadDataSet record);

    int insertSelective(WadDataSet record);

    WadDataSet selectByPrimaryKey(String daseId);

    int updateByPrimaryKeySelective(WadDataSet record);

    int updateByPrimaryKey(WadDataSet record);

	/** @param savevo
	/** @return insomnia */
	WadDataSet selectDataSet(AnaTrgTblVO savevo);

	/** @param rqstNo
	/** @return insomnia */
	List<WaaDbConnTrgVO> selectDataSetListbyrqstNo(String rqstNo);

	/** @param search
	/** @return insomnia */
	List<WadDataSet> selectDataSetList(WadDataSet search);

	/** @param search
	/** @return insomnia */
	WaaDbConnTrgVO selectTgtDbmsbyDs(WadDataSet search);
	
	WaaDbConnTrgVO selectTgtDbmsbyTrgId(WadDataSet search);
}