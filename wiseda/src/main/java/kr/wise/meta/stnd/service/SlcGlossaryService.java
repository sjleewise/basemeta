package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

public interface SlcGlossaryService {

	List<SlcGlossaryM> getSlcGlossaryList(SlcGlossaryM data) throws Exception;

	List<SlcGlossaryM> getSlcGlossaryChangeList(String stwdId) throws Exception;

	SlcGlossaryM getSlcGlossaryDetail(SlcGlossaryM searchVO) throws Exception;

	int regSlcGlossary(ArrayList<SlcGlossaryM> list) throws Exception;

	int delSlcGlossary(ArrayList<SlcGlossaryM> list) throws Exception;

	List getSlcGlossaryListXls(SlcGlossaryM data) throws Exception;

}
