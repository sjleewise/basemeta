package kr.wise.dq.stnd.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;


/**
 * 
 * 	GLOSSARY_SEQ		NUMBER(5)		용어순번 
	GLOSSARY_CLAS_NM	VARCHAR2(30)	Not Null	용어분류명 
	SLC_GLOSSARY_NM		VARCHAR2(50)	수도권매립지용어명 
	SLC_GLOSSARY_ENG_NM	VARCHAR2(100)	수도권매립지용어영문명 
	GLOSSARY_EXPL		VARCHAR2(4000)	용어설명 
	GLOSSARY_SRC_EXPL	VARCHAR2(200)	용어출처설명 
	REGST_DT			DATE			등록일자 
	REGSTPSN_ID			VARCHAR2(10)	등록자ID 
	UPD_DT				DATE			수정일자 
	UPDER_ID			VARCHAR2(10)	수정자ID 
 *
 */
public class WdqSlcGlossaryM extends CommonVo {
    private String glossaryClasNm;

    private Integer glossarySeq;

    private String slcGlossaryNm;

    private String slcGlossaryEngNm;

    private String glossaryExpl;

    private String glossarySrcExpl;

    private Date regstDt;

    private String regstpsnId;

    private Date updDt;

    private String upderId;

    public String getGlossaryClasNm() {
        return glossaryClasNm;
    }

    public void setGlossaryClasNm(String glossaryClasNm) {
        this.glossaryClasNm = glossaryClasNm;
    }

    public Integer getGlossarySeq() {
        return glossarySeq;
    }

    public void setGlossarySeq(Integer glossarySeq) {
        this.glossarySeq = glossarySeq;
    }

    public String getSlcGlossaryNm() {
        return slcGlossaryNm;
    }

    public void setSlcGlossaryNm(String slcGlossaryNm) {
        this.slcGlossaryNm = slcGlossaryNm;
    }

    public String getSlcGlossaryEngNm() {
        return slcGlossaryEngNm;
    }

    public void setSlcGlossaryEngNm(String slcGlossaryEngNm) {
        this.slcGlossaryEngNm = slcGlossaryEngNm;
    }

    public String getGlossaryExpl() {
        return glossaryExpl;
    }

    public void setGlossaryExpl(String glossaryExpl) {
        this.glossaryExpl = glossaryExpl;
    }

    public String getGlossarySrcExpl() {
        return glossarySrcExpl;
    }

    public void setGlossarySrcExpl(String glossarySrcExpl) {
        this.glossarySrcExpl = glossarySrcExpl;
    }

    public Date getRegstDt() {
        return regstDt;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setRegstDt(Date regstDt) {
        this.regstDt = regstDt;
    }

    public String getRegstpsnId() {
        return regstpsnId;
    }

    public void setRegstpsnId(String regstpsnId) {
        this.regstpsnId = regstpsnId;
    }

    public Date getUpdDt() {
        return updDt;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getUpderId() {
        return upderId;
    }

    public void setUpderId(String upderId) {
        this.upderId = upderId;
    }
}