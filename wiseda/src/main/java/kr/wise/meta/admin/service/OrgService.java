package kr.wise.meta.admin.service;

import java.util.List;

public interface OrgService {
	
	public List<WaaOrg> getOrgList(WaaOrg search);
	
	public int regOrgList(List<WaaOrg> list) throws Exception;
	
	public int delOrgList(List<WaaOrg> list) throws Exception;
	
	public int regOrg(WaaOrg record) throws Exception;
}
