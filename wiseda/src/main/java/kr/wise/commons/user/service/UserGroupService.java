package kr.wise.commons.user.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.user.WaaUserg;

public interface UserGroupService {
	
	public List<WaaUserg> getUsergList(WaaUserg search) ;
	
	public WaaUserg findUserg(WaaUserg search) ;
	
	public int regUserg(WaaUserg record) throws Exception ;

	public int regUsergList(ArrayList<WaaUserg> list) throws Exception ;


	public int delUsergList(ArrayList<WaaUserg> list) throws Exception ;

	public int getUsergCnt(WaaUserg waaUserg);

}
