package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadAnaVarMapper {
    int deleteByPrimaryKey(String anlVarId); 

    int insert(WadAnaVar record);

    int insertSelective(WadAnaVar record);

    WadAnaVar selectByPrimaryKey(String anlVarId);

    int updateByPrimaryKeySelective(WadAnaVar record);

    int updateByPrimaryKey(WadAnaVar record);

	/** @param dslist
	/** @return insomnia */
	List<WadAnaVar> selectListbyDslist(List<WadDataSet> dslist);

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
	List<WadAnaVar> selectDmnPdtListbyDs(WadDataSet search);

	/** @param savevo
	/** @return insomnia */
	int updateDmnResult(WadAnaVar savevo);

	/** @param dataset
	/** @return insomnia */
	List<WadAnaVar> selectVarListbydsid(WadDataSet dataset);

	/** @param search
	/** @return insomnia */
	List<WadAnaVar> selectVarListbyOutlier(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVar> selectVarListbyOtl(WadOtlDtc search);

	List<WadAnaVar> selectDaseColList(WadDataSet search);
}