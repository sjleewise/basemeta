/**
 * 0. Project  : NHIS DQMS 프로젝트
 *
 * 1. FileName : CtqService.java
 * 2. Package : kr.wise.dq.criinfo.ctq.service
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 19. 오후 7:58:00
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 19. :            : 최초작성
 */
package kr.wise.dq.criinfo.ctq.service;

import java.util.List;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CtqService.java
 * 2. Package : kr.wise.dq.criinfo.ctq.service
 * 3. Comment : 
 * 4. 작성자  : wisePooh
 * 5. 작성일  : 2014. 3. 19. 오후 7:58:00
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    wisePooh : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
public interface CtqService {
	public List<WamCtqVO> getCtqList(WamCtqVO search);
	
	public WamCtqVO getCtqDetail(String sCtqId);
	
	public List<WahCtqVO> getCtqHstLst(String sCtqId);

	public List<WamCtqWhereUsed> getCtqWhereusedLst(String ctqId);

}
