package kr.wise.commons.cmm.service;

public interface MessengerService {
	public int sendMsg(String sendUser, String targetUser, String content, String url, String kindName) throws Exception;
}
