package kr.wise.meta.stnd.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.meta.stnd.service.DicApiService;
import kr.wise.meta.stnd.service.WaqDic;
import kr.wise.meta.stnd.service.WaqDicMapper;
import kr.wise.meta.stnd.web.DicApiCtrl.WaqDics;

@Service("dicApiService")
public class DicApiServiceImpl implements DicApiService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Inject
	private WaqDicMapper waqDicMapper;

	@Override
	public List<WaqDic> postKoreaApi(WaqDics data) {

		List<WaqDic> list = data.get("data");

		List<WaqDic> resultList = null;
		resultList = new ArrayList<WaqDic>();

		try {

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			for (WaqDic savevo : list) {

				String url = "https://krdict.korean.go.kr/api/search?certkey_no=1037&key=4264D1B77DEE16940A55BD97C7F9E980&type_search=search&q=";
				url += savevo.getWdName(); // 검색할 단어 url에 추가

				Document doc = dBuilder.parse(url);
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("item");

				// 해당 단어 조회
				for (int temp = 0; temp < nList.getLength(); temp++) {
					// 영문 이름이 없을때
					boolean checkEnnm = false;
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						if (eElement.getElementsByTagName("origin").item(0) == null)
							checkEnnm = false;
						else
							checkEnnm = true;

						int checkWord = getTagValue("word", eElement).compareTo(savevo.getWdName());
						// 해당 단어가 존재 할 경우
						if (checkWord == 0) {
							WaqDic resultVo = new WaqDic();

							resultVo.setWdName(savevo.getWdName());

							Pattern pattern = Pattern.compile("[A-Za-z]");

							if (checkEnnm) {
								checkEnnm = false;
								Matcher matcher = pattern.matcher(getTagValue("origin", eElement));
								while (matcher.find()) {
									checkEnnm = true;
									break;
								}
							}

							// 영문명이 있을 경우
							if (checkEnnm) {
								resultVo.setEnnm(getTagValue("origin", eElement).toString());
							} else {
								// 없을 경우 영문명을 빈칸으로
								resultVo.setEnnm("");
							}

							// logger.debug("뜻 >>> " + getTagValue("definition", eElement).toString());

							resultVo.setDicOrgn("국립국어원");
							resultVo.setDicDesc(getTagValue("definition", eElement).toString());

							resultList.add(resultVo);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<WaqDic> postNaverApi(WaqDics data) {
		List<WaqDic> list = data.get("data");

		List<WaqDic> resultList = null;
		resultList = new ArrayList<WaqDic>();

		String clientId = "bDvAPShrbLVsHVSWgGpg"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "A2LRKAldK2"; // 애플리케이션 클라이언트 시크릿값"

		try {
			String text = null;

			for (WaqDic savevo : list) {
				try {
					text = URLEncoder.encode(savevo.getWdName(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("검색어 인코딩 실패", e);
				}
				
				String apiURL = "https://openapi.naver.com/v1/search/encyc.xml?query=" + text; // xml 결과

				URL url = new URL(apiURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("X-Naver-Client-Id", clientId);
				con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

				int responseCode = con.getResponseCode();
				BufferedReader br;
				if (responseCode == 200) { // 정상 호출
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				} else { // 에러 발생
					br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();


				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(new InputSource(new StringReader(response.toString())));
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("item");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						int checkWord = getTagValue("title", eElement).compareTo(savevo.getWdName());
						
						//해당 단어가 존재 할 경우
						if(checkWord ==0) {
							WaqDic resultVo = new WaqDic();
							
							resultVo.setWdName(savevo.getWdName());
							
							resultVo.setDicOrgn("네이버백과사전");
							resultVo.setDicDesc(getTagValue("description", eElement));
							
							resultList.add(resultVo);
						}

					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		String val =nValue.getNodeValue();
		if(tag == "title") return val.substring(3,val.length()-4);
		else if(tag == "description") {
			val = val.replaceAll("<b>", "");
			val = val.replaceAll("</b>", "");
			return val.substring(0,val.indexOf('.')+1);			
		}
		else return nValue.getNodeValue(); 
	}

	@Override
	public Map<String, String> regDic(List<WaqDic> list) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String dicrqstno = objectIdGnrService.getNextStringId();

		int execCnt = list.size();
		int rtnCnt = execCnt;

		for (WaqDic savevo : list) {			
			if(savevo.getDicDesc() != null && !savevo.getDicDesc().equals("")) {
				savevo.setDicRqstNo(dicrqstno);
				savevo.setDicRqstUserId(userid);
				waqDicMapper.insertDic(savevo);
			}
		}

		resultMap.put("result", Integer.toString(rtnCnt));
		resultMap.put("dicrqstno", dicrqstno);

		return resultMap;
	}

	@Override
	public List<WaqDic> getDicRqstList(WaqDic record) {
		// 사전 조회
		List<WaqDic> list = waqDicMapper.selectDicList(record);
		return list;
	}

	@Override
	public Map<String, String> delDicList(List<WaqDic> list) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int rtnCnt = 0;
		String dicrqstno = "";
		for (WaqDic savevo : list) {
			dicrqstno = savevo.getDicRqstNo();
			rtnCnt += waqDicMapper.delDicList(savevo);
		}

		resultMap.put("result", Integer.toString(rtnCnt));
		resultMap.put("dicrqstno", dicrqstno);

		return resultMap;
	}

}