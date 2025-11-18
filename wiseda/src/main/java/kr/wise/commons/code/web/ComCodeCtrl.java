/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ComCodeManagerCtrl.java
 * 2. Package : kr.wise.commons.code.web
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 17. 오전 8:56:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 17. :            : 신규 개발.
 */
package kr.wise.commons.code.web;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ComCodeManagerCtrl.java
 * 3. Package  : kr.wise.commons.code.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 17. 오전 8:56:13
 * </PRE>
 */
@Controller
public class ComCodeCtrl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MessageSource message;


	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}



	@RequestMapping("/commons/code/getComboIbs.do")
	@ResponseBody
	public Object getCodeList( String codenm, String type) {

		logger.debug("codenm:{}, type:{}", codenm, type);

		Object codelist = null;

		if ("LI".equals(type)) {
			codelist = codeListService.getCodeListIBS(codenm);
		} else if ("L".equals(type)) {
			codelist = codeListService.getCodeList(codenm);
		} else if ("CI".equals(type)) {
			codelist = cmcdCodeService.getCodeListIBS(codenm);
		} else if ("C".equals(type)) {
			if("cateCode".equals(codenm)){
				codelist = codeListService.getCodeList(codenm);
			}
			else{
				codelist = cmcdCodeService.getCodeList(codenm);
			}
		}
		return codelist;
	}


	/** 공통코드 상세코드명 조회 @return insomnia */
	@RequestMapping("/commons/code/getDetailCodeName.do")
	@ResponseBody
	public CodeListVo getDetailCodeName(String codenm, String type, String dtlcode) {
		logger.debug("codenm:{}, type, dtlcode:{}", codenm, new String[]{type, dtlcode});
		String result = cmcdCodeService.getDetailCodeNm(codenm, dtlcode);

		CodeListVo codevo = new CodeListVo();
		codevo.setCodeCd(dtlcode);
		codevo.setCodeLnm(result);

		logger.debug("codevo:{}", codevo);

		return codevo;

	}




}
