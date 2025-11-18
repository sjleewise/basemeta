package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.meta.ddl.service.WamDdlSeq;

@Mapper
public interface WamDdlSeqMapper {
    int deleteByPrimaryKey(String ddlSeqId);

    int insert(WamDdlSeq record);

    int insertSelective(WamDdlSeq record);

    WamDdlSeq selectByPrimaryKey(String ddlSeqId);

    int updateByPrimaryKeySelective(WamDdlSeq record);

    int updateByPrimaryKeyWithBLOBs(WamDdlSeq record);

    int updateByPrimaryKey(WamDdlSeq record);
    
    List<WamDdlSeq> selectWamSeqList(WamDdlSeq search);
    
    WamDdlSeq selectWamSeqListById(@Param("ddlSeqId")String ddlSeqId, @Param("rqstNo")String rqstNo);
    
    List<WamDdlSeq> selectSeqChangeList(WamDdlSeq search);
    
    int updateDdlSeqRqstPrc(WamDdlTbl record);

}