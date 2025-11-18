package kr.wise.meta.ddl.service;

import java.util.List;



public interface DdlPartService {
	WaqDdlPart getDdlPartDtlInfo(String ddlPartId, String rqstNo);
	
	List<WaqDdlPart> getDdlPartLst(WaqDdlPart searchVO);
	
	List<WaqDdlPart> getDdlPartHistLst(WaqDdlPart searchVO);
	
	List<WaqDdlPartMain> getDdlPartMainList(WaqDdlPart search);

	List<WaqDdlPartSub> getDdlPartSubList(WaqDdlPart search);
	
}
