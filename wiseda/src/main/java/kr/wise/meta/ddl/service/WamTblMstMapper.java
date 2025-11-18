package kr.wise.meta.ddl.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamTblMstMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 11. 17.
 * </PRE>
 */ 
@Mapper
public interface WamTblMstMapper {

    
    List<WamTblMst> selectTblMstList(WamTblMst record);
    
    WamTblMst selectTblMstByKey(String objId);
    
    int regTblMst(WamTblMst record);
    
    int updateTblMst(WamTblMst record);
    
    int delTblMst(WamTblMst record);
    
    WamTblMst checkDup(WamTblMst record);
}


