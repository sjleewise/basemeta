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
import kr.wise.meta.intf.service.EraDataelmt;
import kr.wise.meta.intf.service.EraDataelmtService;

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


@Controller("EraDataelmtCtrl")
public class EraDataelmtCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());



	@Inject
	private EraDataelmtService eraDataelmtService;

	@RequestMapping("/meta/intf/testEraDataelmt.do") //테스트페이지
	public String goTestForm() throws Exception {		
		return "/meta/stnd/testEraDataelmt";
	
	}

    
    /** ERA 연계 조회 제공 */
    @RequestMapping("/intf/StndTermLst.do")
    @ResponseBody
    public void selectStndTermLst( @RequestParam("trdGb") String trdGb /* item:표준조회, gap:gap조회 */
            					 , @RequestParam("reqXml") String reqXml  
    		, HttpServletRequest request, HttpServletResponse response){    	
    try{	
    	
//    	logger.debug(trdGb);
//    	logger.debug(reqXml);
    	
//    	/** 
//    	 * test xml 작성
//    	 */
//    	reqXml = makeTestReqXml2(trdGb);
//    	if(trdGb.startsWith("item")) trdGb = "item";
//    	logger.debug(trdGb);
//    	logger.debug(reqXml);
    	

//System.out.println(trdGb);
//System.out.println(reqXml);
    	String sAll = "";    	
    	if ( reqXml.equals("all") ) sAll = reqXml;
    		
    	if ( (reqXml.equals("") || reqXml.equals("all"))  && trdGb.equals("item")) { 
	    	reqXml = "<?xml version='1.0' encoding='UTF-8' ?>\n";
	    	reqXml += "<REQUEST>\n<ROW>\n";	    	
			if ( sAll.equals("all") ) reqXml += "<SEARCHCON>A</SEARCHCON>\n"; else reqXml += "<SEARCHCON>E</SEARCHCON>\n";
	    	reqXml += "<SEARCHVAL></SEARCHVAL>\n";
	    	reqXml += "<CHECKTERMNMEN>N</CHECKTERMNMEN>\n";
	    	reqXml += "<IN_MODELNM></IN_MODELNM>\n";
	    	reqXml += "<IN_SUBJECTNM></IN_SUBJECTNM>\n";
	    	reqXml += "</ROW>\n</REQUEST>";
    	}
    	
    	if ( (reqXml.equals("") || reqXml.equals("all"))  && trdGb.equals("gap")) { 
	    	reqXml = "<?xml version='1.0' encoding='UTF-8' ?>\n";
	    	reqXml += "<REQUEST>\n<ROW>\n";
			
			reqXml += "<IN_MODELNM></IN_MODELNM>\n";
			reqXml += "<IN_SUBJECTNM></IN_SUBJECTNM>\n";

			reqXml += "<IN_ENTITYNM></IN_ENTITYNM>\n";
			reqXml += "<IN_TABLENM></IN_TABLENM>\n";
			reqXml += "<IN_COLUMNSEQ></IN_COLUMNSEQ>\n";
			reqXml += "<IN_INFORTYPE></IN_INFORTYPE>\n";
			if ( sAll.equals("all") ) reqXml += "<IN_TERMNM>%</IN_TERMNM>\n"; else reqXml += "<IN_TERMNM></IN_TERMNM>\n";
			reqXml += "<IN_TERMNMEN></IN_TERMNMEN>\n";
			// reqXml += "<IN_DOMAINNM></IN_DOMAINNM>\n";
			reqXml += "<IN_LDATATYPE></IN_LDATATYPE>\n";
			reqXml += "<IN_DATATYPE></IN_DATATYPE>\n";
			reqXml += "<IN_ATTRIBUTEID></IN_ATTRIBUTEID>\n";
			reqXml += "</ROW>\n</REQUEST>";
    	}
    	
    	
    	List<EraDataelmt> reqData = parseReqXml(trdGb, reqXml);
    	List<EraDataelmt> resData = eraDataelmtService.getEraDataelmtList(reqData, trdGb);
    	
    	String resultXml = makeResultXml(resData, trdGb, sAll);
    	
//    	logger.debug(resultXml);   	
    	
//System.out.println(resultXml);

		response.setContentType("text/xml");
//		response.setHeader("Pragma","");
//		response.setHeader("Cache-Control","");

		PrintWriter printwriter = null;  
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
    
    
    private List<EraDataelmt> parseReqXml(String trdGb, String reqXml) {
    	List<EraDataelmt> list = new ArrayList<EraDataelmt>();
//    	EraDataelmt row = new EraDataelmt();
//    	row.setSearchval("자기부담1금액");    	
//    	row.setSearchcon("E");    	
//    	list.add(row);
//    	
//    	row = new EraDataelmt();
//    	row.setSearchval("자기부담2금액");    	
//    	row.setSearchcon("E");    	
//    	list.add(row);
//
//    	row = new EraDataelmt();
//    	row.setSearchval("자기부담3금액");    	
//    	row.setSearchcon("E");    	
//    	list.add(row);
    	
    	
    	InputSource is = new InputSource(new StringReader(reqXml));

		EraDataelmt row = null;
    	Document document = null;
		NodeList srchValNodeList = null;
		NodeList srchConNodeList = null;
		NodeList srchChkEnNodeList = null;
		NodeList srchModelNmList = null;
		NodeList srchSubjectNmList = null;
		NodeList gapInNodeList1 = null;
		NodeList gapInNodeList2 = null;
		NodeList gapInNodeList3 = null;
		NodeList gapInNodeList4 = null;
		NodeList gapInNodeList5 = null;
		NodeList gapInNodeList6 = null;
		NodeList gapInNodeList7 = null;
		NodeList gapInNodeList8 = null;
		NodeList gapInNodeList9 = null;
		NodeList gapInNodeList10 = null;
		
		Node srchValNode = null;
		Node srchConNode = null;
		Node srchChkEnNode = null;
		Node srchModelNm = null;
		Node srchSubjectNm = null;
		
		String check;
		
    	try{
    		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

    		XPath xpath = XPathFactory.newInstance().newXPath();
    		
//    		nodeList = document.getElementsByTagName("ROW");

    		/**
    		 * 표준조회와 gap조회 reqXml포멧이 틀림..ㅠ.ㅠ
    		 */
			if("item".equals(trdGb)){
	    		srchValNodeList = (NodeList)xpath.evaluate("//ROW/SEARCHVAL", document, XPathConstants.NODESET);
	    		srchConNodeList = (NodeList)xpath.evaluate("//ROW/SEARCHCON", document, XPathConstants.NODESET);
	    		srchChkEnNodeList = (NodeList)xpath.evaluate("//ROW/CHECKTERMNMEN", document, XPathConstants.NODESET);
	    		
	    		srchModelNmList   = (NodeList)xpath.evaluate("//ROW/IN_MODELNM", document, XPathConstants.NODESET);
	    		srchSubjectNmList = (NodeList)xpath.evaluate("//ROW/IN_SUBJECTNM", document, XPathConstants.NODESET);
	    		
			}else if("gap".equals(trdGb)){
				srchValNodeList = (NodeList)xpath.evaluate("//ROW/IN_TERMNM", document, XPathConstants.NODESET);
				
	    		srchModelNmList   = (NodeList)xpath.evaluate("//ROW/IN_MODELNM", document, XPathConstants.NODESET);
	    		srchSubjectNmList = (NodeList)xpath.evaluate("//ROW/IN_SUBJECTNM", document, XPathConstants.NODESET);
	    		
				gapInNodeList1 = (NodeList)xpath.evaluate("//ROW/IN_ENTITYNM", document, XPathConstants.NODESET);
				gapInNodeList2 = (NodeList)xpath.evaluate("//ROW/IN_TABLENM", document, XPathConstants.NODESET);
				gapInNodeList3 = (NodeList)xpath.evaluate("//ROW/IN_COLUMNSEQ", document, XPathConstants.NODESET);
				gapInNodeList4 = (NodeList)xpath.evaluate("//ROW/IN_TERMNM", document, XPathConstants.NODESET);
				gapInNodeList5 = (NodeList)xpath.evaluate("//ROW/IN_TERMNMEN", document, XPathConstants.NODESET);
				gapInNodeList6 = (NodeList)xpath.evaluate("//ROW/IN_INFORTYPE", document, XPathConstants.NODESET);
//				gapInNodeList7 = (NodeList)xpath.evaluate("//ROW/IN_DOMAINNM", document, XPathConstants.NODESET);
				gapInNodeList8 = (NodeList)xpath.evaluate("//ROW/IN_LDATATYPE", document, XPathConstants.NODESET);
				gapInNodeList9 = (NodeList)xpath.evaluate("//ROW/IN_DATATYPE", document, XPathConstants.NODESET);
				gapInNodeList10 = (NodeList)xpath.evaluate("//ROW/IN_ATTRIBUTEID", document, XPathConstants.NODESET);
			}

//	    	logger.debug("srchValNodeList.getLength() = " +srchValNodeList.getLength());
    		for(int i = 0; i< srchValNodeList.getLength(); i++){
    			
    			row = new EraDataelmt();    			
    			srchValNode = srchValNodeList.item(i);
    			

    			if("item".equals(trdGb)){
    				srchConNode = srchConNodeList.item(i);
    				srchChkEnNode = srchChkEnNodeList.item(i);
    				srchModelNm = srchModelNmList.item(i);
    				srchSubjectNm = srchSubjectNmList.item(i);
    				
//    				logger.debug("srchValNode.getNodeValue() = " +srchValNode.getNodeValue());
//    				logger.debug("srchValNode.getFirstChild().getNodeValue() = " +srchValNode.getFirstChild().getNodeValue());

        			if (srchValNode.getChildNodes().getLength() != 0 ) {
        				row.setSearchval(srchValNode.getFirstChild().getNodeValue());
         			   if (row.getSearchval().equals("undefined")) row.setSearchval("");
        			}
    				else row.setSearchval("");
        			
        			if (srchConNode.getChildNodes().getLength() != 0 ) { 
        				row.setSearchcon(srchConNode.getFirstChild().getNodeValue());
          			   if (row.getSearchcon().equals("undefined")) row.setSearchcon("E");
         			} else row.setSearchcon("E");
        			
        			if (srchChkEnNode.getChildNodes().getLength() != 0 ) {
        				row.setChecktermnmen(srchChkEnNode.getFirstChild().getNodeValue());
           			   if (row.getChecktermnmen().equals("undefined")) row.setChecktermnmen("N");
        			}
        			
        			if (srchModelNm.getChildNodes().getLength() != 0 ) {
       				   row.setModelnm(srchModelNm.getFirstChild().getNodeValue());
        			   if (row.getModelnm().equals("undefined")) row.setModelnm(""); 
        			} else row.setModelnm("");
        			
        			if (srchSubjectNm.getChildNodes().getLength() != 0 ) {
    					row.setSubjectnm(srchSubjectNm.getFirstChild().getNodeValue());
       			        if (row.getSubjectnm().equals("undefined")) row.setSubjectnm("");
        			} else row.setSubjectnm("");
    				
    			}else if("gap".equals(trdGb)){
    				row.setSearchcon("E");
    				row.setChecktermnmen("N");      	
    				srchModelNm = srchModelNmList.item(i);
    				srchSubjectNm = srchSubjectNmList.item(i);
    				
        			if (srchValNode.getChildNodes().getLength() != 0 ) {
        				row.setSearchval(srchValNode.getFirstChild().getNodeValue());
         			   if (row.getSearchval().equals("undefined")) row.setSearchval("");
        			} else row.setSearchval("");
        			
        			if (srchModelNm.getChildNodes().getLength() != 0 ) {
        				   row.setModelnm(srchModelNm.getFirstChild().getNodeValue());
         			   if (row.getModelnm().equals("undefined")) row.setModelnm(""); 
         			} else row.setModelnm("");
         			
         			if (srchSubjectNm.getChildNodes().getLength() != 0 ) {
     					row.setSubjectnm(srchSubjectNm.getFirstChild().getNodeValue());
        			        if (row.getSubjectnm().equals("undefined")) row.setSubjectnm("");
         			} else row.setSubjectnm("");

        			
        			if (gapInNodeList1.item(i).getChildNodes().getLength() != 0 ) 
    					row.setInentitynm (gapInNodeList1.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList2.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIntablenm  (gapInNodeList2.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList3.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIncolumnseq(gapInNodeList3.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList4.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIntermnm   (gapInNodeList4.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList5.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIntermnmen (gapInNodeList5.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList6.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIninfortype(gapInNodeList6.item(i).getFirstChild().getNodeValue());
//        			if (gapInNodeList7.item(i).getChildNodes().getLength() != 0 ) 
//    					row.setIndomainnm (gapInNodeList7.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList8.item(i).getChildNodes().getLength() != 0 ) 
    					row.setInldatatype(gapInNodeList8.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList9.item(i).getChildNodes().getLength() != 0 ) 
    					row.setIndatatype (gapInNodeList9.item(i).getFirstChild().getNodeValue());
        			if (gapInNodeList10.item(i).getChildNodes().getLength() != 0 ) 
    					row.setInattributeid(gapInNodeList10.item(i).getFirstChild().getNodeValue());
    			}
    			list.add(row);  
    		}
    		
    	}catch(Exception ex){
    		logger.error("",ex);
    	}
		return list;
	}

	private String makeResultXml(List<EraDataelmt> list, String trdGb, String sAll){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
    	sb.append("<RESPONSE>\n");
    	Integer iCnt=0; 
    	
    	if(list != null){
	    	for(EraDataelmt row : list){
		    	sb.append("	<ROW>\n");
		    	if("gap".equals(trdGb)){
			    	sb.append("		<IN_ENTITYNM><![CDATA["+ UtilString.null2Blank(row.getInentitynm()) + "]]></IN_ENTITYNM>\n");
			    	sb.append("		<IN_TABLENM><![CDATA["+ UtilString.null2Blank(row.getIntablenm()) + "]]></IN_TABLENM>\n");
			    	sb.append("		<IN_COLUMNSEQ><![CDATA["+ UtilString.null2Blank(row.getIncolumnseq()) + "]]></IN_COLUMNSEQ>\n");
			    	sb.append("		<IN_TERMNM><![CDATA["+ UtilString.null2Blank(row.getIntermnm()) + "]]></IN_TERMNM>\n");
			    	sb.append("		<IN_TERMNMEN><![CDATA["+ UtilString.null2Blank(row.getIntermnmen()) + "]]></IN_TERMNMEN>\n");
			    	sb.append("		<IN_INFORTYPE><![CDATA["+ UtilString.null2Blank(row.getIninfortype()) + "]]></IN_INFORTYPE>\n");
			    	sb.append("		<IN_DOMAINNM><![CDATA["+ UtilString.null2Blank(row.getIndomainnm()) + "]]></IN_DOMAINNM>\n");
			    	sb.append("		<IN_LDATATYPE><![CDATA["+ UtilString.null2Blank(row.getInldatatype()) + "]]></IN_LDATATYPE>\n");
			    	sb.append("		<IN_DATATYPE><![CDATA["+ UtilString.null2Blank(row.getIndatatype()) + "]]></IN_DATATYPE>\n");
			    	sb.append("		<IN_ATTRIBUTEID><![CDATA["+ UtilString.null2Blank(row.getInattributeid()) + "]]></IN_ATTRIBUTEID>\n");
		    	}
		    	sb.append("		<TERMNM><![CDATA["      + UtilString.null2Blank(row.getTermnm()) + "]]></TERMNM>\n");
		    	sb.append("		<TERMNMEN><![CDATA["    + UtilString.null2Blank(row.getTermnmen()) + "]]></TERMNMEN>\n");
		    	sb.append("		<LDATATYPE><![CDATA["    + UtilString.null2Blank(row.getDataType()) + "]]></LDATATYPE>\n");
		    	sb.append("		<DATATYPE><![CDATA["    + UtilString.null2Blank(row.getDataType()) + "]]></DATATYPE>\n");
		    	sb.append("		<DOMAINNM><![CDATA["    + UtilString.null2Blank(row.getDomainnm()) + "]]></DOMAINNM>\n");
		    	sb.append("		<DOMAINNMEN><![CDATA["  + UtilString.null2Blank(row.getDomainnmen()) + "]]></DOMAINNMEN>\n");
		    	sb.append("		<INFORTYPE><![CDATA["    + UtilString.null2Blank(row.getInfotype()) + "]]></INFORTYPE>\n");
		    	sb.append("		<DESCRIPTION><![CDATA[" + UtilString.null2Blank(row.getDescription()) + "]]></DESCRIPTION>\n");    	
		    	sb.append("	</ROW>\n");

		    	if ("all".equals(sAll)) { iCnt = iCnt + 1; if (iCnt > 100) break; } 
	    	}
    	}
    	sb.append("</RESPONSE>\n");
    	
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





