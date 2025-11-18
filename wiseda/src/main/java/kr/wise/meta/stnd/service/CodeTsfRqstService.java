/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeTsfRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 25. 오전 8:48:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 11. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;


public interface CodeTsfRqstService extends CommonRqstService {

     List<WamCdValTsf> selectDmnValueList(String dmnLnm);
          
     List<WamCdValTsf> selectDmnListTsf(WamCdValTsf searchVo);
     
     int regCodeTsf(WaqMstr reqmst, ArrayList<WaqCdValTsf> list) throws Exception;
     
     List<WaqCdValTsf> selectDmnValueRqstList(WaqMstr reqmst);
     
     int delCodeTsfRqstList(WaqMstr reqmst, ArrayList<WaqCdValTsf> list) throws Exception;
     
     WaqCdValTsf getCodeTsfRqstDetail(WaqCdValTsf searchVo);

     List<WamCdValTsf> selectCdTsfWamList(WamCdValTsf searchVo);
     
     int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception ;
     
}
