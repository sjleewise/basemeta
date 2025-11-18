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

	long long1 = 0;
	long long2 = 0;
	
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
			long1 = System.currentTimeMillis();
			list	= ERwin.getComboRelationList	(param	);
			long2 = System.currentTimeMillis();

			if(list.size() > 0){
				Map map = (Map)list.get(0);
				String model_name 	= (String)map.get("model_name");
				out.println("<b>model_id : "+model_id +" - model_name : "+model_name +"</b> - list cnt : "+list.size() +" 출력전, 소요시간 초 "+ (long2 - long1) / 1000 );
			}

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
		<td>no</td>
		<td>관계명</td>
		<td>Relation</td>

		<td>부엔티티</td>
		<td>부tanle</td>
		<td>자엔티티</td>
		<td>자table</td>

		<td>관계유형</td>
		<td>카디널티</td>
		<td>null YN</td>
		<td>부 주제영역</td>
		<td>자 주제영역</td>

	</tr>

<%
if(list != null){
	for(int i=0; i<list.size(); i++){
		//if(i > 1000) break;
		Map map = (Map)list.get(i);
%>
		<tr>
			<td><%= i+1 %></td><!--  relation_id : <%= map.get("relation_id")%> -->
			<td><input type=text value="<%= map.get("relation_name") %>" style="font-size:12" size="10" readOnly ></td>
			<td><input type=text value="<%= map.get("relation_name_physical") %>" style="font-size:12" size="10" readOnly ></td>
			
			<td><input type=text value="<%= map.get("relation_entity_name_parent"			)%>"	style="font-size:11" size="25" readOnly size=20></td>
			<td><input type=text value="<%= map.get("relation_entity_name_physical_parent"	)%>"	style="font-size:11" size="25" readOnly size=20></td>
			<td><input type=text value="<%= map.get("relation_entity_name_child"			)%>"	style="font-size:11" size="25" readOnly size=20></td>
			<td><input type=text value="<%= map.get("relation_entity_name_physical_child"	)%>"	style="font-size:11" size="25" readOnly size=20></td>

			<td align="center"><%= map.get("relation_type")%></td>
			<td align="center"><%= map.get("relation_cardinality")%></td>
			<td align="center"><%= map.get("relation_null_YN")%></td>
			<td><input type=text value="<%= map.get("relation_subjectArea_name_parent"			)%>" style="font-size:12" size="25" readOnly size=20></td>
			<td><input type=text value="<%= map.get("relation_subjectArea_name_child"	)%>" style="font-size:11" size="25" readOnly size=20></td>

			<td>
<%
StringBuffer sb = new StringBuffer();

Object[] keys = map.keySet().toArray();
Arrays.sort(keys);
for (int j = 0; j < keys.length; j++) {
	Object key 		= (String)keys[j];
	Object value 	= map.get(key);
	if("1073742128".equals(key) || "1075851071".equals(key) || "1075851072".equals(key) || "1075851272".equals(key) || "1075851263".equals(key)  || "1075852262".equals(key) ) continue;
	sb.append(" #@! "+key);
	try{
		String str = value.toString();
		if(str.length() >0 ) {
			if((int)str.charAt(0) == 0){
				sb.append("-******");
			}else{
					sb.append(" : "+value);
			}
		}
	}catch(Exception e){
	}	
}
%>			
				  <input type=text style="font-size:11" size="10" readOnly value="<%= sb.toString() %>">	
			</td>
		</tr>
<%
	}
}
%>

</table>
<%
	long long3 = System.currentTimeMillis();
	out.println("출력 후, 소요시간 "+ ((long3 - long1) / 1000) );
%>