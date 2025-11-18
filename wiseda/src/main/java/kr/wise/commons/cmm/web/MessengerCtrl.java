package kr.wise.commons.cmm.web;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.MessengerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessengerCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private static MessengerService messengerService;
	
	/*public static void main(String[] args) throws Exception {
		messengerService.sendMsg("김부식", "psn19294", "content_test", "", "kindName_test");
	}*/
	
	public int sendMsg(String sendUser, String targetUser, String content, String url, String kindName) throws Exception {
		return messengerService.sendMsg(sendUser, targetUser, content, url, kindName);
	}
}
