package kr.wise.commons.code.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ComCodeDetailMapper {
    int deleteByPrimaryKey(@Param("codeId") String codeId, @Param("code") String code);

    int insert(ComCodeDetailVO record);

    int insertSelective(ComCodeDetailVO record);

    ComCodeDetailVO selectByPrimaryKey(@Param("codeId") String codeId, @Param("code") String code);

    int updateByPrimaryKeySelective(ComCodeDetailVO record);

    int updateByPrimaryKey(ComCodeDetailVO record);

	List<ComCodeDetailVO> selectComDetailCodeList(ComCodeDetailVO searchVO);

	int deleteComDetailCOde(Map<String, String[]> param);
}