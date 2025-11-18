package kr.wise.advisor.prepare.udefoutlier.boxplot.service;

import java.util.List;

public interface UodcBoxplotService {

	WadUodcBoxplot getUodcBoxplotDetail(WadUodcBoxplot search);

	List<WadUodcBoxplotCol> getUodcBoxplotColList(WadUodcBoxplot search);

	int regUodcBoxplot(List<WadUodcBoxplotCol> list, WadUodcBoxplot mstData) throws Exception;

	List<WadUodcBoxplotCol> getUodcBoxplotColListForScrt(WadUodcBoxplot search);

}
