<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>

--- 사용법 : object.jsp?c_id=123&o_id=456
<br></br>
<%

	String work			= request.getParameter("work"	);	

	List list		= null;
	Map param	 = new HashMap();

	String c_id				= request.getParameter("c_id"	);
	String o_id				= request.getParameter("o_id"	);
	String o_parentid			= request.getParameter("o_parentid"	);
	String o_type			= request.getParameter("o_type"	);
	String o_name_like		= request.getParameter("o_name_like"	);

	param.put("c_id"			, c_id		);
	param.put("o_id"			, o_id		);
	param.put("o_parentid"		, o_parentid		);
	param.put("o_type"			, o_type	);
	param.put("o_name_like"		, o_name_like	);
	
	out.println("--- 파라메타");
	out.println("<br>c_id : " +c_id);
	out.println("<br>o_id : " +o_id);
	//out.println("<br>p_id : " +p_id);
	//out.println("<br>p_tpye : " +p_tpye);
	//out.println("<br>o_name_like : " +o_name_like);

	try{
		if(c_id != null)
		list	= ERwin.getObjectList	(param	);
		out.println("<br>list size : "+list.size());
	}catch(Exception e){
		out.println("<br><br>에러 : " +e);
	}
	

%>
<br></br>
---- 결과 : 
<br></br>
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
			//if("1073742128".equals(key) || "1075851071".equals(key) || "1075851072".equals(key) || "1075851272".equals(key) || "1075851263".equals(key)  || "1075852262".equals(key) ) continue;
			sb.append("\n"+key);
			try{
				String str = value.toString();
				if(str.length() >0 ) {
					str = str.trim();
					if((int)str.charAt(0) == 0){
						sb.append("-******");
					}else{
						//if("udp_entity.logical_cnt".equals(key) || "udp_entity.physical_cnt".equals(key) || "udp_Attribute.Logical_cnt".equals(key) || "udp_Attribute.Physical_cnt".equals(key) )
							sb.append(": "+value);
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

