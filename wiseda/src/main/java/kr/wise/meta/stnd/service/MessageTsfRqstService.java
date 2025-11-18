/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageTsfRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 12. 01 
 * 6. 변경이력 :
 *       
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;


public interface MessageTsfRqstService extends CommonRqstService {

     List<WaqMsgTsf> selectMsgListTsf(WaqMsgTsf searchVo);
     int regMessageTsf(WaqMstr reqmst, ArrayList<WaqMsgTsf> list) throws Exception;
     List<WaqMsgTsf> selectMsgTsfRqstList(WaqMstr reqmst);
     int delCodeTsfRqstList(WaqMstr reqmst, ArrayList<WaqMsgTsf> list) throws Exception;
     WaqMsgTsf getMsgTsfRqstDetail(WaqMsgTsf searchVo);
     List<WaqMsgTsf> selectMsgWamList(WaqMsgTsf searchVo);
     int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception ;         
     
}
