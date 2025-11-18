
package kr.wise.commons.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.inject.Inject;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import kr.wise.commons.user.service.impl.KISA_SHA256;

/**
 * @version 1.0<br>
 * 클래스 설명 ------------------------------------------------------------------------<br>
 * String 관련 기능성 Method를 제공하는 Util Class<br>
 * <br>
 * ----------------------------------------------------------------------------------<br>
 */
@Component
public class UtilApi
{
	Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Inject
//	private MessageSource message;	
	
	//오브젝트노드로 변환
    public  ObjectNode ObjectNodeFromObject(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setDateFormat(new SimpleDateFormat("yyyyMMdd HHmmss"));
	        return (ObjectNode) mapper.readTree(mapper.writeValueAsString(object));
   }
    
    /***
     * 서버에 세팅한 토큰값+날짜(20190305)-> 해시값 생성
     * @param message
     * @return
     */
    public static String getApiToken(MessageSource message) {
    	try{
    		// dt,rt간 토큰값
    		// 1.System.getenv("container_api_token")
    		// 2.configure.properties    		
    		String container_api_token = UtilString.replaceNull(System.getenv("container_api_token"), UtilString.replaceNull(message.getMessage("container_api_token", null, null),"")).trim();
    		String cDate = UtilDate.getCurrentDate();
    		
    		return KISA_SHA256.SHA256_Encrpyt(container_api_token+"_"+cDate);
    	}catch(Exception ex){
    		return "";
    	}
        
    }     
    
    /***
     * 주제영역 경로 형식 변경
     * @param input
     * @return
     */
    public  String getSubjectpath(String input){
    	// ebay>PublicationSystemSample_2018R1_TEST>Book
    	// -> Mart/ebay/PublicationSystemSample_2018R1_TEST <-2depth까지만 생성
    	String output="";
    	try{
    		String[] arr = input.replaceAll(">", "/").split("/");
    		if(arr.length==1){
    			output = "Mart/"+ arr[0];	
    		}else{
    			output = "Mart/"+ arr[0]+"/"+arr[1];
    		}
    		 
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    	return output;
    }    
    
    
    //포스트방식으로 파라미터를 던짐  return map
    public Object requestUrlHelper(String url,Object search, MessageSource message) throws IOException{
      	 Map<String, Object> map= new HashMap<String, Object>(); 
      	 RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
           
            HttpHeaders requestHeaders = new HttpHeaders();
            Charset utf8 = Charset.forName("UTF-8");
            MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
            requestHeaders.setContentType(mediaType);
            
            // dt,rt간 토큰값
            String serverToken = UtilApi.getApiToken(message); 		
            requestHeaders.set("remoteToken", serverToken);
            
//            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            logger.info("url 확인 : "+url + "  param : " + search.toString());
            try{
                 HttpEntity<LinkedMultiValueMap<String,String>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,String>>((LinkedMultiValueMap<String, String>) search,requestHeaders); 
                 ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
                 
                 logger.info("code : " +response.getStatusCode());
                 if(response.getStatusCode().value()!=200){
//                	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
                     logger.info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
                 }
                 
                 String res = response.getBody();
                 map =  new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
                 
   	           
            }catch (Exception e) {
    			// TODO: handle exception
          	  logger.error(e.getMessage());
          	  throw new IOException();
    		}
            return map;
       }
    
//    public Object requestUrlHelper(String url,Object search) throws IOException{
//   	 Map<String, Object> map= new HashMap<String, Object>(); 
//   	 RestTemplate restTemplate = new RestTemplate();
//         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
//         restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        
//         HttpHeaders requestHeaders = new HttpHeaders();
//         Charset utf8 = Charset.forName("UTF-8");
//         MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
//         requestHeaders.setContentType(mediaType);
//         
//         // dt,rt간 토큰값
//         String serverToken = UtilApi.getApiToken(message); 		
//         requestHeaders.set("remoteToken", serverToken);
//         
////         HttpHeaders requestHeaders = new HttpHeaders();
////         requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//         logger.info("url 확인 : "+url + "  param : " + search.toString());
//         try{
//              HttpEntity<LinkedMultiValueMap<String,String>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,String>>((LinkedMultiValueMap<String, String>) search,requestHeaders); 
//              ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//              
//              logger.info("code : " +response.getStatusCode());
//              if(response.getStatusCode().value()!=200){
////             	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
//                  logger.info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
//              }
//              
//              String res = response.getBody();
//              map =  new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
//              
//	           
//         }catch (Exception e) {
// 			// TODO: handle exception
//       	  logger.info(e.getMessage());
//       	  throw new IOException();
// 		}
//         return map;
//    }
    
    //포스트방식으로 파라미터를 던짐 return list
    public Object requestUrlHelperList(String url,Object search, MessageSource message) throws IOException{
   	  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         RestTemplate restTemplate = new RestTemplate();
         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
         restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
         
         HttpHeaders requestHeaders = new HttpHeaders();
         Charset utf8 = Charset.forName("UTF-8");
         MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
         requestHeaders.setContentType(mediaType);

         // dt,rt간 토큰값
         String serverToken = UtilApi.getApiToken(message); 		
         requestHeaders.set("remoteToken", serverToken);         
         
         logger.info("url 확인 : "+url + "  param : " + search.toString());
         try{
         HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>((LinkedMultiValueMap<String, Object>) search,requestHeaders); 
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
         logger.info("code : " +response.getStatusCode());
         if(response.getStatusCode().value()!=200){
//        	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
             logger.info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
         }
         String res = response.getBody();
         list = new ObjectMapper().readValue(res, new TypeReference<List<Map<String, Object>>>(){});
         }catch (Exception e) {
       	  logger.error(e.getMessage());
       	  throw new IOException();
		  }
	     return list;
    }
   
  //포스트방식으로 파라미터를 던짐  return map (보통파라미터일경우  list아님)
    public  Object requestUrlJsonHelper(String url,Object search, MessageSource message) throws IOException{
         RestTemplate restTemplate = new RestTemplate();
         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
         restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
         restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
         HttpHeaders requestHeaders = new HttpHeaders();
         Charset utf8 = Charset.forName("UTF-8"); 
         MediaType mediaType = new MediaType("application", "json", utf8); 
         requestHeaders.setContentType(mediaType);
         
         // dt,rt간 토큰값
         String serverToken = UtilApi.getApiToken(message); 		
         requestHeaders.set("remoteToken", serverToken);
         
         logger.info("url 확인 : "+url + "  param : " + search.toString());
         HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>((LinkedMultiValueMap<String, Object>) search,requestHeaders); 
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
         logger.info("code : " +response.getStatusCode());
         if(response.getStatusCode().value()!=200){
//        	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
        	 logger.error("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
         }
         String res = response.getBody();
         Map<String, Object> map = new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
	     return map;
    }
    
    //포스트방식으로 파라미터를 던짐  return map  list(json String을 그대로 받아서 넘김)와 vo를 같이 넘길때 
    public  Object requestUrlJsonHelper(String url,String list,HashMap<String, Object> search, MessageSource message) throws IOException{
         RestTemplate restTemplate = new RestTemplate();
         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
         restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
         restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
         HttpHeaders requestHeaders = new HttpHeaders();
         Charset utf8 = Charset.forName("UTF-8"); 
         MediaType mediaType = new MediaType("application", "json", utf8); 
         requestHeaders.setContentType(mediaType);
         
         // dt,rt간 토큰값
         String serverToken = UtilApi.getApiToken(message); 		
         requestHeaders.set("remoteToken", serverToken);         

         //url + 파라미터
         url = url +MapToQueryString(search);
         logger.info("url 확인 : "+url + "  param : " + search.toString());
         HttpEntity<String> requestEntity = new HttpEntity<String>(list, requestHeaders);
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);   //url,전송방식, body, return, param
         logger.info("code : " +response.getStatusCode());
         if(response.getStatusCode().value()!=200){
        	 logger.error("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
         }
         String res = response.getBody();
         Map<String, Object> map = new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
	     return map;
    }

    //포스트방식으로 파라미터를 던짐 (보통파라미터) return list
    public  Object requestUrlJsonHelperList(String url,Object search, MessageSource message) throws IOException{
         RestTemplate restTemplate = new RestTemplate();
         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
         restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
         restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
         
         HttpHeaders requestHeaders = new HttpHeaders();
         Charset utf8 = Charset.forName("UTF-8"); 
         MediaType mediaType = new MediaType("application", "json", utf8); 
         requestHeaders.setContentType(mediaType);
         
         // dt,rt간 토큰값
         String serverToken = UtilApi.getApiToken(message); 		
         requestHeaders.set("remoteToken", serverToken);         

         logger.info("[start]url 확인 : "+url + "  param : " + search.toString());
         HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>((LinkedMultiValueMap<String, Object>) search,requestHeaders); 
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
         logger.info("url code : " +response.getStatusCode());
         if(response.getStatusCode().value()!=200){
//        	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
        	 logger.error("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
         }
         logger.info("[end]url 확인");
         String res = response.getBody();
         List<Map<String, Object>> list = new ObjectMapper().readValue(res, new TypeReference<List<Map<String, Object>>>(){});
	     return list;
    }
    
    public  Map convertObjectToMap(Object obj){
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i=0; i <fields.length; i++){
            fields[i].setAccessible(true);
            try{
                map.put(fields[i].getName(), fields[i].get(obj));
            }catch(Exception e){
                e.printStackTrace();
            }
        }        
        return map;
    }
 
 
    public  Object convertMapToObject(Map<String,Object> map,Object obj){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        
        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                if(methodString.equals(methods[i].getName())){
                    try{
                        methods[i].invoke(obj, map.get(keyAttribute));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
    
    private String MapToQueryString(Map<String,Object> map){
    	String result ="?";
    	Set<String> keyset = map.keySet();
    	int i=0;
    	for(String vo : keyset){
    		result += vo +"="+ map.get(vo);
    		i++;
    		if(i < keyset.size()){
    			result += "&";
    		}
    	}
    	return result;
    }
    
    
    //포스트방식으로 파라미터를 던짐  return string
    public Object requestUrlHelperReturnString(String url,Object search, MessageSource message) throws IOException{
//   	 Map<String, Object> map= new HashMap<String, Object>(); 
   	 String res ="";
   	 RestTemplate restTemplate = new RestTemplate();
         restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
         restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        
         HttpHeaders requestHeaders = new HttpHeaders();
         Charset utf8 = Charset.forName("UTF-8");
         MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
         requestHeaders.setContentType(mediaType);
         
         // dt,rt간 토큰값
         String serverToken = UtilApi.getApiToken(message); 		
         requestHeaders.set("remoteToken", serverToken);         
         

         logger.info("url 확인 : "+url + "  param : " + search.toString());
         try{
//              HttpEntity<LinkedMultiValueMap<String,String>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,String>>((LinkedMultiValueMap<String, String>) search,requestHeaders); 
//              ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
              
              HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>((LinkedMultiValueMap<String, Object>) search,requestHeaders); 
              ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);              
              
              logger.info("code : " +response.getStatusCode());
              if(response.getStatusCode().value()!=200){
//             	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
                  logger.info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
              }
              
              res = response.getBody();
//              map =  new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
              
	           
         }catch (Exception e) {
 			// TODO: handle exception
       	  logger.error(e.getMessage());
       	  throw new IOException();
 		}
         return res;
    }
    
    
    //보통파라미터를 던지고 리턴으로 String
    public  Object requestUrlJsonHelperString(String url,Object search, MessageSource message) throws IOException{
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
        HttpHeaders requestHeaders = new HttpHeaders();
        Charset utf8 = Charset.forName("UTF-8"); 
        MediaType mediaType = new MediaType("application", "json", utf8); 
        requestHeaders.setContentType(mediaType);
        
        // dt,rt간 토큰값
        String serverToken = UtilApi.getApiToken(message); 		
        requestHeaders.set("remoteToken", serverToken);         

        logger.info("url 확인 : "+url + "  param : " + search.toString());
        HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>((LinkedMultiValueMap<String, Object>) search,requestHeaders); 
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println("code : " +response.getStatusCode());
        if(response.getStatusCode().value()!=200){
//       	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
       	 logger.error("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
        }
        String res = response.getBody();
	     return res;
   }    
    
    
    //파라미터를 objectnode jsong화 시켜서 string으로 변환 후 전송  (파라미터가 map<String,vo>) 형식일때
    public Object requestUrlHelperPostJson(String url,Object search, MessageSource message) throws IOException{
  	    Map<String, Object> map= new HashMap<String, Object>(); 
     	 String res ="";
  	    RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
       
        HttpHeaders requestHeaders = new HttpHeaders();
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);
        requestHeaders.setContentType(mediaType);
        
        // dt,rt간 토큰값
        String serverToken = UtilApi.getApiToken(message); 		
        requestHeaders.set("remoteToken", serverToken);         
        

//        logger.info("url 확인 : "+url + "  param : " + search.toString());
        logger.info("url 확인 : "+url);
        try{
        	ObjectNode node = ObjectNodeFromObject(search);
             HttpEntity<ObjectNode> requestEntity = new HttpEntity<ObjectNode>(node,requestHeaders); 
             ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
             
             logger.info("code : " +response.getStatusCode());
             if(response.getStatusCode().value()!=200){
//            	 LogFactory.getLog("requestUrlHelper").info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
                 logger.info("code : " +response.getStatusCode() + "contents : Api Request failed. " + response.getBody());
             }
             
             res = response.getBody();
             map =  new ObjectMapper().readValue(res, new TypeReference<Map<String, Object>>(){});
             
	           
        }catch (Exception e) {
			// TODO: handle exception
      	  logger.error(e.getMessage());
      	  throw new IOException();
		}
        return map;
   }
    
}
