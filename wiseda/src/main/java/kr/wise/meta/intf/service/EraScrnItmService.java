package kr.wise.meta.intf.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : EraDataelmtService.java
 * 3. Package  : kr.wise.meta.intf.service
 * 4. Comment  :
 * 5. 작성자   : jys
 * 6. 작성일   : 2016. 5. 12. 오전 9:14:02
 * </PRE>
 */
public interface EraScrnItmService  {

	List<EraScrnItm> getEraScrnItmOne(String vcb, String langCode);
	
	List<EraScrnItm> getEraScrnItmList(List<EraScrnItm> data, String trdGb);

}
