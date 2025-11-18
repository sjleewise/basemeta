package kr.wise.meta.intf.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface EraScrnItmMapper {

	List<EraScrnItm> selectEraScrnItmOne(@Param("vcb") String vcb, @Param("langCode") String langCode);
	List<EraScrnItm> selectEraScrnItm(@Param("vcb") String vcb,@Param("vcbId") String vcbId, @Param("langCode") String langCode );
}