/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlScriptServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.script.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오후 5:29:18
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.script.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.boxplot.service.UodcBoxplotService;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplot;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotCol;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.UodcColDaseDvService;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDv;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvCol;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.UodcDaseImpService;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.UodcDaseRowDvService;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.WadUodcDaseRowDv;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.UodcDataClnService;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnCol;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.UodcDaseCndDvService;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCnd;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.UodcDaseJnService;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJn;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnCol;
import kr.wise.advisor.prepare.udefoutlier.elev.service.UodcElevService;
import kr.wise.advisor.prepare.udefoutlier.elev.service.WadUodcElev;
import kr.wise.advisor.prepare.udefoutlier.isolationforest.service.UodcIsfrService;
import kr.wise.advisor.prepare.udefoutlier.isolationforest.service.WadUodcIsfr;
import kr.wise.advisor.prepare.udefoutlier.lof.service.UodcLofService;
import kr.wise.advisor.prepare.udefoutlier.lof.service.WadUodcLof;
import kr.wise.advisor.prepare.udefoutlier.ocv.service.UodcOcvService;
import kr.wise.advisor.prepare.udefoutlier.ocv.service.WadUodcOcv;
import kr.wise.advisor.prepare.udefoutlier.rrgr.service.UodcRrgrService;
import kr.wise.advisor.prepare.udefoutlier.rrgr.service.WadUodcRrgr;
import kr.wise.advisor.prepare.udefoutlier.saveres.service.UodcSaveResService;
import kr.wise.advisor.prepare.udefoutlier.saveres.service.WadUodcSvRes;
import kr.wise.advisor.prepare.udefoutlier.script.LinkDataArray;
import kr.wise.advisor.prepare.udefoutlier.script.MdlJsonInf;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.script.WadUodLnk;
import kr.wise.advisor.prepare.udefoutlier.script.WadUodLnkMapper;
import kr.wise.advisor.prepare.udefoutlier.service.UdefOutlierService;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlScriptServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.script.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오후 5:29:18
 * </PRE>
 */
@Service("pythonScriptService")
public class PythonScriptServiceImpl implements PythonScriptService {  

	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Inject
	private UdefOutlierService udefOutlierService;

	@Inject
	private UodcDaseImpService uodcDaseImpService;  
	
	@Inject
	private UodcColDaseDvService uodcColDaseDvService;    
	
	@Inject
	private UodcDaseRowDvService uodcDaseRowDvService;      
	
	@Inject
	private UodcDaseCndDvService uodcDaseCndDvService;       
	
	@Inject
	private UodcDaseJnService uodcDaseJnService;
	
	@Inject
	private UodcDataClnService uodcDataClnService;
	
	@Inject
	private UodcBoxplotService uodcBoxplotService;
	
	@Inject
	private UodcIsfrService uodcIsfrService;  
	
	@Inject
	private UodcLofService uodcLofService;
	
	@Inject
	private UodcOcvService uodcOcvService;
	
	@Inject
	private UodcElevService uodcElevService;
	
	@Inject
	private UodcRrgrService uodcRrgrService;
	
	@Inject
	private UodcSaveResService uodcSaveResService;
	
	@Inject 
	private AnaTrgService anaTrgService;
	
	@Inject 
	private WadUodLnkMapper lnkMapper; 
	
	@Inject
	private MessageSource message;


	public int scriptFileCre(String udfOtlDtcId) throws Exception{ 
		
		int result = 0;
				
		String python_script_path  =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ "python.script.dir", null, Locale.getDefault());
		
		String pyFileNm = udfOtlDtcId + ".py";
		
		String filePath = python_script_path + "/" + pyFileNm; 
				
		File unfile = new File(filePath); 
		
		//======디렉토리 없을시 생성========== 
		File dir = new File(python_script_path);
		
		//디렉토리가 없으면 생성 
		if(!dir.exists()){ 
			
			logger.debug("\n 디렉토리 생성");
			dir.mkdir();
		}
		//====================================
		 
		FileWriter fw = new FileWriter(unfile, false);  //append 안항
		
		String pyScript = getUdefOutlierScript(udfOtlDtcId);
		
		//String pyScript = getUdefOutlierScriptNew(udfOtlDtcId);
		
		fw.write(pyScript);
		
		fw.flush();
		fw.close();
		
		result = 1;
		
		return result;
	}
	
	
	/** insomnia
	 * @throws IOException 
	 * @throws ParseException */
	public String getUdefOutlierScript(String udfOtlDtcId) throws Exception { 

		logger.debug("udefOtlDtcId:{}", udfOtlDtcId);

		String scrtTxt = "";
		String mdlJsonInf ="";
		
		WadUod srchUod = new WadUod();
		
		srchUod.setUdfOtlDtcId(udfOtlDtcId);
		
		//LINK 정보 INSERT
		WadUod uodVo = insertLinkInfo(udfOtlDtcId); 
		
		
		if(uodVo != null ){
			
			List<WadUodLnk> uodLnkLst = lnkMapper.selectUodLnkList(udfOtlDtcId); 
			
			scrtTxt = getPythonCommon();
			
			String sLstToKey = "";
			
			for(WadUodLnk linkVo : uodLnkLst) {
				
				String sFrKey = linkVo.getLnkFrom();
				sLstToKey = UtilString.null2Blank(linkVo.getLnkTo());
				
				WadUodCreComp tmpVo = new WadUodCreComp();
				
				tmpVo.setUdfOtlDtcId(udfOtlDtcId);
				tmpVo.setCreCompId(sFrKey);
				
				WadUodCreComp creComp = udefOutlierService.getUodCompForScrt(tmpVo);

				if(creComp != null){
					
					scrtTxt += getUodcPyScrt(creComp);
					
				}
			}
			
			if(!sLstToKey.equals("")) {
				
				WadUodCreComp tmpVo = new WadUodCreComp();
				
				tmpVo.setUdfOtlDtcId(udfOtlDtcId);
				tmpVo.setCreCompId(sLstToKey);
				
				WadUodCreComp creComp = udefOutlierService.getUodCompForScrt(tmpVo);
				
				if(creComp != null){
										
					scrtTxt += getUodcPyScrt(creComp);
					
				}
			}
		}

		return scrtTxt;
	}
	
	/** insomnia
	 * @throws IOException 
	 * @throws ParseException */
	public String getUdefOutlierScriptNew(String udfOtlDtcId) throws Exception { 

		logger.debug("udefOtlDtcId:{}", udfOtlDtcId);

		String scrtTxt = "";
		String mdlJsonInf ="";
		
		WadUod srchUod = new WadUod();
		
		srchUod.setUdfOtlDtcId(udfOtlDtcId);
		
		//LINK 정보 INSERT
		WadUod uodVo = insertLinkInfo(udfOtlDtcId); 
		
		
		if(uodVo != null ){
			
			List<WadUodLnk> uodLnkLst = lnkMapper.selectUodLnkCompList(udfOtlDtcId);
			
			//공통부 저장 
			scrtTxt = getPythonCommon(); 			
			
			for(WadUodLnk lnkVo: uodLnkLst){
				
				WadUodLnk lnkFromVo = lnkMapper.selectUodLnkCompFrom(lnkVo); 
				
				if(lnkFromVo != null){
					
					String sFrKey = lnkFromVo.getLnkFrom();
					//sLstToKey = UtilString.null2Blank(lnkFromVo.getLnkTo());
					
					WadUodCreComp tmpVo = new WadUodCreComp();
					
					tmpVo.setUdfOtlDtcId(udfOtlDtcId);
					tmpVo.setCreCompId(sFrKey);
					
					WadUodCreComp creComp = udefOutlierService.getUodCompForScrt(tmpVo);
					
					if(creComp != null){
											
						scrtTxt += getUodcPyScrt(creComp);
						
					}
				}
				
				WadUodLnk lnkToVo = lnkMapper.selectUodLnkCompTo(lnkFromVo); 
			}
						
			scrtTxt = getPythonCommon();
			
			String sLstToKey = "";
			
			for(WadUodLnk linkVo : uodLnkLst) {
				
				String sFrKey = linkVo.getLnkFrom();
				sLstToKey = UtilString.null2Blank(linkVo.getLnkTo());
				
				WadUodCreComp tmpVo = new WadUodCreComp();
				
				tmpVo.setUdfOtlDtcId(udfOtlDtcId);
				tmpVo.setCreCompId(sFrKey);
				
				WadUodCreComp creComp = udefOutlierService.getUodCompForScrt(tmpVo);
				
				if(creComp != null){
										
					scrtTxt += getUodcPyScrt(creComp);
					
				}
			}	
			
			if(!sLstToKey.equals("")) {
				
				WadUodCreComp tmpVo = new WadUodCreComp();
				
				tmpVo.setUdfOtlDtcId(udfOtlDtcId);
				tmpVo.setCreCompId(sLstToKey);
				
				WadUodCreComp creComp = udefOutlierService.getUodCompForScrt(tmpVo);
				
				if(creComp != null){
										
					scrtTxt += getUodcPyScrt(creComp);
					
				}
			}
		}

		return scrtTxt;
	}
	
	public WadUod insertLinkInfo(String udfOtlDtcId) throws Exception{
		
		String scrtTxt = "";
		String mdlJsonInf ="";
		
		WadUod srchUod = new WadUod();
		
		srchUod.setUdfOtlDtcId(udfOtlDtcId);
		
		//==============LINK 정보 INSERT==================
		WadUod uodVo = udefOutlierService.getWadUodSelectDetail(srchUod);
		
		if(uodVo != null){
			
			mdlJsonInf = uodVo.getMdlJsonInf();
			
			String lstChr = mdlJsonInf.substring(mdlJsonInf.length()-1,mdlJsonInf.length());
		    
			if(lstChr.equals(",")) {
				
				mdlJsonInf = mdlJsonInf.substring(0, mdlJsonInf.length()-1);
			}
			
			mdlJsonInf = mdlJsonInf.replace("class", "name");
						
			JSONParser jsonParser = new JSONParser();  
			
			JSONObject objSrc = (JSONObject) jsonParser.parse(mdlJsonInf);    
			
			ObjectMapper mapper = new ObjectMapper(); 
			
			MdlJsonInf jsonInf =  mapper.convertValue(objSrc,  new TypeReference<MdlJsonInf>(){} );
			
			//=========WAD_UOD_LNK INSERT======
			int iRtn = 0;
			int lnkSno = 0;
			
			iRtn = lnkMapper.deleteByPrimaryKey(udfOtlDtcId);   
			
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			
			for(LinkDataArray linkVo : jsonInf.getLinkDataArray()) {
				
				String sFrKey = linkVo.getFrom();
				String sToKey = linkVo.getTo();
				
				lnkSno++;
				
				WadUodLnk insVo = new WadUodLnk();  
				
				insVo.setUdfOtlDtcId(udfOtlDtcId);
				insVo.setLnkSno(lnkSno); 
				insVo.setLnkFrom(sFrKey);
				insVo.setLnkTo(sToKey);
				insVo.setWritUserId(userid);
				
				iRtn = lnkMapper.insertSelective(insVo);  
				
			}
			//====================================						
		}
		
		return uodVo;
	}
	
	
	private String getUodcPyScrt(WadUodCreComp creCompVo){
		
		String scrtTxt = "";
		
		String compDcd = UtilString.null2Blank(creCompVo.getCompDcd());
		
		//Data Import
		if(compDcd.equals("DataImp")){
									
			scrtTxt += getPythonDataImp(creCompVo);
		}else if(compDcd.equals("SubsetRow")){
			
			scrtTxt += getPythonSubsetRow(creCompVo);	
		}else if(compDcd.equals("SubsetCol")){
			
			scrtTxt += getPythonSubsetCol(creCompVo);
		}else if(compDcd.equals("Join")){
			
			scrtTxt += getPythonJoin(creCompVo);	
		}else if(compDcd.equals("Filter")){
			
			scrtTxt += getPythonFilter(creCompVo);													
		}else if(compDcd.equals("Cleansing")){
			
			scrtTxt += getPythonCleansing(creCompVo);
		}else if(compDcd.equals("ODBP")){
			
			scrtTxt += getPythonBoxPlot(creCompVo);
		}else if(compDcd.equals("ODIsFr")){
			
			scrtTxt += getPythonIsFr(creCompVo);
		}else if(compDcd.equals("ODLOF")){
			
			scrtTxt += getPythonLof(creCompVo);
		}else if(compDcd.equals("ODOCV")){
			
			scrtTxt += getPythonOcv(creCompVo);
		}else if(compDcd.equals("ODELEV")){
			
			scrtTxt += getPythonElev(creCompVo);
		}else if(compDcd.equals("RRGR")){
			
			scrtTxt += getPythonRrgr(creCompVo);
		}else if(compDcd.equals("SaveRes")){
			
			scrtTxt += getPythonSaveRes(creCompVo);			
		}
		
		return scrtTxt;
	}
		

	
	public String getPythonCommon(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("import pandas as pd                                                    \n");
		sb.append("import numpy as np                                                     \n");
		sb.append("import math                                                            \n");
        sb.append("                                                                       \n");
        sb.append("import matplotlib.pyplot as plt                                        \n");
        sb.append("plt.switch_backend('agg')   #Lx                                        \n"); //리눅스 환경인 경우 추가
		sb.append("from matplotlib.pyplot import boxplot, hist, figure                    \n");
        sb.append("                                                                       \n");
		sb.append("from matplotlib import font_manager, rc                                \n"); 
		sb.append("import seaborn as sns                                                  \n");
		sb.append("sns.set()                                                              \n");
		sb.append("plt.rcParams['axes.unicode_minus'] = False                             \n");
		sb.append("import platform                                                        \n");
		sb.append("if platform.system() == 'Darwin':                                      \n");
		sb.append("    rc('font', family='AppleGothic')                                   \n");
		sb.append("elif platform.system() == 'Windows':                                   \n");
		sb.append("    path = 'c:/Windows/Fonts/malgun.ttf'                               \n");
		sb.append("    font_name = font_manager.FontProperties(fname=path).get_name()     \n");
		sb.append("    rc('font',family=font_name)                                        \n");
        sb.append("                                                                       \n");
		sb.append("import cx_Oracle                                                       \n");
		sb.append("import pyodbc                                                          \n");
		sb.append("#import pymssql                                                        \n");
		sb.append("from sqlalchemy import create_engine                                   \n");
		sb.append("from sklearn.utils import shuffle                                      \n");
        sb.append("from pyhive import hive                                                \n");
		sb.append("from sklearn.covariance import EllipticEnvelope                        \n");
		sb.append("from sklearn.ensemble import IsolationForest                           \n");
		sb.append("from sklearn.svm import OneClassSVM                                    \n"); 
		sb.append("from sklearn.neighbors import LocalOutlierFactor                       \n");
		sb.append("from sklearn.linear_model import RANSACRegressor                       \n");
        sb.append("                                                                       \n");
		sb.append("import json                                                            \n");
		sb.append("from collections import OrderedDict                                    \n");
		
		return sb.toString(); 		
	}


	public String getPythonDataImp(WadUodCreComp creCompVo){
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcDaseImp search = new WadUodcDaseImp(); 
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcDaseImpCol> daseImpLst =  uodcDaseImpService.gectDaseImpColForScrt(search);
		
		String connStr = ""; 
		String dbcTblNm = "";
		String dbcColNm = "";
		String anaDaseNm = "";
		WadUodcDaseImp dcd = null;
		
		for(WadUodcDaseImpCol vo : daseImpLst){
					
			String dbConnTrgId = UtilString.null2Blank(vo.getDbConnTrgId());
			
			dcd = uodcDaseImpService.getUodcDaseImpDataDcd(vo);
			
			if(dcd.getImpDataDcd().equals("T") || dcd.getImpDataDcd().equals("H")) {
				WaaDbConnTrgVO tgtdb = anaTrgService.selectAnaTrgDbmsDetail(dbConnTrgId);
				connStr = getTgtdbStr(tgtdb);
			}
			
			dbcTblNm = UtilString.null2Blank(vo.getDbSchPnm()).toLowerCase() + "." + UtilString.null2Blank(vo.getDbcTblNm()).toLowerCase(); 
			
			dbcColNm += ",'" + UtilString.null2Blank(vo.getColPnm()).toLowerCase()  + "'";
			
			anaDaseNm = UtilString.null2Blank(vo.getAnaDaseNm());
		}
		
		if(dcd.getImpDataDcd().equals("T")) {
			dbcColNm = dbcColNm.substring(1, dbcColNm.length());
					
			sb.append("\n");
			sb.append("ls=1000000                                         \n");
			sb.append("connInfo='" + connStr +"'                          \n");	
			sb.append("tableName='"+ dbcTblNm +"'                         \n");
			sb.append("                                                   \n");
			sb.append("colName = ["+ dbcColNm +"]                         \n");
			sb.append("colName = [x.lower() for x in colName]             \n");
			sb.append("str_col = ','.join(colName)                        \n"); 
			sb.append("engine = create_engine(connInfo)                   \n");
			sb.append("conn = engine.connect()                            \n");
			sb.append("q = 'select {} from {}'.format(str_col, tableName) \n");
			sb.append("" + anaDaseNm + " = pd.read_sql(q,conn)            \n"); 
			sb.append("                                                   \n");
		} else if(dcd.getImpDataDcd().equals("H")) {
			dbcColNm = dbcColNm.substring(1, dbcColNm.length());
					
			sb.append("\n");
			sb.append("ls=1000000                                         \n");
			sb.append("connInfo='" + connStr +"'                          \n");	
			sb.append("tableName='"+ dbcTblNm +"'                         \n");
			sb.append("                                                   \n");
			sb.append("colName = ["+ dbcColNm +"]                         \n");
			sb.append("colName = [x.lower() for x in colName]             \n");
			sb.append("str_col = ','.join(colName)                        \n"); 
			sb.append("engine = create_engine(connInfo, connect_args={'auth':'NOSASL'}) \n");
			sb.append("conn = engine.connect()                            \n");
			sb.append("q = 'select {} from {}'.format(str_col, tableName) \n");
			sb.append("" + anaDaseNm + " = pd.read_sql(q,conn)            \n"); 
			sb.append("                                                   \n");
		} else {
			sb.append("\n");
			sb.append("ls=1000000                                         \n");
			sb.append("" + anaDaseNm + " = pd.read_csv('" + daseImpLst.get(0).getFilePath() + "',encoding='utf-8')    \n");
			sb.append("" + anaDaseNm + ".columns = [x.lower() for x in " + anaDaseNm + ".columns]                     \n");
			sb.append("                                                   \n");
		}

		
		return sb.toString(); 		
	}
	
	private String getPythonSubsetRow(WadUodCreComp creCompVo) {
		StringBuffer sb = new StringBuffer();
		
		WadUodcDaseRowDv search = new WadUodcDaseRowDv(); 
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		WadUodcDaseRowDv rowVo =  uodcDaseRowDvService.getUodcDaseRowDvDetail(search);
		
		if(rowVo != null){
			
			String rowSltTypCd  = UtilString.null2Blank(rowVo.getRowSltTypCd()); 
			String srcAnaDaseNm = UtilString.null2Blank(rowVo.getSrcAnaDaseNm());
			String anaDaseNm    = UtilString.null2Blank(rowVo.getAnaDaseNm());
			String rdmSmplTypCd = UtilString.null2Blank(rowVo.getRdmSmplTypCd());
			String rdmSmplCnt   = UtilString.null2Blank(rowVo.getRdmSmplCnt());
			String rdmSmplRate  = UtilString.null2Blank(rowVo.getRdmSmplRate()); 
			String rdmValFix    = UtilString.null2Blank(rowVo.getRdmValFix());
			
			String rdmState = "";
			
			if(rdmValFix.equals("1")){
				
				rdmState = ", random_state=42";
			}
			
			String sctStrVal = UtilString.null2Blank(rowVo.getSctStrVal());
			String sctEndVal = UtilString.null2Blank(rowVo.getSctEndVal());
			
			String sctVal = sctStrVal + ":" + sctEndVal; 
			
			if(rowSltTypCd.equals("S")){
				if(rdmSmplTypCd.equals("C")){
					sb.append("\n"+ anaDaseNm +" = "+ srcAnaDaseNm +".sample("+ rdmSmplCnt + rdmState + ").sort_index(axis=0) ");
				}else{
					sb.append("\n"+ anaDaseNm +" = "+ srcAnaDaseNm +".sample(frac="+ rdmSmplRate + rdmState + ").sort_index(axis=0) ");
				} 				
			}else{
				sb.append("\n"+ anaDaseNm +" = "+ srcAnaDaseNm +".loc["+ sctVal +"+1] ");
			}
			
			sb.append("\n "); 
		}
				
		return sb.toString();
	}

	
	private String getPythonSubsetCol(WadUodCreComp creCompVo) {
		StringBuffer sb = new StringBuffer();
		
		WadUodcColDaseDv search = new WadUodcColDaseDv(); 
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcColDaseDvCol> colDaseDvLst =  uodcColDaseDvService.getUodcColDaseDvColLstForScrt(search);
				
		String dbcColNm = "";
		String anaDaseNm = "";
		String srcAnaDaseNm = "";
		
		for(WadUodcColDaseDvCol vo : colDaseDvLst){
								
			dbcColNm += ",'" + UtilString.null2Blank(vo.getColPnm()).toLowerCase()  + "'";
			
			anaDaseNm    = UtilString.null2Blank(vo.getAnaDaseNm());
			srcAnaDaseNm = UtilString.null2Blank(vo.getSrcAnaDaseNm());
		}
		
		dbcColNm = dbcColNm.substring(1, dbcColNm.length());
				
		sb.append("\n");
		sb.append("\n"+ anaDaseNm +" = "+ srcAnaDaseNm +"[ [" + dbcColNm + "]]  ");			
		sb.append("\n");

		
		return sb.toString(); 		
	}
	


	private String getPythonFilter(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcDaseCndDv search = new WadUodcDaseCndDv();  
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcDaseCndDvCnd> cndDvLst = uodcDaseCndDvService.getUodcDaseCndDvCndForScrt(search); 
		
		String cndConts     = "";
		String anaDaseNm    = ""; 
		String srcAnaDaseNm = "";
		
		String colPnm = "";
		String cndCd = "";
		String oprCd = "";
		String cndVal = "";
		
		String sCndTemp = "";
		
		int iCnt = 0;
		
		for(WadUodcDaseCndDvCnd cndDvVo : cndDvLst){
			
			cndConts     = UtilString.null2Blank(cndDvVo.getCndConts());
			anaDaseNm    = UtilString.null2Blank(cndDvVo.getAnaDaseNm()); 
			srcAnaDaseNm = UtilString.null2Blank(cndDvVo.getSrcAnaDaseNm());
			
			colPnm = UtilString.null2Blank(cndDvVo.getColPnm()).toLowerCase(); 
			cndCd  = UtilString.null2Blank(cndDvVo.getCndCd());
			oprCd  = UtilString.null2Blank(cndDvVo.getOprCd());
			cndVal = UtilString.null2Blank(cndDvVo.getCndVal());
				
			if(iCnt == 0) {
				sCndTemp += "(" + srcAnaDaseNm + "['" + colPnm + "']" + oprCd + " " + cndVal + ")";
			}else{
				if(cndCd.equals("AND")){
					sCndTemp += " & (" + srcAnaDaseNm + "['" + colPnm + "']" + oprCd + " " + cndVal + ")";
				}else{
					sCndTemp += " | (" + srcAnaDaseNm + "['" + colPnm + "']" + oprCd + " " + cndVal + ")";
				} 				
			}
			
			iCnt++;
		}
		
		sb.append("\n"+ anaDaseNm +" = "+ srcAnaDaseNm +".loc[" + sCndTemp + "] "); 
						
		return sb.toString(); 
	}


	private String getPythonJoin(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcDaseJn search = new WadUodcDaseJn();   
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcDaseJnCol> jnColLst =  uodcDaseJnService.getUodcDaseJnColLstForScrt(search);   
		
		String leftAnaDaseNm  = "";
		String rightAnaDaseNm = "";
		String anaDaseNm      = "";
		String jnTypNm        = "";
		String leftColPnm     = "";
		String rightColPnm    = "";
		
		for(WadUodcDaseJnCol jnColVo: jnColLst) {
			
			leftAnaDaseNm  = UtilString.null2Blank(jnColVo.getLeftAnaDaseNm());
			rightAnaDaseNm = UtilString.null2Blank(jnColVo.getRightAnaDaseNm());
			anaDaseNm      = UtilString.null2Blank(jnColVo.getAnaDaseNm());
			jnTypNm        = UtilString.null2Blank(jnColVo.getJnTypNm());
			
			leftColPnm  += ",'" + UtilString.null2Blank(jnColVo.getLeftColPnm().toLowerCase()) + "'";
			rightColPnm += ",'" + UtilString.null2Blank(jnColVo.getRightColPnm().toLowerCase()) + "'";
		}
		
		leftColPnm  = leftColPnm.substring(1,leftColPnm.length());
		rightColPnm = rightColPnm.substring(1,rightColPnm.length());
		
		
		sb.append("\n");
		sb.append("\n"+ anaDaseNm +" = pd.merge("+ leftAnaDaseNm +", "+ rightAnaDaseNm +", how = '"+ jnTypNm +"', left_on = ["+ leftColPnm +"], right_on=["+ rightColPnm +"]) "); 
		sb.append("\n");
		
		return sb.toString(); 
	}
	

	private String getPythonCleansing(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		//WadUodcDataCln search = new WadUodcDataCln();
		WadUodcDataClnCol search2 = new WadUodcDataClnCol();
		
		//search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		//search.setCreCompId(creCompVo.getCreCompId());
		
		search2.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search2.setCreCompId(creCompVo.getCreCompId());
		
		//WadUodcDataCln clnVo =  uodcDataClnService.getDataClnDetail(search);
		List<WadUodcDataClnCol> clnColVo = uodcDataClnService.getUodcDataClnColList2(search2);
			
		String anaDaseNm   = "";
		String resPrcDcd   = "";
		String srcAnaDaseNm = "";
		
		/*if(clnVo != null) {
			
			 anaDaseNm = UtilString.null2Blank(clnVo.getAnaDaseNm());
			 resPrcDcd = UtilString.null2Blank(clnVo.getResPrcDcd());
			 srcAnaDaseNm = UtilString.null2Blank(clnVo.getSrcAnaDaseNm());
			 
			 sb.append("\n  ");
			 
			 if(resPrcDcd.equals("01")){
				 sb.append("\n"+ anaDaseNm +"= "+ srcAnaDaseNm + ".dropna()  "); 
			 }else if(resPrcDcd.equals("02")){
				 sb.append("\n"+ anaDaseNm +"= "+ srcAnaDaseNm + ".fillna(0) "); 
			 }else if(resPrcDcd.equals("03")){
				 sb.append("\n"+ anaDaseNm +"= "+ srcAnaDaseNm + ".fillna(df.mean()) "); 
			 }else if(resPrcDcd.equals("04")){
				 sb.append("\n"+ anaDaseNm +"= "+ srcAnaDaseNm + ".fillna(df.median()) "); 
			 }
				
			 sb.append("\n  ");			
		}*/
		
		if(clnColVo != null) {
			anaDaseNm = UtilString.null2Blank(clnColVo.get(0).getAnaDaseNm());
			srcAnaDaseNm = UtilString.null2Blank(clnColVo.get(0).getSrcAnaDaseNm());
			int cnt = 0;
			
			for(int i=0; i<clnColVo.size(); i++) {
				String colPnm = UtilString.null2Blank(clnColVo.get(i).getColPnm().toLowerCase());
				resPrcDcd = UtilString.null2Blank(clnColVo.get(i).getResPrcDcd());
				if(resPrcDcd.equals("01")){
					 cnt++;
				 }else if(resPrcDcd.equals("02")){
					 sb.append("\n"+ srcAnaDaseNm +"['" + colPnm + "']= "+ srcAnaDaseNm + "['" + colPnm + "'].fillna(0) "); 
				 }else if(resPrcDcd.equals("03")){
					 sb.append("\n"+ srcAnaDaseNm +"['" + colPnm + "']= "+ srcAnaDaseNm + "['" + colPnm + "'].fillna("+ srcAnaDaseNm + "['" + colPnm + "'].mean()) ");
				 }else if(resPrcDcd.equals("04")){
					 sb.append("\n"+ srcAnaDaseNm +"['" + colPnm + "']= "+ srcAnaDaseNm + "['" + colPnm + "'].fillna("+ srcAnaDaseNm + "['" + colPnm + "'].median()) ");
				 }
			}
			
			if(cnt > 0) {
				sb.append("\n"+ srcAnaDaseNm +"= "+ srcAnaDaseNm + ".dropna()  "); 
			}
			
			sb.append("\n"+ anaDaseNm +"= "+ srcAnaDaseNm + ".copy()  ");
			sb.append("\n  ");
		}
				
		return sb.toString(); 
	}
	
	private String getPythonBoxPlot(WadUodCreComp creCompVo) {
		StringBuffer sb = new StringBuffer();
		
		WadUodcBoxplot search = new WadUodcBoxplot();    
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcBoxplotCol> boxLst =  uodcBoxplotService.getUodcBoxplotColListForScrt(search);  
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm   = "";
		String otlColPnm   = "";
		
		for(WadUodcBoxplotCol bxcolVo : boxLst) {
			
			anaDaseNm    = UtilString.null2Blank(bxcolVo.getAnaDaseNm());
			srcAnaDaseNm = UtilString.null2Blank(bxcolVo.getSrcAnaDaseNm());
			
			colPnm    += ",'" + UtilString.null2Blank(bxcolVo.getColPnm()).toLowerCase() + "'";
			otlColPnm += ",'" + UtilString.null2Blank(bxcolVo.getOtlColPnm()) + "'";
		}
		
		colPnm    = colPnm.substring(1,colPnm.length());
		otlColPnm = otlColPnm.substring(1,otlColPnm.length());
		
		sb.append("\n");   
		/*sb.append("TEMP_DT = pd.DataFrame("+ srcAnaDaseNm +"["+ colPnm +"])                         \n"); 
		sb.append("BOX = boxplot(TEMP_DT["+ colPnm +"].tolist())                                    \n");
		sb.append("OUT_DT= BOX['fliers'][0].get_data()[1].tolist()                                  \n");
		sb.append("TEMP_DT["+ otlColPnm +"]=1                                                       \n");
		sb.append("for i in range(len(OUT_DT)):                                                     \n");
		sb.append("	TEMP_DT.loc[TEMP_DT["+ colPnm +"] == OUT_DT[i], "+ otlColPnm +"]=-1             \n");
		sb.append(""+ anaDaseNm +"= TEMP_DT.copy()                                                  \n");*/

		/*2018.06.26 
		스크립트 수정*/
		String sLocale = message.getMessage("mode", null, Locale.getDefault());
		String pyImgPath = message.getMessage(sLocale + ".python.img", null, Locale.getDefault());

		sb.append("imgName = '" + pyImgPath + "/" + udfOtlDtcId + ".png'						\n");
		sb.append("fig = plt.figure(1, figsize=(10,8))										\n");
		sb.append("ax = fig.add_subplot(111)												\n");
		sb.append("																			\n");
		sb.append("TEMP_DT = pd.DataFrame(" + srcAnaDaseNm + "[" + colPnm + "])				\n");
		sb.append("BOX = ax.boxplot(TEMP_DT[" + colPnm + "].tolist())						\n");
		sb.append("OUT_DT= BOX['fliers'][0].get_data()[1].tolist()							\n");
		sb.append("TEMP_DT[" + otlColPnm + "]=1												\n");
		sb.append("for i in range(len(OUT_DT)):												\n");
		sb.append("	TEMP_DT.loc[TEMP_DT[" + colPnm + "] == OUT_DT[i], " + otlColPnm + "]=-1	\n");
		sb.append("																			\n");
		sb.append(""+ anaDaseNm +"= TEMP_DT.copy()											\n");
		sb.append("fig.savefig(imgName, bbox_inches='tight')								\n");
		sb.append("\n");
		
		return sb.toString(); 
	}


	/*2016.06.26
	스크립트 수정*/
	private String getCommonScrt(String udfOtlDtcId, String anaDaseNm, String colPnm, String srcAnaDaseNm, String otlColPnm) {
		String sLocale = message.getMessage("mode", null, Locale.getDefault());
		String pyImgPath = message.getMessage(sLocale + ".python.img", null, Locale.getDefault());
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("imgName = '" + pyImgPath + "/" + udfOtlDtcId + ".png'																											\n");
		sb.append("colName = [" + colPnm + "]																																	\n");
		sb.append("script=[]																																					\n");
		sb.append("for i in " + srcAnaDaseNm + ".index:																															\n");
		sb.append("    temp={}																																					\n");
		sb.append("    if " + srcAnaDaseNm + "[" + otlColPnm + "][i] == -1:																										\n");
		sb.append("        script.append('Outlier')																																\n");
		sb.append("    else:																																					\n");
		sb.append("        script.append('Inlier')																																\n");
		sb.append("" + srcAnaDaseNm + "['Outlier']=script																														\n");
		sb.append("del script																																					\n");
		sb.append("																																								\n");
		
		sb.append("h_order = ['Inlier', 'Outlier']                                                                                                                              \n");
		sb.append("color_custom = [ '#3498db','#e74c3c']                                                                                                                        \n");
		sb.append("set_custom = dict(marker=['^', 'v'],alpha=[0.3,0.5],s=[10,20])                                                                                               \n");
		sb.append("g = sns.PairGrid(" + srcAnaDaseNm + ",vars=colName, hue='Outlier', palette= color_custom,hue_order=h_order, hue_kws=set_custom)                              \n");
		//sb.append("color_custom = [ '#e74c3c','#3498db']																														\n");
		//sb.append("g = sns.PairGrid(" + srcAnaDaseNm + ",vars=colName, hue='Outlier', palette= color_custom, hue_kws=dict(marker=['v','^'],alpha=[0.3,0.3],s=[20,10]))			\n");
		//sb.append("color_custom = [ '#3498db','#e74c3c']																														\n");
		//sb.append("g = sns.PairGrid(" + srcAnaDaseNm + ",vars=colName, hue='Outlier', palette= color_custom, hue_kws=dict(marker=['^','v'],alpha=[0.3,0.3],s=[10,20]))			\n");
		sb.append("g = g.map_diag(plt.hist, lw=0.1, edgecolor='w')																												\n");
		sb.append("g = g.map_offdiag(plt.scatter, s=20, edgecolor='w', lw=0.1)																									\n");
		sb.append("																																								\n");
		sb.append("g = g.add_legend()																																			\n");
		sb.append("g = g.fig.set_size_inches(3*len(colName), 3*len(colName))																									\n");
		sb.append("																																								\n");
		sb.append("plt.savefig(imgName)																																			\n");
		sb.append("																																								\n");
		sb.append("corr = " + srcAnaDaseNm + "[colName].corr()																													\n");
		sb.append("f, ax = plt.subplots(figsize=(2*len(colName), 1.7*len(colName)))																								\n");
		sb.append("cmap = sns.color_palette('Blues')																															\n");
		sb.append("h = sns.heatmap(corr,cmap=cmap, vmax=1, vmin=-1,center=0,linewidths=.3,annot=True, annot_kws={'size':20},fmt='.2f')											\n");
		sb.append("																																								\n");
		sb.append("plt.yticks(rotation=0)																																		\n");
		sb.append("imgName2= imgName.split('.')[-2]+'_cor.'+imgName.split('.')[-1]																								\n");
		sb.append("plt.savefig(imgName2, bbox_inches='tight')																													\n");

		return sb.toString();
	}
	
	private String getPythonIsFr(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcIsfr search = new WadUodcIsfr();     
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcIsfr> isfrLst =  uodcIsfrService.getUodcIsfrColLstForScrt(search);
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm      = "";
		String otlColPnm   = "";
		String otlRt       = "";
		
		for(WadUodcIsfr colVo: isfrLst) {
			
			anaDaseNm    = UtilString.null2Blank(colVo.getAnaDaseNm()) ;
			srcAnaDaseNm = UtilString.null2Blank(colVo.getSrcAnaDaseNm()) ;
			
			otlRt =  UtilString.null2Blank(colVo.getOtlRt()) ;
			
			colPnm    += ",'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
			otlColPnm = "'" + UtilString.null2Blank(colVo.getOtlColPnm()).toLowerCase() + "'";
		}
		
		colPnm    = colPnm.substring(1,colPnm.length());		
		
		sb.append("\n");            
		
		sb.append(""+ srcAnaDaseNm +" = "+ srcAnaDaseNm +"[["+  colPnm +"]]                 \n");
		
		//2019.01.29
		//대용량 처리 스크립트 수정
		sb.append("if len(" + srcAnaDaseNm+ ") > ls:                           \n");
		sb.append("    " + srcAnaDaseNm + " = shuffle(" + srcAnaDaseNm + ", random_state = 40)    \n");
		sb.append("    " + srcAnaDaseNm + "[" + otlColPnm + "] =0                                 \n");
		sb.append("    for i in range(math.ceil(len(" + srcAnaDaseNm + ")/ls)):                   \n");
		sb.append("        if i == 0:                                                             \n");
		sb.append("            clf=IsolationForest(contamination=" + otlRt + ",n_jobs=-1).fit(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][:ls]) \n");
		sb.append("            " + srcAnaDaseNm + "[" + otlColPnm + "][:ls]=clf.predict(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][:ls])       \n");
		sb.append("        elif i == (math.ceil(len(" + srcAnaDaseNm + ")/ls)-1):                 \n");
		sb.append("            " + srcAnaDaseNm + "[" + otlColPnm + "][(ls*i):]=clf.predict(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][(ls*i):]) \n");
		sb.append("        else:                                                                  \n");
		sb.append("            " + srcAnaDaseNm + "[" + otlColPnm + "][(ls*i):(ls*(i+1))]=clf.predict(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][(ls*i):(ls*(i+1))])  \n");
		sb.append("    " + srcAnaDaseNm + " = " + srcAnaDaseNm + ".sort_index()                   \n");
		sb.append("else:                                                                          \n");
		sb.append("    clf=IsolationForest(contamination=" + otlRt + ").fit(" + srcAnaDaseNm + ") \n");
		sb.append("    " + srcAnaDaseNm + "[" + otlColPnm + "] =clf.predict(" + srcAnaDaseNm + ") \n");
		
        /*sb.append("clf=IsolationForest (contamination="+ otlRt +").fit("+ srcAnaDaseNm +")  \n");
        sb.append(""+ srcAnaDaseNm +"["+ otlColPnm +"] = clf.predict("+ srcAnaDaseNm +")    \n");*/
        
        sb.append(""+ anaDaseNm +" = "+ srcAnaDaseNm +".copy()                              \n");
        
        sb.append(getCommonScrt(udfOtlDtcId, anaDaseNm, colPnm, srcAnaDaseNm, otlColPnm));

		sb.append("\n");                                                                                 
				
		return sb.toString(); 
	}

	private String getPythonLof(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcLof search = new WadUodcLof();     
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcLof> lofLst =  uodcLofService.getUodcLofColLstForScrt(search);
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm      = "";
		String otlColPnm   = "";
		String otlRt       = "";
		String nghbCnt	   = "";
		
		for(WadUodcLof colVo: lofLst) {
			
			anaDaseNm    = UtilString.null2Blank(colVo.getAnaDaseNm()) ;
			srcAnaDaseNm = UtilString.null2Blank(colVo.getSrcAnaDaseNm()) ;
			
			otlRt =  UtilString.null2Blank(colVo.getOtlRt()) ;
			nghbCnt = UtilString.null2Blank(colVo.getNghbCnt());
			
			colPnm    += ",'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
			otlColPnm = "'" + UtilString.null2Blank(colVo.getOtlColPnm()).toLowerCase() + "'";
		}
		
		colPnm    = colPnm.substring(1,colPnm.length());		
		
		sb.append("\n");            
		
		sb.append(""+ srcAnaDaseNm +" = "+ srcAnaDaseNm +"[["+  colPnm +"]]                 									\n");
		
		//2019.01.29
		//대용량 처리 스크립트 수정
		sb.append("if len(" + srcAnaDaseNm + ") > ls:                                                     \n");
		sb.append("    " + srcAnaDaseNm + " = shuffle(" + srcAnaDaseNm + ", random_state = 40)            \n");
		sb.append("    " + srcAnaDaseNm + "["+ otlColPnm +"] =0                                           \n");
		sb.append("    for i in range(math.ceil(len(" + srcAnaDaseNm + ")/ls)):                           \n");
        sb.append("        if i == (math.ceil(len(" + srcAnaDaseNm + ")/ls)-1):                           \n");
        sb.append("            " + srcAnaDaseNm + "["+ otlColPnm +"][(ls*i):] = LocalOutlierFactor(contamination="+ otlRt +",n_neighbors=" + nghbCnt + ",n_jobs=-1).fit_predict(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][(ls*i):]) \n");
        sb.append("        else:                                                                          \n");
        sb.append("            " + srcAnaDaseNm + "["+ otlColPnm +"][(ls*i):(ls*(i+1))] = LocalOutlierFactor(contamination="+ otlRt +",n_neighbors=" + nghbCnt + ",n_jobs=-1).fit_predict(" + srcAnaDaseNm + "[" + srcAnaDaseNm + ".columns[:-1]][(ls*i):(ls*(i+1))]) \n");
        sb.append("    " + srcAnaDaseNm + " = " + srcAnaDaseNm + ".sort_index()                           \n");
        sb.append("else:                                                                                  \n");
        sb.append("    " + srcAnaDaseNm + "["+ otlColPnm +"] = LocalOutlierFactor(contamination="+ otlRt +",n_neighbors=" + nghbCnt + ",n_jobs=-1).fit_predict("+ srcAnaDaseNm +")  \n");
        
		/*sb.append(""+ srcAnaDaseNm +"["+  otlColPnm +"]=LocalOutlierFactor(contamination="+ otlRt +",n_neighbors=" + nghbCnt + ").fit_predict("+ srcAnaDaseNm +")  \n");*/
	    
        sb.append(""+ anaDaseNm +" = "+ srcAnaDaseNm +".copy()                              									\n");
	    sb.append(getCommonScrt(udfOtlDtcId, anaDaseNm, colPnm, srcAnaDaseNm, otlColPnm));
	    
		sb.append("\n");                                                                                 
				
		return sb.toString(); 
	}
	
	private String getPythonOcv(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcOcv search = new WadUodcOcv();     
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcOcv> isfrLst =  uodcOcvService.getUodcOcvColLstForScrt(search);
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm      = "";
		String otlColPnm   = "";
		String otlRt       = "";
		
		for(WadUodcOcv colVo: isfrLst) {
			
			anaDaseNm    = UtilString.null2Blank(colVo.getAnaDaseNm()) ;
			srcAnaDaseNm = UtilString.null2Blank(colVo.getSrcAnaDaseNm()) ;
			
			otlRt =  UtilString.null2Blank(colVo.getOtlRt()) ;
			
			colPnm    += ",'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
			otlColPnm = "'" + UtilString.null2Blank(colVo.getOtlColPnm()).toLowerCase() + "'";
		}
		
		colPnm    = colPnm.substring(1,colPnm.length());		
		
		sb.append("\n");            
		
		sb.append(""+ srcAnaDaseNm +" = "+ srcAnaDaseNm +"[["+  colPnm +"]]                 \n");
        sb.append("clf=OneClassSVM (nu="+ otlRt +").fit("+ srcAnaDaseNm +")      			\n");
        sb.append(""+ srcAnaDaseNm +"["+ otlColPnm +"] = clf.predict("+ srcAnaDaseNm +")    \n");
        sb.append(""+ anaDaseNm +" = "+ srcAnaDaseNm +".copy()                              \n");
        sb.append(getCommonScrt(udfOtlDtcId, anaDaseNm, colPnm, srcAnaDaseNm, otlColPnm));
        
		sb.append("\n");                                                                                 
				
		return sb.toString(); 
	}
	
	private String getPythonElev(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcElev search = new WadUodcElev();     
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcElev> isfrLst =  uodcElevService.getUodcElevColLstForScrt(search);
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm      = "";
		String otlColPnm   = "";
		String otlRt       = "";
		
		for(WadUodcElev colVo: isfrLst) {
			
			anaDaseNm    = UtilString.null2Blank(colVo.getAnaDaseNm()) ;
			srcAnaDaseNm = UtilString.null2Blank(colVo.getSrcAnaDaseNm()) ;
			
			otlRt =  UtilString.null2Blank(colVo.getOtlRt()) ;
			
			colPnm    += ",'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
			otlColPnm = "'" + UtilString.null2Blank(colVo.getOtlColPnm()).toLowerCase() + "'";
		}
		
		colPnm    = colPnm.substring(1,colPnm.length());		
		
		sb.append("\n");            
		
		sb.append(""+ srcAnaDaseNm +" = "+ srcAnaDaseNm +"[["+  colPnm +"]]                 \n");
		
		//2019.01.29
		//대용량 처리 스크립트 수정
		sb.append("if len("+ srcAnaDaseNm +") > ls:                                            \n");
        sb.append("    "+ srcAnaDaseNm +" = shuffle("+ srcAnaDaseNm +", random_state = 40)     \n");
        sb.append("    "+ srcAnaDaseNm +"["+ otlColPnm +"] =0                                  \n");
        sb.append("    for i in range(math.ceil(len("+ srcAnaDaseNm +")/ls)):                  \n");
        sb.append("        if i == 0:                                                          \n");
        sb.append("            clf= EllipticEnvelope(contamination="+ otlRt +",n_jobs=-1).fit("+ srcAnaDaseNm +"["+ srcAnaDaseNm +".columns[:-1]][:ls]) \n");
        sb.append("            "+ srcAnaDaseNm +"["+ otlColPnm +"][:ls]=clf.predict("+ srcAnaDaseNm +"["+ srcAnaDaseNm +".columns[:-1]][:ls])           \n");
        sb.append("        elif i == (math.ceil(len("+ srcAnaDaseNm +")/ls)-1):                \n");
        sb.append("            "+ srcAnaDaseNm +"["+ otlColPnm +"][(ls*i):]=clf.predict("+ srcAnaDaseNm +"["+ srcAnaDaseNm +".columns[:-1]][(ls*i):])   \n");
        sb.append("        else:                                                               \n");
        sb.append("            "+ srcAnaDaseNm +"["+ otlColPnm +"][(ls*i):(ls*(i+1))]=clf.predict("+ srcAnaDaseNm +"["+ srcAnaDaseNm +".columns[:-1]][(ls*i):(ls*(i+1))]) \n");
        sb.append("    "+ srcAnaDaseNm +" = "+ srcAnaDaseNm +".sort_index()                    \n");
        sb.append("else:                                                                       \n");
        sb.append("    clf=EllipticEnvelope(contamination="+ otlRt +").fit("+ srcAnaDaseNm +") \n");
        sb.append("    "+ srcAnaDaseNm +"["+ otlColPnm +"] =clf.predict("+ srcAnaDaseNm +")    \n");
		
        /*sb.append("clf=EllipticEnvelope (contamination="+ otlRt +").fit("+ srcAnaDaseNm +") \n");
        sb.append(""+ srcAnaDaseNm +"["+ otlColPnm +"] = clf.predict("+ srcAnaDaseNm +")    \n");*/
        
        sb.append(""+ anaDaseNm +" = "+ srcAnaDaseNm +".copy()                              \n");
        sb.append(getCommonScrt(udfOtlDtcId, anaDaseNm, colPnm, srcAnaDaseNm, otlColPnm));
        
		sb.append("\n");                                                                                 
				
		return sb.toString(); 
	}

	//2019.01.31
	//Robust Regression 기능 추가
	private String getPythonRrgr(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcRrgr search = new WadUodcRrgr();     
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		List<WadUodcRrgr> rrgrLst =  uodcRrgrService.getUodcRrgrColLstForScrt(search);
		
		String udfOtlDtcId = search.getUdfOtlDtcId();
		String anaDaseNm   = "";		
		String srcAnaDaseNm = "";
		String colPnm1      = "";
		String colPnm2      = "";
		String otlColPnm   = "";
		

		WadUodcRrgr colVo = rrgrLst.get(0);
		
		anaDaseNm    = UtilString.null2Blank(colVo.getAnaDaseNm()) ;
		srcAnaDaseNm = UtilString.null2Blank(colVo.getSrcAnaDaseNm()) ;
		
		colPnm1    = "'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
		otlColPnm = "'" + UtilString.null2Blank(colVo.getOtlColPnm()).toLowerCase() + "'";
		
		colVo = rrgrLst.get(1);
		
		colPnm2    = "'" + UtilString.null2Blank(colVo.getColPnm()).toLowerCase() + "'";
		
		sb.append("\n");
		
		sb.append("ransac = RANSACRegressor()								                                   \n");
		sb.append(""+ srcAnaDaseNm +" = shuffle("+ srcAnaDaseNm +", random_state = 40)				           \n");
		sb.append(""+ srcAnaDaseNm +" ["+ otlColPnm +"]=-1							                           \n");
		sb.append("X = np.array(list("+ srcAnaDaseNm +"["+ colPnm1 +"])).reshape(len("+ srcAnaDaseNm +"),1)	   \n");
		sb.append("Y = np.array(list("+ srcAnaDaseNm +"["+ colPnm2 +"])).reshape(len("+ srcAnaDaseNm +"),1)	   \n");
		sb.append("inlier_mask=[]									                                           \n");
		sb.append("outlier_mask=[]									                                           \n");
		sb.append("											                                                   \n");
		sb.append("if len("+ srcAnaDaseNm +") > ls:      							                           \n");
		sb.append("    for i in range(math.ceil(len("+ srcAnaDaseNm +")/ls)):					               \n");
		sb.append("        if i == 0:									                                       \n");
		sb.append("            ransac.fit(X[:ls], Y[:ls])						                               \n");
		sb.append("            line_y_ransac = ransac.predict(X[:ls])        				                   \n");
		sb.append("            "+ srcAnaDaseNm +".loc["+ srcAnaDaseNm +"[:ls].index[ransac.inlier_mask_],"+ otlColPnm +"]=1		           \n");
		sb.append("											                                                   \n");
		sb.append("        elif i == (math.ceil(len("+ srcAnaDaseNm +")/ls)-1):					               \n");
		sb.append("            ransac.fit(X[ls*i:], Y[ls*i:])						                           \n");
		sb.append("            line_y_ransac = ransac.predict(X[ls*i:])					                       \n");
		sb.append("            "+ srcAnaDaseNm +".loc["+ srcAnaDaseNm +"[ls*i:].index[ransac.inlier_mask_],"+ otlColPnm +"]=1	           \n");
		sb.append("															                                   \n");
		sb.append("        else:													                           \n");
		sb.append("            ransac.fit(X[(ls*i):(ls*(i+1))], Y[(ls*i):(ls*(i+1))])						   \n");
		sb.append("            line_y_ransac = ransac.predict(X[(ls*i):(ls*(i+1))])							   \n");
		sb.append("            "+ srcAnaDaseNm +".loc["+ srcAnaDaseNm +"[(ls*i):(ls*(i+1))].index[ransac.inlier_mask_],"+ otlColPnm +"]=1  \n");
		sb.append("															                                   \n");
		sb.append("        inlier_mask.extend( list(ransac.inlier_mask_) )								       \n");
		sb.append("        outlier_mask.extend( list(np.logical_not(ransac.inlier_mask_)) )					   \n");
		sb.append("															                                   \n");
		sb.append("else:														                               \n");
		sb.append("    ransac.fit(X, Y)													                       \n");
		sb.append("    line_y_ransac = ransac.predict(X)										               \n");
		sb.append("    inlier_mask.extend( list(ransac.inlier_mask_) )									       \n");
		sb.append("    outlier_mask.extend( list(np.logical_not(inlier_mask)) )								   \n");
		sb.append("    "+ srcAnaDaseNm +".loc[inlier_mask,"+ otlColPnm +"]=1								   \n");
		sb.append("															   								   \n");
		sb.append(""+ srcAnaDaseNm +" = "+ srcAnaDaseNm +".sort_index()								           \n");
		
		sb.append(""+ anaDaseNm +" = "+ srcAnaDaseNm +".copy()                                                 \n");
        sb.append(getCommonScrt(udfOtlDtcId, anaDaseNm, colPnm1 + "," + colPnm2, srcAnaDaseNm, otlColPnm));
        
		sb.append("\n");                                                                                 
				
		return sb.toString(); 
	}

	private String getPythonSaveRes(WadUodCreComp creCompVo) {
		
		StringBuffer sb = new StringBuffer();
		
		WadUodcSvRes search = new WadUodcSvRes();      
		
		search.setUdfOtlDtcId(creCompVo.getUdfOtlDtcId());
		search.setCreCompId(creCompVo.getCreCompId());
		
		WadUodcSvRes svresVo =  uodcSaveResService.getUodcSaveResForScrt(search);
		
		String udfOtlDtcId = "";	
		String anaDaseNm   = "";		
				
		if(svresVo != null) {
			
			String sLocale = message.getMessage("mode", null, Locale.getDefault()) ;
								
			String pyCsvPath = message.getMessage(sLocale + ".python.csv.dir", null, Locale.getDefault()); 
			
			udfOtlDtcId = UtilString.null2Blank(svresVo.getUdfOtlDtcId());
			anaDaseNm   = UtilString.null2Blank(svresVo.getAnaDaseNm()); 
			
			String csvFileNm = pyCsvPath + "/"+ udfOtlDtcId + ".csv";
			
			sb.append(anaDaseNm + ".to_csv('" +  csvFileNm + "', index=None)   \n");
		}
		
		return sb.toString(); 		
	}

	
	public static String getTgtdbStr(WaaDbConnTrgVO tgtdb) {  
		String pydburl = "";
		String dbType = tgtdb.getDbmsTypCd();
		int idx = tgtdb.getConnTrgLnkUrl().indexOf("@");
		int lidx = tgtdb.getConnTrgLnkUrl().lastIndexOf(":");
		String ipAddr = tgtdb.getConnTrgLnkUrl().substring(idx+1, lidx);
		
		if ("ORA".equals(dbType)) {
			//오라클일 경우...  "oracle+cx_oracle://wiseda:wise1012@fds"
			//pydburl = "oracle+cx_oracle://" + tgtdb.getDbConnAcId() + ":"+tgtdb.getDbConnAcPwd() +"@"+tgtdb.getConnTrgDbLnkChrw();
			
			//리눅스 환경인 경우 아래 형식으로 connect
			pydburl = "oracle://" + tgtdb.getDbConnAcId() + ":"+tgtdb.getDbConnAcPwd()+"@"+ipAddr+"/"+tgtdb.getConnTrgDbLnkChrw();
			
		}else if ("MSQ".equals(dbType)) { 
			//MS-SQLSERVER
			pydburl = "mssql+pyodbc://" + tgtdb.getDbConnAcId() + ":"+tgtdb.getDbConnAcPwd()+"@"+ tgtdb.getDbLnkSts() +"/"+ tgtdb.getDbConnTrgPnm() +"?driver=ODBC+Driver+13+for+SQL+Server";
		}else if ("HIV".equals(dbType)) { 
			//Hive
			int i = ipAddr.lastIndexOf("/");
			pydburl = "hive://" + ipAddr.substring(i+1) + "/"+ tgtdb.getConnTrgDbLnkChrw();
		}
		
		return pydburl;
	}

	
}
