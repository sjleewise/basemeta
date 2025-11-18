package kr.wise.meta.stnd.service;

import java.util.List;

public interface AsisVsStndDicService {

	List<AsisColItemMap> getAsisColvsItemList(AsisColItemMap data);

	List<AsisColItemMap> getAsisColvsItemListUseModel(AsisColItemMap data);

}
