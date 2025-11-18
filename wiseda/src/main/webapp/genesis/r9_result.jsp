<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>

<%

	String work			= request.getParameter("work"	);	

	List list		= null;
	Map param	 = new HashMap();

	String category_id				= request.getParameter("category_id"	);	
	String model_id					= request.getParameter("model_id"		);
	String subjectArea_id	= request.getParameter("subjectArea_id"	);
	String entity_id				= request.getParameter("entity_id"		);
	String entity_name				= request.getParameter("entity_name"	);
	String attribute_id				= request.getParameter("attribute_id"	);

	String entity_name_physical			= request.getParameter("entity_name_physical"	);
	String viewLP			= request.getParameter("viewLP"	);

	if("1".equals(work)){
		// 첫페이지. 아무것도 안함.
	}else{


		if(category_id		!= null && !category_id		.equals("0"))  param.put("category_id"		, category_id		);
		if(model_id			!= null && !model_id		.equals("0"))  param.put("model_id"			, model_id			);
		if(entity_id		!= null && !entity_id		.equals("0"))  param.put("entity_id"		, entity_id			);
		if(attribute_id		!= null && !attribute_id	.equals("0"))  param.put("attribute_id"		, attribute_id		);
		if(subjectArea_id	!= null && !subjectArea_id	.equals("0"))  param.put("subjectArea_id", subjectArea_id	);
		
		if(entity_name			!= null )  param.put("entity_name"			, entity_name				);
		if(entity_name_physical	!= null )  param.put("entity_name_physical"	, entity_name_physical		);

		param.put("order_name", "entity_name");
		param.put("viewLP", viewLP);

		try{

			list	= ERwin.getComboList2	(param	);

			out.println("list cnt : "+list.size());
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

%>

<script language>
	var endTime = new Date();
	if (parent.iframe_search.document.getElementById("searchButton") != undefined){ 
		parent.iframe_search.document.getElementById("searchButton").innerText = "검색";
	}
	//alert((endTime - parent.iframe_search.startTime  ) / 1000);

</script>

<table border=1 style="font-size:12">
	<tr style="font-size:13; color:blue">
		<td></td>
		<td>카테<br>고리</td>
		<td>모델</td>
		<td>주제<br>영역</td>
		<td>엔티티</td>
		<td>table</td>
<td>entity<br>Owneer</td>
		<td>속성<br>no</td>
		<td>속성</td>
		<td>column</td>

		<td>PK<br>YN</td>
		<td>FK<br>YN</td>
		<td>Null<br>YN</td>
		<td>도메인</td>
		<td>Data Type</td>
		<td>Comment</td>
		<td>Definition</td>
	</tr>

<%
if(list != null){
	for(int i=0; i<list.size(); i++){
		if(i > 1000) break;
		Map map = (Map)list.get(i);
%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= map.get("category_name")%></td>
			<td><%= map.get("model_name")%></td>
			<td>
				<input type=text style="font-size:11" size="20" readOnly size=20 value="<%= map.get("subjectArea_name") %>">
			</td>
			<td><input type=text value="<%= map.get("entity_name"				)%>" style="font-size:12" size="22" readOnly size=20></td>
			<td><input type=text value="<%= map.get("entity_name_physical"		)%>" style="font-size:11" size="25" readOnly size=20></td>
			<td align="center"><%= map.get("entity_owner_name")%></td>
			<td align="center"><%= map.get("attribute_order")%></td>
			<td><input type=text value="<%= map.get("attribute_name"			)%>" style="font-size:12" size="25" readOnly size=20></td>
			<td><input type=text value="<%= map.get("attribute_name_physical"	)%>" style="font-size:11" size="25" readOnly size=20></td>


			<td align="center"><%= map.get("attribute_key_YN")%></td>
			<td align="center"><%= map.get("attribute_key_fk_YN")%></td>
			<td align="center"><%= map.get("attribute_null_YN")%></td>
			<td><input type=text value="<%= map.get("attribute_domain_name")%>" readOnly size=10></td>
			<td><%= map.get("attribute_data_type_physical")%></td>
			<td><input type=text value="<%= (map.get("attribute_comment") != null ) ? map.get("attribute_comment") : "" %>" readOnly size=10></td>
			<td><input type=text value="<%= (map.get("attribute_definition") != null ) ? map.get("attribute_definition") : "" %>" readOnly size=10></td>
		</tr>
<%
	}
}
%>

</table>

<%
if(list != null && list.size() > 1000){
%>

<table>
<tr>
<td>
<textarea cols="200" rows="80">
<%
	out.println("no|카테고리|모델|주제영역|엔티티|table|속성|column|PK YN|FK YN|Null YN|도메인|Data Type|Comment|Definition");
	for(int i=0; i<list.size(); i++){
		Map map = (Map)list.get(i);

		out.print(i+1);
		out.print("|"+map.get("category_name"));
		out.print("|"+map.get("model_name"));
		out.print("|");
						out.print(map.get("subjectArea_name"));
		out.print("|"+map.get("entity_name"));
		out.print("|"+map.get("entity_name_physical"));
		out.print("|"+map.get("attribute_name"));
		out.print("|"+map.get("attribute_data_type_physical"));
		out.print("|"+map.get("attribute_comment"));
		out.print("|"+map.get("attribute_definition"));

		out.println();
	}
	%>
</textarea>
</td>
</tr>
</table>
<%
}
%>