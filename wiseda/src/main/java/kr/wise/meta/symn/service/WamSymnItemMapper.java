package kr.wise.meta.symn.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.symn.service.WamSymnItem;
import kr.wise.meta.symn.service.WamSymnItemExample;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WamSymnItemMapper {
    long countByExample(WamSymnItemExample example);

    int deleteByExample(WamSymnItemExample example);

    int deleteByPrimaryKey(String symnItemId);

    int insert(WamSymnItem record);

    int insertSelective(WamSymnItem record);

    List<WamSymnItem> selectByExample(WamSymnItemExample example);

    WamSymnItem selectByPrimaryKey(String symnItemId);

    int updateByExampleSelective(@Param("record") WamSymnItem record, @Param("example") WamSymnItemExample example);

    int updateByExample(@Param("record") WamSymnItem record, @Param("example") WamSymnItemExample example);

    int updateByPrimaryKeySelective(WamSymnItem record);

    int updateByPrimaryKey(WamSymnItem record);

    /** 유사항목 WAM 조회 */
	List<WamSymnItem> selectList(WamSymnItem data);

	/** 유사항목 상세정보 */
	WamSymnItem selectSymnItemInfoDetail(String stwdId);

	/** 유사항목 변경이력 */
	List<WamSymnItem> selectSymnItemChangeList(String stwdId);
}