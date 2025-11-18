package kr.wise.meta.intf.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface EraDataelmtMapper {

	EraDataelmt selectEraDataelmtOne(@Param("searchVal") String searchVal
                                   , @Param("searchModel") String searchModelnm, @Param("searchSubject") String searchSubjectnm );
	List<EraDataelmt> selectEraDataelmt(@Param("searchVal") String searchVal,@Param("searchCon") String searchCon
			                          , @Param("searchModel") String searchModelnm, @Param("searchSubject") String searchSubjectnm );
	List<EraDataelmt> selectEraDataelmtEn(@Param("searchVal") String searchVal,@Param("searchCon") String searchCon
                                        , @Param("searchModel") String searchModelnm, @Param("searchSubject") String searchSubjectnm );
}










