/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeTsfService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 27. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 11. 27. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;



public interface CodeTsfService {

     List<WamCdValTsf> selectCodeTsfWamList(WamCdValTsf searchVo);
     
     WamCdValTsf selectCodeTsfWamDetail(WamCdValTsf searchVo);   
     
     List<WamCdValTsf> selectCodeTsfWamChg(WamCdValTsf searchVo);
     
}
