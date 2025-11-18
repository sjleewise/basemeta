package kr.wise.commons.damgmt.approve.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaAprPrcMapper {
    int insert(WaaAprPrc record);

    int insertSelective(WaaAprPrc record);

	/** @param search
	/** @return insomnia */
	List<WaaAprPrc> selectapproveline(WaqMstr search);

	/** @param savevo
	/** @return insomnia */
	int insertapproveprocess(WaaAprPrc savevo);

	/** @param mstVo
	/** @return insomnia */
	int updateapproveprocessline(WaqMstr mstVo);

	int updateApproveProcess(WaaAprPrc aprvo);

	/** @param mstVo
	/** @return insomnia */
	int getcountapproveprocess(WaqMstr mstVo);

	/** @param mstVo insomnia
	 * @return */
	List<WaaAprPrc> getapproveprocess(WaqMstr mstVo);

	/** @param search
	/** @return insomnia */
	List<WaaAprPrc> selectapproveprocess(WaqMstr search);

	/** @param rqstNo
	/** @param userid
	/** @return insomnia */
	List<WaaAprPrc> selectcheckapproveuser(@Param("rqstNo") String rqstNo, @Param("cuserid") String userid);
}