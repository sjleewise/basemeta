package kr.wise.commons.damgmt.approve.service;

import java.util.ArrayList;
import java.util.List;

public interface ApproveUserService {

	public List<WaaAprgUser> getList(WaaAprgUser search) ;

	public List<WaaAprgUser> getListById(String aprgId) ;
	
	public List<WaaAprgUser> getListById(String aprgId, String userNm) ;
	
	public WaaAprg findAprg(WaaAprgUser search) ;

	public int regAprgUser(WaaAprgUser record, String aprgId) throws Exception ;

	public int regList(ArrayList<WaaAprgUser> list, String aprgId) throws Exception ;


	public int delList(ArrayList<WaaAprgUser> list) throws Exception ;

}
