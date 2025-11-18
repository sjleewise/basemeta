<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>

<%!
	public String printTab(String str, int tabcnt){
		int temp = str.length() / 4;
		int temp2 = str.length() % 4;
		StringBuffer sb_tab = new StringBuffer();
		
		for(int i=0; i < 4 - temp2 ; i++){
			sb_tab.append(" ");
		}
		for(int i=0; i < tabcnt - temp ; i++){
			sb_tab.append("\t");
		}
		
		return sb_tab.toString();
}

%>

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
		if(entity_id 		!= null && !"0".equals(entity_id		)) 	param.put("entity_id"			, entity_id			);
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
		
		List attri_list	= ERwin.getComboList2  	(param	);
		
		StringBuffer sb_tera = new StringBuffer();
		
		sb_tera.append("\nDROP TABLE " + map.get("entity_name_physical") +";");
		sb_tera.append("\n");
		sb_tera.append("\nCREATE TABLE " + map.get("entity_name_physical") );
		sb_tera.append("\n(");
		
		for(int j=0; j < attri_list.size(); j++){
			Map attri_map = (Map)attri_list.get(j);
			sb_tera.append("\n\t" + attri_map.get("attribute_name_physical"));
			sb_tera.append(printTab((String)attri_map.get("attribute_name_physical"), 5));
			
			if(attri_map.get("attribute_data_type_physical").toString().startsWith("VARCHAR2")){
				sb_tera.append(attri_map.get("attribute_data_type_physical").toString().replace("VARCHAR2", "VARCHAR"));
				sb_tera.append(printTab("VARCHAR", 2));
				
			}else if(attri_map.get("attribute_data_type_physical").toString().startsWith("CLOB")){
				sb_tera.append(attri_map.get("attribute_data_type_physical").toString().replace("CLOB", "VARCHAR(9999)"));
				sb_tera.append(printTab("VARCHAR(9999)", 2));
				
			}else{
				sb_tera.append(attri_map.get("attribute_data_type_physical"));
				sb_tera.append(printTab((String)attri_map.get("attribute_data_type_physical"), 2));
			}
			
			
			if("Y".equals(attri_map.get("attribute_null_YN"))){
				sb_tera.append("NULL\t\t");
			}else{
				sb_tera.append("NOT NULL\t");
			}
			
			sb_tera.append("TITLE '");
			sb_tera.append(attri_map.get("attribute_name"));
			sb_tera.append("'");
			
			if(attri_map.get("attribute_default_value") != null ){
				String attri_type = ((String)attri_map.get("attribute_data_type_physical")).toUpperCase();
				if(attri_type.startsWith("NUMBER")
					|| attri_type.startsWith("INTEGER")
					|| attri_type.startsWith("LONG")
					|| attri_type.startsWith("DATE")
				){
					if(!"".equals(attri_map.get("attribute_default_value"))){
						sb_tera.append("\tDEFAULT\t");
						sb_tera.append(attri_map.get("attribute_default_value"));
					}
				}else{
					sb_tera.append("\tDEFAULT\t");
					if("".equals(attri_map.get("attribute_default_value"))){
						sb_tera.append("''");
					}else{
						sb_tera.append("'" + attri_map.get("attribute_default_value") +"'");
					}
					
				}
			}
			
			if(attri_list.size()-1 != j) sb_tera.append(",");
		}
		sb_tera.append("\n);");
		sb_tera.append("\n");
		if( map.get("entity_comment") != null){
			sb_tera.append("\nCOMMENT ON TABLE " +map.get("entity_name_physical") +" IS '"+ map.get("entity_comment")  +"';");
		}
		
		
		for(int j=0; j < attri_list.size(); j++){
			Map attri_map = (Map)attri_list.get(j);
			if( attri_map.get("attribute_comment") != null){
				
				sb_tera.append("\nCOMMENT ON COLUMN " +map.get("entity_name_physical") +"."+attri_map.get("attribute_name_physical") +" IS '"+ attri_map.get("attribute_comment")  +"';");
			}
		}
		
		Map index_param = new HashMap();
		index_param.put("model_id", map.get("model_id").toString());
		index_param.put("entity_id", map.get("entity_id").toString());
		
		List index_list	= ERwin.getComboIndexList  	(index_param	);
		
		String pre_index_name = "";
		String pre_index_colums = "";
		for(int j=0; j < index_list.size(); j++){
			Map index_map = (Map)index_list.get(j);
			
			if(!pre_index_name.equals(index_map.get("index_name_physical"))){
				
				if(!pre_index_colums.equals("")){
					pre_index_colums = pre_index_colums.substring(0, pre_index_colums.length()-2);
					sb_tera.append("\n");
					sb_tera.append("\nCREATE UNIQUE INDEX " + pre_index_name);
					sb_tera.append("\n(");
					sb_tera.append("\t" +pre_index_colums);
					sb_tera.append(map.get("entity_name_physical") );
					sb_tera.append("\n) ON " + map.get("entity_name_physical").toString() +";");
					pre_index_colums = "";
					
					
				}
				pre_index_name = (String)index_map.get("index_name_physical");
			
			}
			pre_index_colums =  pre_index_colums + "\n\t" + index_map.get("attribute_name_physical") + ",";
			
			if(j == index_list.size() -1 ){
				sb_tera.append("\n");
				sb_tera.append("\nCREATE UNIQUE INDEX " + pre_index_name);
				sb_tera.append("\n(");
				sb_tera.append("\t" +pre_index_colums);
				sb_tera.append(map.get("entity_name_physical") );
				sb_tera.append("\n) ON " + map.get("entity_name_physical").toString() +";");
				pre_index_colums = "";				
			}
		
		}
		
		
		
%>		
<br>	
<textarea cols="800" rows="100"><%= sb_tera.toString() %>
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

