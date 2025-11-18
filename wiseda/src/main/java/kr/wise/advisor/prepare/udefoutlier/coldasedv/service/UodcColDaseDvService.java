package kr.wise.advisor.prepare.udefoutlier.coldasedv.service;

import java.util.List;
import java.util.Map;



public interface UodcColDaseDvService {

	WadUodcColDaseDv getUodcColDaseDvDetail(WadUodcColDaseDv search);

	List<WadUodcColDaseDv> getUodcColDaseDvColList(WadUodcColDaseDv search);

	int regColDaseDvlist(List<WadUodcColDaseDvCol> list, WadUodcColDaseDv mstData) throws Exception;

	List<WadUodcColDaseDvCol> getUodcColDaseDvColLstForScrt(WadUodcColDaseDv search);       

	
}
