<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.service.c" %>
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
			
			
			
			Object[] keys = param.keySet().toArray();
			Arrays.sort(keys);
			for (int j = 0; j < keys.length; j++) {
				Object key 		= (String)keys[j];
				Object value 	= param.get(key);
				System.out.println(key +" : "+value);
				
			}
			
			
			long1 = System.currentTimeMillis();
			list	= ERwin.getComboList2	(param	);
			
			
			long2 = System.currentTimeMillis();

if(list.size() > 0){
	Map map = (Map)list.get(0);
	String model_name 	= (String)map.get("model_name");
	out.println("<b>model_id : "+model_id +" - model_name : "+model_name +"</b> - list cnt : "+list.size() +"출력 전, 소요시간 초 "+ (long2 - long1) / 1000 );
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
		<td>주제<br>영역</td>
		<td>엔티티</td>
		<td>table</td>
		<td>속성</td>
		<td>column</td>

		<td>PK<br>YN</td>
		<td>FK<br>YN</td>
		<td>Null<br>YN</td>
		<td>Logi<br>YN</td>
		<td>Physi<br>YN</td>
		<td>Logi<br>No</td>
		<td>Logi<br>No2</td>
		<td>Physi<br>No</td>
		<td>Physi<br>No2</td>
		<td>도메인</td>
		<td>Data Type</td>
		<td>Comment</td>
		<td>Definition</td>
		<td>Valid</td>
		<td>Default</td>
		<td>UDP 논리</td>                       
		<td>UDP 물리</td>                       
	</tr>

<%
if(list != null){
	for(int i=0; i<list.size(); i++){
		if(i+1 > 500) break;
		Map map = (Map)list.get(i);
		StringBuffer udp_list = new StringBuffer();
%>
		<tr>
			<td><%= i+1 %></td><!-- attribute_id : <%= map.get("attribute_id")%> -->
			<td>
				<input type=text style="font-size:11" size="12" readOnly  value="<%= map.get("subjectArea_name") %>">
			</td>
			<td><input type=text value="<%= map.get("entity_name"				)%>" style="font-size:11" size="10" readOnly ></td>
			<td><input type=text value="<%= map.get("entity_name_physical"		)%>" style="font-size:11" size="10" readOnly ></td>
			<td><input type=text value="<%= map.get("attribute_name"			)%>" style="font-size:11" size="20" readOnly ></td>
			<td><input type=text value="<%= map.get("attribute_name_physical"	)%>" style="font-size:11" size="20" readOnly ></td>

			<td align="center"><%= map.get("attribute_key_YN")%></td>
			<td align="center"><%= map.get("attribute_key_fk_YN")%></td>
			<td align="center"><%= map.get("attribute_null_YN")%></td>
			<td align="center"><%= map.get("attribute_logical_YN")%></td>
			<td align="center"><%= map.get("attribute_physical_YN")%></td>
			<td align="center"><%= map.get("attribute_order_logical")%></td>
			<td align="center"><%= map.get("attribute_order_logical2")%></td>
			<td align="center"><%= map.get("attribute_order_physical")%></td>
			<td align="center"><%= map.get("attribute_order_physical2")%></td>
			<td><input type=text style="font-size:11" value="<%= map.get("attribute_domain_name")%>" readOnly size=15></td>
			<td><input type=text style="font-size:11" value="<%= (map.get("attribute_data_type_physical") != null ) ? map.get("attribute_data_type_physical") : "" %>" readOnly size=15></td>
			<td><input type=text style="font-size:11" value="<%= (map.get("attribute_comment") != null ) ? map.get("attribute_comment") : "" %>" readOnly size=5></td>
			<td><input type=text style="font-size:11" value="<%= (map.get("attribute_definition") != null ) ? map.get("attribute_definition") : "" %>" readOnly size=5></td>
			<td><input type=text style="font-size:11" value="<%= (map.get("attribute_valid_value") != null ) ? map.get("attribute_valid_value") : "" %>" readOnly size=5></td>
			<td><input type=text style="font-size:11" value="<%= (map.get("attribute_default_value") != null ) ? map.get("attribute_default_value") : "" %>" readOnly size=5></td>

			<td>
				<% if( map.get("udp_list_Attribute.Logical") == null){ %>
					<input type=text style="font-size:11" size="50" readOnly value="<%= map.get("udp_Attribute.Logical_value") %>">	
				<% } else {
					List list2 =  (List)map.get("udp_list_Attribute.Logical");
					
					for(int j=0; j<list2.size(); j++){
						c info = (c)(list2.get(j));
						udp_list.append(info.b() +"-"+info.c()+":");
					}
				}%>
				<input type=text style="font-size:11" size="50" readOnly value="<%=udp_list.toString()%>">	
							
			</td>
			<td>
				<%  udp_list = new StringBuffer(); %>
				<% if( map.get("udp_list_Attribute.Physical") == null){ %>
					<input type=text style="font-size:11" size="50" readOnly value="<%= map.get("udp_Attribute.Physical_value") %>">	
				<% } else {
					List list2 =  (List)map.get("udp_list_Attribute.Physical");
					
					for(int j=0; j<list2.size(); j++){
						c info = (c)(list2.get(j));
						udp_list.append(info.b() +"-"+info.c()+":");
					}
				}%>
				<input type=text style="font-size:11" size="50" readOnly value="<%=udp_list.toString()%>">	
			</td>

		</tr>
<%
	}
}
%>

</table>
<br><br>
<%
int page_cnt = list.size() / 500;
for(int z = 0 ; z < page_cnt ; z++){
%>
<br><br><br><br>
<table>
<tr>
<td>
<textarea cols="200" rows="80">
<%

	out.println("no$카테고리$모델$주제영역$엔티티$table$Owner$no$속성$column$PK YN$FK YN$Null YN$도메인$Data Type$Comment$Definition$Valid$DEFAULT$UDP");
	for(int i= 500 * (z+1); i< list.size(); i++){

if(i+1 > 500 * (z+1) + 500) break;

		Map map = (Map)list.get(i);

		out.print("@#"+(i+1));
		out.print("$"+map.get("category_name"));
		out.print("$"+map.get("model_name"));
		out.print("$"+map.get("subjectArea_name"));
		out.print("$"+map.get("entity_name"));
		out.print("$"+map.get("entity_name_physical"));
		out.print("$"+map.get("entity_owner_name"));
		out.print("$"+map.get("attribute_order"));
		out.print("$"+map.get("attribute_name"));
		out.print("$"+map.get("attribute_data_type_physical"));
out.print("$"+map.get("attribute_key_YN"));
out.print("$"+map.get("attribute_key_fk_YN"));
out.print("$"+map.get("attribute_null_YN"));
out.print("$"+map.get("attribute_domain_name"));
out.print("$"+map.get("attribute_data_type_physical"));

		out.print("$"+map.get("attribute_comment"));
		out.print("$"+map.get("attribute_definition"));
		out.print("$"+map.get("attribute_valid_value"));
		out.print("$"+map.get("attribute_default_value"));


out.print("$"+map.get("udp_Attribute.Logical_value"));

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
</table>
<%
	long long3 = System.currentTimeMillis();
	out.println("출력 후, 소요시간 "+ ((long3 - long1) / 1000) );
%>