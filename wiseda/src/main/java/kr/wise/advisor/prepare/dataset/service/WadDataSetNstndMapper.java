package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

@Mapper
public interface WadDataSetNstndMapper {
    int deleteByPrimaryKey(String daseId);

    int insert(WadDataSetNstnd record);

    int insertSelective(WadDataSetNstnd record);

    WadDataSetNstnd selectByPrimaryKey(String daseId);

    int updateByPrimaryKeySelective(WadDataSetNstnd record);

    int updateByPrimaryKey(WadDataSetNstnd record);

	/** @param savevo
	/** @return insomnia */
	WadDataSetNstnd selectDataSetNstnd(AnaTrgTblVO savevo);

	/** @param rqstNo
	/** @return insomnia */
	List<WaaDbConnTrgVO> selectDataSetNstndListbyrqstNo(String rqstNo);

	/** @param search
	/** @return insomnia */
	List<WadDataSetNstnd> selectDataSetNstndList(WadDataSetNstnd search);

	/** @param search
	/** @return insomnia */
	WaaDbConnTrgVO selectTgtDbmsbyDs(WadDataSetNstnd search);
}