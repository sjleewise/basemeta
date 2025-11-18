/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysServiceImpl.java
 * 2. Package : kr.wise.meta.codecfcsys.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 7. 오후 4:33:42
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 7. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.codecfcsys.service.CodeCfcSysService;
import kr.wise.meta.codecfcsys.service.WamCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WamCodeCfcSysItemMapper;
import kr.wise.meta.codecfcsys.service.WamCodeCfcSysMapper;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItem;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysServiceImpl.java
 * 3. Package  : kr.wise.meta.codecfcsys.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 7. 오후 4:33:42
 * </PRE>
 */
@Service("CodeCfcSysService")
public class CodeCfcSysServiceImpl implements CodeCfcSysService {

	@Inject
	WamCodeCfcSysMapper wamCodeCfcSysMapper;
	
	@Inject
	WamCodeCfcSysItemMapper wamCodeCfcSysItemMapper;
	
	/** meta */
	@Override
	public List<WamCodeCfcSys> getCodeCfcSysList(WamCodeCfcSys search) {
		return wamCodeCfcSysMapper.selectList(search);
	}

	/** meta */
	@Override
	public List<WaqCodeCfcSysItem> getCodeCfcSysItemList(
			WaqCodeCfcSysItem search) {
		return wamCodeCfcSysItemMapper.selectItemList(search);
	}

	/** meta */
	@Override
	public WamCodeCfcSys getCodeCfcSysDetail(String codeCfcSysId) {
		return wamCodeCfcSysMapper.selectByPrimaryKey(codeCfcSysId);
	}

	/** meta */
	@Override
	public List<WaqCodeCfcSys> getCodeCfcSysHistList(WaqCodeCfcSys search) {
		return wamCodeCfcSysMapper.selectHistList(search);
	}

	/** meta */
	@Override
	public List<WaqCodeCfcSysItem> getCodeCfcSysItemHistList(
			WaqCodeCfcSysItem search) {
		return wamCodeCfcSysItemMapper.selectHistList(search);
	}

}
