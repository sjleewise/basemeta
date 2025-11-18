<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>
<%
	List listCategory		= new ArrayList();
	List listModel			= new ArrayList();
	List listSubjectArea	= new ArrayList();
	List listTable			= new ArrayList();
	List listAttribute		= new ArrayList();

	Map param	 = new HashMap();

	String category_id	= request.getParameter("category_id"	);	
	String model_id		= request.getParameter("model_id"	);
	String entity_id	= request.getParameter("entity_id"	);
	String attribute_id	= request.getParameter("attribute_id"	);
	String subjectArea_id	= request.getParameter("subjectArea_id"	);
	String work		= request.getParameter("work"	);
	String viewLP		= request.getParameter("viewLP"	);

	if(category_id		!= null && !category_id		.equals("0"))  param.put("category_id"		, category_id		);
	if(model_id		!= null && !model_id		.equals("0"))  param.put("model_id"		, model_id		);
	if(entity_id		!= null && !entity_id		.equals("0"))  param.put("entity_id"		, entity_id		);
	if(attribute_id		!= null && !attribute_id	.equals("0"))  param.put("attribute_id"		, attribute_id		);
	if(subjectArea_id	!= null && !subjectArea_id	.equals("0"))  param.put("subjectArea_id"	, subjectArea_id	);

	try{
		//param.put("order_name", "entity_name");
		param.put("order_name", "category_name");
		listCategory		= ERwin.getCategoryList		(param	);
		listModel		= ERwin.getModelList		(param	);

		if(model_id != null && !"0".equals(model_id))	listSubjectArea		= ERwin.getSubjectAreaList		(param	);
		if(model_id != null && !"0".equals(model_id)) 	listTable		= ERwin.getEntityList			(param	);
	}catch(Exception e){
		System.out.println(e);
	}
	
%>

<script language>
	
	function changeParam(){
		var fx = document.fx;	
		fx.action	= "r9_search.jsp";
		fx.target	= "_self";
		fx.submit();
	}

	function checkParam(){
		var fx = document.fx;	
		if(fx.model_id[0].checked == true){
			
		}

	}



	var startTime;

	function goSearch(){
		var fx = document.fx;	

		if(fx.model_id[0].selected == true){
			alert('모델 1개는 선택하여햐 합니다.');
			return ;
		}

		startTime = new Date();
		document.getElementById("searchButton").innerText = "조회중";
		if(document.fx.work[0].checked == true) fx.action	= "r9_result_entity.jsp";
		if(document.fx.work[1].checked == true) fx.action	= "r9_result_attri.jsp";
		if(document.fx.work[2].checked == true) fx.action	= "r9_result_index.jsp";
		if(document.fx.work[3].checked == true) fx.action	= "r9_result_relation.jsp";
		if(document.fx.work[4].checked == true) fx.action	= "r9_result_relation_attri.jsp";
		fx.target = "iframe_result";
		fx.submit();
	}
	
	function enterkey() {
        if (window.event.keyCode == 13) {
             // 엔터키가 눌렸을 때 실행할 내용
             goSearch();
        }
	}	
</script>

<form name="fx" method="post">

<table style="font-size:12">
	<tr>

		<td>
			<input type="radio" name="work" value="entity"		<%=  "entity".equals(work) | work == null ? "checked" : ""   %> >Entity
			<br>
			<input type="radio" name="work" value="attri"		<%=  "attri".equals(work) ? "checked" : ""   %> >Attribute
		</td>
		<td>
			<input type="radio" name="work" value="index"		<%=  "index".equals(work) ? "checked" : ""   %> >Index
			<br>
			<input type="radio" name="work" value="relation"	<%=  "relation".equals(work) ? "checked" : ""   %> >Relation
			<br>
			<input type="radio" name="work" value="relationAttri"	<%=  "relationAttri".equals(work) ? "checked" : ""   %> >Relation Attri
		</td>
		<td>
			<input type="radio" name="viewLP" value="0"	 checked> Logi/Physi
			<br>
			<input type="radio" name="viewLP" value="L"	 >Logical
			<br>
			<input type="radio" name="viewLP" value="P"	 >Physical
		</td>
		<td>
		</td>
		<td>
			<a style="color:red;font-size:20" id="searchButton" href="javascript:goSearch()" >검색</a>
		</td>
		
		<td>&nbsp;&nbsp;
		</td>
		
		<td> 카테고리<br>
			<select name = "category_id" id = "category_id" onChange="javascript:changeParam()">
				<option value="0" >선택</option>
				
<%
		for(int i=0; i< listCategory.size(); i++){
			Map map = (Map)listCategory.get(i);
			
%>
				<option value="<%= map.get("category_id") %>"  <%=  (String.valueOf(map.get("category_id")).equals(category_id) ? "selected" : ""  )  %> ><%= map.get("category_name") %></option>
<%
		}
%>
			</select>
		</td>

		<td>|</td>

		<td> 모델<br>
			<select name = "model_id" id = "model_id" onChange="javascript:changeParam()">
				<option value="0" >선택</option>
				
<%
		for(int i=0; i< listModel.size(); i++){
			Map map = (Map)listModel.get(i);
			
%>
				<option value="<%= map.get("model_id") %>" <%=  (String.valueOf(map.get("model_id")).equals(model_id) ? "selected" : ""  )  %> ><%= map.get("model_name") %></option>
<%
		}
%>
			</select>
		</td>

		<td>|</td>

		<td> 주제영역<br>
			<select name = "subjectArea_id" id = "subjectArea_id" onChange="javascript:changeParam()">
				<option value="0" >선택</option>
				
<%
		for(int i=0; i< listSubjectArea.size(); i++){
			Map map = (Map)listSubjectArea.get(i);
			
%>
				<option value="<%= map.get("subjectArea_id") %>" <%=  (String.valueOf(map.get("subjectArea_id")).equals(subjectArea_id) ? "selected" : ""  )  %> ><%= map.get("subjectArea_name").toString().replaceAll("<", "&lt").replaceAll(">", "&gt") %></option>
<%
		}
%>
			</select>
		</td>


		<td>|</td>
		<td> 테이블<br>
			<select name = "entity_id" id = "entity_id" onChange="javascript:changeParam()">
				<option value="0" >선택</option>
				
<%
		for(int i=0; i< listTable.size(); i++){
			Map map = (Map)listTable.get(i);
			
%>
				<option value="<%= map.get("entity_id") %>" <%=  (String.valueOf(map.get("entity_id")).equals(entity_id) ? "selected" : ""  )  %> ><%= map.get("entity_name") %></option>
<%
		}
%>
			</select>
		</td>
		<td>
			<a style="color:red;font-size:20" id="searchButton" href="javascript:goSearch()" >검색</a>
		</td>
				
		<td>
			entity명:<input type="text" name="entity_name" onkeyup="enterkey();" style="font-size:11" size="15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<br>
			table명:<input type="text" name="entity_name_physical" onkeyup="enterkey();" style="font-size:11" size="15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>

			

	</tr>
</table>

</form>


