/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 9. 24. 오전 8:48:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 9. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:48:38
 * </PRE>
 */
public interface MsgService{

	/** @param searchVo
	/** @return lsi */
    List<WamMsg> getMessageList(WamMsg data);
    
    WamMsg selectMessageDetail(String msgId);

    //메시지 변경이력 조회
    List<WamMsg> selectMessageChangeList(String msgId);
}
