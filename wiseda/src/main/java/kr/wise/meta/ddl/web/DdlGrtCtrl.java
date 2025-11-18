package kr.wise.meta.ddl.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.ddl.service.DdlGrtRqstService;
import kr.wise.meta.ddl.service.DdlGrtService;
import kr.wise.meta.ddl.service.WamDdlGrt;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlGrt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : DdlGrtCtrl
 * 2. FileName  : DdlGrtCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL기타오브젝트관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
@Controller("DdlGrtCtrl")
public class DdlGrtCtrl {

   private final Logger logger = LoggerFactory.getLogger(getClass());

   @InitBinder
   public void initBinder(WebDataBinder binder) {
       binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
       binder.setDisallowedFields("rqstDtm");
   }

   @Inject
   private CmcdCodeService cmcdCodeService;

   @Inject
   private CodeListService codeListService;

   private Map<String, Object> codeMap;

   @Inject
   private MessageSource message;

    @Inject
   private EgovIdGnrService requestIdGnrService;

   @Inject
   private RequestMstService requestMstService;

   @Inject
   private DdlGrtRqstService ddlGrtRqstService;
   
   @Inject
   private DdlGrtService ddlGrtService;

   @Inject
   private ApproveLineServie approveLineServie;

   /** DDL 기타오브젝트 변경대상 추가 팝업  */
    @RequestMapping("/meta/ddl/popup/ddlGrtPop.do")
    public String goDdlGrtPop(@ModelAttribute("search") WamDdlGrt search, Model model, Locale locale) {

       return "/meta/ddl/popup/ddlgrt_pop";
    }
    
   /** DDL 기타오브젝트 WAM 리스트 조회 - IBSheet JSON */
   @RequestMapping("/meta/ddl/getDdlGrtlist.do")
   @ResponseBody
   public IBSheetListVO<WamDdlGrt> selectDdlGrtList(@ModelAttribute WamDdlGrt search) {
      List<WamDdlGrt> list = ddlGrtService.getWamGrtList(search);

      return new IBSheetListVO<WamDdlGrt>(list, list.size());
   }
   
   /** DDL 기타오브젝트 WAH 리스트 조회 - IBSheet JSON */
   @RequestMapping("/meta/ddl/getDdlGrtChange.do")
   @ResponseBody
   public IBSheetListVO<WamDdlGrt> getGrtChangeList(@ModelAttribute WamDdlGrt search) {
      List<WamDdlGrt> list = ddlGrtService.getGrtChangeList(search);

      return new IBSheetListVO<WamDdlGrt>(list, list.size());
   }
   
   /** DDL 기타오브젝트 WAM 상세정보 조회 */
   @RequestMapping("/meta/ddl/ajaxgrid/ddlgrtinfo_dtl.do")
   public String selectDdlIdxInfoDetail(String ddlGrtId, String rqstNo, ModelMap model) {
      //메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
      if(!UtilObject.isNull(ddlGrtId) && !UtilObject.isNull(rqstNo)) {
         WamDdlGrt result = ddlGrtService.getWamGrtDetail(ddlGrtId, rqstNo);
         model.addAttribute("result", result);
      }
      return "/meta/ddl/ddlgrtinfo_dtl";
   }
   
   /** DDL 기타오브젝트 WAH 상세정보 조회 */
   @RequestMapping("/meta/ddl/ajaxgrid/ddlgrtRqstinfo_dtl.do")
   public String selectDdlGrtInfoRqstDetail(String ddlGrtId, String rqstNo, ModelMap model) {
      logger.debug("DDL 기타오브젝트 상세정보 조회 / ddlGrtId :{}", rqstNo);
      //메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
      if(!UtilObject.isNull(rqstNo)) {
         WamDdlGrt grt = new WamDdlGrt();
         grt.setDdlGrtId(ddlGrtId);
         grt.setRqstNo(rqstNo);
         WamDdlGrt result = ddlGrtService.getWahGrtDetail(grt);
         model.addAttribute("result", result);
      }
      return "/meta/ddl/ddlgrtinfo_dtl";
   }
   
   /** DDL기타오브젝트 조회페이지 호출 */
   @RequestMapping("/meta/ddl/ddlgrt_lst.do")
   public String goDdlGrtForm(@ModelAttribute("search")WamDdlGrt search, String linkFlag, Model model) {
      model.addAttribute("linkFlag",linkFlag);
      
      return "/meta/ddl/ddlgrt_lst";
   }

  
  /** 공통코드 및 목록성 코드리스트를 가져온다. */
   @ModelAttribute("codeMap")
   public Map<String, Object> getcodeMap() {

      codeMap = new HashMap<String, Object>();

      //공통코드 - IBSheet Combo Code용
      //검토상태코드
      codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
      codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
      //요청구분코드
      codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
      codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
      //업무구분코드
      codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
      codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
      //결재방식코드
      codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
      codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
      //등록유형코드
      codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
      codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
      //요청단계코드(RQST_STEP_CD)
      codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
      codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
      
      
      //진단대상/스키마 정보(double_select용 목록성코드)
      codeMap.put("connTrgSch", UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2")));
      
      String connTrgRole   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgRoleIdReqOnly"));
      codeMap.put("connTrgRole", connTrgRole);
      
      String prcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRC_TYP_CD"));
      codeMap.put("prcTypCd", cmcdCodeService.getCodeList("PRC_TYP_CD"));
      codeMap.put("prcTypCdibs", prcTypCd);

      //오브젝트유형코드
      codeMap.put("objDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OBJ_DCD")));
      codeMap.put("objDcd", cmcdCodeService.getCodeList("OBJ_DCD"));
      

      

      return codeMap;
   }
}