package kr.wise.commons.code.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;

public interface CodeListService {

	public String getCodeListJson (String codenm);

	public List<CodeListVo> getCodeList(String codenm);

	public List<CodeListVo> getComCodeList(String type, String codenm);

	public ComboIBSVo getCodeListIBS(String codenm);
	
	public ComboIBSVo getCodeListIBS(List<CodeListVo> codeList);

	/** @param comDcd
	/** @return insomnia */
	public List<CodeListVo> getCodeList(CodeListAction codelistnm);

	/** @param comDcd
	/** @return insomnia */
	public ComboIBSVo getCodeListIBS(CodeListAction codelistnm);


	public List<ComboIBSVo> getApproveListIBS(ArrayList<WaaRqstAprp> approveList) ;

	/** @param list
	/** @return insomnia */
	public Map<String, ComboIBSVo> getDbmsDataTypIBS(List<CodeListVo> list);

	Map<String, ComboIBSVo> getDmngByStndAsrtIBS(List<CodeListVo> list);

	public List<CodeListVo> getAuthOrgCdList(LoginVO user);

	public List<CodeListVo> getInfoSysCdEachList(String ssOrgCd); 


}
