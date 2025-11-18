package kr.wise.commons.cmm.service;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.util.WebUtil;

import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;





/**

 * @Class Name  : EgovFileMngUtil.java

 * @Description : 메시지 처리 관련 유틸리티

 * @Modification Information

 *

 *     수정일         수정자                   수정내용

 *     -------          --------        ---------------------------

 *   2009.02.13       이삼섭                  최초 생성

 *   2011.08.09       서준식                  utl.fcc패키지와 Dependency제거를 위해 getTimeStamp()메서드 추가

 * @author 공통 서비스 개발팀 이삼섭

 * @since 2009. 02. 13

 * @version 1.0

 * @see

 *

 */

@Component("FileManagerUtil")

public class FileManagerUtil {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FileManagerUtil.class);


    public static final int BUFF_SIZE = 2048;

//    @Value("#{configure['upload.dir']}") private static String uploadpath;
    public static String uploadpath = WiseConfig.UPLOAD_PATH;



//    @Resource(name = "egovFileIdGnrService")
    @Inject
    private EgovIdGnrService egovFileIdGnrService;
    
    @Inject
    private MessageSource message;


    /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {

	int fileKey = fileKeyParam;



	String storePathString = "";

	String atchFileIdString = "";

	
//	uploadpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.UPLOAD_PATH, null, Locale.getDefault()); 
	storePathString = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.UPLOAD_PATH, null, Locale.getDefault()); 
	LOG.debug("uploadpath:{}", storePathString);
/*	if ("".equals(storePath) || storePath == null) {

	    storePathString = EgovProperties.getProperty("Globals.fileStorePath");

	} else {

	    storePathString = EgovProperties.getProperty(storePath);

	}*/



	if ("".equals(atchFileId) || atchFileId == null) {

	    atchFileIdString = egovFileIdGnrService.getNextStringId();
		//TODO:id 생성은 나중에 확인...
//	    atchFileIdString = UniqueKeyGenerator.getKey();

	} else {

	    atchFileIdString = atchFileId;

	}



	File saveFolder = new File(WebUtil.filePathBlackList(storePathString));



	if (!saveFolder.exists() || saveFolder.isFile()) {

	    saveFolder.mkdirs();

	}



	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();

	MultipartFile file;

	String filePath = "";

	List<FileVO> result  = new ArrayList<FileVO>();

	FileVO fvo;



	while (itr.hasNext()) {

	    Entry<String, MultipartFile> entry = itr.next();



	    file = entry.getValue();

	    String orginFileName = file.getOriginalFilename();



	    //--------------------------------------

	    // 원 파일명이 없는 경우 처리

	    // (첨부가 되지 않은 input file type)

	    //--------------------------------------

	    if ("".equals(orginFileName)) {

		continue;

	    }

	    ////------------------------------------
	    /** 파일확장자 체크
	     * 	업로드 금지 확장자는 제외.... */
	    String file_ext = "," +orginFileName.substring(orginFileName.lastIndexOf('.') + 1)+",";
//		String allowExt = ",hwp,doc,ppt,xls,pdf,jpg,pptx,docx,xlsx,txt,zip,rar,jpg,gif,png,";
		if (WiseConfig.ALLOW_EXT.indexOf(file_ext) == -1) {
			LOG.debug("[ERROR]업로드 금지 파일: {}", orginFileName);
			throw new WiseBizException("업로드 금지 파일: "+orginFileName);
			//continue;
		}



	    int index = orginFileName.lastIndexOf(".");

	    //String fileName = orginFileName.substring(0, index);

	    String fileExt = orginFileName.substring(index + 1);

	    String newName = KeyStr + getTimeStamp() + fileKey;

	    long _size = file.getSize();



	    if (!"".equals(orginFileName)) {

		filePath = storePathString + File.separator + newName;

		file.transferTo(new File(WebUtil.filePathBlackList(filePath)));

	    }

	    fvo = new FileVO();

	    fvo.setFileExtsn(fileExt);

	    fvo.setFileStreCours(storePathString);

	    fvo.setFileMg(Long.toString(_size));

	    fvo.setOrignlFileNm(orginFileName);

	    fvo.setStreFileNm(newName);

	    fvo.setAtchFileId(atchFileIdString);

	    fvo.setFileSn(String.valueOf(fileKey));



	    //writeFile(file, newName, storePathString);

	    result.add(fvo);



	    fileKey++;

	}



	return result;

    }



    /**

     * 첨부파일을 서버에 저장한다.

     *

     * @param file

     * @param newName

     * @param stordFilePath

     * @throws Exception

     */

    protected void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {

	InputStream stream = null;

	OutputStream bos = null;



	try {

	    stream = file.getInputStream();

	    File cFile = new File(stordFilePath);



	    if (!cFile.isDirectory()) {

		boolean _flag = cFile.mkdir();

		if (!_flag) {

		    throw new IOException("Directory creation Failed ");

		}

	    }



	    bos = new FileOutputStream(stordFilePath + File.separator + newName);



	    int bytesRead = 0;

	    byte[] buffer = new byte[BUFF_SIZE];



	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {

		bos.write(buffer, 0, bytesRead);

	    }

	} catch (IOException e) {

	    //e.printStackTrace();

	    LOG.error("IGNORE:", e);	// 2011.10.10 보안점검 후속조치

	} finally {

	    if (bos != null) {

		try {

		    bos.close();

		} catch (IOException ignore) {

		    LOG.debug("IGNORED: " + ignore.getMessage());

		}

	    }

	    if (stream != null) {

		try {

		    stream.close();

		} catch (IOException ignore) {

		    LOG.debug("IGNORED: " + ignore.getMessage());

		}

	    }

	}

    }



    /**

     * 서버의 파일을 다운로드한다.

     *

     * @param request

     * @param response

     * @throws Exception

     */

    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {



	String downFileName = "";

	String orgFileName = "";



	if ((String)request.getAttribute("downFile") == null) {

	    downFileName = "";

	} else {

	    downFileName = (String)request.getAttribute("downFile");

	}



	if ((String)request.getAttribute("orgFileName") == null) {

	    orgFileName = "";

	} else {

	    orgFileName = (String)request.getAttribute("orginFile");

	}



	orgFileName = orgFileName.replaceAll("\r", "").replaceAll("\n", "");



	File file = new File(WebUtil.filePathBlackList(downFileName));



	if (!file.exists()) {

	    throw new FileNotFoundException(downFileName);

	}



	if (!file.isFile()) {

	    throw new FileNotFoundException(downFileName);

	}



	byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.



	response.setContentType("application/x-msdownload");

	response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(), "UTF-8"));

	response.setHeader("Content-Transfer-Encoding", "binary");

	response.setHeader("Pragma", "no-cache");

	response.setHeader("Expires", "0");



	BufferedInputStream fin = null;

	BufferedOutputStream outs = null;



	try {

		fin = new BufferedInputStream(new FileInputStream(file));

	    outs = new BufferedOutputStream(response.getOutputStream());

	    int read = 0;



		while ((read = fin.read(b)) != -1) {

		    outs.write(b, 0, read);

		}

	} finally {

	    if (outs != null) {

			try {

			    outs.close();

			} catch (IOException ignore) {

			    //System.out.println("IGNORED: " + ignore.getMessage());

			    LOG.debug("IGNORED: " + ignore.getMessage());

			}

		    }

		    if (fin != null) {

			try {

			    fin.close();

			} catch (IOException ignore) {

			    //System.out.println("IGNORED: " + ignore.getMessage());

			    LOG.debug("IGNORED: " + ignore.getMessage());

			}

		    }

		}

    }



    /**

     * 첨부로 등록된 파일을 서버에 업로드한다.

     *

     * @param file

     * @return

     * @throws Exception

     */

    public HashMap<String, String> uploadFile(MultipartFile file) throws Exception {



	HashMap<String, String> map = new HashMap<String, String>();

	//Write File 이후 Move File????

	String newName = "";

//	String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
	String stordFilePath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.UPLOAD_PATH, null, Locale.getDefault());
//	String stordFilePath = message.getMessage(uploadpath, null, Locale.getDefault()); 

	String orginFileName = file.getOriginalFilename();



	int index = orginFileName.lastIndexOf(".");

	//String fileName = orginFileName.substring(0, _index);

	String fileExt = orginFileName.substring(index + 1);

	long size = file.getSize();



	//newName 은 Naming Convention에 의해서 생성

	newName = getTimeStamp();	// 2012.11 KISA 보안조치

	writeFile(file, newName, stordFilePath);

	//storedFilePath는 지정

	map.put(WiseConfig.ORIGIN_FILE_NM, orginFileName);

	map.put(WiseConfig.UPLOAD_FILE_NM, newName);

	map.put(WiseConfig.FILE_EXT, fileExt);

	map.put(WiseConfig.FILE_PATH, stordFilePath);

	map.put(WiseConfig.FILE_SIZE, String.valueOf(size));



	return map;

    }



    /**

     * 파일을 실제 물리적인 경로에 생성한다.

     *

     * @param file

     * @param newName

     * @param stordFilePath

     * @throws Exception

     */

    protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {

	InputStream stream = null;

	OutputStream bos = null;



	try {

	    stream = file.getInputStream();

	    File cFile = new File(WebUtil.filePathBlackList(stordFilePath));



	    if (!cFile.isDirectory())

		cFile.mkdir();



	    bos = new FileOutputStream(WebUtil.filePathBlackList(stordFilePath + File.separator + newName));



	    int bytesRead = 0;

	    byte[] buffer = new byte[BUFF_SIZE];



	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {

		bos.write(buffer, 0, bytesRead);

	    }

	} catch (FileNotFoundException e) {

	    //e.printStackTrace();

	    //throw new RuntimeException(e);	// 보안점검 후속조치

		LOG.debug("IGNORED: " + e.getMessage());

	} finally {

	    if (bos != null) {

		try {

		    bos.close();

		} catch (IOException ignore) {

		    LOG.debug("IGNORED: " + ignore.getMessage());

		}

	    }

	    if (stream != null) {

		try {

		    stream.close();

		} catch (IOException ignore) {

		    LOG.debug("IGNORED: " + ignore.getMessage());

		}

	    }

	}

    }



    /**

     * 서버 파일에 대하여 다운로드를 처리한다.

     *

     * @param response

     * @param streFileNm

     *            : 파일저장 경로가 포함된 형태

     * @param orignFileNm

     * @throws Exception

     */

    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws Exception {

	String downFileName = streFileNm;

	String orgFileName = orignFileNm;



	File file = new File(downFileName);

	//log.debug(this.getClass().getName()+" downFile downFileName "+downFileName);

	//log.debug(this.getClass().getName()+" downFile orgFileName "+orgFileName);



	if (!file.exists()) {

	    throw new FileNotFoundException(downFileName);

	}



	if (!file.isFile()) {

	    throw new FileNotFoundException(downFileName);

	}



	//byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.

	int fSize = (int)file.length();

	if (fSize > 0) {

	    BufferedInputStream in = null;



	    try {

		in = new BufferedInputStream(new FileInputStream(file));



    	    	String mimetype = "text/html"; //"application/x-msdownload"



    	    	response.setBufferSize(fSize);

		response.setContentType(mimetype);

		response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);

		response.setContentLength(fSize);

		//response.setHeader("Content-Transfer-Encoding","binary");

		//response.setHeader("Pragma","no-cache");

		//response.setHeader("Expires","0");

		FileCopyUtils.copy(in, response.getOutputStream());

	    } finally {

		if (in != null) {

		    try {

			in.close();

		    } catch (IOException ignore) {

			

			LOG.debug("IGNORED: " + ignore.getMessage());

		    }

		}

	    }

	    response.getOutputStream().flush();

	    response.getOutputStream().close();

	}



	/*

	String uploadPath = propertiesService.getString("fileDir");



	File uFile = new File(uploadPath, requestedFile);

	int fSize = (int) uFile.length();



	if (fSize > 0) {

	    BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));



	    String mimetype = "text/html";



	    response.setBufferSize(fSize);

	    response.setContentType(mimetype);

	    response.setHeader("Content-Disposition", "attachment; filename=\""

					+ requestedFile + "\"");

	    response.setContentLength(fSize);



	    FileCopyUtils.copy(in, response.getOutputStream());

	    in.close();

	    response.getOutputStream().flush();

	    response.getOutputStream().close();

	} else {

	    response.setContentType("text/html");

	    PrintWriter printwriter = response.getWriter();

	    printwriter.println("<html>");

	    printwriter.println("<br><br><br><h2>Could not get file name:<br>" + requestedFile + "</h2>");

	    printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");

	    printwriter.println("<br><br><br>&copy; webAccess");

	    printwriter.println("</html>");

	    printwriter.flush();

	    printwriter.close();

	}

	//*/





	/*

	response.setContentType("application/x-msdownload");

	response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(),"UTF-8" ));

	response.setHeader("Content-Transfer-Encoding","binary");

	response.setHeader("Pragma","no-cache");

	response.setHeader("Expires","0");



	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));

	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

	int read = 0;



	while ((read = fin.read(b)) != -1) {

	    outs.write(b,0,read);

	}

	log.debug(this.getClass().getName()+" BufferedOutputStream Write Complete!!! ");



	outs.close();

    	fin.close();

	//*/

    }



    /**

     * 2011.08.09

     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함

     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능

     *

     * @param

     * @return Timestamp 값

     * @exception MyException

     * @see

     */

    private static String getTimeStamp() {



	String rtnStr = null;



	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))

	String pattern = "yyyyMMddhhmmssSSS";



	try {

	    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);

	    Timestamp ts = new Timestamp(System.currentTimeMillis());



	    rtnStr = sdfCurrent.format(ts.getTime());

	} catch (RuntimeException e) {

	    //e.printStackTrace();

		

	    //throw new RuntimeException(e);	// 보안점검 후속조치

	    LOG.debug("IGNORED: " + e.getMessage());

	}



	return rtnStr;

    }

}

