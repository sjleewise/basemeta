<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"
		 import="java.util.*"
%>

<script type="text/javascript">
	function deleteModelSvg(){
		var modelName = document.deleteModelSvg.modelName.value;
		document.location.href="/webviewer41/admin/batch/deleteModelSvg.do?modelName="+modelName;
		
	}
</script>
<%

	String realPath = request.getSession().getServletContext().getRealPath("/");
	String ip = java.net.InetAddress.getLocalHost().getHostAddress();
	String host = java.net.InetAddress.getLocalHost().getHostName();
	String retStr = "";
	
	if(this.getClass().getResource("/") != null) out.println("/ : " + this.getClass().getResource("/").toString());
	if(this.getClass().getResource(".") != null) out.println("<br>. : " + this.getClass().getResource(".").toString());
	if(this.getClass().getResource("")  != null) out.println("<br>'': " + this.getClass().getResource("").toString());	
%>

<script language>
	
	function goMeta(url){
		url = encodeURI(url);
		document.form.action=url;
		document.form.submit();
	
	}
</script>
	
<h3>request Path : <%= realPath %></h3>
<h3>ip : <%= ip %></h3>
<h3>host : <%= host %></h3>


<form name="form">
<input type="hidden" name="libraryName" 	value="TEST">
<input type="hidden" name="modelName" 		value="TEST">
<input type="hidden" name="subjectName" 	value="sub1한글">
<input type="hidden" name="diagramName" 	value="dia2">
<input type="hidden" name="accessId" 		value="meta">

</form>
<a href="/webviewer41/metamain.do?libraryName=TEST&modelName=TEST&subjectName=sub1한글&diagramName=한글&pl=Logical&accessId=meta">메타연동</a>
<a href="javascript:goMeta('/webviewer41/metamain.do')">메타연동</a>
<br>
</br>

<a href="../admin/batch/deleteModelSvg.do">SVG 삭제</a>


<%
	String w_db_name = "";
	if(System.getProperty("os.name").toLowerCase().indexOf("window") > -1){
		w_db_name = "w_db_win.xml";
	}else{
		w_db_name = "w_db_unix.xml";
	}

%>

<h5>** 계정홈 : <%= System.getProperty("user.home") + System.getProperty("file.separator") %>  <br>** 실행위치 : <%= System.getProperty("user.dir") + System.getProperty("file.separator") %></h5>


<h3>user.home : <%= System.getProperty("user.home") + System.getProperty("file.separator") + w_db_name %></h3>
<%
	StringBuilder contents = new StringBuilder();
	try{
		
		String file_path = System.getProperty("user.home")+System.getProperty("file.separator") + w_db_name;
		File db_config_file = new File(file_path);
		
		if(db_config_file.exists()){
			BufferedReader input =  new BufferedReader(new FileReader(db_config_file));
	        String line = null; 
			try{
		        while (( line = input.readLine()) != null){
		          contents.append(line);
		          contents.append(System.getProperty("line.separator"));
		        }
			} finally {input.close();}
			
		}else{
			out.println("존재 하지 않음 ㅠㅠ");
		}
		
	}catch (Exception ex){ex.printStackTrace();}
%>

	<textarea cols="100" rows="10"> 
	<%=  contents %>
	</textarea>
