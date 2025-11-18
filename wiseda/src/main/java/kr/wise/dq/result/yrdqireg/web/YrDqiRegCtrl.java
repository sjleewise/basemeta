package kr.wise.dq.result.yrdqireg.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.dq.result.yrdqireg.service.YrDqiRegService;
import kr.wise.dq.result.yrdqireg.service.YrDqiRegVO;
import kr.wise.portal.dashboard.service.TotalDashService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ValAnaResultCtrl.java
 * 3. Package  : kr.wise.dq.report.sdq.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2021. 11. 01
 * </PRE>
 */ 
@Controller
public class YrDqiRegCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
   private Map<String, Object> codeMap;
   
   @Inject
   private CmcdCodeService cmcdCodeService;
   
   @Inject
   private CodeListService codeListService;
   
   @Inject
   private TotalDashService totalDashService;
   
   @Inject
   private MessageSource message;

   @Inject
   private YrDqiRegService yrDqiRegService;
   
   static class ResultVOs extends HashMap<String, ArrayList<YrDqiRegVO>> { };
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
///// 연도별 데이터 품질수준평가 등록
	   
   @RequestMapping("/dq/result/regresult_byyear.do")
   public String regResultByYear(@ModelAttribute ResultVO search) {
      
        // 0. Spring Security 사용자권한 처리
       Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
       
       
      return "/dq/result/regresult_byyear";
   }
   
   //연도별~ 조회
   @RequestMapping("/dq/result/yrdqireg/getRegTbl.do")
   @ResponseBody
   public IBSheetListVO<YrDqiRegVO> getRegList(@ModelAttribute YrDqiRegVO search) { 
       
      List<YrDqiRegVO> list = yrDqiRegService.getRegTbl(search);     
                  
      return new IBSheetListVO<YrDqiRegVO>(list, list.size()); 
   }
   
   
   @RequestMapping("/dq/result/yrdqireg/regYrDqiTbl.do")
   @ResponseBody
   public IBSResultVO<YrDqiRegVO> regYrDqiTbl(@RequestBody ResultVOs data, Locale locale) throws Exception {
      logger.debug("{}", data);
      
      ArrayList<YrDqiRegVO> list = data.get("data");
      
      for(int i=0; i<list.size(); i++) {
         if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")) {
            
            int cntDup = yrDqiRegService.checkDup(list.get(i));
            
            if(cntDup > 0) {
               int result = -1;
               String resmsg = "중복된 기록이 있습니다. <br> 입력한 값을 확인해주세요";
   
               String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
      
               return new IBSResultVO<YrDqiRegVO>(result, resmsg, action);
            }
         }
      }
      
      
      int result = yrDqiRegService.regYrDqiTbl(list);  
      String resmsg;

      if(result > 0) {
         result = 0;
         resmsg = message.getMessage("MSG.SAVE", null, locale);
      } else {
         result = -1;
         resmsg = message.getMessage("ERR.SAVE", null, locale);
      }

      String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
      return new IBSResultVO<YrDqiRegVO>(result, resmsg, action);

   }
   
   @RequestMapping("/dq/result/yrdqireg/delYrDqi.do")
   @ResponseBody
   public IBSResultVO<YrDqiRegVO> delYrDqi(@RequestBody ResultVOs data, Locale locale) throws Exception {
      logger.debug("{}", data);
      
      ArrayList<YrDqiRegVO> list = data.get("data"); 
      
      int result = yrDqiRegService.delYrDqi(list);    
      String resmsg;

      if(result > 0) {
         result = 0;
         resmsg = message.getMessage("MSG.DEL", null, locale);
      } else {
         result = -1;
         resmsg = message.getMessage("ERR.DEL", null, locale);
      }

      String action = WiseMetaConfig.IBSAction.DEL.getAction();
      return new IBSResultVO<YrDqiRegVO>(result, resmsg, action);

   }
	
	
   
   @ModelAttribute("codeMap")
   public Map<String, Object> getcodeMap() {

      codeMap = new HashMap<String, Object>();

      //시스템영역 코드리스트 JSON
      String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
      
      //진단대상
      List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
      codeMap.put("connTrgDbmsCd", connTrgDbms);      
      codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
      
    //진단대상/스키마 정보(double_select용 목록성코드)
      String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
      codeMap.put("connTrgSch", connTrgSch);
      
            
      return codeMap;
   }
	
}
