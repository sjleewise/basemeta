<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>

<br>
<%

	String work			= request.getParameter("work"	);	

	List list		= null;
	Map param	 = new HashMap();

	String model_id				= request.getParameter("model_id"	);
	String subjectArea_id		= request.getParameter("subjectArea_id"	);
	String entity_id			= request.getParameter("entity_id"	);
	String entity_name			= request.getParameter("entity_name"	);
	String viewLP				= request.getParameter("viewLP"	);
	String entity_name_physical	= request.getParameter("entity_name_physical"	);
	
	if(model_id == null){
		
	}else{
		param.put("model_id"		, model_id		);
		if(subjectArea_id 	!= null && !"0".equals(subjectArea_id	)) 	param.put("subjectArea_id"		, subjectArea_id	);
		if(entity_id 		!= null && !"0".equals(entity_id			)) 	param.put("entity_id"			, entity_id			);
		if(entity_name 		!= null && !"".equals(entity_name		)) 	param.put("entity_name"			, entity_name			);
		if(viewLP 			!= null && !"".equals(viewLP			)) 	param.put("viewLP"				, viewLP			);
		if(entity_name_physical != null && !"".equals(entity_name_physical	)) 	param.put("entity_name_physical", entity_name_physical	);
		
		try{
			
			try{
				out.println("entity_id : "+entity_id);
				
				Object[] keys = param.keySet().toArray();
				Arrays.sort(keys);
				for (int j = 0; j < keys.length; j++) {
					Object key = (String) keys[j];
					Object value = param.get(key);
					System.out.print(key);
					try {
						String str = value.toString();
						if (str.length() > 0) {
							if ((int) str.charAt(0) == 0) {
								System.out.print("-******");
							} else {
								System.out.print(" : " + str);
							}
						}

					} catch (Exception e) {
					}
					System.out.println();
				}
				
			}catch(Exception e){
				out.println(e);
			}
			
			list	= ERwin.getComboEntityList  	(param	);
			out.println("<br>list size : "+list.size());
		}catch(Exception e){
			out.println(e);
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

