package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.WaqMstr;


public interface AppPrgmItemService {

	List<WamAppPrgm> getPrgmItemList(WamAppPrgm data);

	WamAppPrgm selectAppPrgmDetail(String appPrgmId);

	List<WamAppPrgm> selectAppPrgmChangeList(String appPrgmId);

	WaqAppPrgm getAppPrgmRqstDetail(WaqAppPrgm searchVo);
	
	int check(WaqMstr reqmst);

	int register(WaqMstr mstVo, List<WaqAppPrgm> reglist) throws Exception;

	List<WaqAppPrgm> getAppPrgmItemRqstList(WaqMstr search);

	boolean checkEmptyRqst(WaqMstr reqmst);

	int delAppPrgmItemRqst(WaqMstr reqmst, ArrayList<WaqAppPrgm> list) throws Exception;

	int approve(WaqMstr reqmst, ArrayList<WaqAppPrgm> list) throws Exception;

	int regWam2Waq(@Param("reqmst")WaqMstr reqmst, @Param("list") ArrayList<WaqAppPrgm> list) throws Exception;

}
