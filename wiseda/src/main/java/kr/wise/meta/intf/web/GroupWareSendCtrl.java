package kr.wise.meta.intf.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.intf.service.GroupWareSendService;
import kr.wise.meta.intf.service.GroupWareSendVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.meta.intf.service.Item;
import kr.wise.meta.intf.service.Packet;

@Controller("GroupWareSendCtrl")
public class GroupWareSendCtrl {

	@Inject
	private GroupWareSendService groupWareSendService;
	
//	static class GroupWareSendVOs extends HashMap<String, ArrayList<GroupWareSendVO>> {}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/meta/intf/testGroupWareIO.do") //테스트페이지
	public String goTestGroupWareIOForm() throws Exception {		
		return "/meta/stnd/testGroupWareIO";
	
	}
	
	@RequestMapping("/intf/groupWareSend.do")
    @ResponseBody
    public void groupWareSend(GroupWareSendVO vo, String saction, Locale locale){
	//	logger.debug("GroupWardSendVo:{}", vo);
		
		Packet packet = groupWareSendService.makePacket(vo);
		
		groupWareSendService.send(packet);
    }
}
