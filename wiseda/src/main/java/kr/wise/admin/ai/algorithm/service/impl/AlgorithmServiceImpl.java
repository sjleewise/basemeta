/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : AlgorithmServiceImpl.java
 * 2. Package : kr.wise.admin.ai.algorithm.service.impl
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 23. 오후 3:39:04
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 23. :            : 신규 개발.
 */
package kr.wise.admin.ai.algorithm.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.admin.ai.algorithm.service.AlgorithmService;
import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArgMapper;
import kr.wise.admin.ai.algorithm.service.WaaAlgMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AlgorithmServiceImpl.java
 * 3. Package  : kr.wise.admin.ai.algorithm.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 23. 오후 3:39:04
 * </PRE>
 */
@Service("algorithmService")
public class AlgorithmServiceImpl implements AlgorithmService {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaAlgMapper mapper;
	
	@Inject
	private WaaAlgArgMapper argmapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService;

	/** insomnia */
	public List<WaaAlg> getAlgorithmList(WaaAlg search) {
		logger.debug("알고리즘 목록 조회:{}", search);
		
		return mapper.selectAlgorithmList(search);
	}

	/** insomnia 
	 * @throws Exception */
	public int regAlgorithm(List<WaaAlg> list) throws Exception {
		logger.debug("알고리즘 저장");
		int result = 0;
		LoginVO uservo = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = uservo.getUniqId();
		
		//알고리즘 목록을 저장정한다.
		for (WaaAlg savevo : list) {
			savevo.setWritUserId(userid);
			
			String tmpsts = savevo.getIbsStatus();
			if ("I".equals(tmpsts)) {
				//알고리즘 ID 채번한다...
				String objid = objectIdGnrService.getNextStringId();
				savevo.setAlgId(objid);
				savevo.setRegTypCd("C");
				
				result =+ mapper.insert(savevo);
				
			} else if ("U".equals(tmpsts)) {
				savevo.setRegTypCd("U");
				result =+ mapper.updateByPrimaryKey(savevo);
				
			} else if ("D".equals(tmpsts)) {
				//삭제시 알고리즘에 포함된 변수목록도 삭제한다.
				argmapper.deleteByalgId(savevo.getAlgId());
				
				savevo.setRegTypCd("D");
				result =+ mapper.deleteByPrimaryKey(savevo.getAlgId());
			}
					
		}
		
		
		return result;
	}

	/** insomnia */
	public int delAlgorithm(List<WaaAlg> list) {
		logger.debug("알고리즘 삭제");
		int result = 0;
		
		LoginVO uservo = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = uservo.getUniqId();
		
		for (WaaAlg savevo : list) {
			savevo.setWritUserId(userid);
			//삭제시 알고리즘에 포함된 변수목록도 삭제한다.
			argmapper.deleteByalgId(savevo.getAlgId());
			
			savevo.setRegTypCd("D");
			result =+ mapper.deleteByPrimaryKey(savevo.getAlgId());
		}
		
		return result;
	}

	/** insomnia */
	public List<WaaAlgArg> getAlgorithmParam(WaaAlg search) {
		logger.debug("알고리즘 파라미터 조회 by 알고리즘ID:{}", search.getAlgId());
		return argmapper.selectArgListbyId(search);
	}

	/** insomnia 
	 * @throws Exception */
	public int regAlgorithmParam(WaaAlg algvo, List<WaaAlgArg> list) throws Exception {
		logger.debug("알고리즘 파라미터 저장 by 알고리즘ID:{}", algvo.getAlgId());
		int result = 0 ;
		
		String algid = algvo.getAlgId();
		LoginVO uservo = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = uservo.getUniqId();
		
		for (WaaAlgArg savevo : list) {
			savevo.setWritUserId(userid);
			savevo.setAlgId(algid);
			String tmpsts = savevo.getIbsStatus();
			if ("I".equals(tmpsts)) {
				//파라미터 ID 채번한다...
				String objid = objectIdGnrService.getNextStringId();
				savevo.setAlgArgId(objid);
				savevo.setRegTypCd("C");
				
				result =+ argmapper.insert(savevo);
				
			} else if ("U".equals(tmpsts)) {
				savevo.setRegTypCd("U");
				result =+ argmapper.updateByPrimaryKey(savevo);
				
			} else if ("D".equals(tmpsts)) {
				
				result =+ argmapper.deleteByPrimaryKey(savevo.getAlgId(), savevo.getAlgArgId());
				
			}
			
		}
		
		return result;
	}

	/** insomnia */
	public int delAlgorithmParam(WaaAlg algvo, List<WaaAlgArg> list) {
		
		int result = 0;
				
		LoginVO uservo = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = uservo.getUniqId();
		
		for (WaaAlgArg savevo : list) {
			savevo.setWritUserId(userid); 			
			savevo.setRegTypCd("D");	
			
			result =+ argmapper.deleteByPrimaryKey(savevo.getAlgId(), savevo.getAlgArgId());
		}
		
		return result;
	}

}
