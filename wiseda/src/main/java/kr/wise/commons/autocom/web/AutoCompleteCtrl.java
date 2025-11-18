/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AutoComplete.java
 * 2. Package : kr.wise.commons.autocom.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 30. 오후 1:07:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 30. :            : 신규 개발.
 */
package kr.wise.commons.autocom.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.autocom.service.AutoComService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AutoComplete.java
 * 3. Package  : kr.wise.commons.autocom.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 30. 오후 1:07:38
 * </PRE>
 */
@Controller("AutoCompleteCtrl")
public class AutoCompleteCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AutoComService autoComService;


	@RequestMapping("/commons/autocom/getTermList.do")
	@ResponseBody
	public List<String> getAutoComList(@RequestParam Map<String, Object> searchmap) {
		logger.debug("searchmap:{}", searchmap);

		List<String> list = autoComService.getsearchTermList(searchmap);
		
		return list;
	}

}
