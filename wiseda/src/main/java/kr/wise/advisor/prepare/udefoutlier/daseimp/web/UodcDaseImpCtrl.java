/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierDetectCtrl.java
 * 2. Package : kr.wise.advisor.prepare.outlier.web
 * 3. Comment : 테이상값 탐지 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:21:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.daseimp.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.UodcDaseImpService;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * <PRE>
 * 1. ClassName : OutlierDetectCtrl
 * 2. FileName  : OutlierDetectCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.web
 * 4. Comment  : 이상값 탐지 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class UodcDaseImpCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService; 
	
	@Inject
    private EgovIdGnrService uodIdGnrService;  
	
	@Inject
	private UodcDaseImpService uodcDaseImpService;  
	
	
	static class WadUodcDaseImpCols extends HashMap<String, ArrayList<WadUodcDaseImpCol>> {}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSchHive   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchIdHive"));
		codeMap.put("connTrgSchHive", connTrgSchHive);

		
		return codeMap;
	}
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpDetail.do")
	@ResponseBody
	public WadUodcDaseImp getUodcDaseImpDetail(WadUodcDaseImp search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId());
		
		WadUodcDaseImp daseImpVo = uodcDaseImpService.getUodcDaseImpDataDcd(search);
		
		search.setImpDataDcd(daseImpVo.getImpDataDcd());
		
		daseImpVo =  uodcDaseImpService.getUodcDaseImpDetail(search);
		
		return daseImpVo; 
	}
	
	
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcDaseImpCol> getUodcDaseImpList(WadUodcDaseImp search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId()); 
		
		List<WadUodcDaseImpCol> list =  uodcDaseImpService.getUodcDaseImpColList(search);
		
		return new IBSheetListVO<WadUodcDaseImpCol>(list, list.size()); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpHiveColList.do")
	@ResponseBody
	public IBSheetListVO<WadUodcDaseImpCol> getUodcDaseImpHiveList(WadUodcDaseImp search) {
		
		logger.debug("\n dbSchId:" + search.getDbSchId()); 
		
		List<WadUodcDaseImpCol> list =  uodcDaseImpService.getUodcDaseImpHiveColList(search);
		
		return new IBSheetListVO<WadUodcDaseImpCol>(list, list.size()); 
	}
	
	 
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/regDataImptlist.do")
	@ResponseBody
	public IBSResultVO<WadUodcDaseImp> regDataImptlist(@RequestBody WadUodcDaseImpCols data,  WadUodcDaseImp mstData , Locale locale) throws Exception { 
		
		//logger.debug("division:{}", data);
		int result ;
		
    	List<WadUodcDaseImpCol> list = data.get("data"); 
    	
    	logger.debug("\n list[0] >>> " + list.get(0).toString());
    	logger.debug("\n mstData:" + mstData.getUdfOtlDtcId()); 
    	
    	//데이터임포트 등록 
    	result = uodcDaseImpService.regDataImptlist(list, mstData);  
    
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUodcDaseImp>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/regDataImpFile.do")
	public Object regDataImpFile(MultipartHttpServletRequest multi, WadUodcDaseImp mstData, Locale locale) throws Exception { 
		
		logger.debug("multi:{}", multi);
		logger.debug("mstData : {}", mstData.toString());
		logger.debug("anaDaseNm >>> " + mstData.getAnaDaseNm());
		
		// 저장 경로 설정
        String root = multi.getSession().getServletContext().getRealPath("/");
        root = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ "python.csv.dir", null, Locale.getDefault());
        //root = "C:/ide/tmp/";
        logger.debug("root >>> " + root);

        String path = root;

        String newFileName = ""; // 업로드 되는 파일명

        File dir = new File(path);

        if(!dir.isDirectory()){
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while(files.hasNext()){
            String uploadFile = files.next();

            MultipartFile mFile = multi.getFile(uploadFile);

            String fileName = mFile.getOriginalFilename();

            System.out.println("실제 파일 이름 : " +fileName);

            newFileName = System.currentTimeMillis()+"."
                    +fileName.substring(fileName.lastIndexOf(".")+1);
            
            mstData.setFilePath(path + "/" + newFileName);
            mstData.setFileName(fileName);
            
            try {
                mFile.transferTo(new File(path + "/" + newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        ArrayList<String> al = null;
        al = new ArrayList<String>();
        
        try{
        	File csv = new File(path + "/" + newFileName);
        	BufferedReader br = null;
        	br = new BufferedReader(new FileReader(csv));
            String line = "";
            int row =0 ,i;
            
            if((line = br.readLine()) != null) {
	            String[] token = null;
	            token = line.split(",", -1);
	            
	            for(i=0;i<token.length;i++) {
	             al.add(token[i]);
	            }
	            
	            // CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
	            /*for(i=0;i<token.length;i++) {
	             System.out.println(al.get(i) + ",");
	            }*/
            }
 
            br.close();
        } catch(Exception e) {
        	logger.debug(e.toString());
        }

        List<WadUodcDaseImpCol> list = null;
        list = new ArrayList<WadUodcDaseImpCol>();
        
        for(int i=0; i<al.size(); i++) {
        	WadUodcDaseImpCol colVal = null;
        	colVal = new WadUodcDaseImpCol();
        	colVal.setColPnm(al.get(i));
        	colVal.setColLnm(al.get(i));
        	colVal.setIbsCheck("1");
        	
        	list.add(colVal);
        	
        	logger.debug(colVal.getColPnm());
        }
        
        int result = 0;
        
        //데이터임포트 등록 
    	result = uodcDaseImpService.regDataImptlist(list, mstData);  
    
    	if(result > 0 ){
			result = 0;
		} else {
			result = -1;
		}
        
    	return result;
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/daseimp/regDataImpHadoopFile.do")
	public Object regDataImpHadoopFile(WadUodcDaseImp mstData, Locale locale) throws Exception { 
		
		//logger.debug("multi:{}", multi);
		logger.debug("mstData : {}", mstData.toString());
		logger.debug("anaDaseNm >>> " + mstData.getAnaDaseNm());
		
		
		Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://169.56.81.7:8089");
        
        FileSystem dfs = null;
        
        try {
            dfs = FileSystem.get(configuration);
            System.out.println("Home Path : " + dfs.getHomeDirectory());
            System.out.println("Work Path : " + dfs.getWorkingDirectory());
            
            Path filenamePath = new Path("/wise/TB_SIDO_ATMOS_POLUTN_MESURE_L.csv");
            System.out.println("File Exists : " + dfs.exists(filenamePath));
            
            /*if(dfs.exists(filenamePath)) {
             dfs.delete(filenamePath, true);
            }*/
            
           /* FSDataOutputStream out = dfs.create(filenamePath);
            out.writeUTF("Hello, world!\n");
            out.close();*/
            
            FSDataInputStream in = dfs.open(filenamePath);
            String messageIn = in.readUTF();
            System.out.print(messageIn);
            in.close();
            
            dfs.close();
        } finally {
            
            if (dfs != null) {
                dfs.close();
            }
        }

/*
		// 저장 경로 설정
        String root = multi.getSession().getServletContext().getRealPath("/");
        root = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ "python.csv.dir", null, Locale.getDefault());
        //root = "C:/ide/tmp/";
        logger.debug("root >>> " + root);

        String path = root;

        String newFileName = ""; // 업로드 되는 파일명

        File dir = new File(path);

        if(!dir.isDirectory()){
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while(files.hasNext()){
            String uploadFile = files.next();

            MultipartFile mFile = multi.getFile(uploadFile);

            String fileName = mFile.getOriginalFilename();

            System.out.println("실제 파일 이름 : " +fileName);

            newFileName = System.currentTimeMillis()+"."
                    +fileName.substring(fileName.lastIndexOf(".")+1);
            
            mstData.setFilePath(path + "/" + newFileName);
            mstData.setFileName(fileName);
            
            try {
                mFile.transferTo(new File(path + "/" + newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        ArrayList<String> al = null;
        al = new ArrayList<String>();
        
        try{
        	File csv = new File(path + "/" + newFileName);
        	BufferedReader br = null;
        	br = new BufferedReader(new FileReader(csv));
            String line = "";
            int row =0 ,i;
            
            if((line = br.readLine()) != null) {
	            String[] token = null;
	            token = line.split(",", -1);
	            
	            for(i=0;i<token.length;i++) {
	             al.add(token[i]);
	            }
	            
	            // CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
	            for(i=0;i<token.length;i++) {
	             System.out.println(al.get(i) + ",");
	            }
            }
 
            br.close();
        } catch(Exception e) {
        	logger.debug(e.toString());
        }

        List<WadUodcDaseImpCol> list = null;
        list = new ArrayList<WadUodcDaseImpCol>();
        
        for(int i=0; i<al.size(); i++) {
        	WadUodcDaseImpCol colVal = null;
        	colVal = new WadUodcDaseImpCol();
        	colVal.setColPnm(al.get(i));
        	colVal.setColLnm(al.get(i));
        	colVal.setIbsCheck("1");
        	
        	list.add(colVal);
        	
        	logger.debug(colVal.getColPnm());
        }
        
        int result = 0;
        
        //데이터임포트 등록 
    	result = uodcDaseImpService.regDataImptlist(list, mstData);  
    
    	if(result > 0 ){
			result = 0;
		} else {
			result = -1;
		}
        */
        
        int result = 1;
        
    	return result;
	}
	
	/** 결과보기 팝업  
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/popup/dataimp_pop.do") 
	public String goResultView(@ModelAttribute("search") WadUodcDaseImpCol search, Model model, Locale locale) throws Exception {
		logger.debug("WadUodcDaseImpColVO:{}", search);
		//logger.debug("WadOtlResult:{}", resvo);

		int headerCnt = 0;
		String headerText = new String();
		
		List<WadUodcDaseImpCol> datalist = uodcDaseImpService.getResultViewColNm(search);

		
		if( null != datalist && !datalist.isEmpty()){
			headerCnt = datalist.size()-1  ;

			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;

			
			if(headerCnt > 0){
				colNm.append("COL_PNM0");
				tmpColNm.append(datalist.get(0).getColPnm()+"|");
				
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_PNM" + i);

					tmpColNm.append(datalist.get(i).getColPnm()+"|");
				}
				
				//IBSHEET 헤더텍스트 조회
				headerText = tmpColNm.substring(0, tmpColNm.length()-1);
			}
			
			search.setColNm(colNm.toString());
			
		}
		JSONObject data = csv2json(datalist.get(0).getFilePath());
		
		search.setColCnt(headerCnt);
		search.setHeaderText(headerText);

		
		model.addAttribute("headerVO", search);
		model.addAttribute("colist", datalist);
		model.addAttribute("data", data);
		
		return "/advisor/prepare/udefoutlier/popup/dataimp_pop";
	}
	
	private JSONObject csv2json(String filePath) {
		ArrayList<String> al = null;
        al = new ArrayList<String>();
        
        //최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();
        
		try{
        	File csv = new File(filePath);
        	BufferedReader br = null;
        	br = new BufferedReader(new FileReader(csv));
            String line = "";
            int i=0;
     
            //person의 JSON정보를 담을 Array 선언
            JSONArray dataArray = new JSONArray();
     
            //person의 한명 정보가 들어갈 JSONObject 선언
            JSONObject dataInfo = null;
            
            while((line = br.readLine()) != null) {
	            String[] token = null;
	            token = line.split(",", -1);
	            dataInfo = new JSONObject();
	            
	            if(i==0) {
		            for(int j=0;j<token.length;j++) {
		             al.add(token[j]);
		            }
	            }
	            
	            if(i>0) {
		            // CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
		            for(int j=0;j<token.length;j++) {
		            	//정보 입력
		            	//dataInfo.put(al.get(j), token[j]);
		            	dataInfo.put("colNm" + j, token[j]);
		            }
		            dataArray.add(dataInfo);
	            }
	            
	            if(i > 999) break;
	            
	            i++;
            }
            
            //전체의 JSONObject에 사람이란 name으로 JSON의 정보로 구성된 Array의 value를 입력
            jsonObject.put("data", dataArray);
 
            br.close();
        } catch(Exception e) {
        	logger.debug(e.toString());
        }
		
		return jsonObject;
	}
}
