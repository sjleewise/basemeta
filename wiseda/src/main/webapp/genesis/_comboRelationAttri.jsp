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
			list	= ERwin.getComboRelationAttriList	(param	);
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

<%
if(list != null){
	for(int i=0; i<list.size(); i++){
		//if(i > 1000) break;
		Map map = (Map)list.get(i);
		
		StringBuffer sb = new StringBuffer();
		
		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);
		for (int j = 0; j < keys.length; j++) {
			Object key 		= (String)keys[j];
			Object value 	= map.get(key);
			if("1073742128".equals(key) || "1075851071".equals(key) || "1075851072".equals(key) || "1075851272".equals(key) || "1075851263".equals(key)  || "1075852262".equals(key) ) continue;
			sb.append("\n"+key);
			try{
				String str = value.toString();
				if(str.length() >0 ) {
					if((int)str.charAt(0) == 0){
						sb.append("-******");
					}else{
						//if("udp_entity.logical_cnt".equals(key) || "udp_entity.physical_cnt".equals(key) || "udp_Attribute.Logical_cnt".equals(key) || "udp_Attribute.Physical_cnt".equals(key) )
							sb.append(" : "+value);
					}
				}
			}catch(Exception e){
			}	
		}
%>		
<br>	
<textarea cols="100" rows="10"><%= sb.toString() %>
</textarea>
<%
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