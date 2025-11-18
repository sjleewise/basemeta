<%@page import="kr.wise.commons.util.UniqueKeyGenerator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="java.io.File, java.util.List, java.io.IOException"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.http.protocol.HttpContext"%>

<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
</head>
<body>
<%
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);  //multipart로 전송되었는가 체크
	if (isMultipart) {
		// 설정단계
 		File temporaryDir = new File("c:/ide/tmp/");  //업로드된 파일의 임시 저장 폴더
 		String realDir = config.getServletContext().getRealPath(request.getParameter("realDir"));  //실제 저장될 파일경로
 		String sFunc = request.getParameter("CKEditorFuncNum");
 		String realUrl = request.getParameter("realUrl");

 		// 디스크 기반의 파일 아이템 팩토리 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1 * 1024 * 1024);  //최대 메모리 크기
		factory.setRepository(temporaryDir);  // 임시저장폴더 연결

		// 구현단계
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10 * 1024 * 1024);  //최대 업로드 크기
		List<FileItem> items = upload.parseRequest(request); //이 부분에서 파일이 생성
		Iterator iter = items.iterator();
 
		while (iter.hasNext()) {
			FileItem fileItem = (FileItem) iter.next();
			
			if (fileItem.isFormField()) {  // File 컴포넌트가 아닌 일반 컴포넌트일 경우
				out.println(fileItem.getFieldName() + "=" + fileItem.getString("euc-kr") + "<br/>");
			}else{
				if (fileItem.getSize() > 0) {  //파일이 업로드 되었나 안되었나 체크
					
					String fieldName = fileItem.getFieldName();
					String fileName = fileItem.getName();
					String contentType = fileItem.getContentType();
					boolean isInMemory = fileItem.isInMemory();
					long sizeInBytes = fileItem.getSize();
					
					/* 
			 		out.println("[realDir] : "+ realDir +"<br/>");
			     	out.println("[fieldName] : "+ fieldName +"<br/>");
			        out.println("[fileName] : "+ fileName +"<br/>");
			        out.println("[contentType] : "+ contentType +"<br/>");
			        out.println("[isInMemory] : "+ isInMemory +"<br/>");
			        out.println("[sizeInBytes] : "+ sizeInBytes +"<br/>");
			 		*/
			 		
			 		//한글파일명을 랜덤파일명으로 변경한다.
			 		String newFileName = UniqueKeyGenerator.getKey() + fileName.substring(fileName.lastIndexOf('.'));
			 		//NKIS 포털에서 보려면 앞에 http://www.nkis.re.kr 정보가 추가되어야 함.
			 		String httpName = "";
			 		
			 		
			 		out.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + sFunc + ", '"+ httpName + realUrl + newFileName + "', '완료');</script>");

			 		try {
			 			File uploadedFile = new File(realDir, newFileName);
			 			fileItem.write(uploadedFile);  //실제 디렉토리에 카피
			 			fileItem.delete();   //temp폴더의 파일 제거
			 		} catch(IOException ex) {
			 			out.println("error : "+ ex +"<br/>");
			 		}
				}
			}
		}
	} else {
// 		out.println("인코딩 타입이 multipart/form-data 가 아님.");
	}
%>
</body>
</html>