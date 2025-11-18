/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : TextMatchServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.textcluster.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 8. 오후 5:02:04
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 8. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotCol;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotColMapper;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotMapper;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDv;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvColMapper;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpColMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpMapper;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.WadUodcDaseRowDvMapper;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnCol;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnColMapper;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnMapper;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCndMapper;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvMapper;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnColMapper;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnMapper;
import kr.wise.advisor.prepare.udefoutlier.elev.service.WadUodcElevMapper;
import kr.wise.advisor.prepare.udefoutlier.isolationforest.service.WadUodcIsfrMapper;
import kr.wise.advisor.prepare.udefoutlier.lof.service.WadUodcLofMapper;
import kr.wise.advisor.prepare.udefoutlier.ocv.service.WadUodcOcvMapper;
import kr.wise.advisor.prepare.udefoutlier.saveres.service.WadUodcSvResMapper;
import kr.wise.advisor.prepare.udefoutlier.script.WadUodLnkMapper;
import kr.wise.advisor.prepare.udefoutlier.service.UdefOutlierService;
import kr.wise.advisor.prepare.udefoutlier.service.WadErrData;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreCompMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.advisor.prepare.udefoutlier.usrdef.service.WadUodcUsrdefMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : TextMatchServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 5:02:04
 * </PRE>
 */
@Service("udefOutlierService")
public class UdefOutlierServiceImpl implements  UdefOutlierService { 

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private EgovIdGnrService requestIdGnrService; 
	
	@Inject
	private WadUodMapper mapper;
	
	@Inject
	private WadUodCreCompMapper wadCreCompMapper; 
	
	@Inject
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper;
	
	@Inject
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper; 
	
	@Inject
	private WadUodcDaseImpMapper wadUodcDaseImpMapper; 

	@Inject
	private WadUodcDaseImpColMapper wadUodcDaseImpColMapper;   
	
	@Inject
	private WadUodcColDaseDvMapper wucddm;
	
	@Inject
	private WadUodcColDaseDvColMapper wucddcm;
	
	@Inject
	private WadUodcDaseRowDvMapper wudrdm;
	
	@Inject
	private WadUodcDaseJnMapper wudjm;
	
	@Inject
	private WadUodcDaseJnColMapper wudjcm;
	
	@Inject
	private WadUodcDaseCndDvMapper wudcdm;
	
	@Inject
	private WadUodcDaseCndDvCndMapper wudcdcm;
	
	@Inject
	private WadUodcDataClnMapper wudcm;
	
	@Inject
	private WadUodcDataClnColMapper wudccm;
	
	@Inject
	private WadUodcBoxplotMapper wubm;
	
	@Inject
	private WadUodcBoxplotColMapper wubcm;
	
	@Inject
	private WadUodcIsfrMapper wuim;
	
	@Inject
	private WadUodcLofMapper wulm;
	
	@Inject
	private WadUodcOcvMapper wuom;
	
	@Inject
	private WadUodcElevMapper wuem;
	
	@Inject
	private WadUodcUsrdefMapper wuum;
	
	@Inject
	private WadUodcSvResMapper wusrm;
	
	@Inject
	private WadUodLnkMapper wadUodLnkMapper;
	 
	@Override 
	public List<WadUod> getUdefOutlierList(WadUod search) {
		
		return mapper.selectUdefOutlierList(search);
	}

	@Override
	public int delUdfOtlDtcList(List<WadUod> list) { 

		int result = 0;

		for(WadUod delvo : list){
			
			String udfOtlDtcId = delvo.getUdfOtlDtcId();
			
			//WAD_UDOC_ANA_DASE_COL  DELETE
			result += wadUodcAnaDaseColMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UDOC_ANA_DASE DELETE 
			result += wadUodcAnaDaseMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UDOC_DASE_IMP_COL  DELETE 
			result += wadUodcDaseImpColMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UDOC_DASE_IMP  DELETE 
			result += wadUodcDaseImpMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_DASE_ROW_DV
			result += wudrdm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_COL_DASE_DV_COL
			result += wucddcm.delteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UODC_COL_DASE_DV
			result += wucddm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_DASE_JN_COL
			result += wudjcm.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UODC_DASE_JN
			result += wudjm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_DASE_CND_DV_CND
			result += wudcdcm.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UODC_DASE_CND_DV
			result += wudcdm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_DATA_CLN_COL
			result += wudccm.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UODC_DATA_CLN
			result += wudcm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_BOXPLOT_COL
			result += wubcm.deleteByUdfOtlDtcId(udfOtlDtcId);
			//WAD_UODC_BOXPLOT
			result += wubm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_ISL_FRST
			result += wuim.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_LCL_OTL_FCTR
			result += wulm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_ONE_CLSS_SVM
			result += wuom.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_ELLPT_ENVLP
			result += wuem.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_USR_DEF_MDL
			result += wuum.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UODC_SV_RES
			result += wusrm.deleteByUdfOtlDtcId(udfOtlDtcId);
			
			//WAD_UOD_LNK
			result += wadUodLnkMapper.deleteByPrimaryKey(udfOtlDtcId);
			
			result += wadCreCompMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
		
			result += mapper.deleteByPrimaryKey(udfOtlDtcId);
		}

		return result;
	}

	@Override
	public WadUod getWadUodSelectDetail(WadUod uodVo) {  
		
		String udfOtlDtcId = UtilString.null2Blank(uodVo.getUdfOtlDtcId());
		
		return mapper.selectByPrimaryKey(udfOtlDtcId);
	}

	@Override 
	public List<WadUodcAnaDase> getAnaDaseId(WadUodcAnaDase search) {

		return wadUodcAnaDaseMapper.selectAnaDaseIdByFk(search); 
	}
	
	@Override 
	public List<WadUodcAnaDase> getAnaDaseIdSaveres(WadUodcAnaDase search) {

		return wadUodcAnaDaseMapper.selectAnaDaseIdSaveres(search); 
	}

	@Override
	public WadUodCreComp getUodCompForScrt(WadUodCreComp tmpVo) { 
		// TODO Auto-generated method stub
		return wadCreCompMapper.selectUodCompForScrt(tmpVo);
	}

	@Override
	public int delComp(WadUodCreComp param) {
		// TODO Auto-generated method stub
		int result = 0;
		
		String udfOtlDtcId = param.getUdfOtlDtcId();
		String creCompId = param.getCreCompId();
		
    	if(param.getCreCompNm().equals("DataImp")) {
    		//WAD_UDOC_DASE_IMP  DELETE 
			result += wadUodcDaseImpMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
			
			WadUodcDaseImp wudi = new WadUodcDaseImp();
			wudi.setUdfOtlDtcId(udfOtlDtcId);
			wudi.setCreCompId(creCompId);
			
			//WAD_UDOC_DASE_IMP_COL  DELETE 
			result += wadUodcDaseImpColMapper.deleteCreCompId(wudi);
    		
    	} else if(param.getCreCompNm().equals("SubsetCol")) {
    		result += wucddm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    		WadUodcColDaseDv wucdd = new WadUodcColDaseDv();
    		wucdd.setUdfOtlDtcId(udfOtlDtcId);
    		wucdd.setCreCompId(creCompId);
    		
    		result += wucddcm.deleteCreCompId(wucdd);
    		
    	} else if(param.getCreCompNm().equals("SubsetRow")) {
    		result += wudrdm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("Join")) {
    		result += wudjm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		result += wudjcm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("Filter")) {
    		result += wudcdcm.deleteCreCompId(udfOtlDtcId, creCompId);
    		result += wudcdm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("Cleansing")) {
    		result += wudcm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    				
    		WadUodcDataClnCol wudcc = new WadUodcDataClnCol();
    		wudcc.setUdfOtlDtcId(udfOtlDtcId);
    		wudcc.setCreCompId(creCompId);
    		
    		result += wudccm.deleteDataClnCol(wudcc);
    		
    	} else if(param.getCreCompNm().equals("ODBP")) {
    		result += wubm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    				
    		WadUodcBoxplotCol wubc = new WadUodcBoxplotCol();
    		wubc.setUdfOtlDtcId(udfOtlDtcId);
    		wubc.setCreCompId(creCompId);
    		
    		result += wubcm.deleteBoxplotCol(wubc);
    		
    	} else if(param.getCreCompNm().equals("ODIsFr")) {
    		result += wuim.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("ODLOF")) {
    		result += wulm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("ODOCV")) {
    		result += wuom.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("ODELEV")) {
    		result += wuem.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("UsrDef")) {
    		result += wuum.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    		
    	} else if(param.getCreCompNm().equals("SaveRes")) {
    		result += wusrm.deleteByPrimaryKey(udfOtlDtcId, creCompId);
    	}
    	
    	//WAD_UDOC_ANA_DASE DELETE 
		//result += wadUodcAnaDaseMapper.deleteByUdfOtlDtcId(udfOtlDtcId);
		
		result += wadUodcAnaDaseMapper.deleteByCreCompId(param);
		
		//WAD_UDOC_ANA_DASE_COL  DELETE 
		WadUodcAnaDase delvo = new WadUodcAnaDase() ;
		delvo.setUdfOtlDtcId(udfOtlDtcId);
		delvo.setCreCompId(creCompId);
		
		result += wadUodcAnaDaseColMapper.deleteAnaDaseColJnCol(delvo);
		
		result += wadCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		return result;
	}

	@Override
	public int updUod(WadUod data) {
		// TODO Auto-generated method stub
		return mapper.updateMdlScrtJson(data);
	}
	
	/** insomnia */
	public List<WadErrData> getResultData(WadErrData pattvo) {
		
		return mapper.selectResultDataList(pattvo);
	}

	@Override
	public List<WadErrData> getResultViewColNm(WadErrData search) {
		// TODO Auto-generated method stub
		return mapper.selectResViewColNm(search);
	}

	@Override
	public int updateOtlYn(List<WadErrData> list) {
		// TODO Auto-generated method stub
		int result = 0;

		for (WadErrData savevo : list) {
			result =+ mapper.updateOtlYn(savevo);
		}
		
		return result;
	}

	@Override
	public int updateOtlRpl(WadErrData data) {
		// TODO Auto-generated method stub
		int res = mapper.updateOtlRpl(data);
		
		res = mapper.deleteOtlDataRpl(data);

		if(data.getOtlRpl() == null || data.getOtlRpl().equals(""))
			return res;	

		data.setOtlRplVal1("CRE_COMP_ID, UDF_OTL_DTC_ID, ANA_STR_DTM, ER_DATA_SNO, ANA_DGR, COL_CNT, OTL_YN, OTL_RPL");
		data.setOtlRplVal2("A.CRE_COMP_ID, A.UDF_OTL_DTC_ID, A.ANA_STR_DTM, A.ER_DATA_SNO, A.ANA_DGR, A.COL_CNT, A.OTL_YN, A.OTL_RPL");
		
		String str1 = "";
		String str2 = "";
		String str3 = "";
		
		if("01".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MAX(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MAX(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("02".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MIN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MIN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("03".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", 'INVALID'";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
		} else if("04".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", 'DEL'";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
		} else if("05".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "STATS_MODE(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "STATS_MODE(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("06".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "AVG(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "AVG(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("07".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MEDIAN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MEDIAN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		}

		res = mapper.insertOtlDataRpl(data);

		return res;
	}

}
