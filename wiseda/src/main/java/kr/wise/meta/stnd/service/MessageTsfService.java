/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageTsfService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 12. 02.
 * 6. 변경이력 :

 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;


public interface MessageTsfService{

	/** @param searchVo
	/** @return lsi */
    List<WamMsgTsf> getMessageTsfList(WamMsgTsf data);
    
    WamMsgTsf selectMessageTsfDetail(WamMsgTsf data);

    //메시지 변경이력 조회
    List<WamMsgTsf> selectMessageTsfChangeList(WamMsgTsf data);
}
