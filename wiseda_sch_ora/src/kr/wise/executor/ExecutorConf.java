package kr.wise.executor;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.Logger;

import kr.wise.commons.ConnectionHelper;

public class ExecutorConf {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExecutorConf.class);
	
	public static final String WISEDA_PROPERTIES_FILENAME    = "daexecutor.properties";
	public static final String SCHEDULER_PROPERTIES_FILENAME = "schedulerRMI.properties";
	
	private static ExecutorConf instance = null;

    Integer threadCount  = null;
    Integer errDataLimit = null;
    Integer colDataLimit = null;
    Integer pkDataLimit = null;
	String da_driver     = null;
	String da_jdbcUrl    = null;
	String da_tns    = null;
	String da_user       = null;
	String da_password   = null;
	String meta_driver   = null;
	String meta_jdbcUrl  = null;
	String meta_user     = null;
	String meta_password = null;
	
	String ssh_host     = null;
	Integer ssh_port = null;
	String ssh_user     = null;
	String ssh_pass = null;
	String ssh_path = null;
	
	private void initValue() throws Exception {
		if(threadCount == null) {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(ExecutorConf.WISEDA_PROPERTIES_FILENAME);
			
			Properties properties = new Properties();
			properties.load(inputStream);
			
			threadCount  = new Integer(properties.getProperty("THREAD_COUNT").trim());
			errDataLimit = new Integer(properties.getProperty("ERR_DATA_LIMIT").trim());
			colDataLimit = new Integer(properties.getProperty("COL_DATA_LIMIT").trim());
			pkDataLimit = new Integer(properties.getProperty("PK_DATA_LIMIT").trim());

			if(threadCount.intValue() <= 0) {
				throw new Exception("Thread Count 를 0보다 크게 설정하십시오.");
			}

			da_driver     = properties.getProperty("DA_JDBC_DRIVER");
			da_jdbcUrl    = properties.getProperty("DA_DB_URL");
			da_tns    	  = properties.getProperty("DA_DB_TNS");
			da_user       = properties.getProperty("DA_DB_USER");
			da_password   = properties.getProperty("DA_DB_PASS");
			
			if("".equals(da_driver.trim()) || "".equals(da_jdbcUrl.trim()) || "".equals(da_user.trim()) || "".equals(da_password.trim())) {
				throw new Exception("DQ 접속정보를 입력하십시오.");
			}
			Connection con = null;
			try {
				con = ConnectionHelper.getConnection(da_driver, da_jdbcUrl, da_user, da_password);
			} catch(Exception e) {
				logger.error(e);
				throw new Exception("접속테스트에 실패하였습니다. (" + e.getMessage() + ")");
			} finally {
				if(con != null) try { con.close(); } catch(Exception igonred) {}
			}
			
			meta_driver   = properties.getProperty("META_JDBC_DRIVER");
			meta_jdbcUrl  = properties.getProperty("META_DB_URL");
			meta_user     = properties.getProperty("META_DB_USER");
			meta_password = properties.getProperty("META_DB_PASS");
			
//			ssh_host = properties.getProperty("SSH_HOST");
//			ssh_port = new Integer(properties.getProperty("SSH_PORT").trim());
//			ssh_user = properties.getProperty("SSH_USER");
//			ssh_pass = properties.getProperty("SSH_PASS");
//			ssh_path = properties.getProperty("SSH_PATH");
			
		}
	}
	
	private ExecutorConf() throws Exception {
		initValue();
	}
	
	private static ExecutorConf getInstance() throws Exception {
		if(instance == null) {
			instance = new ExecutorConf();
//			instance.initValue();
		}
		return instance;
	}
	
	public static int getThreadCount() throws Exception {
		return getInstance().threadCount.intValue();
	}

	public static int getErrDataLimit() throws Exception {
		return getInstance().errDataLimit.intValue();
	}

	public static int getColDataLimit() throws Exception {
		return getInstance().colDataLimit.intValue();
	}

	public static int getPkDataLimit() throws Exception {
		return getInstance().pkDataLimit.intValue();
	}

	public static String getDa_driver() throws Exception {
		return getInstance().da_driver;
	}

	public static String getDa_jdbcUrl() throws Exception {
		return getInstance().da_jdbcUrl;
	}

	public static String getDa_user() throws Exception {
		return getInstance().da_user;
	}

	public static String getDa_password() throws Exception {
		return getInstance().da_password;
	}

	public static String getMeta_driver() throws Exception {
		return getInstance().meta_driver;
	}

	public static String getMeta_jdbcUrl() throws Exception {
		return getInstance().meta_jdbcUrl;
	}

	public static String getMeta_user() throws Exception {
		return getInstance().meta_user;
	}

	public static String getMeta_password() throws Exception {
		return getInstance().meta_password;
	}

	public static String getDa_tns() throws Exception {
		return getInstance().da_tns;
	}

	public static String getSsh_host() throws Exception {
		return getInstance().ssh_host;
	}
	public static Integer getSsh_port() throws Exception {
		return getInstance().ssh_port;
	}
	public static String getSsh_user() throws Exception {
		return getInstance().ssh_user;
	}
	public static String getSsh_pass() throws Exception {
		return getInstance().ssh_pass;
	}
	public static String getSsh_path() throws Exception {
		return getInstance().ssh_path;
	}
}
