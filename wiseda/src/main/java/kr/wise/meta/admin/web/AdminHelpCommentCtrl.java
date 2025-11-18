package kr.wise.meta.admin.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.meta.admin.service.AdminHelpCommentService;
import kr.wise.meta.admin.service.HelpComment;
import kr.wise.meta.admin.service.WaaInfoSys;

@Controller
public class AdminHelpCommentCtrl {

	@Inject
	private MessageSource message;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private AdminHelpCommentService adminHelpCommentService;
	
	@RequestMapping("/meta/admin/popup/infosys_help.do")
	public String goHelpPage(@RequestParam("typCd") String typCd, ModelMap model) {
		System.out.println("####################"+typCd);
		model.addAttribute("typCd", typCd);
		return "/meta/admin/popup/infosys_help";
	}
	
	@RequestMapping("/meta/admin/popup/getInfoSysHelp.do")
	@ResponseBody
	public IBSheetListVO<HelpComment> getInfoSysList(@ModelAttribute HelpComment search) throws Exception {
		/*
		 * typCd 
		 * 		-info : 정보시스템
		 * 		-db : 데이터베이스
		 * 		-tbl : 테이블
		 * 		-col : 컬럼
		 */
		//search.setTypCd("info");
		
		List<HelpComment> list = adminHelpCommentService.getHelpList(search);
		return new IBSheetListVO<HelpComment>(list, list.size());
	}
	
}
