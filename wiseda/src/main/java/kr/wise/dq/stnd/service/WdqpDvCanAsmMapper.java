package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WdqpDvCanAsmMapper {

    int insertFirst(WdqpDvCanDic record);
    
    int insertDvListFirst(WdqpDvCanAsm record);

    int insertAsmDmn(String dvRqstNo);

    int insertAsmDic(String dvRqstNo);

    int insertAsmNotExistDic(String dvRqstNo);

    int deleteDmnAsm(String dvRqstNo);
    
    int deleteExistDmnAsm(String dvRqstNo);

    int deleteNotEndPrcAsmDic(String dvRqstNo);

    int updateNotEndPrcAsmDic(String dvRqstNo);

    int deleteDvCanAsmByDvRqstNo(String dvRqstNo);

    int deleteDvCanAsmByDup(String dvRqstNo);
    
    int deleteDvCanAsmByDvOrderBy(WdqpDvCanDic record);

    List<WdqpDvCanAsm> selectList(String dvRqstNo);
    
    List<WdqpDvCanAsm> selectItemDvRqstList(WdqpDvCanDic record);
    
    List<WdqpDvCanAsm> selectDmnDvRqstList(WdqpDvCanDic record);

	/** @param rqstno
	/** @param dvrqstno
	/** @param sditmLnm insomnia */
	int insertFirstList(@Param("rqstNo") String rqstno, @Param("rqstNo") String dvrqstno, String sditmLnm);

	/** @param dvrqstno
	/** @return insomnia */
	List<WdqpDvCanAsm> selectItemDivList(String dvrqstno);
	
	int delItemAutoDiv(WdqpDvCanAsm record);
	
	int delDmnAutoDiv(WdqpDvCanAsm record);
	
	int insertFirstApp(WdqpDvCanDic record);
	
    int insertAsmAppDic(String dvRqstNo);
    
    int insertAsmNotExistAppDic(String dvRqstNo);
    
    int deleteNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateUnderbar(String dvRqstNo);
}