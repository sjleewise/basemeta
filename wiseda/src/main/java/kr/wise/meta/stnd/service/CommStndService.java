package kr.wise.meta.stnd.service;

import java.util.HashMap;
import java.util.List;

public interface CommStndService {

	List<HashMap<String, String>> getCommSditmList(HashMap<String, String> search) throws Exception;

	List<HashMap<String, String>> getCommDmnList(HashMap<String, String> search) throws Exception;

	List<HashMap<String, String>> getCommStwdList(HashMap<String, String> search) throws Exception;

}
