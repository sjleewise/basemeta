package kr.wise.commons.helper;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionBindingListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

	public void valueBound(HttpSessionBindingEvent arg0) {
		// 세션 생성 이벤트(로그인 기록)
		logger.info("{} is login." + userId);
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// 세션 종료 이벤트(로그아웃 기록)
		logger.info("{} is logout.", userId);
	}
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
