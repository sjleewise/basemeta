package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadAnaVarNstndMapper {
    int deleteByPrimaryKey(String anlVarId);

    int insert(WadAnaVarNstnd record);

    int insertSelective(WadAnaVarNstnd record);

    WadAnaVarNstnd selectByPrimaryKey(String anlVarId);

    int updateByPrimaryKeySelective(WadAnaVarNstnd record);

    int updateByPrimaryKey(WadAnaVarNstnd record);

	/** @param dslist
	/** @return insomnia */
	List<WadAnaVarNstnd> selectListbyDslist(List<WadDataSetNstnd> dslist);

	/** @param rqstNo
	/** @return insomnia */
	int updateDataTypeMap(String rqstNo);

	/** @param rqstNo
	/** @return insomnia */
	int updatefloatYn(String rqstNo);

	/** @param rqstNo
	/** @return insomnia */
	int updatenumYn(String rqstNo);

	/** @param rqstNo
	/** @return insomnia */
	int updatedateYn(String rqstNo);

	/** @param rqstNo
	/** @return insomnia */
	int updatelenchgYn(String rqstNo);

	/** @param rqstNo
	/** @return insomnia */
	int updatelenexcYn(String rqstNo);

	/** @param search
	/** @return insomnia */
	List<WadAnaVarNstnd> selectDmnPdtListbyDs(WadDataSetNstnd search);

	/** @param savevo
	/** @return insomnia */
	int updateNstndDmnResult(WadAnaVarNstnd savevo);

	/** @param dataset
	/** @return insomnia */
	List<WadAnaVarNstnd> selectVarListbydsid(WadDataSetNstnd dataset);

	/** @param search
	/** @return insomnia */
	List<WadAnaVarNstnd> selectVarListbyOutlier(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVarNstnd> selectVarListbyOtl(WadOtlDtc search);

	int updateAnaVarNstnd(String rqstNo); 
}