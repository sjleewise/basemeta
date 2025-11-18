package kr.wise.commons.code.service;

import java.util.List;

public interface CmcdCodeService {

	public void init();

	public List<CodeListVo> getCodeList(String codenm);

	public ComboIBSVo getCodeListIBS(String codenm);
	
	public String getDetailCodeNm(String commDcd, String commDtlCd);

}
