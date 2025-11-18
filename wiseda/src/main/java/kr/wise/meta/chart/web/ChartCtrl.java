package kr.wise.meta.chart.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import kr.wise.commons.rqstmst.service.WaqMstr;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ChartCtrl")
public class ChartCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Autowired
	ServletContext context; 
	
    @RequestMapping("/meta/chart/data_dictionary.do")
    public String goDataDictionary(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);
    	
    	JSONParser parser = new JSONParser();
	   	String path = context.getRealPath("/") + "js"+ File.separator + "d3"+ File.separator + "data_dictionary.json";
	   	 
	   	JSONObject jsonObject = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF8")));
    	
    	model.addAttribute("data",jsonObject);

    	return "/meta/chart/data_dictionary";

    }

    @RequestMapping("/meta/chart/reg_rqst_flow.do")
    public String goRegRqstFlow(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);
    	
    	JSONParser parser = new JSONParser();
    	String path = context.getRealPath("/") + "js"+ File.separator + "d3"+ File.separator + "reg_rqst_flow.json";
    	
    	JSONObject jsonObject = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF8")));
    	
    	model.addAttribute("data",jsonObject);
    	
    	return "/meta/chart/reg_rqst_flow";
    	
    }
}
