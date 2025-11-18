package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

public interface StndSymnTrmService {
	WamSymnTrm selectSymnDetail(String symnId);
	
	List<WamSymnTrm> selectSymnList(WamSymnTrm search);

	int saveSymnRow(WamSymnTrm saveVO) throws Exception;
	
	int deleteSymnList(ArrayList<WamSymnTrm> list) throws Exception;
	
	int saveSymnList(ArrayList<WamSymnTrm> list) throws Exception ;

	int deleteSymn(WamSymnTrm record);
	
	//동음이의어-----------------------------------------------------------
	WamSymnTrm selectHmnmDetail(String symnId);
	
	List<WamSymnTrm> selectHmnmList(WamSymnTrm search);

	int saveHmnmRow(WamSymnTrm saveVO) throws Exception;
	
	int deleteHmnmList(ArrayList<WamSymnTrm> list) throws Exception;
	
	int saveHmnmList(ArrayList<WamSymnTrm> list) throws Exception ;

	int deleteHmnm(WamSymnTrm record);
}
