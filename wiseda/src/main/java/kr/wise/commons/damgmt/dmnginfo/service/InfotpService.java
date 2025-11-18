package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : InfotpService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:45:39
 * </PRE>
 */
public interface InfotpService {

	public List<WaaInfoType> getList(WaaInfoType search);

	public WaaInfoType findInfotp(WaaInfoType search);

	public int regInfotp(WaaInfoType record) throws Exception ;

	public int regInfotpList(ArrayList<WaaInfoType> list) throws Exception ;


	public int delInfotpList(ArrayList<WaaInfoType> list) ;

	/**  insomnia */
	public List<WaaInfoType> getInfoTypeList();

}
