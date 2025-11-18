package kr.wise.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0<br>
 * 클래스 설명 ------------------------------------------------------------------------<br>
 * 외부 명령을 실행하였을시 결과로 나오는 각종 결과 log를 획득하는 Class<br>
 * <br>
 * ----------------------------------------------------------------------------------<br>
 */
public class CStreamGobbler extends Thread {
	/** for Log <code>logger</code> */
	Logger logger = LoggerFactory.getLogger(getClass());
	
	InputStream	is;
	String		type;
	Result result;

	/**
	 * 생성자
	 * @param is InputStream
	 * @param type log Type(ERROR, OUTPUT)
	 */
	public CStreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
		this.result = new Result();
	}

	/** (non-Javadoc)
	 * Thread의 implement형태로써 입력되는 inputStream을 log화 시킨다.
	 * @see java.lang.Runnable#run()
	 * 
	 */
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sf = new StringBuffer();
	
			while ((line = br.readLine()) != null) {
//			    logger.debug(type + ">" + line);
//			    if ("ERROR".equals(type)) 
			    	sf.append(line).append("\n");
			    
			}
//			if ("ERROR".equals(type)) 
				result.setOutmsg(sf.toString());
		} catch (IOException ioe) {
		    logger.error("외부 명령 호출시 Error ", ioe);
		} catch (Exception e) {
		    logger.error("외부 명령 호출시 Error ", e);
		}
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}
}