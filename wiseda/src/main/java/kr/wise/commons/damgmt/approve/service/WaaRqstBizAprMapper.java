package kr.wise.commons.damgmt.approve.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaRqstBizAprMapper {
    int deleteByPrimaryKey(@Param("bizDcd") String bizDcd, @Param("aprLvl") Short aprLvl, @Param("sysDcd") String sysDcd);

    int insert(WaaRqstBizApr record);

    int insertSelective(WaaRqstBizApr record);

    WaaRqstBizApr selectByPrimaryKey(@Param("bizDcd") String bizDcd, @Param("aprLvl") Short aprLvl, @Param("sysDcd") String sysDcd);

    int updateByPrimaryKeySelective(WaaRqstBizApr record);

    int updateByPrimaryKey(WaaRqstBizApr record);

	/** @return insomnia */
	List<WaaRqstBizApr> selectAprvLineList(WaaRqstBizApr search);

	/** @return insomnia */
	int insertApproveLine(WaaRqstBizApr savevo);

	/** @return insomnia */
	int updateApproveLine(WaaRqstBizApr savevo);

	/** @return insomnia */
	int deleteApproveLine(WaaRqstBizApr savevo);

	int updateApproveLineAs(WaaRqstBizApr savevo);
}