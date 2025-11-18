package kr.wise.meta.govstnd.service;

import java.util.HashMap;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DiagDmnMapper {

	List<HashMap<String, String>> selectCommSditmList(HashMap<String, String> search);

	List<HashMap<String, String>> selectCommDmnList(HashMap<String, String> search);

	List<HashMap<String, String>> selectCommStwdList(HashMap<String, String> search);

}
