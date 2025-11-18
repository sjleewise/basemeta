package kr.wise.dq.dqrs.service;

import java.util.ArrayList;
import java.util.List;

public interface DqrsExpService {

	List<DqrsExpTbl> getExpTbl(DqrsExpTbl vo);

	int regExpTblLst(ArrayList<DqrsExpTbl> list);

	List<DqrsExpCol> getExpCol(DqrsExpCol search);

	int regExpColLst(ArrayList<DqrsExpCol> list);
}
