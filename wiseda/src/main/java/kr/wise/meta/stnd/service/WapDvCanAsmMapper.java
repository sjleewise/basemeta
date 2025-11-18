package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WapDvCanAsmMapper {

    int insertFirst(WapDvCanDic record);
    
    int insertDvListFirst(WapDvCanAsm record);

    int insertAsmDmn(String dvRqstNo);

    int insertAsmDic(String dvRqstNo);

    int insertAsmNotExistDic(String dvRqstNo);

    int deleteDmnAsm(String dvRqstNo);
    
    int deleteExistDmnAsm(String dvRqstNo);

    int deleteNotEndPrcAsmDic(String dvRqstNo);

    int updateNotEndPrcAsmDic(String dvRqstNo);

    int deleteDvCanAsmByDvRqstNo(String dvRqstNo);

    int deleteDvCanAsmByDup(String dvRqstNo);
    
    int deleteDvCanAsmByDvOrderBy(WapDvCanDic record);

    List<WapDvCanAsm> selectList(String dvRqstNo);
    
    List<WapDvCanAsm> selectItemDvRqstList(WapDvCanDic record);
    
    List<WapDvCanAsm> selectDmnDvRqstList(WapDvCanDic record);

	/** @param rqstno
	/** @param dvrqstno
	/** @param sditmLnm insomnia */
	int insertFirstList(@Param("rqstNo") String rqstno, @Param("rqstNo") String dvrqstno, String sditmLnm);

	/** @param dvrqstno
	/** @return insomnia */
	List<WapDvCanAsm> selectItemDivList(String dvrqstno);
	
	int delItemAutoDiv(WapDvCanAsm record);
	
	int delDmnAutoDiv(WapDvCanAsm record);
	
	int insertFirstApp(WapDvCanDic record);
	
    int insertAsmAppDic(String dvRqstNo);
    
    int insertAsmNotExistAppDic(String dvRqstNo);
    
    int deleteNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateUnderbar(String dvRqstNo);
}