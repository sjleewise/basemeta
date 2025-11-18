package kr.wise.meta.intf.web;

import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import kr.wise.commons.util.UtilString;
import kr.wise.meta.intf.service.EraScrnItm;
import kr.wise.meta.intf.service.EraScrnItmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


@Controller("EraScrnItmCtrl")
public class EraScrnItmCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());



	@Inject
	private EraScrnItmService eraScrnItmService;


	@RequestMapping("/meta/intf/testEraScrnItm.do") //테스트페이지
	public String goTestForm() throws Exception {		
		return "/meta/stnd/testEraScrnItm";
	
	}


	/** ERA 연계 조회 제공 */
    @RequestMapping("/intf/ScrnItmOne.do")
    @ResponseBody
    public void selectScrnItmOne( @RequestParam("LANG_CODE") String langCode
            					 , @RequestParam("VCB") String vcb  
    		, HttpServletRequest request, HttpServletResponse response){
    	try{	
    		List<EraScrnItm> data = eraScrnItmService.getEraScrnItmOne(vcb, langCode);
        	
        	String resultXml = "";
        	
        	if(data!=null){
        		resultXml = makeResultXml(data);
        	}
        	
    		response.setContentType("text/xml");

    		PrintWriter printwriter = null;
    		
//    		System.out.println(resultXml);
    		
    		try{
    		
    			printwriter = response.getWriter();
    			printwriter.println(resultXml);
    			printwriter.flush();
    		}catch(Exception ex){
    			logger.error("", ex);
    		
    		}finally{
    			printwriter.close();
    		}
        }catch(Exception ex){
    		logger.error("", ex);
    	
    	}	
        	
    }
    
    /** ERA 연계 조회 제공 */
    @RequestMapping("/intf/ScrnItmLst.do")
    @ResponseBody
    public void selectStndTermLst( @RequestParam("LANG_CODE") String langCode
            					 , @RequestParam("reqXml") String reqXml  
    		, HttpServletRequest request, HttpServletResponse response){    	
    try{	
    	
    	logger.debug(langCode);
    	logger.debug(reqXml);
    	
//    	/** 
//    	 * test xml 작성
//    	 */
//    	reqXml = makeTestReqXml2(trdGb);
//    	if(trdGb.startsWith("item")) trdGb = "item";
//    	logger.debug(trdGb);
//    	logger.debug(reqXml);
    	

//System.out.println(trdGb);
//System.out.println(reqXml);
    	
    	
    	List<EraScrnItm> reqData = parseReqXml(reqXml);
    	List<EraScrnItm> resData = eraScrnItmService.getEraScrnItmList(reqData, langCode);
    	
    	String resultXml = makeResultXml(resData);
    	
//    	logger.debug(resultXml);   	
    	
//System.out.println(resultXml);

		response.setContentType("text/xml");
//		response.setHeader("Pragma","");
//		response.setHeader("Cache-Control","");

		PrintWriter printwriter = null;  
		
//		System.out.println(resultXml);
		
		try{
		
			printwriter = response.getWriter();
			printwriter.println(resultXml);
			printwriter.flush();
		}catch(Exception ex){
			logger.error("", ex);
		
		}finally{
			printwriter.close();
		}
    }catch(Exception ex){
		logger.error("", ex);
	
	}	
    	
    }
    
    
    private List<EraScrnItm> parseReqXml(String reqXml) {
    	List<EraScrnItm> list = new ArrayList<EraScrnItm>();
    	
    	InputSource is = new InputSource(new StringReader(reqXml));

		EraScrnItm row = null;
    	Document document = null;
		NodeList gapInNodeList1 = null;
		NodeList gapInNodeList2 = null;
		
    	try{
    		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

    		XPath xpath = XPathFactory.newInstance().newXPath();
    		
//    		nodeList = document.getElementsByTagName("ROW");

    		/**
    		 * 표준조회와 gap조회 reqXml포멧이 틀림..ㅠ.ㅠ
    		 */

			
			gapInNodeList1 = (NodeList)xpath.evaluate("//ROW/VCB", document, XPathConstants.NODESET);
			gapInNodeList2 = (NodeList)xpath.evaluate("//ROW/VCB_ID", document, XPathConstants.NODESET);
				

	    	logger.debug("gapInNodeList1.getLength() = " +gapInNodeList1.getLength());
    		for(int i = 0; i< gapInNodeList1.getLength(); i++){
    			
    			row = new EraScrnItm();    			
				row.setSearchcon("E");

				row.setVcb (gapInNodeList1.item(i).getFirstChild().getNodeValue());
				row.setVcbId  (gapInNodeList2.item(i).getFirstChild().getNodeValue());
    			
    			list.add(row);  
    		}
    		
    	}catch(Exception ex){
    		logger.error("",ex);
    	}
		return list;
	}
    
    private String makeResultXmlOne(EraScrnItm data){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
    	sb.append("<RESPONSE>");
    	if(data != null){
	    	sb.append("	<ROW>\n");
	    	sb.append("		<VCB_ID><![CDATA["      + UtilString.null2Blank(data.getVcbId()) + "]]></VCB_ID>\n");
	    	sb.append("		<VCB><![CDATA["    + UtilString.null2Blank(data.getVcb()) + "]]></VCB>\n");
	    	sb.append("		<VCB_LANG><![CDATA["    + UtilString.null2Blank(data.getVcbLang()) + "]]></VCB_LANG>\n");
	    	sb.append("		<LANG_CODE><![CDATA["    + UtilString.null2Blank(data.getLangCode()) + "]]></LANG_CODE>\n");
	    	sb.append("	</ROW>\n");
    	}
    	sb.append("</RESPONSE>");
    	
    	return sb.toString();
    }

	private String makeResultXml(List<EraScrnItm> list){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
    	sb.append("<RESPONSE>");
    	if(list != null){
	    	for(EraScrnItm row : list){
		    	sb.append("	<ROW>\n");
		    	sb.append("		<VCB_ID><![CDATA["      + UtilString.null2Blank(row.getVcbId()) + "]]></VCB_ID>\n");
		    	sb.append("		<VCB><![CDATA["    + UtilString.null2Blank(row.getVcb()) + "]]></VCB>\n");
		    	sb.append("		<VCB_LANG><![CDATA["    + UtilString.null2Blank(row.getVcbLang()) + "]]></VCB_LANG>\n");
		    	sb.append("		<LANG_CODE><![CDATA["    + UtilString.null2Blank(row.getLangCode()) + "]]></LANG_CODE>\n");
		    	sb.append("	</ROW>\n");
	    	}
    	}
    	sb.append("</RESPONSE>");
    	
    	return sb.toString();
    }
    
	

	private String makeTestReqXml(String trdGb){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
    	sb.append("<REQUEST>");
    	
	    for(int i = 0; i < 10 ; i++){
	    	sb.append("	<ROW>\n");
	    	if("gap".equals(trdGb)){
	    		sb.append("		<TERMNM><![CDATA[자기부담" + i + "금액]]></TERMNM>\n");
	    	}else if("item".equals(trdGb)){
	    		sb.append("		<SEARCHVAL><![CDATA[자기부담" + i + "금액]]></SEARCHVAL>\n");
	    		sb.append("		<SEARCHCON>E</SEARCHCON>\n");
	    		sb.append("		<CHECKTERMNMEN>N</CHECKTERMNMEN>\n");
	    	}else if("item1".equals(trdGb)){
	    		sb.append("		<SEARCHVAL><![CDATA[자기부담" + i + "금액]]></SEARCHVAL>\n");
	    		sb.append("		<SEARCHCON>A</SEARCHCON>\n");
	    		sb.append("		<CHECKTERMNMEN>N</CHECKTERMNMEN>\n");
	    	}else if("item2".equals(trdGb)){
	    		sb.append("		<SEARCHVAL><![CDATA[ONBN" + i + "_AMT]]></SEARCHVAL>\n");
	    		sb.append("		<SEARCHCON>E</SEARCHCON>\n");
	    		sb.append("		<CHECKTERMNMEN>Y</CHECKTERMNMEN>\n");
	    	}
	    	sb.append("	</ROW>\n");	    	
    	}
    	sb.append("</REQUEST>");
    	
    	return sb.toString();
    }

	private String makeTestReqXml2(String trdGb){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
		sb.append("<REQUEST>");
		sb.append("	<ROW>\n");
    	
		if ("gap".equals(trdGb) ) {
//			sb.append("<IN_TERMNM><![CDATA[SWIFT50공통5전문내용]]></IN_TERMNM>\n");
//			sb.append("<IN_TERMNM><![CDATA[S&P신용등급코드]]></IN_TERMNM>\n");
			sb.append("<IN_ENTITYNM><![CDATA[]]></IN_ENTITYNM>\n");
			sb.append("<IN_TABLENM><![CDATA[]]></IN_TABLENM>\n");
			sb.append("<IN_COLUMNSEQ><![CDATA[]]></IN_COLUMNSEQ>\n");
//			sb.append("<IN_TERMNM><![CDATA[SWIFT50공통5전문내용]]></IN_TERMNM>\n");
			sb.append("<IN_TERMNM><![CDATA[S&P신용등급코드]]></IN_TERMNM>\n");
			sb.append("<IN_TERMNMEN><![CDATA[]]></IN_TERMNMEN>\n");
			sb.append("<IN_INFORTYPE><![CDATA[]]></IN_INFORTYPE>\n");
			sb.append("<IN_DOMAINNM><![CDATA[]]></IN_DOMAINNM>\n");
			sb.append("<IN_LDATATYPE><![CDATA[]]></IN_LDATATYPE>\n");
			sb.append("<IN_DATATYPE><![CDATA[]]></IN_DATATYPE>\n");
			sb.append("<IN_ATTRIBUTEID><![CDATA[]]></IN_ATTRIBUTEID>\n");
			
		} else {
	    	sb.append("		<SEARCHVAL><![CDATA[1년간입출항횟수]]></SEARCHVAL>\n");
	    	sb.append("		<SEARCHCON>A</SEARCHCON>\n");
	    	sb.append("		<CHECKTERMNMEN>N</CHECKTERMNMEN>\n");
		}
		sb.append("	</ROW>\n");	    	
		sb.append("</REQUEST>");
    	
    	return sb.toString();
    }


}





