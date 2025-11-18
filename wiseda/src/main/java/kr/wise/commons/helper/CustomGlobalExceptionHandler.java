/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CustomGlobalExceptionHandler.java
 * 2. Package : kr.wise.commons.helper
 * 3. Comment : 전역 예외처리 로직. 스프링 4 버전으로 되어있음
 * 4. 작성자  : lsi
 * 5. 작성일  : 2020
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *  
 */
package kr.wise.commons.helper;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.exception.WiseException;
import kr.wise.commons.cmm.service.ErrLogService;
import kr.wise.commons.cmm.service.WaaErrLog;
import kr.wise.commons.util.UtilString;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {

	static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	@Inject
	ErrLogService errLogService;
	
	@ExceptionHandler(RuntimeException.class) 
	@ResponseBody
	public  HashMap<String,Object> custom(HttpServletRequest request, HttpServletResponse response,Exception ex) 
	{ 
		HashMap<String,Object> rm = new HashMap<String, Object>();
		String contentType = request.getContentType();
		logger.error("##ERROR 발생", ex);
		boolean ajaxYn = false;
		if(UtilString.null2Blank(request.getHeader("X-Requested-With")).equals("XMLHttpRequest")){  // ajax 요청인지 아닌지 판단
			ajaxYn = true;
		}
	      
	
         LoginVO loginVo = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		  
	      String userId="";
	      String userNm="";
	      if(loginVo!=null){
	         userId = UtilString.null2Blank(loginVo.getId());
	         userNm = UtilString.null2Blank(loginVo.getName());
	      }
	      
	      String executePgm="";
	      String resultBody="";
	      String className= "";
	      StackTraceElement[] stack = ex.getStackTrace();
	      for(int i=0; i < stack.length ; i++){
	    	  if(stack[i]!=null && stack[i].getClassName().contains("kr.wise")&& !stack[i].getClassName().contains("WiseBizException")){
	    		  executePgm+=stack[i].getFileName();
	    		  className+=stack[i].getClassName();
	    		  break;
	    	  }
	      }
	      String cause = "";
	      
	      
	      if(ex.getCause()!=null && ex.getCause().getCause()!=null){
	    	  cause = ex.getCause().getCause().toString();
	      }else{
	    	  cause = ex.getClass().getName()+":"+ex.getLocalizedMessage();
	      }
	      if(ex instanceof WiseBizException){
	    	  cause += UtilString.null2Blank(((WiseBizException) ex).getErrMsg());
	      }
	      resultBody = "{QUERY:\""+UtilString.null2Blank(ex.getMessage()) +"\" ,CAUSE: \""+cause+"\"}";
		  
	      WaaErrLog vo = new WaaErrLog();
	      vo.setPgmNm(executePgm);
	      vo.setErrLog(resultBody);
	      vo.setUserId(userId);
	      errLogService.insertErrLog(vo);   //WAE_ERR_LOG 테이블에 에러내용 적재
	      
//		if(ex instanceof RuntimeException && contentType !=null||ex instanceof UnsupportedOperationException){
		if(ex instanceof RuntimeException && contentType !=null && ajaxYn){	//ajax일 경우 Map으로 반환
			      
			   
			      
			      if(ex instanceof WiseBizException){
			    	  rm.put("message",className+":"+UtilString.null2Blank(((WiseBizException) ex).getErrMsg()));  
			      }
			      else if(ex.getCause()!=null){
			    	  rm.put("message",ex.getCause().getMessage());
			      }else{
			    	  rm.put("message",ex.getClass().getName()+":"+ex.getLocalizedMessage());  
			      }
			    
//			      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			      response.setStatus(499);
		}else{
			   throw new WiseException(ex.getMessage());
		     
		}
		return rm;
	}
}
