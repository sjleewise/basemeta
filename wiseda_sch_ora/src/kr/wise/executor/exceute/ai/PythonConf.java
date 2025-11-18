/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : PythonConf.java
 * 2. Package : kr.wise.executor.exceute.ai
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 1. 25. 오전 10:40:13
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 1. 25. :            : 신규 개발.
 */
package kr.wise.executor.exceute.ai;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.Logger;

import kr.wise.commons.ConnectionHelper;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dm.TargetDbmsDM;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : PythonConf.java
 * 3. Package  : kr.wise.executor.exceute.ai
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 1. 25. 오전 10:40:13
 * </PRE>
 */
public class PythonConf {
	
	private static final Logger logger = Logger.getLogger(PythonConf.class);
	
	public static final String PYTHON_PROPERTIES_FILENAME = "python.properties";
	
//	public static  String PYTHON_EXECUTE_PATH = ""; //"D:/ide/Anaconda3/python";
//	
//	public static  String PYTHON_SCRIPT_PATH  = ""; //"D:/ide/work_ai/wisead_sch/scheduler/python/script/";
//	
//	public static  String PYTHON_DATA_PATH    = ""; //"D:/ide/work_ai/wisead_sch/scheduler/python/data/json/"; 
//	
//	public static  String PYTHON_IMG_PATH     = ""; //"D:/ide/work_ai/wisead/src/main/webapp/img/advisor/"; 
	
	private String python_exec_path = null;
	private String python_script_path = null; 
	private String python_data_path = null;
	private String python_udfotl_path = null;
	private String python_img_path = null;
	
	
	private static PythonConf instance = null;
	
	private static PythonConf getInstance() throws Exception {
		if(instance == null) {
			instance = new PythonConf();
//			instance.initValue();
		}
		return instance;
	}
	
	private PythonConf() throws Exception {
		initValue(); 
	}
	
	private void initValue() throws Exception {
		
		if(python_exec_path == null){
			
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(PYTHON_PROPERTIES_FILENAME);
			
			Properties properties = new Properties();
			properties.load(inputStream);
			
			python_exec_path   = properties.getProperty("PYTHON_EXECUTE_PATH").trim();
			python_script_path = properties.getProperty("PYTHON_SCRIPT_PATH").trim();
			python_data_path   = properties.getProperty("PYTHON_DATA_PATH").trim();
			python_img_path    = properties.getProperty("PYTHON_IMG_PATH").trim();
			
			python_udfotl_path   = properties.getProperty("PYTHON_UDFOTL_PATH").trim();
		}
	
	}
	
	
	public static String getPYTHON_EXECUTE_PATH() throws Exception {  
		return getInstance().python_exec_path;
	}
	
	public static String getPYTHON_SCRIPT_PATH() throws Exception {
		return getInstance().python_script_path;
	}

	public static String getPYTHON_DATA_PATH() throws Exception {
		return getInstance().python_data_path;
	}
	
	public static String getPYTHON_IMG_PATH() throws Exception {
		return getInstance().python_img_path;
	}
	
	public static String getPYTHON_UDFOTL_PATH() throws Exception {
		return getInstance().python_udfotl_path;
	}
	
	
	/** @param tgtdb
	/** @return insomnia */
	public static String getTgtdbStr(TargetDbmsDM tgtdb) {  
		String pydburl = "";
		String dbType = tgtdb.getDbms_type_cd();
		int idx = tgtdb.getConnect_string().indexOf("@");
		int lidx_c = tgtdb.getConnect_string().lastIndexOf(":");
		int lidx_s = tgtdb.getConnect_string().lastIndexOf("/");
		
		int lidx = lidx_c > lidx_s ? lidx_c : lidx_s;
		
		String ipAddr = tgtdb.getConnect_string().substring(idx+1, lidx);
		if(tgtdb.getDbLinkNm() == null || tgtdb.getDbLinkNm().equals(""))
			tgtdb.setDbLinkNm(tgtdb.getConnect_string().substring(lidx+1));
		
		if ("ORA".equals(dbType)) {
			//오라클일 경우...  "oracle+cx_oracle://wiseda:wise1012@fds"
			//윈도우일 때, 아래 형식으로 connect 할 수 있음
			//pydburl = "oracle+cx_Oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+tgtdb.getDbLinkNm();
			
			//CentOS7일 때, 아래 형식으로 connect 할 수 있음
			pydburl = "oracle://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+ipAddr+"/"+tgtdb.getDbLinkNm();
		}else if ("MSQ".equals(dbType)) { 
			//MS-SQLSERVER
			pydburl = "mssql+pyodbc://" + tgtdb.getDb_user() + ":"+tgtdb.getDb_pwd()+"@"+ tgtdb.getDbLinkNm() +"/"+ tgtdb.getDbms_enm() +"?driver=ODBC+Driver+13+for+SQL+Server";
		}else if ("HIV".equals(dbType)) { 
			//Hive
			int i = ipAddr.lastIndexOf("/");
			pydburl = "hive://" + ipAddr.substring(i+1) + "/"+ tgtdb.getDbLinkNm();
		}
		
		return pydburl;
	}

}
