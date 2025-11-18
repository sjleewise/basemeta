package kr.wise.commons.net;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.wise.commons.helper.grid.IBSResultVO;


@Component
public class CommNetApiUtil {

	private final static Logger logger = LoggerFactory.getLogger(CommNetApiUtil.class);
	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect
	 */
	public static org.codehaus.jettison.json.JSONObject callJsonApi(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		org.codehaus.jettison.json.JSONObject rtnObj = new org.codehaus.jettison.json.JSONObject();

		// set HttpHeader
		/*
		 * 
		 * access token 요청 시 : headers.add("Authorization", "Basic " + authStringEnc);
		 * 일반 api 호출 시 : headers.add("Authorization", "Bearer " + getAccessToken());
		 */
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.add(appCenterKeyName, appCenterKeyValue);  나중에 header에 보안을 위해 넣는

		// set HttpBody
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				bodyMap.add(key, value);
			}
		}

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(bodyMap,
				headers);
		HttpEntity<String> response = null;

		try {
			response = restTemplate.exchange(uri, httpMethod, entity, String.class);
			logger.info("url1 : " + uri + " response : " + response.getBody());
			rtnObj = new JSONObject(response.getBody());
			logger.info("url2 : " + uri + " response : " + response.getBody());

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			System.out.println("errorCode :" + errorCode);
			logger.error("url111 :// " + uri );
			rtnObj.put("error", errorCode);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return rtnObj;
	}

	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return Map<String, Object>
	 */
	public static Map<String, Object> callMapApi(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		Map<String, Object> map = null;

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		org.codehaus.jettison.json.JSONObject rtnObj = new org.codehaus.jettison.json.JSONObject();

		// set HttpHeader
		/*
		 * 
		 * access token 요청 시 : headers.add("Authorization", "Basic " + authStringEnc);
		 * 일반 api 호출 시 : headers.add("Authorization", "Bearer " + getAccessToken());
		 */
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.add(appCenterKeyName, appCenterKeyValue);

		// set HttpBody
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				bodyMap.add(key, value);
			}
		}

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(bodyMap,
				headers);
		HttpEntity<String> response = null;

		try {
			response = restTemplate.exchange(uri, httpMethod, entity, String.class);
			rtnObj = new org.codehaus.jettison.json.JSONObject(response.getBody());

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			
			logger.error("url111 :// " + uri );
			logger.error("errorCode :" + errorCode);
			
			rtnObj.put("error", errorCode);

		} catch (Exception e) {
			e.printStackTrace();

		}

		map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);

		return map;
	}

	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect
	 */
	public String callStringApi(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {
		
		URL url = new URL(baseUri);
	    URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
	    System.out.println("URI " + uri.toString() + " is OK");
	    
//		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.add(appCenterKeyName, appCenterKeyValue);

		// set HttpBody
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<String, Object>();
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				bodyMap.add(key, value);
			}
		}

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(bodyMap,
				headers);
		HttpEntity<String> response = null;

		try {
			response = restTemplate.exchange(uri, httpMethod, entity, String.class);
			logger.info("url : " + uri + " response : " + response);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url111 :// " + uri );
			logger.error("errorCode :" + errorCode);
			rtnObj = "error";

		} catch (Exception e) {
			e.printStackTrace();

		}

		return rtnObj;
	}

	
	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect
	 */
	public static Map<String, Object> callJsonStringApi(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);
		headers.setContentType(mediaType);
		//headers.add(appCenterKeyName, appCenterKeyValue);

		org.codehaus.jettison.json.JSONObject bodyObj = new org.codehaus.jettison.json.JSONObject();
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(!key.equals("class")){
				   bodyObj.put(key, value);
				}
			}
		}

		HttpEntity<String> response = null;
		String JSONInput = bodyObj.toString();
		HttpEntity param = new HttpEntity(JSONInput, headers);
		try {
			logger.info("url :// " + uri + " response : " + bodyObj);
			response = restTemplate.exchange(uri, httpMethod, param, String.class);

			logger.info("url111 :// " + uri + " response : " + JSONInput);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			logger.error("message :// " + ce.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\""+errorCode+"\""+",\"MESSAGE\": \""+ce.getMessage()+"\"}}";

		} catch (Exception e) {
			logger.error("url333 :// " + uri );
			logger.error("message :// " + e.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\"500\""+",\"MESSAGE\": \""+e.getMessage()+"\"}}";

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
		Map<String, Object> map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);
		
		
		return map;
	}
	
	/**
	 * Map을 json으로 변환한다.
	 *
	 * @param map
	 *            Map<String, Object>.
	 * @return JSONObject.
	 */
	public  JSONObject getJsonStringFromMap(Map<String, Object> map) throws Exception {
		 JSONObject jsonObject = new  JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toLowerCase();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}
		return jsonObject;
	}

	/**
	 xo
	 * @param list
	 *            List<Map<String, Object>>.
	 * @return JSONArray.
	 */
	public  JSONArray getJsonArrayFromList(List<Map<String, Object>> list) throws Exception {
		 JSONArray jsonArray = new  JSONArray();
		for (Map<String, Object> map : list) {

			jsonArray.add(getJsonStringFromMap(map));
		}
		logger.info("@@@@@@@@@@ sohn getJsonArrayFromList::" + jsonArray);
		return jsonArray;
	}

	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect
	 */
	public static Map<String, Object> callJsonStringApiParamString(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);

		headers.setContentType(mediaType);
		//headers.add(appCenterKeyName, appCenterKeyValue);

//		JSONObject bodyObj = new JSONObject();
		String bodyObj="";
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(value!=null&&!key.equals("class")){
//				bodyObj.put(key, value);
				bodyObj+="&"+key+"="+value;
				}
			}
		}

		HttpEntity<String> response = null;
		String JSONInput = bodyObj.toString();
		HttpEntity param = new HttpEntity(JSONInput, headers);
		try {
			logger.info("url :// " + uri + " response : " + bodyObj);
			response = restTemplate.exchange(uri+"?"+bodyObj, httpMethod, param, String.class);

			logger.info("url111 :// " + uri + " response : " + bodyObj);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			rtnObj = "error";

		} catch (Exception e) {
			e.printStackTrace();

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
		Map<String, Object> map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);
		
		
		return map;
	}
	
	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect, vo와 List를 같이 json으로 묶어보내는 경우
	 * WaqMstr 요청마스터 -> url파라미터 처리
	 */
	public static Map<String, Object> callJsonStringApiListRequest(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);
		headers.setContentType(mediaType);
		//headers.add(appCenterKeyName, appCenterKeyValue);

		//jsonpaer lib가 없음....
//		JSONObject bodyObj = new JSONObject();
//        JSONParser paser = new JSONParser();
//        
//		if (bodyParams != null && bodyParams.size() > 0) {
//			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				if(value instanceof List){
//					JSONArray jsonarr = new JSONArray(( new ObjectMapper().writeValueAsString(value)));
//					bodyObj.put(key, jsonarr);
//				}else{
//					bodyObj.put(key, paser.parse(new ObjectMapper().writeValueAsString(value)));
//				}
//			}
//		}
		
		//paser가 없어서 수동으로 생성
		String paramString = "{";
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				paramString += ",";
				paramString += "\""+ key +"\":";
				paramString += new ObjectMapper().writeValueAsString(value);
			}
		}
		paramString +="}";
		paramString = paramString.replaceFirst(",", "");

		HttpEntity<String> response = null;
//		String JSONInput = bodyObj.toString();
		String JSONInput = paramString;
		
		HttpEntity param = new HttpEntity(JSONInput, headers);
		try {
			logger.info("url :// " + uri + " response : " + JSONInput);
			response = restTemplate.exchange(uri, httpMethod, param, String.class);

			logger.info("url111 :// " + uri + " response : " + JSONInput);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			logger.error("message :// " + ce.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\""+errorCode+"\""+",\"MESSAGE\": \""+ce.getMessage()+"\"}}";

		} catch (Exception e) {
			logger.error("url333 :// " + uri );
			logger.error("message :// " + e.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\"500\""+",\"MESSAGE\": \""+e.getMessage()+"\"}}";

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
		Map<String, Object> map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);
		
		
		return map;
	}
	
	/*
	 * api 호출 메서드 - post, delete, put 방식 지원 return JSONOBJect
	 * x-www-Form-UrlEncoded 사용시
	 */
	public static Map<String, Object> callFormUrlEncodedApi(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
		headers.setContentType(mediaType);
		
//		MultiValueMap<String,Object> multiMap = new LinkedMultiValueMap<>();
		MultiValueMap<String,Object> multiMap = new LinkedMultiValueMap<String,Object>();
		
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(!key.equals("class")){
					
					if(value!=null){
					   	logger.debug(value.getClass().getName());
					   if(value.getClass().getName().indexOf("Integer")>0){
					      multiMap.add(key, value.toString());
					   }else{
					      multiMap.add(key, value);	
					   }
					}else{
						  multiMap.add(key, value);
					}
				}
			}
		}

		try {

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String,Object>>(multiMap,headers);

			rtnObj = restTemplate.postForObject(uri, request, String.class);

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			logger.error("message :// " + ce.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\""+errorCode+"\""+",\"MESSAGE\": \""+ce.getMessage()+"\"}}";

		} catch (Exception e) {
			logger.error("url333 :// " + uri );
			logger.error("message :// " + e.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\"500\""+",\"MESSAGE\": \""+e.getMessage()+"\"}}";

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
		Map<String, Object> map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);
		
		
		return map;
	}
	
	public static IBSResultVO<Object> callJsonStringApiIbsResult(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);
		headers.setContentType(mediaType);
		//headers.add(appCenterKeyName, appCenterKeyValue);

		JSONObject bodyObj = new JSONObject();
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(!key.equals("class")){
				   bodyObj.put(key, value);
				}
			}
		}

		HttpEntity<String> response = null;
		String JSONInput = bodyObj.toString();
		HttpEntity param = new HttpEntity(JSONInput, headers);
		try {
			logger.info("url :// " + uri + " response : " + bodyObj);
			response = restTemplate.exchange(uri, httpMethod, param, String.class);

			logger.info("url111 :// " + uri + " response : " + JSONInput);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			logger.error("message :// " + ce.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\""+errorCode+"\""+",\"MESSAGE\": \""+ce.getMessage()+"\"}}";

		} catch (Exception e) {
			logger.error("url333 :// " + uri );
			logger.error("message :// " + e.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\"500\""+",\"MESSAGE\": \""+e.getMessage()+"\"}}";

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
//		Map<String, Object> map = new ObjectMapper().readValue(rtnObj.toString(), Map.class);
		IBSResultVO<Object> returnresult = new ObjectMapper().readValue(rtnObj.toString(), IBSResultVO.class);
		return returnresult;
	}
	
	
	
	/*
	 * api 호출 메서드 - get, post, delete, put 방식 지원 return JSONOBJect, vo와 List를 같이 json으로 묶어보내는 경우
	 * WaqMstr 요청마스터 -> url파라미터 처리
	 */
	public static IBSResultVO<Object> callJsonStringApiListRequestIbsResult(String baseUri, HttpMethod httpMethod, Map<String, Object> bodyParams)
			throws Exception {

		URI uri = URI.create(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		String rtnObj = "";

		// set HttpHeader
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);
		headers.setContentType(mediaType);
		//headers.add(appCenterKeyName, appCenterKeyValue);
	
		String paramString = "{";
		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				paramString += ",";
				paramString += "\""+ key +"\":";
				paramString += new ObjectMapper().writeValueAsString(value);
			}
		}
		paramString +="}";
		paramString = paramString.replaceFirst(",", "");

		HttpEntity<String> response = null;
//		String JSONInput = bodyObj.toString();
		String JSONInput = paramString;
		
		HttpEntity param = new HttpEntity(JSONInput, headers);
		try {
			logger.info("url :// " + uri + " response : " + JSONInput);
			response = restTemplate.exchange(uri, httpMethod, param, String.class);

			logger.info("url111 :// " + uri + " response : " + JSONInput);
			rtnObj = response.getBody();

		} catch (HttpStatusCodeException ce) {

			HttpStatus errorCode = ce.getStatusCode();
			logger.error("url222 :// " + uri );
			logger.error("errorCode :" + errorCode);
			logger.error("message :// " + ce.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\""+errorCode+"\""+",\"MESSAGE\": \""+ce.getMessage()+"\"}}";

		} catch (Exception e) {
			logger.error("url333 :// " + uri );
			logger.error("message :// " + e.getMessage() );
			rtnObj = "{\"RESULT\":"+"{\"CODE\""+":"+"\"500\""+",\"MESSAGE\": \""+e.getMessage()+"\"}}";

		}
//		logger.info("/linkRequestData.do==============================in:"+rtnObj.toString());
		IBSResultVO<Object> returnresult = new ObjectMapper().readValue(rtnObj.toString(), IBSResultVO.class);
		
		
		return returnresult;
	}
	
	//sms 맵
	public static Map<String, Object> getSms(String inf_dcd, String inf_from, String tgt_sys_cd, String message){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("INF_DCD" , inf_dcd); //SMS
		paramMap.put("INF_FROM" , inf_from); //기관메타
		paramMap.put("TGT_SYS_CD" , tgt_sys_cd); //행정안전부
		paramMap.put("MESSAGE" , message);
		
		return paramMap;
	}
}
